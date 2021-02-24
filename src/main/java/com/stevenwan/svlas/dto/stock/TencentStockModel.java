package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-24 17:42
 */
@Data
public class TencentStockModel {
    /**
     * 代码名称
     */
    private String codeName;

    /**
     * 代码
     */
    private String code;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 价格涨跌幅
     */
    private BigDecimal perPriceVolatility;
}
