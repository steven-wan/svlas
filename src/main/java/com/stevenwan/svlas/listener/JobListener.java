package com.stevenwan.svlas.listener;

import com.stevenwan.svlas.service.QuartzSchedulerJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-25 14:01
 */
@Component
public class JobListener implements ServletContextListener {
    @Autowired
    private QuartzSchedulerJobsService quartzSchedulerJobsService;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("应用停止。。。。。");
    }
}
