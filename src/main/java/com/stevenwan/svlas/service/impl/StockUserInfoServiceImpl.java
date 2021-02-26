package com.stevenwan.svlas.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.common.HsjcConstant;
import com.stevenwan.svlas.config.StockConfig;
import com.stevenwan.svlas.dto.stock.*;
import com.stevenwan.svlas.entity.StockEntity;
import com.stevenwan.svlas.entity.StockUserInfoEntity;
import com.stevenwan.svlas.mapper.StockUserInfoMapper;
import com.stevenwan.svlas.service.StockService;
import com.stevenwan.svlas.service.StockUserInfoRecordService;
import com.stevenwan.svlas.service.StockUserInfoService;
import com.stevenwan.svlas.util.StockUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户股票市值信息表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Service
public class StockUserInfoServiceImpl extends ServiceImpl<StockUserInfoMapper, StockUserInfoEntity> implements StockUserInfoService {
    @Autowired
    private StockService stockService;

    @Autowired
    private StockUserInfoRecordService stockUserInfoRecordService;

    @Autowired
    private StockConfig stockConfig;

    @Override
    public StockUserInfoEntity findByCode(String code) {
        return baseMapper.findByCode(code);
    }

    @Override
    public String getStockUserInfo(Long userId) {

        List<StockUserInfoEntity> stockUserInfoEntityList = baseMapper.findByUserId(userId);

        String codeList = stockUserInfoEntityList.stream().map(stockUserInfoEntity -> "s_".concat(stockUserInfoEntity.getCode())).collect(Collectors.joining(","));
        List<TencentStockModel> stockModelList = StockUtils.tencentTimeData(stockConfig.getTencentTimeUrl(), codeList);
        //更新股票池的价格
        stockUserInfoRecordService.updateStockUserInfo(stockModelList);

        List<StockUserInfoEntity> list = list();
        StockStatisticalModel model = new StockStatisticalModel();

        if (CollectionUtil.isNotEmpty(list)) {
            double totalAmt = list.stream().mapToDouble(stockUserInfoEntity -> stockUserInfoEntity.getCurrentPrice().subtract(BigDecimal.valueOf(stockUserInfoEntity.getNums())).doubleValue()).sum();
            model.setTotalAmt(totalAmt);

            BigDecimal regionATotalAmt = BigDecimal.ZERO;
            BigDecimal regionHKTotalAmt = BigDecimal.ZERO;
            BigDecimal regionUSATotalAmt = BigDecimal.ZERO;
            BigDecimal stockTypeStockTotalAmt = BigDecimal.ZERO;
            BigDecimal stockTypeFundTotalAmt = BigDecimal.ZERO;

            //股票类型比例
            List<StockRateModel> stockRateModelList = new ArrayList<>();
            fillStockModel(stockRateModelList, list, regionATotalAmt, regionHKTotalAmt, regionUSATotalAmt,
                    stockTypeStockTotalAmt, stockTypeFundTotalAmt, totalAmt);
            model.setStockRateModelList(stockRateModelList);
            //证券类型比例
            fillStockTypeRateModelList(model, stockTypeStockTotalAmt, stockTypeFundTotalAmt);
            //地区类型比例
            List<StockRegionRateModel> stockRegionRateModelList = new ArrayList<>();
        }

        return JSONUtil.toJsonStr(model);
    }

    private void fillStockTypeRateModelList(StockStatisticalModel model, BigDecimal stockTypeStockTotalAmt, BigDecimal stockTypeFundTotalAmt) {
        List<StockTypeRateModel> stockTypeRateModelList = new ArrayList<>();
        //股票
        StockTypeRateModel stockModel = new StockTypeRateModel();
        stockModel.setStockTypeName("股票");
        stockModel.setRate(stockTypeStockTotalAmt.divide(stockTypeStockTotalAmt.add(stockTypeFundTotalAmt)));
        stockTypeRateModelList.add(stockModel);
        //基金
        StockTypeRateModel fundModel = new StockTypeRateModel();
        fundModel.setStockTypeName("基金");
        stockModel.setRate(stockTypeFundTotalAmt.divide(stockTypeStockTotalAmt.add(stockTypeFundTotalAmt)));
        stockTypeRateModelList.add(fundModel);

        model.setStockTypeRateModelList(stockTypeRateModelList);
    }

    private void fillStockModel(List<StockRateModel> stockRateModelList, List<StockUserInfoEntity> list,
                                BigDecimal regionATotalAmt, BigDecimal regionHKTotalAmt, BigDecimal regionUSATotalAmt,
                                BigDecimal stockTypeStockTotalAmt, BigDecimal stockTypeFundTotalAmt, double totalAmt) {
        for (StockUserInfoEntity stockUserInfoEntity : list) {
            //股票占比比例
            StockRateModel rateModel = new StockRateModel();
            StockEntity stockEntity = stockService.getById(stockUserInfoEntity.getCode());
            rateModel.setCodeName(stockEntity.getName());
            rateModel.setRate(stockUserInfoEntity.getCurrentPrice().subtract(BigDecimal.valueOf(stockUserInfoEntity.getNums())).divide(BigDecimal.valueOf(totalAmt)));
            stockRateModelList.add(rateModel);
            //地区占比比例
            switch (stockEntity.getRegion()) {
                case HsjcConstant.STOCK_REGION_A:
                    regionATotalAmt = regionATotalAmt.add(stockUserInfoEntity.getCurrentPrice().subtract(BigDecimal.valueOf(stockUserInfoEntity.getNums())));
                    break;
                case HsjcConstant.STOCK_REGION_HK:
                    regionHKTotalAmt = regionHKTotalAmt.add(stockUserInfoEntity.getCurrentPrice().subtract(BigDecimal.valueOf(stockUserInfoEntity.getNums())));
                    break;
                case HsjcConstant.STOCK_REGION_USA:
                    regionUSATotalAmt = regionUSATotalAmt.add(stockUserInfoEntity.getCurrentPrice().subtract(BigDecimal.valueOf(stockUserInfoEntity.getNums())));
                    break;
                default:
                    throw new RuntimeException("错误的地区");
            }

            //证券种类占比比例
            switch (stockEntity.getType()) {
                case HsjcConstant.STOCK_TYPE_STOCK:
                    stockTypeStockTotalAmt = stockTypeStockTotalAmt.add(stockUserInfoEntity.getCurrentPrice().subtract(BigDecimal.valueOf(stockUserInfoEntity.getNums())));
                    break;
                case HsjcConstant.STOCK_TYPE_FUND:
                    stockTypeFundTotalAmt = stockTypeFundTotalAmt.add(stockUserInfoEntity.getCurrentPrice().subtract(BigDecimal.valueOf(stockUserInfoEntity.getNums())));
                    break;
                default:
                    throw new RuntimeException("错误的类型");
            }
        }
    }
}
