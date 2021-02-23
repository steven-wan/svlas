package com.stevenwan.svlas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.common.HsjcConstant;
import com.stevenwan.svlas.common.QuartzTimeJob;
import com.stevenwan.svlas.dto.stock.QuartzAddJobDTO;
import com.stevenwan.svlas.entity.QuartzSchedulerJobsEntity;
import com.stevenwan.svlas.mapper.QuartzSchedulerJobsMapper;
import com.stevenwan.svlas.service.QuartzSchedulerJobsService;
import com.stevenwan.svlas.util.ObjectUtils;
import com.stevenwan.svlas.util.QuartzJobsUtils;
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
        checkParams(quartzAddJobDTO);

        QuartzSchedulerJobsEntity jobsEntity = new QuartzSchedulerJobsEntity();
        BeanUtil.copyProperties(quartzAddJobDTO, jobsEntity);
        jobsEntity.setCreateTime(DateUtil.date());
        jobsEntity.setCreateUser(quartzAddJobDTO.getUserId());
        jobsEntity.setStatus(HsjcConstant.JOB_STATUS_EXCUTING);
        save(jobsEntity);

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (HsjcConstant.TRIGGER_TYPE_CRON.equals(quartzAddJobDTO.getTriggerType())) {
            QuartzJobsUtils.startJobWithCronTrigger(scheduler, QuartzTimeJob.class, quartzAddJobDTO.getJobName(),
                    quartzAddJobDTO.getJobGroupName(), quartzAddJobDTO.getCornExpression());
        } else {
            QuartzJobsUtils.startJobWithSimpleTrigger(scheduler, QuartzTimeJob.class, quartzAddJobDTO.getJobName(), quartzAddJobDTO.getJobGroupName(),
                    quartzAddJobDTO.getSimpleIntervalTime(), quartzAddJobDTO.getSimpleIntervalTimeType(), quartzAddJobDTO.getSimpleRepeatNums());
        }

        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        return Boolean.TRUE;
    }


    private void checkParams(QuartzAddJobDTO quartzAddJobDTO) {
        ObjectUtils.isNullThrowsExcetion(quartzAddJobDTO.getTriggerType(), "triggerType is null");
        if (HsjcConstant.TRIGGER_TYPE_CRON.equals(quartzAddJobDTO.getTriggerType())) {
            ObjectUtils.isNullThrowsExcetion(quartzAddJobDTO.getCornExpression(), "cornExpression is null");
        } else {
            ObjectUtils.isNullThrowsExcetion(quartzAddJobDTO.getSimpleIntervalTimeType(), "simpleIntervalTimeType is null");
            ObjectUtils.isNullThrowsExcetion(quartzAddJobDTO.getSimpleIntervalTime(), "simpleIntervalTime is null");
            ObjectUtils.isNullThrowsExcetion(quartzAddJobDTO.getSimpleRepeatNums(), "simpleRepeatNums is null");
        }

    }
}
