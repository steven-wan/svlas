package com.stevenwan.svlas.common;

import lombok.Data;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-01 16:26
 */
public enum StockTypeConstant {
    /**
     * 股票
     */
    TYPE_STOCK(1, "股票"),
    /**
     * 基金
     */
    TYPE_FUND(2, "基金");
    private Integer value;

    private String desc;

    StockTypeConstant(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
