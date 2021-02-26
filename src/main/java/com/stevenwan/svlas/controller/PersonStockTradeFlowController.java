package com.stevenwan.svlas.controller;


import cn.hutool.core.date.DateUtil;
import com.stevenwan.svlas.dto.stock.PersonStockTradeFlowAddDTO;
import com.stevenwan.svlas.entity.PersonStockTradeFlowEntity;
import com.stevenwan.svlas.service.PersonStockTradeFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 个人股票交易记录流水 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/personStockTradeFlow")
@RequiredArgsConstructor
public class PersonStockTradeFlowController {

    private final PersonStockTradeFlowService personStockTradeFlowService;

    /**
     * 添加个人股票交易成交记录
     * @param personStockTradeFlowAddDTO
     * @return
     */
    @PostMapping("/addTradeFlow")
    @ResponseBody
    public Boolean addTradeFlow(@RequestBody PersonStockTradeFlowAddDTO personStockTradeFlowAddDTO) {
        PersonStockTradeFlowEntity entity = new PersonStockTradeFlowEntity();
        BeanUtils.copyProperties(personStockTradeFlowAddDTO, entity);
        entity.setTradeTime(DateUtil.date());
        return personStockTradeFlowService.save(entity);
    }
}

