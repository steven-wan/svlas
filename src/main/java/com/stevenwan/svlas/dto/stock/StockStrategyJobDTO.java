package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-01 17:53
 */
@Data
public class StockStrategyJobDTO {
    /**
     * id
     */
    private Long id;

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

    /**
     * 策略类型 买点，卖出，加仓
     */
    private Integer strategyType;

    /**
     * 股数
     */
    private Integer nums;

    /**
     * 邮件地址
     */
    private String mailAddress;


    /**
     * 类型 FUND 基金，STOCK 股票，期货，黄金
     */
    private String type;

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        TencentStockModel that = (TencentStockModel) o;
        return Objects.equals(code, that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
