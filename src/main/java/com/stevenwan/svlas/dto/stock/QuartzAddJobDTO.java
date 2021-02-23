package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-23 16:09
 */
@Data
public class QuartzAddJobDTO {
    /**
     * 任务名称
     */
    @NotBlank(message = "jobName is null")
    private String jobName;

    /**
     * 任务所属组名称
     */
    @NotBlank(message = "jobGroupName is null")
    private String jobGroupName;

    /**
     * 任务描述
     */
    @NotBlank(message = "jobDesc is null")
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
     * simple 触发器时间间隔类型：1 - seconds 2 - milliseconds  3 - minutes  4 - hours
     */
    private String simpleIntervalTimeType;

    /**
     * simple 循环次数 0 无限循环
     */
    private Integer simpleRepeatNums;

    /**
     * 用户Id
     */
    @NotNull(message = "userId is null")
    private Long userId;
}
