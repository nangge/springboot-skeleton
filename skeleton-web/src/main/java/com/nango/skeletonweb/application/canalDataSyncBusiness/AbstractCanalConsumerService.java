package com.nango.skeletonweb.application.canalDataSyncBusiness;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.nango.skeletonweb.domain.canal.CanalBusinessEntityContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author nango
 * @package com.nango.skeletonweb.application.canalDataSyncBusiness
 * @class AbstractCanalConsumerService
 * @date 2022/5/17 16:38
 * @description canal 消费接口
 */
@Slf4j
public abstract class AbstractCanalConsumerService {
    /**
     * 获取同步表名称
     * @return
     */
    public abstract String getTableName();

    /**
     * 获取新表名称
     * @return
     */
    public abstract String getTableNewName();

    /**
     * 执行sql
     * 1，同步处理直接执行
     * 2，或异步推mq再消费处理
     * @param productEntityContextList
     */
    public abstract void execSql(List<CanalBusinessEntityContext> productEntityContextList);

    /**
     * 处理canal dump日志
     *
     * @param productEntityContextList
     */
    public List<String> dealDumpLog(List<CanalBusinessEntityContext> productEntityContextList) {
        List<String> sqlList = new ArrayList<>();
        productEntityContextList.forEach(cxt -> {
            if (!Objects.equals(cxt.getTableName(), getTableName())) {
                return;
            }

            switch (cxt.getEventType()) {
                case INSERT:
                    sqlList.addAll(cxt.getColumnsList().stream().map(this::buildInsertSql).collect(Collectors.toList()));
                    break;
                case DELETE:
                    sqlList.addAll(cxt.getColumnsList().stream().map(this::buildDeleteSql).collect(Collectors.toList()));
                    break;
                case UPDATE:
                    sqlList.addAll(cxt.getColumnsList().stream().map(this::buildUpdateSql).collect(Collectors.toList()));
                    break;
                default:
                    log.error("未知事件类型");
            }
        });

        return sqlList;
    }

    /**
     * 组装插入语句
     *
     * @param proColumnsList
     */
    public String buildInsertSql(List<CanalEntry.Column> proColumnsList) {
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(getTableNewName()).append(" (");

        Map<String, String> colValMap = proColumnsList.stream().collect(HashMap::new,
                (m, v) -> m.put(v.getName(), this.handleCol(v.getValue())), HashMap::putAll);
        sb.append(StringUtils.join(colValMap.keySet(), ","))
                .append(") ")
                .append("values(")
                .append(StringUtils.join(colValMap.values(), ","))
                .append(")");
        return sb.toString();
    }

    /**
     * 组装更新语句
     *
     * @param proColumnsList
     */
    public String buildUpdateSql(List<CanalEntry.Column> proColumnsList) {
        CanalEntry.Column pkCol = proColumnsList.stream().filter(CanalEntry.Column::getIsKey).findFirst().get();
        if (this.isExist(pkCol.getValue())) {
            //更新
            StringBuilder sb = new StringBuilder("UPDATE ");
            sb.append(getTableNewName()).append(" SET ");

            for (CanalEntry.Column c : proColumnsList) {
                if (!c.getIsKey()) {
                    sb.append(c.getName())
                            .append("=")
                            .append(this.handleCol(c.getValue()))
                            .append(",");
                }
            }

            sb.deleteCharAt(sb.length() - 1);
            sb.append(" WHERE ")
                    .append(pkCol.getName())
                    .append("=")
                    .append(this.handleCol(pkCol.getValue()));
            return sb.toString();
        }
        //新增
        return this.buildInsertSql(proColumnsList);
    }

    /**
     * 组装删除语句
     *
     * @param proColumnsList
     */
    public String buildDeleteSql(List<CanalEntry.Column> proColumnsList) {
        CanalEntry.Column pkCol = proColumnsList.stream().filter(CanalEntry.Column::getIsKey).findFirst().get();
        return "DELETE FROM " + getTableNewName() + " WHERE " + pkCol.getName() + "=" + this.handleCol(pkCol.getValue());
    }

    /**
     * 记录是否已存在
     *
     * @param id
     * @return
     */
    public boolean isExist(String id) {
        return Boolean.TRUE;
    }


    /**
     * 处理sql字段
     *
     * @param value
     * @return
     */
    public String handleCol(String value) {
        return "\'" + value + "\'";
    }
}
