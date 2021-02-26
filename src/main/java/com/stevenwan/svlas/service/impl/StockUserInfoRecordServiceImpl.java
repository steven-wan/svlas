package com.stevenwan.svlas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.dto.stock.TencentStockModel;
import com.stevenwan.svlas.entity.StockUserInfoEntity;
import com.stevenwan.svlas.entity.StockUserInfoRecordEntity;
import com.stevenwan.svlas.mapper.StockUserInfoRecordMapper;
import com.stevenwan.svlas.service.StockUserInfoRecordService;
import com.stevenwan.svlas.service.StockUserInfoService;
import com.stevenwan.svlas.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户股票市值变更表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Service
public class StockUserInfoRecordServiceImpl extends ServiceImpl<StockUserInfoRecordMapper, StockUserInfoRecordEntity> implements StockUserInfoRecordService {
    @Autowired
    private StockUserInfoService stockUserInfoService;

    @Override
    public StockUserInfoRecordEntity findByCodeAndDate(String code, String date) {
        return baseMapper.findByCodeAndDate(code, date);
    }

    /**
     * 更新股票池当前价格
     *
     * @param stockModelList
     */
    @Override
    public void updateStockUserInfo(List<TencentStockModel> stockModelList) {
        stockModelList.forEach(tencentStockModel -> {
            //stockUserInfo
            StockUserInfoEntity stockUserInfoEntity = stockUserInfoService.findByCode(tencentStockModel.getCode());
            stockUserInfoEntity.setCurrentPrice(tencentStockModel.getPrice());
            stockUserInfoEntity.setCreateTime(DateUtil.date());

            stockUserInfoEntity.setUpdateTime(DateUtil.date());
            stockUserInfoService.updateById(stockUserInfoEntity);

            //stockUserInfoRecord
            StockUserInfoRecordEntity stockUserInfoRecordEntity = findByCodeAndDate(tencentStockModel.getCode(), DateUtil.format(DateUtil.date(), "yyyy-MM-dd"));
            if (!ObjectUtils.isNotNull(stockUserInfoRecordEntity)) {
                stockUserInfoRecordEntity = new StockUserInfoRecordEntity();
                BeanUtil.copyProperties(stockUserInfoEntity, stockUserInfoRecordEntity);
            }
            stockUserInfoRecordEntity.setCurrentPrice(tencentStockModel.getPrice());
            saveOrUpdate(stockUserInfoRecordEntity);
        });
    }
}
