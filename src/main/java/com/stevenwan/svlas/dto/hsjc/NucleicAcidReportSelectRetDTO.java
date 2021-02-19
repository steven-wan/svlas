package com.stevenwan.svlas.dto.hsjc;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stevenwan.svlas.common.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 16:16
 */
@Data
public class NucleicAcidReportSelectRetDTO extends BaseEntity {
    /**
     * 检测医院名称
     */
    private String detectHospitalName;

    /**
     * 检测编号
     */
    private String detectNo;

    /**
     * 检测时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date detectTime;


    /**
     * 检测结果 1 阳性，0 阴性
     */
    private Integer detectResult;

    /**
     * 姓名
     */
    private String name;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 身份证号码
     */
    private String idcardNo;

    /**
     * 电话
     */
    private String mobile;
}
