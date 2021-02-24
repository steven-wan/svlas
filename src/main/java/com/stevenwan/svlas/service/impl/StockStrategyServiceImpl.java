package com.stevenwan.svlas.service.impl;

import com.stevenwan.svlas.dto.stock.StockStrategyJobDTO;
import com.stevenwan.svlas.entity.StockStrategyEntity;
import com.stevenwan.svlas.mapper.StockStrategyMapper;
import com.stevenwan.svlas.service.StockStrategyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 股票策略表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Service
public class StockStrategyServiceImpl extends ServiceImpl<StockStrategyMapper, StockStrategyEntity> implements StockStrategyService {

    @Override
    public List<StockStrategyJobDTO> findByUserId(Long userId) {
        return baseMapper.selectStockStrategyJobList(userId);
    }
}
