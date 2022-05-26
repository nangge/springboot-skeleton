package com.nango.skeletonweb.infrastructure.task;

import com.nango.skeletonweb.application.canalDataSyncBusiness.ProductSyncDataServiceImpl;
import com.nango.skeletonweb.domain.canal.CanalBusinessServer;
import com.nango.skeletonweb.infrastructure.canal.CanalClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author nango
 * @package com.nango.skeletonweb.infrastructure.task
 * @class DataSyncTask
 * @date 2022/5/12 22:13
 * @description 数据同步定时任务
 */
@Component
@Slf4j
public class DataSyncTask {
    @Resource
    private CanalBusinessServer canalBusinessServer;

    @Resource
    private CanalClient canalClient;

    @Resource
    private ProductSyncDataServiceImpl productSyncDataService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void syncData() {
        log.info("+++++++++++++++++++++定时任务dump+++++++++++++++++++++");
        //上次任务结束，发起本次
        if (!canalClient.checkConnValid()) {
            log.info("+++++++++++++++++++++定时任务dump开始+++++++++++++++++++++");
            canalBusinessServer.dumpBinlog(canalClient.getCanalConnector(), productSyncDataService::execSql);
        }
    }

}
