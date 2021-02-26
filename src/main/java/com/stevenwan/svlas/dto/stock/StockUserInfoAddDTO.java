package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-26 10:17
 */
@Data
public class StockUserInfoAddDTO {
    /**
     * 股票代码
     */
    private String code;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 成本价
     */
    private BigDecimal costPrice;


    /**
     * 股数
     */
    private Integer nums;

}
