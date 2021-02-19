package com.stevenwan.svlas.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.stevenwan.svlas.config.SmsConfig;
import com.stevenwan.svlas.dto.hsjc.SmsBalanceResultVo;
import com.stevenwan.svlas.dto.hsjc.SmsSendResultVo;
import com.stevenwan.svlas.service.SmsService;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 17:01
 */
@Service
@AllArgsConstructor
public class SmsServiceImple implements SmsService {
    private static String SUCCEE_CODE = "0";
    private static String prefix = "sms-key:";

    private RedissonClient singleRedissonClient;

    private RedisTemplate redisTemplate;

    private SmsConfig smsConfig;

    @Override
    public Boolean send(String mobile) {
        Integer randNum = ThreadLocalRandom.current().nextInt(100000, 999999);
        String key = prefix + mobile;
        RLock lock = singleRedissonClient.getLock(key);

        try {
            boolean isLock = lock.tryLock(1, 30, TimeUnit.SECONDS);
            if (isLock) {
                if (redisTemplate.hasKey(key)) {
                    throw new RuntimeException("短信发送太频繁，请稍后再试");
                }
                Map<String, Object> params = new HashMap<>();
                params.put("appkey", smsConfig.getAppkey());
                String timestamp = String.valueOf(System.currentTimeMillis());
                params.put("appsecret", SecureUtil.md5(smsConfig.getPassword() + mobile + timestamp).toLowerCase());
                params.put("mobile", mobile);
                params.put("timestamp", timestamp);
                String content = smsConfig.getContent().replace("xxxx", String.valueOf(randNum));
                params.put("content", content);

                String post = HttpUtil.post(smsConfig.getSendUrl(), params);
                SmsSendResultVo result = JSONUtil.toBean(post, SmsSendResultVo.class);

                if (!SUCCEE_CODE.equals(result.getCode())) {
                    throw new Exception("短信发送失败");
                }
                redisTemplate.opsForValue().set(key, String.valueOf(randNum), 60, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("短信发送失败");
        } catch (Exception e) {
            throw new RuntimeException("短信发送失败");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return true;
    }

    @Override
    public Boolean verify(String mobile, String smsCode) {
        String key = prefix + mobile;
        RLock lock = singleRedissonClient.getLock(key);
        try {
            boolean isLock = lock.tryLock(1, 30, TimeUnit.SECONDS);
            if (isLock) {

                if (!redisTemplate.hasKey(key)) {
                    throw new Exception("验证码已经过期");
                }
                String redisCode = redisTemplate.opsForValue().get(key).toString();
                if (!smsCode.equals(redisCode)) {
                    throw new Exception("验证码不正确");
                }
                //验证成功后删除短信验证码
                redisTemplate.delete(key);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("验证码错误");
        } catch (Exception e) {
            throw new RuntimeException("验证码错误");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }

        return Boolean.TRUE;
    }

    @Override
    public String smsBalance() {
        Map<String, Object> params = new HashMap<>();

        String timestamp = String.valueOf(System.currentTimeMillis());
        params.put("appsecret", SecureUtil.md5(smsConfig.getPassword() + timestamp).toLowerCase());
        params.put("appkey", smsConfig.getAppkey());
        params.put("timestamp", timestamp);

        String post = HttpUtil.post(smsConfig.getBalanceUrl(), params);
        SmsBalanceResultVo result = JSONUtil.toBean(post, SmsBalanceResultVo.class);

        if (!SUCCEE_CODE.equals(result.getCode())) {
            throw new RuntimeException("查询余额失败");
        }
        return result.getBalance();
    }
}
