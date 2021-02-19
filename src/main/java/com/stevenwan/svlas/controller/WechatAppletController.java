package com.stevenwan.svlas.controller;

import com.stevenwan.svlas.common.ResultData;
import com.stevenwan.svlas.dto.hsjc.OpenIdDTO;
import com.stevenwan.svlas.service.WechatAppletService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 10:24
 */
@RestController
@RequestMapping("/applet")
@AllArgsConstructor
public class WechatAppletController {

    private WechatAppletService wechatAppletService;


    /***
     * 获取 openId
     * @param openIdDTO
     * @return
     */
    @PostMapping("getOpenIdByKey")
    public ResultData getOpenIdByKey(@Valid @RequestBody OpenIdDTO openIdDTO) {
        return ResultData.data(wechatAppletService.getOpenIdByKey(openIdDTO.getJsCode()));
    }
}
