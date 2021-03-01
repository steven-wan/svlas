package com.stevenwan.svlas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stevenwan.svlas.entity.StockEntity;

/**
 * <p>
 * 股票表 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface StockService extends IService<StockEntity> {
    StockEntity findByCode(String code);
}
