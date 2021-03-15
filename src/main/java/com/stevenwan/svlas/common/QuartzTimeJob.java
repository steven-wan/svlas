package com.stevenwan.svlas.common;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
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
import com.stevenwan.svlas.util.StockUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-23 16:40
 */
@Slf4j
public class QuartzTimeJob extends QuartzJobBean {
    @Autowired
    private StockStrategyService stockStrategyService;

    @Autowired
    private StockConfig stockConfig;

    @Autowired
    private StockUserInfoService stockUserInfoService;

    @Autowired
    private UserService userService;

    private final BigDecimal FUND_PRICE_DOWN_VOLATILTITY = new BigDecimal(-3.5);

    private final BigDecimal STOCK_PRICE_DOWN_VOLATILTITY = new BigDecimal(-8);

    private final BigDecimal STOCK_PRICE_TOTAL_DOWN_VOLATILTITY = new BigDecimal(15);

    private final BigDecimal FUND_PRICE_TOTAL_DOWN_VOLATILTITY = new BigDecimal(10);

    boolean sendNothingFlag = true;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String userId = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("userId");
        UserEntity userEntity = userService.getById(Long.valueOf(userId));
        String mailAddress = userEntity.getMailAddress();

        //检测策略是否触发
        stockStrategyQuery(userId, mailAddress);
        //持仓股票监测，比如累计跌幅，比如突然跌幅达到高位
        stockUserInfoQuery(userId, mailAddress);

        if (DateUtil.date().hour(true) == 14 && sendNothingFlag) {
            sendGoodMails(mailAddress);
        }
    }

    private void stockUserInfoQuery(String userId, String mailAddress) {
        List<StockUserInfoJobDTO> userInfoEntityList = stockUserInfoService.selectStockUserInfoJobList(Long.valueOf(userId));
        if (CollectionUtil.isNotEmpty(userInfoEntityList)) {
            String codeList = userInfoEntityList.stream().map(stockUserInfoEntity -> "s_".concat(stockUserInfoEntity.getCode())).collect(Collectors.joining(","));
            List<TencentStockModel> stockModelList = StockUtils.tencentTimeData(stockConfig.getTencentTimeUrl(), codeList);
            userInfoEntityList.forEach(stockUserInfoEntity -> {
                int index = stockModelList.indexOf(stockUserInfoEntity);
                if (index >= 0) {
                    TencentStockModel tencentStockModel = stockModelList.get(index);
                    ifStockPriceDownWarnMail(tencentStockModel, mailAddress, stockUserInfoEntity.getType(), stockUserInfoEntity.getCostPrice());
                }
            });
        }
    }

    private void stockStrategyQuery(String userId, String mailAddress) {
        List<StockStrategyJobDTO> strategyJobDTOList = stockStrategyService.findByUserId(Long.valueOf(userId));

        if (CollectionUtil.isNotEmpty(strategyJobDTOList)) {
            String codeList = strategyJobDTOList.stream().map(stockStrategyJobDTO -> "s_".concat(stockStrategyJobDTO.getCode())).collect(Collectors.joining(","));
            List<TencentStockModel> stockModelList = StockUtils.tencentTimeData(stockConfig.getTencentTimeUrl(), codeList);

            strategyJobDTOList.forEach(stockStrategyJobDTO -> {
                int index = stockModelList.indexOf(stockStrategyJobDTO);
                if (index >= 0) {
                    TencentStockModel tencentStockModel = stockModelList.get(index);
                    comparseStockStrategy(tencentStockModel, stockStrategyJobDTO, mailAddress);
                }
            });
        }
    }


    private void ifStockPriceDownWarnMail(TencentStockModel stockModel, String mailAddress, String type, BigDecimal costPrice) {
        BigDecimal currentPrice = stockModel.getPrice();
        BigDecimal totalPriceDownVolatility = (costPrice.subtract(currentPrice)).divide(costPrice);

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
        MailUtil.send(mailAddress, "股票累计跌幅触发提醒 ：", codeName + " ， 累计下跌:" + totalPriceDownVolatility + "，  当前价格为：" + currentPrice, false);
        sendNothingFlag = false;
    }

    private void sendStockPriceDownWarnMails(String mailAddress, String codeName, BigDecimal price, BigDecimal perPriceVolatility) {
        MailUtil.send(mailAddress, "股票当天跌幅触发提醒 ：", codeName + " ， 下跌:" + perPriceVolatility + " ， 当前价格为：" + price, false);
        sendNothingFlag = false;
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

    private void sendStockSellWarnMaills(String mailAddress, String codeName, BigDecimal price, BigDecimal perPriceVolatility) {
        MailUtil.send(mailAddress, "股票卖出策略触发提醒 ：", codeName + "， 当前盈利百分比:" + perPriceVolatility + "% ，当前价格为：" + price, false);
        sendNothingFlag = false;
    }

    private void sendGoodMails(String mailAddress) {
        MailUtil.send(mailAddress, "股票每日一句", "好公司，耐心持有现金等待，安全边际（合理价格）,长期持有，慢慢变富", false);
    }

    private void sendStockStrategyWarnMails(String mailAddress, String codeName, BigDecimal price) {
        String content = "股票策略触发提醒：" + codeName + "  当前价格为：" + price;
        MailUtil.send(mailAddress, "股票策略触发提醒", content, false);
        sendNothingFlag = false;
    }
}
