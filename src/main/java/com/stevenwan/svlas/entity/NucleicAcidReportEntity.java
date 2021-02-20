package com.stevenwan.svlas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 核酸报告
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("nucleic_acid_report")
public class NucleicAcidReportEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    private Long employeeId;

    /**
     * 检测医院名称
     */
    private String detectHospitalName;

    /**
     * 检测编号
     */
    private String detectNo;

    /**
     * 检测时间
     */
    private Date detectTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 检测结果 1 阳性，0 阴性
     */
    private Integer detectResult;


}
