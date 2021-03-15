package com.stevenwan.svlas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 个人股票交易记录流水
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("person_stock_trade_flow")
public class PersonStockTradeFlowEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 股票代码
     */
    private String code;

    /**
     * 交易价格
     */
    private BigDecimal price;

    /**
     * 交易股数
     */
    private Integer nums;

    /**
     * 交易原因
     */
    private String volume;

    /**
     * 交易日期
     */
    private Date tradeTime;

    /**
     * 操作类型 int 1 买 0 卖
     */
    private Integer opeType;

}
