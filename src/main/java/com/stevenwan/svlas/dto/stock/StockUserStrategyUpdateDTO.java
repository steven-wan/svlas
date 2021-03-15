package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-01 18:00
 */
@Data
public class StockUserStrategyUpdateDTO {
    /**
     * 策略ID
     */
    @NotNull(message = "id is null")
    private Long id;

    /**
     * 锚点：以该价格为锚点
     */
    @NotNull(message = "priceAnchor is null")
    private BigDecimal priceAnchor;

    /**
     * 空间：回调幅度
     */
    @NotNull(message = "perPriceVolatility is null")
    private BigDecimal perPriceVolatility;
}
