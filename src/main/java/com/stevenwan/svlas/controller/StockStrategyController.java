package com.stevenwan.svlas.controller;


import com.stevenwan.svlas.dto.stock.StockUserStrategyDTO;
import com.stevenwan.svlas.service.StockUserStrategyService;
import lombok.AllArgsConstructor;
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


    private StockUserStrategyService stockUserStrategyService;

    /**
     * 新增用户股票策略
     *
     * @param stockUserStrategyDTO
     * @return
     */
    @PostMapping("/addStockStrategy")
    @ResponseBody
    public Boolean addStockStrategy(@RequestBody StockUserStrategyDTO stockUserStrategyDTO) {
        return stockUserStrategyService.saveUserStrategy(stockUserStrategyDTO);
    }

}

