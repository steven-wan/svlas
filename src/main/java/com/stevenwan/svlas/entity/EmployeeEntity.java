package com.stevenwan.svlas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 员工表
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("employee")
public class EmployeeEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 性别 1 男，0 女
     */
    private Integer sex;

    /**
     * 姓名
     */
    private String name;

    /**
     * 个人标识唯一ID 一般用于存放微信端的 openId
     */
    private String personalId;

    /**
     * 身份证号码
     */
    private String idcardNo;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 创建时间
     */
    private Date createTime;


}
