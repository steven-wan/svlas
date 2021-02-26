package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-26 09:51
 */
@Data
public class PersonStockTradeFlowAddDTO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 股票代码
     */
    private String code;

    /**
     * 交易价格
     */
    private BigDecimal price;

    /**
     * 交易股数
     */
    private Integer nums;

    /**
     * 交易原因
     */
    private String volume;
}
