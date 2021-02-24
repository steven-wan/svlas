package com.stevenwan.svlas.common;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.stevenwan.svlas.dto.stock.StockStrategyJobDTO;
import com.stevenwan.svlas.dto.stock.TencentStockModel;
import com.stevenwan.svlas.util.StockUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-23 16:40
 */
public class QuartzTimeJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String timeJobsData = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("timeJobs");
        String url = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("url");

        List<StockStrategyJobDTO> stockStrategyJobDTOS = JSON.parseArray(timeJobsData, StockStrategyJobDTO.class);

        if (CollectionUtil.isNotEmpty(stockStrategyJobDTOS)) {
            stockStrategyJobDTOS.forEach(stockStrategyJobDTO -> {
                TencentStockModel stockModel = StockUtils.tencentTimeData(stockStrategyJobDTO.getCode(), url);
                comparseStockStrategy(stockModel, stockStrategyJobDTO);
            });
        }
        System.out.println("打印测试数据");
    }

    private void comparseStockStrategy(TencentStockModel stockModel, StockStrategyJobDTO stockStrategyJobDTO) {

    }
}
