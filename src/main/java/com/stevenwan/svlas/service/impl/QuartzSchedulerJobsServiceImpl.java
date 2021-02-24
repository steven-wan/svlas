package com.stevenwan.svlas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.common.HsjcConstant;
import com.stevenwan.svlas.common.QuartzTimeJob;
import com.stevenwan.svlas.dto.stock.QuartzAddJobDTO;
import com.stevenwan.svlas.dto.stock.QuartzUpdateJobDTO;
import com.stevenwan.svlas.entity.QuartzSchedulerJobsEntity;
import com.stevenwan.svlas.mapper.QuartzSchedulerJobsMapper;
import com.stevenwan.svlas.service.QuartzSchedulerJobsService;
import com.stevenwan.svlas.util.ObjectUtils;
import com.stevenwan.svlas.util.QuartzJobsUtils;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-23
 */
@Service
public class QuartzSchedulerJobsServiceImpl extends ServiceImpl<QuartzSchedulerJobsMapper, QuartzSchedulerJobsEntity> implements QuartzSchedulerJobsService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public Boolean addTimesJob(QuartzAddJobDTO quartzAddJobDTO) {
        checkParams(quartzAddJobDTO.getTriggerType(), quartzAddJobDTO.getCornExpression(), quartzAddJobDTO.getSimpleIntervalTimeType(),
                quartzAddJobDTO.getSimpleIntervalTime(), quartzAddJobDTO.getSimpleRepeatNums());

        QuartzSchedulerJobsEntity jobsEntity = new QuartzSchedulerJobsEntity();
        BeanUtil.copyProperties(quartzAddJobDTO, jobsEntity, new String[]{"id"});
        jobsEntity.setCreateTime(DateUtil.date());
        jobsEntity.setCreateUser(quartzAddJobDTO.getUserId());
        jobsEntity.setStatus(HsjcConstant.JOB_STATUS_EXCUTING);
        save(jobsEntity);

        Scheduler scheduler = getScheduler();
        if (HsjcConstant.TRIGGER_TYPE_CRON.equals(quartzAddJobDTO.getTriggerType())) {
            QuartzJobsUtils.startJobWithCronTrigger(scheduler, QuartzTimeJob.class, quartzAddJobDTO.getJobName(),
                    quartzAddJobDTO.getJobGroupName(), quartzAddJobDTO.getCornExpression(), quartzAddJobDTO.getUserId());
        } else {
            QuartzJobsUtils.startJobWithSimpleTrigger(scheduler, QuartzTimeJob.class, quartzAddJobDTO.getJobName(), quartzAddJobDTO.getJobGroupName(),
                    quartzAddJobDTO.getSimpleIntervalTime(), quartzAddJobDTO.getSimpleIntervalTimeType(), quartzAddJobDTO.getSimpleRepeatNums(), quartzAddJobDTO.getUserId());
        }

        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        return Boolean.TRUE;
    }

    private Scheduler getScheduler() {
        return schedulerFactoryBean.getScheduler();
    }

    @Override
    public Boolean deleteTimesJob(Long jobId, Long userId) {
        QuartzSchedulerJobsEntity jobsEntity = getQuartzSchedulerJobsEntityById(jobId);
        Scheduler scheduler = getScheduler();
        JobKey jobKey = new JobKey(jobsEntity.getJobName(), jobsEntity.getJobGroupName());
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            if (ObjectUtils.isNotNull(jobDetail)) {
                jobsEntity.setUpdateTime(DateUtil.date());
                jobsEntity.setUpdateUser(userId);
                jobsEntity.setStatus(HsjcConstant.JOB_STATUS_DELETE);
                updateById(jobsEntity);

                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        return Boolean.TRUE;
    }

    @Override
    public Boolean updateTimesJob(QuartzUpdateJobDTO quartzUpdateJobDTO) {
        checkParams(quartzUpdateJobDTO.getTriggerType(), quartzUpdateJobDTO.getCornExpression(), quartzUpdateJobDTO.getSimpleIntervalTimeType(),
                quartzUpdateJobDTO.getSimpleIntervalTime(), quartzUpdateJobDTO.getSimpleRepeatNums());

        QuartzSchedulerJobsEntity jobsEntity = getQuartzSchedulerJobsEntityById(quartzUpdateJobDTO.getJobId());
        Scheduler scheduler = getScheduler();
        if (HsjcConstant.TRIGGER_TYPE_CRON.equals(quartzUpdateJobDTO.getTriggerType())) {
            if (!jobsEntity.getCornExpression().equalsIgnoreCase(quartzUpdateJobDTO.getCornExpression())) {
                jobsEntity.setUpdateTime(DateUtil.date());
                jobsEntity.setUpdateUser(quartzUpdateJobDTO.getUserId());
                jobsEntity.setCornExpression(quartzUpdateJobDTO.getCornExpression());
                updateById(jobsEntity);

                QuartzJobsUtils.updateJobWithCronTrigger(scheduler, jobsEntity.getJobName(),
                        jobsEntity.getJobGroupName(), quartzUpdateJobDTO.getCornExpression());
            }
        } else {

            if (!jobsEntity.getSimpleIntervalTimeType().equals(quartzUpdateJobDTO.getSimpleIntervalTimeType()) ||
                    !jobsEntity.getSimpleIntervalTime().equals(quartzUpdateJobDTO.getSimpleIntervalTime()) ||
                    !jobsEntity.getSimpleRepeatNums().equals(quartzUpdateJobDTO.getSimpleRepeatNums())) {

                jobsEntity.setUpdateTime(DateUtil.date());
                jobsEntity.setUpdateUser(quartzUpdateJobDTO.getUserId());
                jobsEntity.setSimpleIntervalTime(quartzUpdateJobDTO.getSimpleIntervalTime());
                jobsEntity.setSimpleIntervalTimeType(quartzUpdateJobDTO.getSimpleIntervalTimeType());
                jobsEntity.setSimpleRepeatNums(quartzUpdateJobDTO.getSimpleRepeatNums());

                updateById(jobsEntity);

                QuartzJobsUtils.updateJobWithSimpleTrigger(scheduler, jobsEntity.getJobName(), jobsEntity.getJobGroupName(),
                        quartzUpdateJobDTO.getSimpleIntervalTimeType(), quartzUpdateJobDTO.getSimpleIntervalTime(), quartzUpdateJobDTO.getSimpleRepeatNums());

            }
        }


        return Boolean.TRUE;
    }

    private QuartzSchedulerJobsEntity getQuartzSchedulerJobsEntityById(Long jobId) {
        QuartzSchedulerJobsEntity jobsEntity = baseMapper.findByIdAndStatus(jobId, HsjcConstant.JOB_STATUS_EXCUTING);
        ObjectUtils.isNullThrowsExcetion(jobsEntity, "错误的 jobId");
        return jobsEntity;
    }


    private void checkParams(Integer triggerType, String cornExpression, String simpleIntervalTimeType, Integer simpleIntervalTime, Integer simpleRepeatNums) {
        ObjectUtils.isNullThrowsExcetion(triggerType, "triggerType is null");
        if (HsjcConstant.TRIGGER_TYPE_CRON.equals(triggerType)) {
            ObjectUtils.isNullThrowsExcetion(cornExpression, "cornExpression is null");
        } else {
            ObjectUtils.isNullThrowsExcetion(simpleIntervalTimeType, "simpleIntervalTimeType is null");
            ObjectUtils.isNullThrowsExcetion(simpleIntervalTime, "simpleIntervalTime is null");
            ObjectUtils.isNullThrowsExcetion(simpleRepeatNums, "simpleRepeatNums is null");
        }

    }
}
