package com.stevenwan.svlas.dto.hsjc;

/**
 * @Classname SmsBalanceResultVo
 * @Description TODO
 * @Date 2021/2/4 15:02
 * @Created by wangjs
 */
public class SmsBalanceResultVo {
    private String code;
    private String balance;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
