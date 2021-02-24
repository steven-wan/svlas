package com.stevenwan.svlas.service;

import com.stevenwan.svlas.dto.stock.StockStrategyJobDTO;
import com.stevenwan.svlas.entity.StockStrategyEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 股票策略表 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface StockStrategyService extends IService<StockStrategyEntity> {
    /**
     * 根据用户ID 去查询股票策略
     * @param userId
     * @return
     */
    List<StockStrategyJobDTO> findByUserId(Long userId);
}
