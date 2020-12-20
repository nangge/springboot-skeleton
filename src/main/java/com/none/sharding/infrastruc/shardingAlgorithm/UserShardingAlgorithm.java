package com.none.sharding.infrastruc.shardingAlgorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * @Author nango
 * @Date 2020/12/19
 * @Description 精确分表算法
 */
public class UserShardingAlgorithm implements PreciseShardingAlgorithm<Long>, RangeShardingAlgorithm<Long> {
    /**
     * 精确分片算法
     * 这里我们可以实现自己的分片算法
     * @param collection 实际分表的节点 比如t_user0\t_user1
     * @param preciseShardingValue    分片值对象，包括逻辑表名即原表名t_user; 分片键、分片键的值
     * @return 返回实际表名即t_user0
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        System.out.println("--------------preciseShardingValue" + preciseShardingValue.getColumnName());
        System.out.println("--------------collection" + collection);
        return preciseShardingValue.getLogicTableName() + String.valueOf(preciseShardingValue.getValue()%2);
    }

    /**
     * 范围分片算法
     * 默认将使用全路由即表节点有几个就会去查几张表，然后对表结果进行聚合返回，性能较差
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding results for data sources or tables's names
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        return availableTargetNames;
    }
}
