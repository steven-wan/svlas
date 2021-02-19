package com.stevenwan.svlas.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stevenwan.svlas.common.ResultData;
import com.stevenwan.svlas.dto.hsjc.CompanySelectDTO;
import com.stevenwan.svlas.dto.hsjc.CompanySelectRetDTO;
import com.stevenwan.svlas.dto.hsjc.SendSmsDTO;
import com.stevenwan.svlas.dto.hsjc.VerifyCodeDTO;
import com.stevenwan.svlas.service.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 16:50
 */
@RestController
@RequestMapping("/commonRequest")
@AllArgsConstructor
public class CommonController {

    private SmsService smsService;

    /**
     * 发送短信
     *
     * @param sendSmsDTO
     * @return
     */
    @PostMapping("/sendSms")
    public ResultData sendSms(@Valid @RequestBody SendSmsDTO sendSmsDTO) {
        return ResultData.data(smsService.send(sendSmsDTO.getMobile()));
    }

    /**
     * 验证短信码
     *
     * @param verifyCodeDTO
     * @return
     */
    @PostMapping("/verifySmsCode")
    public ResultData verifySmsCode(@Valid @RequestBody VerifyCodeDTO verifyCodeDTO) {
        return ResultData.data(smsService.verify(verifyCodeDTO.getMobile(), verifyCodeDTO.getSmsCode()));
    }

    /**
     * 获取公司列表
     *
     * @param companySelectDTO
     * @return
     */
    @PostMapping("/getCompanyData")
    public ResultData getCompanyData(@RequestBody CompanySelectDTO companySelectDTO) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like("name", companySelectDTO.getCompanyName());
        ArrayList<Object> objects = new ArrayList<>();
        CompanySelectRetDTO retDTO = new CompanySelectRetDTO();
        retDTO.setCompanyId(1L);
        retDTO.setCompanyName("健保付");

        CompanySelectRetDTO ret = new CompanySelectRetDTO();
        ret.setCompanyId(2L);
        ret.setCompanyName("亿家");
        objects.add(retDTO);
        objects.add(ret);
        return ResultData.data(objects);
    }
}
