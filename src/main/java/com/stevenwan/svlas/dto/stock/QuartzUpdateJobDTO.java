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
public class QuartzUpdateJobDTO {
    /**
     * job Id
     */
    @NotNull(message = "jobId is null")
    private Long jobId;


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
