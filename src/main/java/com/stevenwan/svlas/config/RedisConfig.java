package com.stevenwan.svlas.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 17:11
 */
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;

    @Bean
    public RedissonClient singleRedissonClient() {
        Config config = new Config();
        String url = "redis://" + host + ":" + port;
        config.useSingleServer().setAddress(url);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
