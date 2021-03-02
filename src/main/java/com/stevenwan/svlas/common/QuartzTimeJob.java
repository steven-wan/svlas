package com.stevenwan.svlas.common;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import com.stevenwan.svlas.config.StockConfig;
import com.stevenwan.svlas.dto.stock.StockStrategyJobDTO;
import com.stevenwan.svlas.dto.stock.TencentStockModel;
import com.stevenwan.svlas.entity.StockStrategyEntity;
import com.stevenwan.svlas.service.StockStrategyService;
import com.stevenwan.svlas.util.ObjectUtils;
import com.stevenwan.svlas.util.StockUtils;
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
public class QuartzTimeJob extends QuartzJobBean {
    @Autowired
    private StockStrategyService stockStrategyService;

    @Autowired
    private StockConfig stockConfig;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String userId = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("userId");

        List<StockStrategyJobDTO> strategyJobDTOList = stockStrategyService.findByUserId(Long.valueOf(userId));

        if (CollectionUtil.isNotEmpty(strategyJobDTOList)) {
            String codeList = strategyJobDTOList.stream().map(stockStrategyJobDTO -> "s_".concat(stockStrategyJobDTO.getCode())).collect(Collectors.joining(","));
            List<TencentStockModel> stockModelList = StockUtils.tencentTimeData(stockConfig.getTencentTimeUrl(), codeList);

            strategyJobDTOList.forEach(stockStrategyJobDTO -> {
                int index = stockModelList.indexOf(stockStrategyJobDTO);
                if (index >= 0) {
                    TencentStockModel tencentStockModel = stockModelList.get(index);
                    comparseStockStrategy(tencentStockModel, stockStrategyJobDTO);
                    ifStockPriceDownWarnMail(tencentStockModel, stockStrategyJobDTO.getMailAddress(), stockStrategyJobDTO.getType());
                }
            });

            if (DateUtil.date().hour(true) == 14) {
                sendGoodMails(strategyJobDTOList.get(0).getMailAddress());
            }

        }
    }


    private void ifStockPriceDownWarnMail(TencentStockModel stockModel, String mailAddress, String type) {
        switch (type) {
            case HsjcConstant.STOCK_TYPE_FUND:
                if (stockModel.getPerPriceVolatility().compareTo(new BigDecimal(-9)) <= 0) {
                    senddownWarnMails(mailAddress, stockModel.getCodeName(), stockModel.getPrice(), stockModel.getPerPriceVolatility());
                }
                break;
            case HsjcConstant.STOCK_TYPE_STOCK:
                if (stockModel.getPerPriceVolatility().compareTo(new BigDecimal(-5)) <= 0) {
                    senddownWarnMails(mailAddress, stockModel.getCodeName(), stockModel.getPrice(), stockModel.getPerPriceVolatility());
                }
                break;
            default:
                break;
        }
    }

    private void senddownWarnMails(String mailAddress, String codeName, BigDecimal price, BigDecimal perPriceVolatility) {
        MailUtil.send(mailAddress, "股票大幅波动提醒", codeName + " 下跌:" + perPriceVolatility + " 当前价格为：" + price, false);
    }

    /**
     * 当前价格和股票策略对比
     *
     * @param stockModel
     * @param stockStrategyJobDTO
     */
    private void comparseStockStrategy(TencentStockModel stockModel, StockStrategyJobDTO stockStrategyJobDTO) {
        if (ObjectUtils.isNotNull(stockStrategyJobDTO.getPriceAnchor()) && stockStrategyJobDTO.getPriceAnchor().compareTo(stockModel.getPrice()) > 0) {
            sendWarnMails(stockStrategyJobDTO.getMailAddress(), stockModel.getCodeName(), stockModel.getPrice());

            StockStrategyEntity strategyEntity = stockStrategyService.getById(stockStrategyJobDTO.getId());
            strategyEntity.setStatus(HsjcConstant.STOCK_STRATEGY_STATUS_ACTIVATED);
            stockStrategyService.updateById(strategyEntity);
        }
    }

    private void sendGoodMails(String mailAddress) {
        MailUtil.send(mailAddress, "股票每日一句", "确定性:好公司，合理价格,慢慢变富", false);
    }

    private void sendWarnMails(String mailAddress, String codeName, BigDecimal price) {
        String content = "股票【加仓】和【买点】提醒：" + codeName + "当前价格为：" + price;
        MailUtil.send(mailAddress, "股票加仓和买点提醒", content, false);
    }
}
