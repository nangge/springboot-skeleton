package com.none.sharding.infrastruc.shardingAlgorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * @Author wyn
 * @Date 2020/11/9
 * @Description TODO
 */
public class UserShardingAlgorithm implements PreciseShardingAlgorithm<Long>, RangeShardingAlgorithm<Long> {
    /**
     * Sharding.
     *
     * @param collection available data sources or tables's names
     * @param preciseShardingValue        sharding value
     * @return sharding result for data source or table's name
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        System.out.println("--------------preciseShardingValue" + preciseShardingValue.getColumnName());
        System.out.println("--------------collection" + collection);

        for (String availableTableName : collection) {
            if (availableTableName.endsWith(String.valueOf(preciseShardingValue.getValue()%2))) {
                return availableTableName;
            }
        }
        return null;
    }

    /**
     * Sharding.
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding results for data sources or tables's names
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        return availableTargetNames;
    }

    public static void main(String[] args) {
        "subject123".endsWith("23");
    }
}
