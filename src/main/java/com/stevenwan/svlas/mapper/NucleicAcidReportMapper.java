package com.stevenwan.svlas.mapper;

import com.stevenwan.svlas.dto.hsjc.NucleicAcidReportSelectRetDTO;
import com.stevenwan.svlas.entity.NucleicAcidReportEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 核酸报告 Mapper 接口
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-04
 */
public interface NucleicAcidReportMapper extends BaseMapper<NucleicAcidReportEntity> {

    /**
     * 查询核酸结果列表
     * @param openId
     * @return
     */
    List<NucleicAcidReportSelectRetDTO> selectNucleicAcidReportList(String openId);
}
