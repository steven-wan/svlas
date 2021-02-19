package com.stevenwan.svlas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.dto.hsjc.NucleicAcidReportSelectRetDTO;
import com.stevenwan.svlas.entity.NucleicAcidReportEntity;
import com.stevenwan.svlas.mapper.NucleicAcidReportMapper;
import com.stevenwan.svlas.service.NucleicAcidReportService;
import com.stevenwan.svlas.util.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 核酸报告 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-04
 */
@Service
public class NucleicAcidReportServiceImpl extends ServiceImpl<NucleicAcidReportMapper, NucleicAcidReportEntity> implements NucleicAcidReportService {

    @Override
    public List<NucleicAcidReportSelectRetDTO> selectNucleicAcidReportList(String openId) {
        ObjectUtils.isNullThrowsExcetion(openId, "openId is null");
        return baseMapper.selectNucleicAcidReportList(openId);
    }
}
