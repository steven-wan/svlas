package com.stevenwan.svlas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stevenwan.svlas.entity.QuartzSchedulerJobsEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 任务表 Mapper 接口
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-23
 */
public interface QuartzSchedulerJobsMapper extends BaseMapper<QuartzSchedulerJobsEntity> {

    @Select("select * from quartz_scheduler_jobs where id=#{jobId} and status=#{jobStatus}")
    QuartzSchedulerJobsEntity findByIdAndStatus(@Param("jobId") Long jobId, @Param("jobStatus") Integer jobStatus);
}
