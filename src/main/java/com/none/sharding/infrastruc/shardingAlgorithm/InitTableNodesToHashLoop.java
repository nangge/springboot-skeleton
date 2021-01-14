package com.none.sharding.infrastruc.shardingAlgorithm;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.underlying.common.rule.DataNode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * @Author wyn
 * @Date 2020/12/17
 * @Description sharding-jdbc    初始化表结点，并映射到hash环
 */
@Component
@Slf4j
public class InitTableNodesToHashLoop {

    @Resource
    private ShardingDataSource shardingDataSource;

    @Getter
    private HashMap<String, SortedMap<Long, String>> tableVirtualNodes = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            ShardingRule rule = shardingDataSource.getRuntimeContext().getRule();
            Collection<TableRule> tableRules = rule.getTableRules();
            ConsistentHashAlgorithm consistentHashAlgorithm = new ConsistentHashAlgorithm();
            for (TableRule tableRule : tableRules) {
                String logicTable = tableRule.getLogicTable();

                tableVirtualNodes.put(logicTable,
                        consistentHashAlgorithm.initNodesToHashLoop(
                                tableRule.getActualDataNodes()
                                        .stream()
                                        .map(DataNode::getTableName)
                                        .collect(Collectors.toList()))
                );
            }
        } catch (Exception e) {
            log.error("分表节点初始化失败 {}", e);
        }
    }
}
