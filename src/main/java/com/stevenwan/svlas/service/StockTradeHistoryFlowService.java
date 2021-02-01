package com.stevenwan.svlas.service;

import com.stevenwan.svlas.dto.stock.StockInitHistoryDataDTO;
import com.stevenwan.svlas.entity.StockTradeHistoryFlowEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 股票交易历史流水表 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface StockTradeHistoryFlowService extends IService<StockTradeHistoryFlowEntity> {

    /**
     * 初始化股票历史数据
     * @param initHistoryDataDTO
     * @return
     */
    Boolean initHistoryDataStock(StockInitHistoryDataDTO initHistoryDataDTO);
}
