package com.stevenwan.svlas.dto.stock;

import lombok.Data;

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
     * 策略ID
     */
    private Long strategyId;
}
