package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-26 13:49
 */
@Data
public class FundAutoPlanModel {

    /**
     * id
     */
    private Long id;

    /**
     * 基金代码
     */
    private String code;

    /**
     * 基金代码名称
     */
    private String codeName;

    /**
     * 定投金额
     */
    private BigDecimal price;

}
