package com.stevenwan.svlas.dto.hsjc;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 15:36
 */
@Data
public class NucleicAcidRequestSelectRetDTO extends BaseEntity {

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
     * 姓名
     */
    private String name;

    /**
     * 公司名称
     */
    private String companyName;


    /**
     * 申请状态 0 未申请 1 申请中 2 已上报 3 已取消
     */
    private Integer requestStatus;


    /**
     * 检测受理时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date requestTime;

    /**
     * 申请时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 备注：拒绝申请原因
     */
    private String remark;
}
