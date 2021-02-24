package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author steven-wan
 * @since 2021-02-24
 */
@Data
public class StockTypeRateModel {
    /**
     * 股票类型名称：A股，基金，期货
     */
    private String stockTypeName;

    /**
     * 股票基金占比
     */
    private BigDecimal rate;
}
