package com.stevenwan.svlas.util;

import com.stevenwan.svlas.common.HsjcConstant;
import org.quartz.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-23 16:47
 */
public class QuartzJobsUtils {

    public static void startJobWithSimpleTrigger(Scheduler scheduler, Class jobClass, String jobName, String jobGroupName,
                                                 Integer simpleIntervalTime, String simpleIntervalTimeType, Integer simpleRepeatNums, Long userId) {
        JobDetail jobDetail = addJobDetail(jobClass, jobName, jobGroupName, userId);
        SimpleTrigger trigger = newTrigger().withIdentity(jobName, jobGroupName)
                .withSchedule(getSimpleSchedBuilder(simpleIntervalTime, simpleIntervalTimeType, simpleRepeatNums)).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private static SimpleScheduleBuilder getSimpleSchedBuilder(Integer simpleIntervalTime, String simpleIntervalTimeType, Integer simpleRepeatNums) {
        SimpleScheduleBuilder triggerBuilder;
        switch (simpleIntervalTimeType) {
            case HsjcConstant.SIMPLE_INTERVAL_TIME_TYPE_SECONDS:
                if (Integer.valueOf(0).equals(simpleRepeatNums)) {
                    triggerBuilder = simpleSchedule().withIntervalInSeconds(simpleIntervalTime).repeatForever();
                } else {
                    triggerBuilder = simpleSchedule().withIntervalInSeconds(simpleIntervalTime).withRepeatCount(simpleRepeatNums);
                }
                break;
            case HsjcConstant.SIMPLE_INTERVAL_TIME_TYPE_MILLISECONDS:
                if (Integer.valueOf(0).equals(simpleRepeatNums)) {
                    triggerBuilder = simpleSchedule().withIntervalInMilliseconds(simpleIntervalTime).repeatForever();
                } else {
                    triggerBuilder = simpleSchedule().withIntervalInMilliseconds(simpleIntervalTime).withRepeatCount(simpleRepeatNums);
                }
                break;
            case HsjcConstant.SIMPLE_INTERVAL_TIME_TYPE_HOURS:
                if (Integer.valueOf(0).equals(simpleRepeatNums)) {
                    triggerBuilder = simpleSchedule().withIntervalInHours(simpleIntervalTime).repeatForever();
                } else {
                    triggerBuilder = simpleSchedule().withIntervalInHours(simpleIntervalTime).withRepeatCount(simpleRepeatNums);
                }
                break;
            case HsjcConstant.SIMPLE_INTERVAL_TIME_TYPE_MINUTES:
                if (Integer.valueOf(0).equals(simpleRepeatNums)) {
                    triggerBuilder = simpleSchedule().withIntervalInMinutes(simpleIntervalTime).repeatForever();
                } else {
                    triggerBuilder = simpleSchedule().withIntervalInMinutes(simpleIntervalTime).withRepeatCount(simpleRepeatNums);
                }
                break;
            default:
                throw new RuntimeException("错误的时间类型");
        }
        return triggerBuilder;
    }

    public static void startJobWithCronTrigger(Scheduler scheduler, Class jobClass, String jobName, String jobGroupName,
                                               String cornExpression, Long userId) {
        JobDetail jobDetail = addJobDetail(jobClass, jobName, jobGroupName, userId);

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withSchedule(cronSchedule(cornExpression)).withIdentity(jobName, jobGroupName).build();

        try {

            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateJobWithCronTrigger(Scheduler scheduler, String jobName, String jobGroupName,
                                                String cornExpression) {
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withSchedule(cronSchedule(cornExpression)).withIdentity(jobName, jobGroupName).build();
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroupName);
        try {
            scheduler.rescheduleJob(triggerKey, cronTrigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private static JobDetail addJobDetail(Class jobClass, String jobName, String jobGroupName, Long userId) {

        return JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName)
                .usingJobData("userId", userId)
                .build();
    }

    public static void updateJobWithSimpleTrigger(Scheduler scheduler, String jobName, String jobGroupName, String simpleIntervalTimeType,
                                                  Integer simpleIntervalTime, Integer simpleRepeatNums) {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroupName);

        SimpleTrigger trigger = newTrigger().withIdentity(jobName, jobGroupName)
                .withSchedule(getSimpleSchedBuilder(simpleIntervalTime, simpleIntervalTimeType, simpleRepeatNums)).build();
        try {
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
