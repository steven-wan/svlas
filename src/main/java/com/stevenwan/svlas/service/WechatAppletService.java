package com.stevenwan.svlas.service;

import com.stevenwan.svlas.dto.hsjc.OpenIdRetDTO;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 10:29
 */
public interface WechatAppletService {
    /**
     * 获取 openId 根据 jsCode
     * @param jsCode
     * @return OpenIdDTO
     */
    OpenIdRetDTO getOpenIdByKey(String jsCode);
}
