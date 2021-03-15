package com.stevenwan.svlas.service;

import com.stevenwan.svlas.dto.stock.PersonStockTradeFlowAddDTO;
import com.stevenwan.svlas.entity.PersonStockTradeFlowEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 个人股票交易记录流水 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface PersonStockTradeFlowService extends IService<PersonStockTradeFlowEntity> {

    /**
     * 新增个人交易流水以及更新股票持仓信息
     * @param personStockTradeFlowAddDTO
     * @return
     */
    Boolean addTradeFlowAndUpdateStockUserInfo(PersonStockTradeFlowAddDTO personStockTradeFlowAddDTO);
}
