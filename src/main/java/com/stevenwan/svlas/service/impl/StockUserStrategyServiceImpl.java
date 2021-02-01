package com.stevenwan.svlas.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.dto.stock.StockUserStrategyDTO;
import com.stevenwan.svlas.entity.StockUserStrategyEntity;
import com.stevenwan.svlas.entity.StockUserStrategyRecordEntity;
import com.stevenwan.svlas.mapper.StockUserStrategyMapper;
import com.stevenwan.svlas.service.StockUserStrategyRecordService;
import com.stevenwan.svlas.service.StockUserStrategyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户股票策略表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Service
@AllArgsConstructor
public class StockUserStrategyServiceImpl extends ServiceImpl<StockUserStrategyMapper, StockUserStrategyEntity> implements StockUserStrategyService {
    private StockUserStrategyRecordService strategyRecordService;

    @Override
    @Transactional
    public Boolean saveUserStrategy(StockUserStrategyDTO userStrategyDTO) {
        StockUserStrategyEntity entity = new StockUserStrategyEntity();
        BeanUtils.copyProperties(userStrategyDTO, entity);
        entity.setCreateTime(DateUtil.date());
        //save record
        StockUserStrategyRecordEntity recordEntity = new StockUserStrategyRecordEntity();
        BeanUtils.copyProperties(entity, recordEntity, new String[]{"id"});

        return strategyRecordService.save(recordEntity);
    }
}
