package com.stevenwan.svlas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 任务表
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("quartz_scheduler_jobs")
public class QuartzSchedulerJobsEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务所属组名称
     */
    private String jobGroupName;

    /**
     * 任务描述
     */
    private String jobDesc;

    /**
     * 状态： 0 - 代表正在执行  1 - 已删除  2 - 暂停
     */
    private Integer status;

    /**
     * 触发器类型： 0 - simple  1 - cron
     */
    private Integer triggerType;

    /**
     * corn 表达式
     */
    private String cornExpression;

    /**
     * simple 触发器时间间隔
     */
    private Integer simpleIntervalTime;

    /**
     * simple 循环次数 0 无限循环
     */
    private Integer simpleRepeatNums;

    /**
     * simple 触发器时间间隔类型：1 - seconds 2 - milliseconds  3 - minutes  4 - hours
     */
    private String simpleIntervalTimeType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改人
     */
    private Long updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;


}
