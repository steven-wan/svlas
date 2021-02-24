package com.stevenwan.svlas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户股票市值信息表
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("stock_user_info")
public class StockUserInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 股票代码
     */
    private String code;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

    /**
     * 当前价
     */
    private BigDecimal currentPrice;

    /**
     * 股数
     */
    private Integer nums;

    /**
     * 盈利额
     */
    private BigDecimal profitPrice;

    /**
     * 收益比例
     */
    private BigDecimal profitRate;

    /**
     * 创建日期
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
