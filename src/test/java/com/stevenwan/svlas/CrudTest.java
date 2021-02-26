package com.stevenwan.svlas;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import com.stevenwan.svlas.common.HsjcConstant;
import com.stevenwan.svlas.dto.stock.FundAutoPlanModel;
import com.stevenwan.svlas.dto.stock.StockStrategyJobDTO;
import com.stevenwan.svlas.dto.stock.TencentStockModel;
import com.stevenwan.svlas.entity.StockStrategyEntity;
import com.stevenwan.svlas.service.FundAutoPlanService;
import com.stevenwan.svlas.service.StockStrategyService;
import com.stevenwan.svlas.service.StockUserInfoRecordService;
import com.stevenwan.svlas.util.ObjectUtils;
import com.stevenwan.svlas.util.StockUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-26 14:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrudTest {

    @Autowired
    StockUserInfoRecordService stockUserInfoRecordService;

    @Autowired
    FundAutoPlanService fundAutoPlanService;
    @Autowired
    StockStrategyService stockStrategyService;

    @Test
    void fundAutoPlanTest() {
        List<FundAutoPlanModel> fundAutoPlanEntityList = fundAutoPlanService.findByUserId(Long.valueOf("1"));

        if (CollectionUtil.isNotEmpty(fundAutoPlanEntityList)) {
            sendFundAutoPlanMails(fundAutoPlanEntityList);
        }

    }

    @Test
    void timeQuartzTest() {
        List<StockStrategyJobDTO> strategyJobDTOList = stockStrategyService.findByUserId(Long.valueOf("1"));

        if (CollectionUtil.isNotEmpty(strategyJobDTOList)) {
            String codeList = strategyJobDTOList.stream().map(stockStrategyJobDTO -> "s_".concat(stockStrategyJobDTO.getCode())).collect(Collectors.joining(","));
            List<TencentStockModel> stockModelList = StockUtils.tencentTimeData("https://qt.gtimg.cn/?q=s_", codeList);

            strategyJobDTOList.forEach(stockStrategyJobDTO -> {
                int index = stockModelList.indexOf(stockStrategyJobDTO);
                if (index >= 0) {
                    TencentStockModel tencentStockModel = stockModelList.get(index);
                    comparseStockStrategy(tencentStockModel, stockStrategyJobDTO);
                    ifDownGt10WarnMail(tencentStockModel, stockStrategyJobDTO.getMailAddress());
                }
            });

            if (DateUtil.date().hour(true) == 14) {
                sendGoodMails(strategyJobDTOList.get(0).getMailAddress());
            }
        }
    }

    private void sendFundAutoPlanMails(List<FundAutoPlanModel> fundAutoPlanEntityList) {
        String collect = fundAutoPlanEntityList.stream().map(fundAutoPlanModel -> "当前股票：" + fundAutoPlanModel.getCodeName() + ",定投价格：" + fundAutoPlanModel.getPrice()).collect(Collectors.joining("\n"));
        MailUtil.send("951520698@qq.com", "股票定投提醒", collect, false);
    }

    /**
     * 如果波动大于 10 就发邮件提醒
     *
     * @param stockModel
     */
    private void ifDownGt10WarnMail(TencentStockModel stockModel, String mailAddress) {
        if (stockModel.getPerPriceVolatility().compareTo(new BigDecimal(-9)) <= 0) {
            senddownWarnMails(mailAddress, stockModel.getCodeName(), stockModel.getPrice(), stockModel.getPerPriceVolatility());
        }
    }

    private void senddownWarnMails(String mailAddress, String codeName, BigDecimal price, BigDecimal
            perPriceVolatility) {
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
        MailUtil.send(mailAddress, "股票每日一句", "不要担心踏空，严格按照加仓策略，回过头看不遵守策略，纪律的操作 99% 都是错的，不动持有2年以上都会赚钱。", false);
    }

    private void sendWarnMails(String mailAddress, String codeName, BigDecimal price) {
        String content = "股票【加仓】和【买点】提醒：" + codeName + "当前价格为：" + price + "\\r\\n";
        MailUtil.send(mailAddress, "股票加仓和买点提醒", content, false);
    }
}
