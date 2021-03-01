package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;
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

    /**
     * 地区A股
     */
    private BigDecimal regionATotalAmt;

    /**
     * 地区港股
     */
    private BigDecimal regionHKTotalAmt;

    /**
     * 地区美股
     */
    private BigDecimal regionUSATotalAmt;

    /**
     * 类型：股票
     */
    private BigDecimal stockTypeStockTotalAmt;

    /**
     * 类型：基金
     */
    private BigDecimal stockTypeFundTotalAmt;

}
