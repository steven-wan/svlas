package com.stevenwan.svlas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 股票交易历史流水表
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("stock_trade_history_flow")
public class StockTradeHistoryFlowEntity extends BaseEntity  {

    private static final long serialVersionUID = 1L;

    /**
     * 股票代码
     */
    private String code;

    /**
     * 收盘价
     */
    private BigDecimal priceClose;

    /**
     * 涨跌额
     */
    private BigDecimal priceChange;

    /**
     * 涨跌幅
     */
    private BigDecimal perPriceVolatility;

    /**
     * 总市值 单位亿
     */
    private BigDecimal totalMarketPrice;

    /**
     * 成交额
     */
    private BigDecimal turnover;

    /**
     * 成交量
     */
    private BigDecimal volume;

    /**
     * 交易日期
     */
    private Date tradeTime;


}
