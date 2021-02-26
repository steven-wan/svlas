package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-26 10:03
 */
@Data
public class FundAutoPlanAddDTO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 基金代码
     */
    private String code;

    /**
     * 定投金额
     */
    private BigDecimal price;

}
