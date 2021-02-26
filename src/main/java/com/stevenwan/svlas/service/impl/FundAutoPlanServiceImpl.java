package com.stevenwan.svlas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.dto.stock.FundAutoPlanModel;
import com.stevenwan.svlas.entity.FundAutoPlanEntity;
import com.stevenwan.svlas.mapper.FundAutoPlanMapper;
import com.stevenwan.svlas.service.FundAutoPlanService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 基金定投表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Service
public class FundAutoPlanServiceImpl extends ServiceImpl<FundAutoPlanMapper, FundAutoPlanEntity> implements FundAutoPlanService {

    @Override
    public List<FundAutoPlanModel> findByUserId(Long userId) {
        return baseMapper.findByUserId(userId);
    }
}
