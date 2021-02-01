package com.stevenwan.svlas.controller;


import cn.hutool.core.date.DateUtil;
import com.stevenwan.svlas.dto.stock.StockStrategyDTO;
import com.stevenwan.svlas.entity.StockStrategyEntity;
import com.stevenwan.svlas.service.StockStrategyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 股票策略表 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/stockStrategy")
@AllArgsConstructor
public class StockStrategyController {

    private StockStrategyService strategyService;

    @PostMapping("/addStockStrategy")
    @ResponseBody
    public Boolean addStockStrategy(@RequestBody StockStrategyDTO stockStrategyDTO) {
        StockStrategyEntity entity = new StockStrategyEntity();
        BeanUtils.copyProperties(stockStrategyDTO, entity);
        entity.setCreateTime(DateUtil.date());
        return strategyService.save(entity);
    }
}

