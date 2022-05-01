package com.nango.skeletonweb.infrastructure.aop;

import org.apache.shardingsphere.api.hint.HintManager;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author nango
 * @Date 2020/12/20
 * @Description 多数据源切面实现
 */
@Aspect
@Order(1)
@Component
public class MultiDataSourceAspect {
    /**
     * ds0
     */
    @Pointcut("execution(* com.nango.skeletonweb.infrastructure.persistence..*.*(..))")
    public void switchDataSourceDs0() {
    }

    @Before("switchDataSourceDs0()")
    public void doDs0Before() {
        HintManager.clear();
        HintManager hintManager = HintManager.getInstance();
        hintManager.setDatabaseShardingValue("ds0");
    }

    /**
     * 恢复默认数据源
     */
    @After("switchDataSourceDs0()")
    public void doDs0after() {
        //清理掉当前设置的数据源，让默认的数据源不受影响
        HintManager.clear();
    }

    /**
     * ds1库切入点
     */
    @Pointcut("execution(* com.nango.skeletonweb.infrastructure.persistenceOld..*.*(..))")
    public void switchDataSourceMigrateDs1() {
    }

    @Before("switchDataSourceMigrateDs1()")
    public void doMigrateDsBefore() {
        HintManager.clear();
        HintManager hintManager = HintManager.getInstance();
        hintManager.setDatabaseShardingValue("ds1");
    }

    /**
     * 恢复默认数据源
     */
    @After("switchDataSourceMigrateDs1()")
    public void doMigrateDsafter() {
        //清理掉当前设置的数据源，让默认的数据源不受影响
        HintManager.clear();
    }
}
