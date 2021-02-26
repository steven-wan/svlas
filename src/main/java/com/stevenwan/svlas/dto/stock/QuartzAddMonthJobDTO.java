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
public class QuartzAddMonthJobDTO {
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
     * corn 表达式
     */
    @NotBlank(message = "cornExpression is null")
    private String cornExpression;

    /**
     * 用户Id
     */
    @NotNull(message = "userId is null")
    private Long userId;
}
