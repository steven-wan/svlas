package com.stevenwan.svlas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stevenwan.svlas.dto.stock.StockUserInfoAddDTO;
import com.stevenwan.svlas.dto.stock.StockUserInfoJobDTO;
import com.stevenwan.svlas.entity.StockUserInfoEntity;

import java.math.BigDecimal;
import java.util.List;

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

    /**
     * 新增股票持仓以及记录
     * @param stockUserInfoAddDTO
     * @return
     */
    Boolean saveStockUserInfoAndRecord(StockUserInfoAddDTO stockUserInfoAddDTO);

    /**
     * 更新股票持仓以及记录
     * @param costPrice
     * @param nums
     * @param id
     * @return
     */
    Boolean updateStockUserInfo(BigDecimal costPrice, Integer nums, Long id);

    /**
     * 根据用户查询它的持仓股票
     * @param userId
     * @return
     */
    List<StockUserInfoJobDTO> selectStockUserInfoJobList(Long userId);
}
