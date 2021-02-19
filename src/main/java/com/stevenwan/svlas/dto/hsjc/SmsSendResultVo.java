package com.stevenwan.svlas.dto.hsjc;


/**
 * @Classname SmsSendResultVo
 * @Description TODO
 * @Date 2021/2/4 11:52
 * @Created by wangjs
 */
public class SmsSendResultVo {
    private String code;
    private String fee;
    private String message;
    private String sid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
