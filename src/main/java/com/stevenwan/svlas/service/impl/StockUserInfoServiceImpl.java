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
import com.stevenwan.svlas.util.ObjectUtils;
import com.stevenwan.svlas.util.StockUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
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
        return baseMapper.selectStockUserInfo(code);
    }

    @Override
    public void getStockUserInfo(Long userId) {

        List<StockUserInfoEntity> stockUserInfoEntityList = baseMapper.findByUserId(userId);

        String codeList = stockUserInfoEntityList.stream().map(stockUserInfoEntity -> "s_".concat(stockUserInfoEntity.getCode())).collect(Collectors.joining(","));
        List<TencentStockModel> stockModelList = StockUtils.tencentTimeData(stockConfig.getTencentTimeUrl(), codeList);
        //更新股票池的价格
        stockUserInfoRecordService.updateStockUserInfo(stockModelList);

        List<StockUserInfoEntity> list = list();
        StockStatisticalModel model = new StockStatisticalModel();

        if (CollectionUtil.isNotEmpty(list)) {
            double totalAmt = list.stream().mapToDouble(stockUserInfoEntity -> stockUserInfoEntity.getCurrentPrice().multiply(BigDecimal.valueOf(stockUserInfoEntity.getNums())).doubleValue()).sum();
            model.setTotalAmt(totalAmt);

            MathContext mathContext = new MathContext(2);


            //股票类型比例
            fillStockModel(model, list, totalAmt, mathContext);
            //证券类型比例
            fillStockTypeRateModelList(model, mathContext);
            //地区类型比例
            fillStockRegionModel(model, mathContext);
        }

        System.out.println(JSONUtil.toJsonStr(model));
    }

    private void fillStockRegionModel(StockStatisticalModel model, MathContext mathContext) {
        BigDecimal regionHKTotalAmt = model.getRegionHKTotalAmt();
        BigDecimal regionATotalAmt = model.getRegionATotalAmt();
        BigDecimal regionUSATotalAmt = model.getRegionUSATotalAmt();
        BigDecimal regionTotalAmt = regionATotalAmt.add(regionHKTotalAmt).add(regionUSATotalAmt);
        List<StockRegionRateModel> stockRegionRateModelList = new ArrayList<>();

        //A股
        StockRegionRateModel stockRegionA = new StockRegionRateModel();
        stockRegionA.setStockTypeName("A股");
        stockRegionA.setRate(regionATotalAmt.divide(regionTotalAmt, mathContext));
        stockRegionRateModelList.add(stockRegionA);
        //港股
        StockRegionRateModel stockRegionHK = new StockRegionRateModel();
        stockRegionHK.setStockTypeName("港股");
        stockRegionHK.setRate(regionHKTotalAmt.divide(regionTotalAmt, mathContext));
        stockRegionRateModelList.add(stockRegionHK);
        //USA股
        StockRegionRateModel stockRegionUSA = new StockRegionRateModel();
        stockRegionUSA.setStockTypeName("美股");
        stockRegionUSA.setRate(regionUSATotalAmt.divide(regionTotalAmt, mathContext));
        stockRegionRateModelList.add(stockRegionUSA);
        model.setStockRegionRateModelList(stockRegionRateModelList);
    }

    private void fillStockTypeRateModelList(StockStatisticalModel model, MathContext mathContext) {
        List<StockTypeRateModel> stockTypeRateModelList = new ArrayList<>();
        BigDecimal stockTypeFundTotalAmt = model.getStockTypeFundTotalAmt();
        BigDecimal stockTypeStockTotalAmt = model.getStockTypeStockTotalAmt();

        //股票
        StockTypeRateModel stockModel = new StockTypeRateModel();
        stockModel.setStockTypeName("股票");
        stockModel.setRate(stockTypeStockTotalAmt.divide(stockTypeStockTotalAmt.add(stockTypeFundTotalAmt), mathContext));
        stockTypeRateModelList.add(stockModel);
        //基金
        StockTypeRateModel fundModel = new StockTypeRateModel();
        fundModel.setStockTypeName("基金");
        stockModel.setRate(stockTypeFundTotalAmt.divide(stockTypeStockTotalAmt.add(stockTypeFundTotalAmt), mathContext));
        stockTypeRateModelList.add(fundModel);

        model.setStockTypeRateModelList(stockTypeRateModelList);
    }

    private void fillStockModel(StockStatisticalModel model, List<StockUserInfoEntity> list, double totalAmt, MathContext mathContext) {
        List<StockRateModel> stockRateModelList = new ArrayList<>();
        BigDecimal regionATotalAmt = BigDecimal.ZERO;
        BigDecimal regionHKTotalAmt = BigDecimal.ZERO;
        BigDecimal regionUSATotalAmt = BigDecimal.ZERO;
        BigDecimal stockTypeStockTotalAmt = BigDecimal.ZERO;
        BigDecimal stockTypeFundTotalAmt = BigDecimal.ZERO;

        for (StockUserInfoEntity stockUserInfoEntity : list) {
            //股票占比比例
            StockRateModel rateModel = new StockRateModel();
            StockEntity stockEntity = stockService.findByCode(stockUserInfoEntity.getCode());

            if (ObjectUtils.isNotNull(stockEntity)) {
                rateModel.setCodeName(stockEntity.getName());
                rateModel.setRate(stockUserInfoEntity.getCurrentPrice().multiply(BigDecimal.valueOf(stockUserInfoEntity.getNums())).divide(BigDecimal.valueOf(totalAmt), mathContext));
                stockRateModelList.add(rateModel);
                //地区占比比例
                switch (stockEntity.getRegion()) {
                    case HsjcConstant.STOCK_REGION_A:
                        regionATotalAmt = regionATotalAmt.add(stockUserInfoEntity.getCurrentPrice().multiply(BigDecimal.valueOf(stockUserInfoEntity.getNums())));
                        break;
                    case HsjcConstant.STOCK_REGION_HK:
                        regionHKTotalAmt = regionHKTotalAmt.add(stockUserInfoEntity.getCurrentPrice().multiply(BigDecimal.valueOf(stockUserInfoEntity.getNums())));
                        break;
                    case HsjcConstant.STOCK_REGION_USA:
                        regionUSATotalAmt = regionUSATotalAmt.add(stockUserInfoEntity.getCurrentPrice().multiply(BigDecimal.valueOf(stockUserInfoEntity.getNums())));
                        break;
                    default:
                        throw new RuntimeException("错误的地区");
                }

                //证券种类占比比例
                switch (stockEntity.getType()) {
                    case HsjcConstant.STOCK_TYPE_STOCK:
                        stockTypeStockTotalAmt = stockTypeStockTotalAmt.add(stockUserInfoEntity.getCurrentPrice().multiply(BigDecimal.valueOf(stockUserInfoEntity.getNums())));
                        break;
                    case HsjcConstant.STOCK_TYPE_FUND:
                        stockTypeFundTotalAmt = stockTypeFundTotalAmt.add(stockUserInfoEntity.getCurrentPrice().multiply(BigDecimal.valueOf(stockUserInfoEntity.getNums())));
                        break;
                    default:
                        throw new RuntimeException("错误的类型");
                }
            }

        }
        model.setRegionATotalAmt(regionATotalAmt);
        model.setRegionHKTotalAmt(regionHKTotalAmt);
        model.setRegionUSATotalAmt(regionUSATotalAmt);
        model.setStockTypeFundTotalAmt(stockTypeFundTotalAmt);
        model.setStockTypeStockTotalAmt(stockTypeStockTotalAmt);
        model.setStockRateModelList(stockRateModelList);
    }
}
