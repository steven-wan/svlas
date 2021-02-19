package com.stevenwan.svlas.dto.hsjc;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 15:18
 */
@Data
public class NucleicAcidRequestSelectDTO {

    /**
     * openId
     */
    @NotBlank(message = "openId is null")
    private String openId;

    /**
     * 申请状态  0 未申请 1 申请中 2 已上报 3 已取消
     */
    @NotBlank(message = "requestStatus is null")
    private Integer requestStatus;
}
