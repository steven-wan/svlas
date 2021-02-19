package com.stevenwan.svlas.dto.hsjc;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 16:56
 */
@Data
public class SendSmsDTO {
    /**
     * 手机号
     */
    @NotBlank(message = "mobile is null")
    private String mobile;
}
