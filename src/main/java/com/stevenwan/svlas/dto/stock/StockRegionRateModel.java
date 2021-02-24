package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author steven-wan
 * @since 2021-02-24
 */
@Data
public class StockRegionRateModel {
    /**
     * 地区类型名称：A股，港股，美股
     */
    private String stockTypeName;

    /**
     * 地区占比
     */
    private BigDecimal rate;
}
