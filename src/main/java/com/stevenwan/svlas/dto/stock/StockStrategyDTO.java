package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-01 17:53
 */
@Data
public class StockStrategyDTO {
    /**
     * 股票代码
     */
    private String code;

    /**
     * 锚点：以该价格为锚点
     */
    private BigDecimal priceAnchor;

    /**
     * 时间：调整日期
     */
    private Date adjustTime;

    /**
     * 空间：回调幅度
     */
    private BigDecimal perPriceVolatility;
}
