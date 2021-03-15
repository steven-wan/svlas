package com.stevenwan.svlas.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.dto.stock.PersonStockTradeFlowAddDTO;
import com.stevenwan.svlas.entity.PersonStockTradeFlowEntity;
import com.stevenwan.svlas.entity.StockUserInfoEntity;
import com.stevenwan.svlas.mapper.PersonStockTradeFlowMapper;
import com.stevenwan.svlas.service.PersonStockTradeFlowService;
import com.stevenwan.svlas.service.StockUserInfoService;
import com.stevenwan.svlas.util.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 个人股票交易记录流水 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Service
public class PersonStockTradeFlowServiceImpl extends ServiceImpl<PersonStockTradeFlowMapper, PersonStockTradeFlowEntity> implements PersonStockTradeFlowService {
    @Autowired
    private StockUserInfoService stockUserInfoService;

    @Override
    @Transactional
    public Boolean addTradeFlowAndUpdateStockUserInfo(PersonStockTradeFlowAddDTO personStockTradeFlowAddDTO) {
        //save trade folw
        PersonStockTradeFlowEntity entity = new PersonStockTradeFlowEntity();
        BeanUtils.copyProperties(personStockTradeFlowAddDTO, entity);
        entity.setTradeTime(DateUtil.date());
        save(entity);
        //update stock user info and save user info record
        updateStockUserInfo(personStockTradeFlowAddDTO);
        return true;
    }

    private void updateStockUserInfo(PersonStockTradeFlowAddDTO personStockTradeFlowAddDTO) {
        StockUserInfoEntity stockUserInfo = stockUserInfoService.findByCode(personStockTradeFlowAddDTO.getCode());
        if (ObjectUtils.isNotNull(stockUserInfo)) {
            stockUserInfoService.updateStockUserInfo(personStockTradeFlowAddDTO.getCostPrice(), personStockTradeFlowAddDTO.getTotalNums(), stockUserInfo.getId());
        }
    }
}
