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
public class QuartzUpdateMonthJobDTO {
    /**
     * job Id
     */
    @NotNull(message = "jobId is null")
    private Long jobId;


    /**
     * corn 表达式
     */
    @NotBlank(message = "cornExpression is null")
    private String cornExpression;

}
