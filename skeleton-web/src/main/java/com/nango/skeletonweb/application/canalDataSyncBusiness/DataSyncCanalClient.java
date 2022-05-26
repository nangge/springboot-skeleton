package com.nango.skeletonweb.application.canalDataSyncBusiness;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;
import com.nango.skeletonweb.domain.canal.CanalBusinessEntityContext;
import org.apache.commons.lang.StringUtils;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nango
 * @package com.nango.skeletonweb.application
 * @class DataSyncCanalClient
 * @date 2022/5/3 21:58
 * @description canalDataSyncBusiness   数据同步
 */
public class DataSyncCanalClient {

    public static void main(String args[]) {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",
                11111), "product", "", "");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmptyCount = 120;
            while (emptyCount < totalEmptyCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    dealEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private static ArrayList<CanalBusinessEntityContext> dealEntry(List<Entry> entrys) {
        ArrayList<CanalBusinessEntityContext> canalBusinessEntityContextList = new ArrayList<>(entrys.size());
        for (Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            ArrayList<List<Column>> columnsList = new ArrayList<>();

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    columnsList.add(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    columnsList.add(rowData.getAfterColumnsList());
                } else {
                    dealColumn(rowData.getAfterColumnsList());
                    columnsList.add(rowData.getAfterColumnsList());
                }
            }

            CanalBusinessEntityContext canalBusinessEntityContext = new CanalBusinessEntityContext();
            canalBusinessEntityContext.setEventType(eventType)
                    .setSchemaName(entry.getHeader().getSchemaName())
                    .setTableName(entry.getHeader().getTableName())
                    .setColumnsList(columnsList);
            canalBusinessEntityContextList.add(canalBusinessEntityContext);
        }
        return canalBusinessEntityContextList;
    }

    private static void dealColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }

        System.out.println("拼装插入 sql " + buildInsertSql(columns));
        System.out.println("拼装更新 sql " + buildUpdateSql(columns));
    }

    public static String buildInsertSql(List<Column> proColumnsList) {
        StringBuilder sb = new StringBuilder("insert into ");
        sb.append("t_product").append(" (");

        Map<String, String> colValMap = proColumnsList.stream().collect(HashMap::new,
                (m, v) -> m.put(handleCol(v.getName()), handleCol(v.getValue())),
                HashMap::putAll);
        sb.append(StringUtils.join(colValMap.keySet(), ","))
                .append(") ")
                .append("values(")
                .append(StringUtils.join(colValMap.values(), ","))
                .append(");");
        return sb.toString();
    }

    /**
     * 组装更新语句
     *
     * @param proColumnsList
     */
    public static String buildUpdateSql(List<Column> proColumnsList) {
        Column pkCol = proColumnsList.stream().filter(Column::getIsKey).findFirst().get();
        //更新
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append("t_product").append(" SET ");

        for (Column c : proColumnsList) {
            if (!c.getIsKey()) {
                sb.append(c.getName())
                        .append("=")
                        .append(handleCol(c.getValue()))
                        .append(",");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE ")
                .append(pkCol.getName())
                .append("=")
                .append(handleCol(pkCol.getValue()));
        return sb.toString();
    }

    /**
     * 处理sql字段
     *
     * @param value
     * @return
     */
    private static String handleCol(String value) {
        return "\"" + value + "\"";
    }
}
