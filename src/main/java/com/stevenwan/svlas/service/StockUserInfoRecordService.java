package com.stevenwan.svlas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stevenwan.svlas.entity.StockUserInfoRecordEntity;

/**
 * <p>
 * 用户股票市值变更表 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface StockUserInfoRecordService extends IService<StockUserInfoRecordEntity> {
    /**
     * 根据 code 和日期
     *
     * @param code
     * @param date
     * @return
     */
    StockUserInfoRecordEntity findByCodeAndDate(String code, String date);
}
