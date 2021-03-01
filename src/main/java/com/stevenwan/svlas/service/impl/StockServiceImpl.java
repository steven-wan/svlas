package com.stevenwan.svlas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.entity.StockEntity;
import com.stevenwan.svlas.mapper.StockMapper;
import com.stevenwan.svlas.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 股票表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Service
@AllArgsConstructor
public class StockServiceImpl extends ServiceImpl<StockMapper, StockEntity> implements StockService {


    @Override
    public StockEntity findByCode(String code) {
        return baseMapper.findByCode(code);
    }
}
