package com.stevenwan.svlas.dto.hsjc;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 13:53
 */
@Data
public class OpenIdDTO {
    /**
     * 小程序登录的 code
     */
    @NotBlank(message = "jsCode is null")
    private String jsCode;
}
