package com.stevenwan.svlas.service;

import com.stevenwan.svlas.dto.stock.QuartzAddJobDTO;
import com.stevenwan.svlas.entity.QuartzSchedulerJobsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param quartzAddJobDTO
     * @return
     */
    Boolean addTimesJob(QuartzAddJobDTO quartzAddJobDTO);

}
