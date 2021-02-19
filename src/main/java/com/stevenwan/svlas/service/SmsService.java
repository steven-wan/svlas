package com.stevenwan.svlas.service;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 16:59
 */
public interface SmsService {

    /**
     * 发送短信
     *
     * @param mobile
     * @return
     */
    Boolean send(String mobile);

    /**
     * 短信校验
     *
     * @param mobile
     * @param smsCode
     * @return
     */
    Boolean verify(String mobile, String smsCode);

    /**
     * 查询短信余额
     *
     * @return
     */
    String smsBalance();
}
