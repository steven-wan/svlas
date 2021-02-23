package com.stevenwan.svlas;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-23 10:15
 */
public class HelloJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("test quartz ");
    }
}
