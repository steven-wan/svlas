package com.stevenwan.svlas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 核酸检测申请表
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("nucleic_acid_request")
public class NucleicAcidRequestEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    private Long employeeId;

    /**
     * 检测申请时间
     */
    private Date requestTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 申请状态 0 申请中 1 已上报 2 取消申请 3拒绝申请
     */
    private Integer requestStatus;

    /**
     * 备注：拒绝申请原因
     */
    private String remark;
}
