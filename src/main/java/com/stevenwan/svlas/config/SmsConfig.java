package com.stevenwan.svlas.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 17:13
 */
@Data
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {
    /**
     * 发送url
     */
    private String sendUrl;

    /**
     * 查询余额url
     */
    private String balanceUrl;

    /**
     * appkey
     */
    private String appkey;

    /**
     * 密码
     */
    private String password;

    /**
     * 短信内容模板
     */
    private String content;

}
