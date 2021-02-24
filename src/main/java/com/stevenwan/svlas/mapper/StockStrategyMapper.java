package com.stevenwan.svlas.mapper;

import com.stevenwan.svlas.dto.stock.StockStrategyJobDTO;
import com.stevenwan.svlas.entity.StockStrategyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 股票策略表 Mapper 接口
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface StockStrategyMapper extends BaseMapper<StockStrategyEntity> {

    /**
     * 根据用户ID 去查询股票策略
     * @param userId
     * @return
     */
    List<StockStrategyJobDTO> selectStockStrategyJobList(Long userId);
}
