package com.stevenwan.svlas;

import cn.hutool.core.date.DateUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-02 15:48
 */
public class ScheduleTest {
    public static void main(String[] args) throws Exception{
        //timerTest();
        quartzTest();
    }

    private static void timerTest() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + "我在 rate打印");
                int hour = DateUtil.hour(new Date(), true);
                if (hour == 17) {
                    System.out.println(hour);
                    timer.cancel();
                }
            }
        }, DateUtil.parse("2021-02-02 16:38:32", "yyyy-MM-dd HH:mm:ss"), 1000);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + "我在 one time 打印");
            }
        }, DateUtil.parse("2021-02-02 16:38:30", "yyyy-MM-dd HH:mm:ss"));
    }

    private static void quartzTest() throws Exception {
        //job(jobDetail) trigger schedule
        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
        // and start it off
        JobDetail jobDetail = newJob(HelloJob.class).withIdentity("job1", "group1").build();

        SimpleTrigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(4).withRepeatCount(3)).build();

        //"0 35 11,14 ? * 2-6"
        CronTrigger cronTrigger = newTrigger().withIdentity("trigger2", "group1")
                .startNow()
                .withSchedule(cronSchedule("0/4 * * ? * * *")).build();

        defaultScheduler.scheduleJob(jobDetail,cronTrigger);

        defaultScheduler.start();

        Thread.sleep(60000);

        defaultScheduler.shutdown();
    }
}
