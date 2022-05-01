package com.nango.skeletonweb.infrastructure.shardingAlgorithm;

import com.nango.skeletonweb.infrastructure.utils.SpringContextUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;

/**
 * @Author wyn
 * @Date 2020/12/16
 * @Description 分表策略
 */
public class ConsistentShardingAlgorithm
        implements PreciseShardingAlgorithm<Long>, RangeShardingAlgorithm<Long> {

    /**
     * 精确分片
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        skeletonweb value
     * @return skeletonweb result for data source or table's name
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {

        InitTableNodesToHashLoop initTableNodesToHashLoop =
                SpringContextUtils.getBean(InitTableNodesToHashLoop.class);
        if (CollectionUtils.isEmpty(availableTargetNames)) {
            return shardingValue.getLogicTableName();
        }

        ArrayList<String> availableTargetNameList = new ArrayList<>(availableTargetNames);
        String logicTableName = availableTargetNameList.get(0).replaceAll("[^(a-zA-Z_)]", "");
        SortedMap<Long, String> tableHashNode =
                initTableNodesToHashLoop.getTableVirtualNodes().get(logicTableName);

        ConsistentHashAlgorithm consistentHashAlgorithm = new ConsistentHashAlgorithm(tableHashNode,
                availableTargetNames);

        return consistentHashAlgorithm.getTableNode(String.valueOf(shardingValue.getValue()));
    }

    /**
     * 范围查询规则
     * Sharding.
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        skeletonweb value
     * @return skeletonweb results for data sources or tables's names
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        return availableTargetNames;
    }
}
