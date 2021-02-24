package com.stevenwan.svlas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 股票表
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode
@TableName("stock")
public class StockEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 股票代码
     */
    private String code;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 行业类型
     */
    private Integer industryType;

    /**
     * 类型 FUND 基金，STOCK 股票，期货，黄金
     */
    private String type;

    /**
     * 区域 A A股，HK 港股，USA 美股
     */
    private String region;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Long updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;


}
