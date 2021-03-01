package com.stevenwan.svlas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stevenwan.svlas.entity.StockUserInfoEntity;

/**
 * <p>
 * 用户股票市值信息表 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface StockUserInfoService extends IService<StockUserInfoEntity> {

    /**
     * 根据 code
     *
     * @param code
     * @return
     */
    StockUserInfoEntity findByCode(String code);


    /**
     * 股票比例
     *
     * @param userId
     * @return
     */
    void getStockUserInfo(Long userId);
}
