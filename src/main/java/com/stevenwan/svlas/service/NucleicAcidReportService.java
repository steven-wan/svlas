package com.stevenwan.svlas.service;

import com.stevenwan.svlas.dto.hsjc.NucleicAcidReportSelectRetDTO;
import com.stevenwan.svlas.entity.NucleicAcidReportEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 核酸报告 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-04
 */
public interface NucleicAcidReportService extends IService<NucleicAcidReportEntity> {

    /**
     * 查询核酸结果列表
     * @param openId
     * @return
     */
    List<NucleicAcidReportSelectRetDTO> selectNucleicAcidReportList(String openId);
}
