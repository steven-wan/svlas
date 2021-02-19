package com.stevenwan.svlas.common;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 14:24
 */
public class HsjcConstant {
    /**
     * 申请状态 0 未申请
     */
    public static Integer NUCLEIC_ACID_REQUEST_STATUS_NO_REQUEST = 0;

    /**
     * 申请状态 1 申请中
     */
    public static Integer NUCLEIC_ACID_REQUEST_STATUS_REQUESTING = 1;

    /**
     * 申请状态  2 已上报
     */
    public static Integer NUCLEIC_ACID_REQUEST_STATUS_REPORTED = 2;

    /**
     * 申请状态 3 取消申请
     */
    public static Integer NUCLEIC_ACID_REQUEST_STATUS_CANCEL = 3;


    /**
     * 返回给前端申请状态 1 可以申请，0 不能申请
     */
    public static Integer VIEW_NUCLEIC_ACID_REQUEST_STATUS_YES = 1;

    /**
     * 返回给前端申请状态 1 可以申请，0 不能申请
     */
    public static Integer VIEW_NUCLEIC_ACID_REQUEST_STATUS_NO = 0;
}
