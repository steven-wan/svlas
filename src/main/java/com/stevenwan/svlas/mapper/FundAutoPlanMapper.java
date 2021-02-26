package com.stevenwan.svlas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stevenwan.svlas.dto.stock.FundAutoPlanModel;
import com.stevenwan.svlas.entity.FundAutoPlanEntity;

import java.util.List;

/**
 * <p>
 * 基金定投表 Mapper 接口
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface FundAutoPlanMapper extends BaseMapper<FundAutoPlanEntity> {
    List<FundAutoPlanModel> findByUserId(Long userId);
}
