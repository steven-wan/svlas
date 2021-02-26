package com.stevenwan.svlas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stevenwan.svlas.dto.stock.FundAutoPlanModel;
import com.stevenwan.svlas.entity.FundAutoPlanEntity;

import java.util.List;

/**
 * <p>
 * 基金定投表 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface FundAutoPlanService extends IService<FundAutoPlanEntity> {

    /**
     * 根据用户ID 查询基金列表
     *
     * @param userId
     * @return
     */
    List<FundAutoPlanModel> findByUserId(Long userId);
}
