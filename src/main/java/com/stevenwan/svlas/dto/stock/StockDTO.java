package com.stevenwan.svlas.dto.stock;

import lombok.Data;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-01 15:33
 */
@Data
public class StockDTO {
    /**
     * 代码
     */
    private String code;

    /**
     * 代码名称
     */
    private String name;

    /**
     * 行业类型 1 食品饮料，2 医药制造 3 银行 4 地产 5 保险 6 电子元件 7 新能源 8 互联网 9 民航机场 10 水泥建材
     * 11 汽车行业 12 医疗行业 13 酿酒行业 14 金属制品 15 科技 16 制造业 17 其它
     */
    private Integer industryType;

    /**
     * 类型 基金，股票，期货，黄金 1 股票 2 基金 3 期货 4 黄金
     */
    private Integer type;

    /**
     * 区域 A股，港股，美股  1 A股 2 港股 3 美股
     */
    private Integer region;

    private Long userId;
}
