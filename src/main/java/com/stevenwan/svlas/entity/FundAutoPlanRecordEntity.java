package com.stevenwan.svlas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 基金定投变更表
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fund_auto_plan_record")
public class FundAutoPlanRecordEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 基金代码
     */
    private String code;

    /**
     * 定投金额
     */
    private BigDecimal price;

    /**
     * 定投日期
     */
    private Date createTime;


}
