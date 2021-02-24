package com.stevenwan.svlas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 股票策略表
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("stock_strategy")
public class StockStrategyEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
     * 创建日期
     */
    private Date createTime;

    /**
     * 状态： 0 - 代表正在执行  1 - 已触发
     */
    private Integer status;
}
