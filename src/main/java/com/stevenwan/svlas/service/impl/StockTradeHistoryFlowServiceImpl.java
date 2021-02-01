package com.stevenwan.svlas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.common.StockCSVModel;
import com.stevenwan.svlas.common.StockTypeConstant;
import com.stevenwan.svlas.config.StockConfig;
import com.stevenwan.svlas.dto.stock.StockInitHistoryDataDTO;
import com.stevenwan.svlas.entity.StockEntity;
import com.stevenwan.svlas.entity.StockTradeHistoryFlowEntity;
import com.stevenwan.svlas.mapper.StockTradeHistoryFlowMapper;
import com.stevenwan.svlas.service.StockService;
import com.stevenwan.svlas.service.StockTradeHistoryFlowService;
import com.stevenwan.svlas.util.ObjectUtils;
import com.stevenwan.svlas.util.StockUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 股票交易历史流水表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Service
@AllArgsConstructor

public class StockTradeHistoryFlowServiceImpl extends ServiceImpl<StockTradeHistoryFlowMapper, StockTradeHistoryFlowEntity> implements StockTradeHistoryFlowService {
    private StockConfig stockConfig;
    private StockService stockService;

    @Override
    public Boolean initHistoryDataStock(StockInitHistoryDataDTO initHistoryDataDTO) {

        StockEntity code = stockService.getBaseMapper().selectOne(
                new QueryWrapper<StockEntity>().eq("code", initHistoryDataDTO.getStockCode()).eq("type", StockTypeConstant.TYPE_STOCK.getValue())
        );
        ObjectUtils.isNullThrowsExcetion(code, "stock code is not exist");

        List<StockCSVModel> stockCSVModels = StockUtils.stockHistoryDataByNetEase(stockConfig.getHistoryUrl(), code.getCode(), initHistoryDataDTO.getStartDate(), initHistoryDataDTO.getEndDate());

        if(ObjectUtils.isNotNull(stockCSVModels)){
            List<StockTradeHistoryFlowEntity> historyFlowEntityList = stockCSVModels.stream().map(obj -> {
                StockTradeHistoryFlowEntity entity = new StockTradeHistoryFlowEntity();
                BeanUtils.copyProperties(obj, entity);
                return entity;
            }).collect(Collectors.toList());
            baseMapper.selectBatchIds(historyFlowEntityList);

            return Boolean.TRUE;

        }
        return Boolean.FALSE;
    }

}
