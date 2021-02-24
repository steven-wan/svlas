package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.util.List;

/**
 * @author steven-wan
 * @since 2021-02-24
 */
@Data
public class StockStatisticalModel {
    /**
     * 总金额
     */
    private Double totalAmt;

    /**
     * 股票金额比例
     */
    private List<StockRateModel> stockRateModelList;

    /**
     * 地区比例
     */
    private List<StockRegionRateModel> stockRegionRateModelList;

    /**
     * 证券类型比例
     */
    private List<StockTypeRateModel> stockTypeRateModelList;
}
