package com.stevenwan.svlas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stevenwan.svlas.dto.stock.QuartzAddJobDTO;
import com.stevenwan.svlas.dto.stock.QuartzUpdateJobDTO;
import com.stevenwan.svlas.entity.QuartzSchedulerJobsEntity;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-23
 */
public interface QuartzSchedulerJobsService extends IService<QuartzSchedulerJobsEntity> {

    /**
     * 新增任务
     *
     * @param quartzAddJobDTO
     * @return
     */
    Boolean addTimesJob(QuartzAddJobDTO quartzAddJobDTO);

    /**
     * 删除job
     *
     * @param jobId
     * @param userId
     * @return
     */
    Boolean deleteTimesJob(Long jobId, Long userId);

    /**
     * 修改job
     *
     * @param quartzUpdateJobDTO
     * @return
     */
    Boolean updateTimesJob(QuartzUpdateJobDTO quartzUpdateJobDTO);
}
