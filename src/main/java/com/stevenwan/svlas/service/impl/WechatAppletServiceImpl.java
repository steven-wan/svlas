package com.stevenwan.svlas.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.stevenwan.svlas.common.HsjcConstant;
import com.stevenwan.svlas.config.WechatAppletConfig;
import com.stevenwan.svlas.dto.hsjc.OpenIdRetDTO;
import com.stevenwan.svlas.entity.EmployeeEntity;
import com.stevenwan.svlas.entity.NucleicAcidRequestEntity;
import com.stevenwan.svlas.service.EmployeeService;
import com.stevenwan.svlas.service.NucleicAcidRequestService;
import com.stevenwan.svlas.service.WechatAppletService;
import com.stevenwan.svlas.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 10:30
 */
@Service
public class WechatAppletServiceImpl implements WechatAppletService {

    @Autowired
    private WechatAppletConfig wechatAppletConfig;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private NucleicAcidRequestService nucleicAcidRequestService;

    private final String APPLET_GET_OPENID_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Override
    public OpenIdRetDTO getOpenIdByKey(String jsCode) {
        Map<String, Object> map = new HashMap();
        map.put("appid", wechatAppletConfig.getAppId());
        map.put("secret", wechatAppletConfig.getAppSecret());
        map.put("js_code", jsCode);
        map.put("grant_type", "authorization_code");
        String json = HttpUtil.get(APPLET_GET_OPENID_URL, map);
        JSONObject jsonObject = JSONUtil.parseObj(json);

        if (ObjectUtils.isNotNull(jsonObject.get("errcode"))) {
            throw new RuntimeException((String) jsonObject.get("errmsg"));
        }
        return fillOpenIdRetDTO((String) jsonObject.get("openid"));
    }

    private OpenIdRetDTO fillOpenIdRetDTO(String openid) {
        OpenIdRetDTO dto = new OpenIdRetDTO();
        dto.setOpenId(openid);
        dto.setRegister(false);
        dto.setRequestStatus(HsjcConstant.VIEW_NUCLEIC_ACID_REQUEST_STATUS_YES);

        EmployeeEntity employeeEntity = employeeService.findByOpenId(openid);
        if (ObjectUtils.isNotNull(employeeEntity)) {
            dto.setRegister(true);
            dto.setName(employeeEntity.getName());
            dto.setMobile(employeeEntity.getMobile());
            dto.setIdcardNo(employeeEntity.getIdcardNo());
            dto.setCompanyId(employeeEntity.getCompanyId());
            dto.setCompanyName("茅台");

            NucleicAcidRequestEntity nucleicAcidRequestEntity = nucleicAcidRequestService.findByEmployeeId(employeeEntity.getId());
            if (ObjectUtils.isNotNull(nucleicAcidRequestEntity)) {
                dto.setRequestStatus(HsjcConstant.VIEW_NUCLEIC_ACID_REQUEST_STATUS_NO);
            } else {
                dto.setRequestStatus(HsjcConstant.VIEW_NUCLEIC_ACID_REQUEST_STATUS_YES);
            }
        }
        return dto;
    }
}
