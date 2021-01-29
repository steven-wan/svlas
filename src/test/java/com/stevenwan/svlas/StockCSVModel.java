package com.stevenwan.svlas;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author steven-wan
 * @desc
 * @date 2021-01-29 15:44
 */
@Data
public class StockCSVModel {
    @Alias("日期")
    private String  date;
    @Alias("股票代码")
    private String code;
    @Alias("名称")
    private String codeName;
    @Alias("收盘价")
    private String  priceClose;
    @Alias("最高价")
    private String  priceHigh;
    @Alias("最低价")
    private String  priceLow;
    @Alias("开盘价")
    private String  priceOpen;
    @Alias("前收盘")
    private String  priceLclose;
    @Alias("涨跌额")
    private String  priceChange;
    @Alias("涨跌幅")
    private String  perPriceVolatility;
    @Alias("换手率")
    private String  turnOverRate;
    @Alias("成交量")
    private String  volume;
    @Alias("成交金额")
    private String  turnover;
    @Alias("总市值")
    private String  totalMarketPrice;
    @Alias("流通市值")
    private String  flueMarketPrice;

}
