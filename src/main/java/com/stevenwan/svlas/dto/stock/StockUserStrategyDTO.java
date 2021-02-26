package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-01 18:00
 */
@Data
public class StockUserStrategyDTO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 策略类型 买点，卖出，加仓
     */
    private Integer strategyType;

    /**
     * 股数
     */
    private Integer nums;

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
