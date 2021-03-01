package com.stevenwan.svlas.common;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.mail.MailUtil;
import com.stevenwan.svlas.dto.stock.FundAutoPlanModel;
import com.stevenwan.svlas.entity.UserEntity;
import com.stevenwan.svlas.service.FundAutoPlanService;
import com.stevenwan.svlas.service.UserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-23 16:40
 */
public class QuartzMonthJob extends QuartzJobBean {

    @Autowired
    private FundAutoPlanService fundAutoPlanService;

    @Autowired
    private UserService userService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String userId = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("userId");

        List<FundAutoPlanModel> fundAutoPlanEntityList = fundAutoPlanService.findByUserId(Long.valueOf(userId));

        if (CollectionUtil.isNotEmpty(fundAutoPlanEntityList)) {
            sendFundAutoPlanMails(fundAutoPlanEntityList, userId);
        }
    }

    private void sendFundAutoPlanMails(List<FundAutoPlanModel> fundAutoPlanEntityList, String userId) {
        UserEntity userEntity = userService.getById(Long.valueOf(userId));

        String content = "<html><body><table><tr><td ><b>股票名称</b></td> <td><b>定投额度</b></td></tr>";
        for (FundAutoPlanModel obj : fundAutoPlanEntityList) {
            content = content + "<tr><td>" + obj.getCodeName() + "</td> <td>" + obj.getPrice() + "</td></tr>";
        }
        String tail = "</table><a href=\"https://www.baidu.com\">股票占比比例</a></body></html>";

        MailUtil.send(userEntity.getMailAddress(), "股票定投提醒", content + tail, true);
    }

}
