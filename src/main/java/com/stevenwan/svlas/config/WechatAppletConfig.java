package com.stevenwan.svlas.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author steven-wan
 * @since 2021-01-28
 */
@Data
@ConfigurationProperties(prefix = "applet")
public class WechatAppletConfig {
    /**
     * appId
     */
    private String appId;

    /**
     * appSecret
     */
    private String appSecret;
}
