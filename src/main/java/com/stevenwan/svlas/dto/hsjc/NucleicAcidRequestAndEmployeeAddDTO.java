package com.stevenwan.svlas.dto.hsjc;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 13:44
 */
@Data
public class NucleicAcidRequestAndEmployeeAddDTO {

    /**
     * 姓名
     */
    private String name;

    /**
     * 个人标识唯一ID 一般用于存放微信端的 openId
     */
    @NotBlank(message = "personalId is null")
    private String personalId;

    /**
     * 身份证号码
     */
    private String idcardNo;

    /**
     * 电话
     */
    @NotBlank(message = "mobile is null")
    private String mobile;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 验证码
     */
    @NotBlank(message = "verificatCode is null")
    private String verificatCode;

}
