package com.stevenwan.svlas.dto.hsjc;

import lombok.Data;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 13:11
 */
@Data
public class OpenIdRetDTO {
    /***
     *  openId
     */
    private String openId;


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
     * 注册标识 true 已注册 false 没有注册
     */
    private Boolean register;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 返回给前端申请状态 1 可以申请，0 不能申请
     */
    private Integer requestStatus;
}
