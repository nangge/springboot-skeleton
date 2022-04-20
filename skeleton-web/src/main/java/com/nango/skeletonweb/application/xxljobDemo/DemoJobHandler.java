package com.nango.skeletonweb.application.xxljobDemo;

import com.nango.skeletonweb.infrastructure.persistence.user.dao.UserDao;
import com.nango.skeletonweb.infrastructure.persistence.user.entity.User;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wyn
 * @package com.nango.skeletonweb.application.xxljobDemo
 * @class DemoJobHandler
 * @date 2021/9/3 17:51
 * @description xxl-job 示例
 */
@Component
@RequiredArgsConstructor
public class DemoJobHandler extends IJobHandler {
    @Resource
    private UserDao userDao;

    @Override
    @XxlJob("demoJob")
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("xxl-job 定时任务开始执行。【args: {}】", s);

        List<User> list = userDao.list();

        XxlJobLogger.log("<<<<<>>>>>>>> {}", list);
        return SUCCESS;
    }
}
