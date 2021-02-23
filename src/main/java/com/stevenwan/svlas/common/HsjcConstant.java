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

    /**
     * 触发器类型： 0 - simple  1 - cron
     */
    public static Integer TRIGGER_TYPE_SIMPLE = 0;

    /**
     * 触发器类型： 0 - simple  1 - cron
     */
    public static  Integer TRIGGER_TYPE_CRON = 1;


    /**
     * simple 触发器时间间隔类型： 1 - seconds 2 - milliseconds  3 - minutes  4 - hours
     */
    public static final String SIMPLE_INTERVAL_TIME_TYPE_SECONDS = "1";

    /**
     * simple 触发器时间间隔类型： 1 - seconds 2 - milliseconds  3 - minutes  4 - hours
     */
    public static final String SIMPLE_INTERVAL_TIME_TYPE_MILLISECONDS = "2";

    /**
     * simple 触发器时间间隔类型： 1 - seconds 2 - milliseconds  3 - minutes  4 - hours
     */
    public static final String SIMPLE_INTERVAL_TIME_TYPE_MINUTES = "3";

    /**
     * simple 触发器时间间隔类型： 1 - seconds 2 - milliseconds  3 - minutes  4 - hours
     */
    public static final String SIMPLE_INTERVAL_TIME_TYPE_HOURS = "4";

    /**
     * 状态： 0 - 代表正在执行  1 - 已删除  2 - 暂停
     */
    public static Integer JOB_STATUS_EXCUTING = 0;

    /**
     * 状态： 0 - 代表正在执行  1 - 已删除  2 - 暂停
     */
    public static Integer JOB_STATUS_DELETE = 1;

    /**
     * 状态： 0 - 代表正在执行  1 - 已删除  2 - 暂停
     */
    public static Integer JOB_STATUS_PAUSE = 2;

}
