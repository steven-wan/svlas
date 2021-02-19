package com.stevenwan.svlas.common;

import lombok.Data;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 10:26
 */
@Data
public class ResultData<T> {
    /**
     * 通信状态码  0000 代表成功  9999 错误
     */
    private String resultCode;
    /**
     * 通信错误信息  9999 时有数据
     */
    private String msg;
    /**
     * 具体的业务数据
     */
    private T data;

    public ResultData(String resultCode, String msg, T data) {
        this.resultCode = resultCode;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultData<T> data(T data) {
        return new ResultData("0000", "操作成功", data);
    }

    public static <T> ResultData<T> error(String msg) {
        return new ResultData("9999", msg, null);
    }
}
