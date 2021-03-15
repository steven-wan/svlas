package com.stevenwan.svlas.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.common.HsjcConstant;
import com.stevenwan.svlas.config.StockConfig;
import com.stevenwan.svlas.dto.stock.*;
import com.stevenwan.svlas.entity.StockEntity;
import com.stevenwan.svlas.entity.StockUserInfoEntity;
import com.stevenwan.svlas.entity.StockUserInfoRecordEntity;
import com.stevenwan.svlas.entity.UserEntity;
import com.stevenwan.svlas.mapper.StockUserInfoMapper;
import com.stevenwan.svlas.service.StockService;
import com.stevenwan.svlas.service.StockUserInfoRecordService;
import com.stevenwan.svlas.service.StockUserInfoService;
import com.stevenwan.svlas.service.UserService;
import com.stevenwan.svlas.util.ObjectUtils;
import com.stevenwan.svlas.util.StockUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UserService userService;

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

        sendMailsStockAllStatistical(model, userId);
    }

    @Override
    @Transactional
    public Boolean saveStockUserInfoAndRecord(StockUserInfoAddDTO stockUserInfoAddDTO) {
        StockUserInfoEntity entity = new StockUserInfoEntity();
        BeanUtils.copyProperties(stockUserInfoAddDTO, entity);
        entity.setCreateTime(DateUtil.date());
        entity.setUpdateTime(DateUtil.date());
        save(entity);

        StockUserInfoRecordEntity recordEntity = new StockUserInfoRecordEntity();
        BeanUtils.copyProperties(entity, recordEntity, new String[]{"id"});
        return stockUserInfoRecordService.save(recordEntity);
    }

    @Override
    @Transactional
    public Boolean updateStockUserInfo(BigDecimal costPrice, Integer nums, Long id) {
        StockUserInfoEntity stockUserInfoEntity = getById(id);
        ObjectUtils.isNullThrowsExcetion(stockUserInfoEntity, "错误的 id");

        stockUserInfoEntity.setCostPrice(costPrice);
        stockUserInfoEntity.setNums(nums);
        stockUserInfoEntity.setUpdateTime(DateUtil.date());
        updateById(stockUserInfoEntity);

        StockUserInfoRecordEntity recordEntity = new StockUserInfoRecordEntity();
        BeanUtils.copyProperties(stockUserInfoEntity, recordEntity, new String[]{"id"});
        recordEntity.setCreateTime(DateUtil.date());
        return stockUserInfoRecordService.save(recordEntity);
    }

    @Override
    public List<StockUserInfoJobDTO> selectStockUserInfoJobList(Long userId) {
        return baseMapper.selectStockUserInfoJobList(userId);
    }

    private void sendMailsStockAllStatistical(StockStatisticalModel model, Long userId) {
        UserEntity userEntity = userService.getById(Long.valueOf(userId));

        String head = "<html><body>";
        String contentBody = "<table><tr><td colspan=\"2\"><b>股票占比</b></td></tr><tr><td><b>股票名称</b></td><td><b>占比</b></td></tr>";
        for (StockRateModel obj : model.getStockRateModelList()) {
            contentBody = contentBody + "<tr><td>" + obj.getCodeName() + "</td> <td>" + obj.getRate() + "</td></tr>";
        }
        String contentTail = contentBody + "</table>";

        contentBody = " <br/><table><tr><td colspan=\"2\"><b>股票地区占比</b></td></tr><tr><td><b>地区名称</b></td><td><b>占比</b></td></tr>";
        for (StockRegionRateModel obj : model.getStockRegionRateModelList()) {
            contentBody = contentBody + "<tr><td>" + obj.getStockTypeName() + "</td><td>" + obj.getRate() + "</td></tr>";
        }
        contentTail = contentTail + contentBody + "</table>";

        contentBody = " <br/><table><tr><td colspan=\"2\"><b>股票种类占比</b></td></tr><tr><td><b>种类名称</b></td><td><b>占比</b></td></tr>";
        for (StockTypeRateModel obj : model.getStockTypeRateModelList()) {
            contentBody = contentBody + "<tr><td>" + obj.getStockTypeName() + "</td><td>" + obj.getRate() + "</td></tr>";
        }
        contentTail = contentTail + contentBody + "</table>";

        String tail = "</body></html>";

        MailUtil.send(userEntity.getMailAddress(), "股票统计", head + contentTail + tail, true);
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
        stockRegionA.setRate(regionATotalAmt.multiply(BigDecimal.valueOf(100)).divide(regionTotalAmt, mathContext));
        stockRegionRateModelList.add(stockRegionA);
        //港股
        StockRegionRateModel stockRegionHK = new StockRegionRateModel();
        stockRegionHK.setStockTypeName("港股");
        stockRegionHK.setRate(regionHKTotalAmt.multiply(BigDecimal.valueOf(100)).divide(regionTotalAmt, mathContext));
        stockRegionRateModelList.add(stockRegionHK);
        //USA股
        StockRegionRateModel stockRegionUSA = new StockRegionRateModel();
        stockRegionUSA.setStockTypeName("美股");
        stockRegionUSA.setRate(regionUSATotalAmt.multiply(BigDecimal.valueOf(100)).divide(regionTotalAmt, mathContext));
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
        stockModel.setRate(stockTypeStockTotalAmt.multiply(BigDecimal.valueOf(100)).divide(stockTypeStockTotalAmt.add(stockTypeFundTotalAmt), mathContext));
        stockTypeRateModelList.add(stockModel);
        //基金
        StockTypeRateModel fundModel = new StockTypeRateModel();
        fundModel.setStockTypeName("基金");
        fundModel.setRate(stockTypeFundTotalAmt.multiply(BigDecimal.valueOf(100)).divide(stockTypeStockTotalAmt.add(stockTypeFundTotalAmt), mathContext));
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
                rateModel.setRate(stockUserInfoEntity.getCurrentPrice().multiply(BigDecimal.valueOf(stockUserInfoEntity.getNums())).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(totalAmt), mathContext));
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

    public static void main(String[] args) {
        String a = "{\"regionATotalAmt\":376347.1,\"stockTypeStockTotalAmt\":352757,\"stockTypeRateModelList\":[{\"rate\":0.87,\"stockTypeName\":\"股票\"},{\"rate\":0.13,\"stockTypeName\":\"基金\"}],\"regionHKTotalAmt\":27106,\"stockTypeFundTotalAmt\":50696.1,\"totalAmt\":403453.1,\"regionUSATotalAmt\":0,\"stockRateModelList\":[{\"rate\":0.042,\"codeName\":\"中粮惠康\"},{\"rate\":0.0025,\"codeName\":\"新能车\"},{\"rate\":0.0077,\"codeName\":\"富国天惠\"},{\"rate\":0.047,\"codeName\":\"科创50\"},{\"rate\":0.053,\"codeName\":\"光伏ETF\"},{\"rate\":0.012,\"codeName\":\"中概互联\"},{\"rate\":0.15,\"codeName\":\"乐普医疗\"},{\"rate\":0.063,\"codeName\":\"东方雨虹\"},{\"rate\":0.076,\"codeName\":\"万科A\"},{\"rate\":0.21,\"codeName\":\"中国平安\"},{\"rate\":0.013,\"codeName\":\"安琪酵母\"},{\"rate\":0.15,\"codeName\":\"恒瑞医药\"},{\"rate\":0.025,\"codeName\":\"招商银行\"},{\"rate\":0.14,\"codeName\":\"上海机场\"},{\"rate\":0.013,\"codeName\":\"小米集团\"},{\"rate\":0.0039,\"codeName\":\"广汇转债\"}],\"stockRegionRateModelList\":[{\"rate\":0.93,\"stockTypeName\":\"A股\"},{\"rate\":0.067,\"stockTypeName\":\"港股\"},{\"rate\":0,\"stockTypeName\":\"美股\"}]}\n";
        StockStatisticalModel model = JSON.parseObject(a, StockStatisticalModel.class);
        List<StockRateModel> stockRateModelList = model.getStockRateModelList();
        for (StockRateModel obj : stockRateModelList) {
            System.out.println("股票名称：" + obj.getCodeName() + " 股票占比:" + obj.getRate());
        }


    }
}
