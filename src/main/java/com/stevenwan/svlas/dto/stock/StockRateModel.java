package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author steven-wan
 * @since 2021-02-24
 */
@Data
public class StockRateModel {
    /**
     * 股票名称
     */
    private String codeName;

    /**
     * 股票占比
     */
    private BigDecimal rate;
}
