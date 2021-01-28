package com.stevenwan.svlas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户股票策略变更表
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("stock_user_strategy_record")
public class StockUserStrategyRecordEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

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

    /**
     * 创建日期
     */
    private Date createTime;


}
