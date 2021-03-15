package com.stevenwan.svlas;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSON;
import com.stevenwan.svlas.common.HsjcConstant;
import com.stevenwan.svlas.config.StockConfig;
import com.stevenwan.svlas.dto.stock.StockStrategyJobDTO;
import com.stevenwan.svlas.dto.stock.StockUserInfoJobDTO;
import com.stevenwan.svlas.dto.stock.TencentStockModel;
import com.stevenwan.svlas.entity.StockStrategyEntity;
import com.stevenwan.svlas.entity.UserEntity;
import com.stevenwan.svlas.service.StockStrategyService;
import com.stevenwan.svlas.service.StockUserInfoService;
import com.stevenwan.svlas.service.UserService;
import com.stevenwan.svlas.util.ObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author steven-wan
 * @desc 二期需要测试
 * @date 2021-03-15 15:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockStrategyAndMailSendTest {
    @Autowired
    StockStrategyService stockStrategyService;
    @Autowired
    private StockUserInfoService stockUserInfoService;
    @Autowired
    private StockConfig stockConfig;

    @Autowired
    private UserService userService;

    boolean sendNothingFlag = true;

    private final BigDecimal FUND_PRICE_DOWN_VOLATILTITY = new BigDecimal(-3.5);

    private final BigDecimal STOCK_PRICE_DOWN_VOLATILTITY = new BigDecimal(-8);

    private final BigDecimal STOCK_PRICE_TOTAL_DOWN_VOLATILTITY = new BigDecimal(15);

    private final BigDecimal FUND_PRICE_TOTAL_DOWN_VOLATILTITY = new BigDecimal(10);

    @Test
    public void stockStrategyQueryTest() {
        UserEntity userEntity = userService.getById(Long.valueOf(1L));
        String mailAddress = userEntity.getMailAddress();
        stockStrategyQuery("1", mailAddress);
        if (DateUtil.date().hour(true) == 16 && sendNothingFlag) {
            sendGoodMails(mailAddress);
        }
    }

    private void sendGoodMails(String mailAddress) {
        MailUtil.send(mailAddress, "股票每日一句", "耐心等待股票的【安全边际】到来，到来时，敢于加仓，重仓。其它时间耐的住性子等待，持有现金", false);
    }

    @Test
    public void stockUserInfoQueryTest() {
        UserEntity userEntity = userService.getById(Long.valueOf(1L));
        String mailAddress = userEntity.getMailAddress();
        stockUserInfoQuery("1", mailAddress);

    }

    private void stockUserInfoQuery(String userId, String mailAddress) {
        List<StockUserInfoJobDTO> userInfoEntityList = stockUserInfoService.selectStockUserInfoJobList(Long.valueOf(userId));
        if (CollectionUtil.isNotEmpty(userInfoEntityList)) {
            String codeList = userInfoEntityList.stream().map(stockUserInfoEntity -> "s_".concat(stockUserInfoEntity.getCode())).collect(Collectors.joining(","));
            //List<TencentStockModel> stockModelList = StockUtils.tencentTimeData(stockConfig.getTencentTimeUrl(), codeList);

            List<TencentStockModel> stockModelList = JSON.parseArray("[{\"code\":\"01610\",\"codeName\":\"中粮家佳康\",\"perPriceVolatility\":-9.70,\"price\":4.620},{\"code\":\"515700\",\"codeName\":\"新能车\",\"perPriceVolatility\":-1.03,\"price\":1.857},{\"code\":\"161005\",\"codeName\":\"富国天惠\",\"perPriceVolatility\":-2.41,\"price\":3.480},{\"code\":\"588000\",\"codeName\":\"科创50\",\"perPriceVolatility\":-2.93,\"price\":1.260},{\"code\":\"515790\",\"codeName\":\"光伏ETF\",\"perPriceVolatility\":-3.89,\"price\":1.012},{\"code\":\"513050\",\"codeName\":\"中概互联\",\"perPriceVolatility\":-4.24,\"price\":2.123},{\"code\":\"300003\",\"codeName\":\"乐普医疗\",\"perPriceVolatility\":-0.57,\"price\":28.09},{\"code\":\"002271\",\"codeName\":\"东方雨虹\",\"perPriceVolatility\":-1.79,\"price\":46.76},{\"code\":\"000002\",\"codeName\":\"万  科Ａ\",\"perPriceVolatility\":-0.16,\"price\":31.44},{\"code\":\"601318\",\"codeName\":\"中国平安\",\"perPriceVolatility\":-0.51,\"price\":85.11},{\"code\":\"600298\",\"codeName\":\"安琪酵母\",\"perPriceVolatility\":-4.40,\"price\":49.50},{\"code\":\"600276\",\"codeName\":\"恒瑞医药\",\"perPriceVolatility\":-2.82,\"price\":89.59},{\"code\":\"600036\",\"codeName\":\"招商银行\",\"perPriceVolatility\":-0.39,\"price\":53.11},{\"code\":\"600009\",\"codeName\":\"上海机场\",\"perPriceVolatility\":0.96,\"price\":60.73},{\"code\":\"01810\",\"codeName\":\"小米集团-W\",\"perPriceVolatility\":7.03,\"price\":24.350},{\"code\":\"110072\",\"codeName\":\"广汇转债\",\"perPriceVolatility\":0.90,\"price\":80.800}]", TencentStockModel.class);
            //StockUtils.tencentTimeData(stockConfig.getTencentTimeUrl(), codeList);

            System.out.println("真实的股票价格 ：" + JSON.toJSONString(stockModelList));
            MathContext mathContext = new MathContext(2);
            userInfoEntityList.forEach(stockUserInfoEntity -> {
                int index = stockModelList.indexOf(stockUserInfoEntity);
                if (index >= 0) {
                    TencentStockModel tencentStockModel = stockModelList.get(index);
                    ifStockPriceDownWarnMail(tencentStockModel, mailAddress, stockUserInfoEntity.getType(), stockUserInfoEntity.getCostPrice(), mathContext);
                }
            });
        }
    }

    private void ifStockPriceDownWarnMail(TencentStockModel stockModel, String mailAddress, String type, BigDecimal costPrice, MathContext mathContext) {

        BigDecimal currentPrice = stockModel.getPrice();
        BigDecimal totalPriceDownVolatility = (costPrice.subtract(currentPrice)).divide(costPrice, mathContext).multiply(new BigDecimal(100));

        System.out.println("股民名称：" + stockModel.getCodeName() + " 成本价格" + costPrice + " 当前价格 " + currentPrice + " 累计跌幅:" + totalPriceDownVolatility);
        switch (type) {
            case HsjcConstant.STOCK_TYPE_FUND:
                if (stockModel.getPerPriceVolatility().compareTo(FUND_PRICE_DOWN_VOLATILTITY) <= 0) {
                    sendStockPriceDownWarnMails(mailAddress, stockModel.getCodeName(), stockModel.getPrice(), stockModel.getPerPriceVolatility());
                }

                if (totalPriceDownVolatility.compareTo(BigDecimal.ZERO) > 0 && totalPriceDownVolatility.compareTo(FUND_PRICE_TOTAL_DOWN_VOLATILTITY) >= 0) {
                    sendStockPriceTotalDownWarnMails(mailAddress, stockModel.getCodeName(), totalPriceDownVolatility, currentPrice);
                }
                break;
            case HsjcConstant.STOCK_TYPE_STOCK:
                if (stockModel.getPerPriceVolatility().compareTo(STOCK_PRICE_DOWN_VOLATILTITY) <= 0) {
                    sendStockPriceDownWarnMails(mailAddress, stockModel.getCodeName(), stockModel.getPrice(), stockModel.getPerPriceVolatility());
                }
                if (totalPriceDownVolatility.compareTo(BigDecimal.ZERO) > 0 && totalPriceDownVolatility.compareTo(STOCK_PRICE_TOTAL_DOWN_VOLATILTITY) >= 0) {
                    sendStockPriceTotalDownWarnMails(mailAddress, stockModel.getCodeName(), totalPriceDownVolatility, currentPrice);
                }
                break;
            default:
                break;
        }
    }

    private void sendStockPriceTotalDownWarnMails(String mailAddress, String codeName, BigDecimal totalPriceDownVolatility, BigDecimal currentPrice) {
        // MailUtil.send(mailAddress, "股票累计跌幅触发提醒 ：", codeName + " ， 累计下跌:" + totalPriceDownVolatility + "，  当前价格为：" + currentPrice, false);
        System.out.println("股票累计跌幅触发提醒 ：" + codeName + " ， 累计下跌:" + totalPriceDownVolatility + "，  当前价格为：" + currentPrice);

        sendNothingFlag = false;
    }

    private void sendStockPriceDownWarnMails(String mailAddress, String codeName, BigDecimal price, BigDecimal perPriceVolatility) {
        // MailUtil.send(mailAddress, "股票当天跌幅触发提醒 ：", codeName + " ， 下跌:" + perPriceVolatility + " ， 当前价格为：" + price, false);
        System.out.println("股票当天跌幅触发提醒 ：" + codeName + " ， 下跌:" + perPriceVolatility + " ， 当前价格为：" + price);
        sendNothingFlag = false;
    }

    private void stockStrategyQuery(String userId, String mailAddress) {
        List<StockStrategyJobDTO> strategyJobDTOList = stockStrategyService.findByUserId(Long.valueOf(userId));

        if (CollectionUtil.isNotEmpty(strategyJobDTOList)) {
            String codeList = strategyJobDTOList.stream().map(stockStrategyJobDTO -> "s_".concat(stockStrategyJobDTO.getCode())).collect(Collectors.joining(","));
            List<TencentStockModel> stockModelList = JSON.parseArray("[{\"code\":\"600009\",\"codeName\":\"上海机场\",\"perPriceVolatility\":0.96,\"price\":60.73},{\"code\":\"600298\",\"codeName\":\"安琪酵母\",\"perPriceVolatility\":-4.40,\"price\":49.50},{\"code\":\"601318\",\"codeName\":\"中国平安\",\"perPriceVolatility\":-0.51,\"price\":85.11},{\"code\":\"000002\",\"codeName\":\"万  科Ａ\",\"perPriceVolatility\":-0.16,\"price\":31.44},{\"code\":\"600436\",\"codeName\":\"片仔癀\",\"perPriceVolatility\":-3.71,\"price\":258.20},{\"code\":\"603288\",\"codeName\":\"海天味业\",\"perPriceVolatility\":-3.93,\"price\":149.30},{\"code\":\"300760\",\"codeName\":\"迈瑞医疗\",\"perPriceVolatility\":-5.97,\"price\":361.11},{\"code\":\"600276\",\"codeName\":\"恒瑞医药\",\"perPriceVolatility\":-2.82,\"price\":89.59},{\"code\":\"600036\",\"codeName\":\"招商银行\",\"perPriceVolatility\":-0.39,\"price\":53.11},{\"code\":\"002271\",\"codeName\":\"东方雨虹\",\"perPriceVolatility\":-1.79,\"price\":46.76},{\"code\":\"000858\",\"codeName\":\"五 粮 液\",\"perPriceVolatility\":-5.08,\"price\":251.01},{\"code\":\"515790\",\"codeName\":\"光伏ETF\",\"perPriceVolatility\":-3.89,\"price\":1.012},{\"code\":\"588000\",\"codeName\":\"科创50\",\"perPriceVolatility\":-2.93,\"price\":1.260},{\"code\":\"161005\",\"codeName\":\"富国天惠\",\"perPriceVolatility\":-2.41,\"price\":3.480},{\"code\":\"002050\",\"codeName\":\"三花智控\",\"perPriceVolatility\":-4.24,\"price\":20.11},{\"code\":\"00700\",\"codeName\":\"腾讯控股\",\"perPriceVolatility\":-3.46,\"price\":628.000}]", TencentStockModel.class);
            //StockUtils.tencentTimeData(stockConfig.getTencentTimeUrl(), codeList);

            System.out.println("真实的股票价格 ：" + JSON.toJSONString(stockModelList));

            strategyJobDTOList.forEach(stockStrategyJobDTO -> {
                int index = stockModelList.indexOf(stockStrategyJobDTO);
                if (index >= 0) {
                    TencentStockModel tencentStockModel = stockModelList.get(index);
                    comparseStockStrategy(tencentStockModel, stockStrategyJobDTO, mailAddress);
                }
            });
        }
    }

    /**
     * 当前价格和股票策略对比
     *
     * @param stockModel
     * @param stockStrategyJobDTO
     */
    private void comparseStockStrategy(TencentStockModel stockModel, StockStrategyJobDTO stockStrategyJobDTO, String mailAddress) {
        //监听股票价格描点
        if (HsjcConstant.STRATEGY_TYPE_BUY.equals(stockStrategyJobDTO.getStrategyType()) && ObjectUtils.isNotNull(stockStrategyJobDTO.getPriceAnchor()) &&
                stockStrategyJobDTO.getPriceAnchor().compareTo(stockModel.getPrice()) > 0) {
            sendStockStrategyWarnMails(mailAddress, stockModel.getCodeName(), stockModel.getPrice());
            updateWhenStrategyIsFire(stockStrategyJobDTO);
        }
        //监听股票,当收益达到指定位置就提醒卖出
        if (HsjcConstant.STRATEGY_TYPE_SELL.equals(stockStrategyJobDTO.getStrategyType()) && ObjectUtils.isNotNull(stockStrategyJobDTO.getPriceAnchor()) &&
                ObjectUtils.isNotNull(stockStrategyJobDTO.getPerPriceVolatility())) {

            //当股票策略为卖出时，卖出价格是以瞄点价格的基础上乘于指定的比例大于当前价格就提示卖出
            BigDecimal currentPrice = stockModel.getPrice();

            BigDecimal totalPrice = stockStrategyJobDTO.getPriceAnchor().add(stockStrategyJobDTO.getPriceAnchor().multiply(stockStrategyJobDTO.getPerPriceVolatility().divide(new BigDecimal(100))));

            if (currentPrice.compareTo(totalPrice) >= 0) {
                updateWhenStrategyIsFire(stockStrategyJobDTO);
                sendStockSellWarnMaills(mailAddress, stockModel.getCodeName(), stockModel.getPrice(), stockStrategyJobDTO.getPerPriceVolatility());
            }
        }
    }

    private void updateWhenStrategyIsFire(StockStrategyJobDTO stockStrategyJobDTO) {
        StockStrategyEntity strategyEntity = stockStrategyService.getById(stockStrategyJobDTO.getId());
        strategyEntity.setStatus(HsjcConstant.STOCK_STRATEGY_STATUS_ACTIVATED);
        stockStrategyService.updateById(strategyEntity);
    }

    private void sendStockStrategyWarnMails(String mailAddress, String codeName, BigDecimal price) {
        String content = "股票策略触发提醒：" + codeName + "  当前价格为：" + price;
        MailUtil.send(mailAddress, "股票策略触发提醒", content, false);
        sendNothingFlag = false;
    }

    private void sendStockSellWarnMaills(String mailAddress, String codeName, BigDecimal price, BigDecimal perPriceVolatility) {
        MailUtil.send(mailAddress, "股票卖出策略触发提醒 ：", codeName + "， 当前盈利百分比:" + perPriceVolatility + "% ，当前价格为：" + price, false);
        sendNothingFlag = false;
    }
}
