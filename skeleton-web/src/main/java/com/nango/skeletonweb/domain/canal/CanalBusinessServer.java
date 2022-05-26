package com.nango.skeletonweb.domain.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;
import com.nango.skeletonweb.infrastructure.canal.CanalClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author nango
 * @package com.nango.skeletonweb.application
 * @class DataSyncCanalClient
 * @date 2022/5/3 21:58
 * @description canalDataSyncBusiness   数据同步
 */
@Component
@Slf4j
public class CanalBusinessServer {
    @Resource
    private CanalClient canalClient;

    /**
     * 从canal中拉取数据
     */
    public void dumpBinlog(CanalConnector connector,
                           Consumer<List<CanalBusinessEntityContext>> consumer) {
        int batchSize = 1000;
        int emptyCnt = 0;
        int maxTryCnt = 60;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();

            while (emptyCnt < maxTryCnt) {
                // 获取指定数量的数据
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                try {
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        emptyCnt++;
                        log.info("=====================睡眠1s=====================" + emptyCnt);
                        connector.ack(batchId);
                        //睡眠1s，继续尝试
                        Thread.sleep(1000);
                        continue;
                    }

                    consumer.accept(dealEntry(message.getEntries()));
                    connector.ack(batchId);
                    emptyCnt=0;
                } catch (Exception e) {
                    log.error("处理失败 {}", e);
                    connector.rollback(batchId);
                }
            }

        } finally {
            log.info("=====================断开链接=====================");
            canalClient.destroy();
        }
    }


    private static ArrayList<CanalBusinessEntityContext> dealEntry(List<Entry> entrys) {
        ArrayList<CanalBusinessEntityContext> canalBusinessEntityContextList = new ArrayList<>(entrys.size());
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
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
}
