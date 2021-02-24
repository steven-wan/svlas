package com.stevenwan.svlas.common;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import com.stevenwan.svlas.config.StockConfig;
import com.stevenwan.svlas.dto.stock.*;
import com.stevenwan.svlas.entity.StockEntity;
import com.stevenwan.svlas.entity.StockStrategyEntity;
import com.stevenwan.svlas.entity.StockUserInfoEntity;
import com.stevenwan.svlas.service.StockService;
import com.stevenwan.svlas.service.StockStrategyService;
import com.stevenwan.svlas.service.StockUserInfoService;
import com.stevenwan.svlas.util.ObjectUtils;
import com.stevenwan.svlas.util.StockUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-23 16:40
 */
public class QuartzTimeJob extends QuartzJobBean {
    @Autowired
    private StockStrategyService stockStrategyService;

    @Autowired
    private StockConfig stockConfig;

    @Autowired
    private StockUserInfoService stockUserInfoService;

    @Autowired
    private StockService stockService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Long userId = (Long) jobExecutionContext.getJobDetail().getJobDataMap().get("userId");

        List<StockStrategyJobDTO> strategyJobDTOList = stockStrategyService.findByUserId(userId);

        if (CollectionUtil.isNotEmpty(strategyJobDTOList)) {
            strategyJobDTOList.forEach(stockStrategyJobDTO -> {
                TencentStockModel stockModel = StockUtils.tencentTimeData(stockStrategyJobDTO.getCode(), stockConfig.getTencentTimeUrl());
                comparseStockStrategy(stockModel, stockStrategyJobDTO);
            });
        }
    }

    private void comparseStockStrategy(TencentStockModel stockModel, StockStrategyJobDTO stockStrategyJobDTO) {
        if (ObjectUtils.isNotNull(stockStrategyJobDTO.getPriceAnchor()) && stockStrategyJobDTO.getPriceAnchor().compareTo(stockModel.getPrice()) > 0) {
            sendWarnMails(stockStrategyJobDTO.getMailAddress(), stockModel.getCodeName(), stockModel.getPrice());

            StockStrategyEntity strategyEntity = stockStrategyService.getById(stockStrategyJobDTO.getId());
            strategyEntity.setStatus(HsjcConstant.STOCK_STRATEGY_STATUS_ACTIVATED);
            stockStrategyService.updateById(strategyEntity);
        } else {
            if (DateUtil.date().hour(true) == 14) {
                sendGoodMails(stockStrategyJobDTO.getMailAddress());
            }
        }
    }

    private void sendGoodMails(String mailAddress) {
        MailUtil.send(mailAddress, "股票每日一句", "不要担心踏空，严格按照加仓策略，回过头看不遵守策略，纪律的操作 99% 都是错的，不动持有2年以上都会赚钱。", false);
    }

    private void sendWarnMails(String mailAddress, String codeName, BigDecimal price) {
        String content = "股票【加仓】和【买点】提醒：" + codeName + "当前价格为：" + price + "\\r\\n";
        MailUtil.send(mailAddress, "股票加仓和买点提醒", content, false);
    }

    private void stockUserInfo() {
        List<StockUserInfoEntity> list = stockUserInfoService.list();
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
