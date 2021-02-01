package com.stevenwan.svlas.controller;


import com.stevenwan.svlas.dto.stock.StockUserStrategyDTO;
import com.stevenwan.svlas.service.StockUserStrategyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户股票策略表 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/stockUserStrategy")
@AllArgsConstructor
public class StockUserStrategyController {

    private StockUserStrategyService stockUserStrategyService;

    @PostMapping("/addStockUserStrategy")
    @ResponseBody
    public Boolean addStockUserStrategy(@RequestBody StockUserStrategyDTO userStrategyDTO) {
        return stockUserStrategyService.saveUserStrategy(userStrategyDTO);
    }
}

