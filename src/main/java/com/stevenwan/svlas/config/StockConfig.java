package com.stevenwan.svlas.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author steven-wan
 * @since 2021-01-28
 */
@Data
@ConfigurationProperties(prefix = "stock")
public class StockConfig {
    /**
     * 获取历史数据地址
     */
    private String neteaseHistoryUrl;

    /**
     * 获取实时数据地址
     */
    private String tencentTimeUrl;
}
