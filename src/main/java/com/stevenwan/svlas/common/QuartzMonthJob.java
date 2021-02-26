package com.stevenwan.svlas.common;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.mail.MailUtil;
import com.stevenwan.svlas.config.StockConfig;
import com.stevenwan.svlas.dto.stock.FundAutoPlanModel;
import com.stevenwan.svlas.dto.stock.TencentStockModel;
import com.stevenwan.svlas.entity.UserEntity;
import com.stevenwan.svlas.service.FundAutoPlanService;
import com.stevenwan.svlas.service.StockUserInfoRecordService;
import com.stevenwan.svlas.service.UserService;
import com.stevenwan.svlas.util.StockUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-23 16:40
 */
public class QuartzMonthJob extends QuartzJobBean {
    @Autowired
    private StockConfig stockConfig;

    @Autowired
    private StockUserInfoRecordService stockUserInfoRecordService;

    @Autowired
    private FundAutoPlanService fundAutoPlanService;

    @Autowired
    private UserService userService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String userId = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("userId");

        List<FundAutoPlanModel> fundAutoPlanEntityList = fundAutoPlanService.findByUserId(Long.valueOf(userId));

        if (CollectionUtil.isNotEmpty(fundAutoPlanEntityList)) {
            String codeList = fundAutoPlanEntityList.stream().map(fundAutoPlanEntity -> "s_".concat(fundAutoPlanEntity.getCode())).collect(Collectors.joining(","));
            List<TencentStockModel> stockModelList = StockUtils.tencentTimeData(stockConfig.getTencentTimeUrl(), codeList);
            //更新股票池的价格
            stockUserInfoRecordService.updateStockUserInfo(stockModelList);

            sendFundAutoPlanMails(fundAutoPlanEntityList, userId);
        }
    }

    private void sendFundAutoPlanMails(List<FundAutoPlanModel> fundAutoPlanEntityList, String userId) {
        UserEntity userEntity = userService.getById(Long.valueOf(userId));
        String collect = fundAutoPlanEntityList.stream().map(fundAutoPlanModel -> "当前股票：" + fundAutoPlanModel.getCodeName() + ",定投价格：" + fundAutoPlanModel.getPrice()).collect(Collectors.joining("\n"));
        MailUtil.send(userEntity.getMailAddress(), "股票定投提醒", collect, false);
    }
}
