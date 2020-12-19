//package com.none.sharding.infrastruc.shardingAlgorithm;
//
//import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
//import io.shardingsphere.api.algorithm.sharding.ShardingValue;
//import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
//import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
//import io.shardingsphere.core.routing.strategy.complex.ComplexShardingStrategy;
//import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
//import org.springframework.util.CollectionUtils;
//
//import java.util.Collection;
//import java.util.Objects;
//
///**
// * @Author wyn
// * @Date 2020/11/9
// * @Description TODO
// */
//public class ComplexShardingAlgorithm
//        implements ComplexKeysShardingAlgorithm {
//    @Override
//    public Collection<String> doSharding(Collection<String> collection, Collection<ShardingValue> ShardingValueCollection) {
//
//        System.out.println("--------------collection" + collection);
//        System.out.println("--------------ShardingValueCollection" + ShardingValueCollection);
////        StringBuilder tableAftprefix = null;
////        for (ShardingValue shardingValue : ShardingValueCollection) {
////            if (Objects.equals(shardingValue.getColumnName(), "date")) {
////                tableAftprefix.append(shardingValue);
////            }
////        }
////
////        for (String availableTableName : collection) {
////            if (availableTableName.endsWith(String.valueOf(tableAftprefix)) {
////                return availableTableName;
////            }
////        }
//        return null;
//    }
//}
