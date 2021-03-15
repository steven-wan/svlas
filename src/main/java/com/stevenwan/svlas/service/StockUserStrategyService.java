package com.stevenwan.svlas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stevenwan.svlas.dto.stock.StockUserStrategyDTO;
import com.stevenwan.svlas.entity.StockUserStrategyEntity;

/**
 * <p>
 * 用户股票策略表 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface StockUserStrategyService extends IService<StockUserStrategyEntity> {

    /**
     * 保存用户的股票策略
     *
     * @param userStrategyDTO
     * @return
     */
    Boolean saveUserStrategy(StockUserStrategyDTO userStrategyDTO);

    /**
     * 删除用户的股票策略
     * @param id
     * @return
     */
    Boolean deleteStockStrategy(Long id);
}
