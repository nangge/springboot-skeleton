package com.none.sharding.infrastruc.shardingAlgorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * @Author nango
 * @Date 2020/12/19
 * @Description 复合分片规则
 */
public class ComplexShardingAlgorithm
        implements ComplexKeysShardingAlgorithm {
    /**
     * 复合分片规则
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding results for data sources or tables's names
     */
    @Override
    public Collection<String> doSharding(Collection availableTargetNames, ComplexKeysShardingValue shardingValue) {
        System.out.println("--------------collection" + availableTargetNames);
        System.out.println("--------------ShardingValueCollection" + shardingValue);

        //范围分片
        Map<String, Range<String>> rangeAndShardingValuesMap =
                shardingValue.getColumnNameAndRangeValuesMap();
        HashSet<Integer> yearSet = new HashSet<>();
        HashSet<Long> userIdSet = new HashSet<>();
        //时间作为范围查询
        if (rangeAndShardingValuesMap.containsKey("create_time")) {
            Range<String> createTimeRange = rangeAndShardingValuesMap.get("create_time");
            String lowerTime = "2020-01-01 00:00:00";
            if (createTimeRange.hasLowerBound()) {
                lowerTime = createTimeRange.lowerEndpoint();
            }
            yearSet.add(LocalDateTime.parse(String.valueOf(lowerTime),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).getYear());

            String upperTime = "2021-01-01 00:00:00";
            if (createTimeRange.hasUpperBound()) {
                upperTime = createTimeRange.upperEndpoint();
            }

            yearSet.add(LocalDateTime.parse(String.valueOf(upperTime),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).getYear());

        } else {
            //如果查询条件没有createTimed则执行全路由
            yearSet.add(2020);
            yearSet.add(2021);
        }

        //精确分片
        Map<String, Collection<Long>> columnNameAndShardingValuesMap =
                shardingValue.getColumnNameAndShardingValuesMap();
        //user_id作为精确分片
        if (columnNameAndShardingValuesMap.containsKey("user_id")) {
            for (Object value : columnNameAndShardingValuesMap.get("user_id")) {
                userIdSet.add((long) value % 3);
            }
        } else {
            //如果查询条件没有userId则执行全路由
            userIdSet.add(0L);
            userIdSet.add(1L);
            userIdSet.add(2L);
        }

        ArrayList<String> actualTables = new ArrayList<>();
        for (Integer year : yearSet) {
            for (Long uidMod : userIdSet) {
                actualTables.add(shardingValue.getLogicTableName() + year + uidMod);
            }
        }

        return actualTables;
    }
}
