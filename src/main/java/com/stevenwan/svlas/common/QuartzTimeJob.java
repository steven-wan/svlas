package com.stevenwan.svlas.common;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-23 16:40
 */
public class QuartzTimeJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("打印测试数据");
    }
}
