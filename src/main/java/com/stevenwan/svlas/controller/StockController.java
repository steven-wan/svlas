package com.stevenwan.svlas.controller;


import com.stevenwan.svlas.dto.stock.StockInitHistoryDataDTO;
import com.stevenwan.svlas.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 股票表 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/stockEntity")
@AllArgsConstructor
public class StockController {

    private StockService stockService;

    @PostMapping("/initHistoryDataStock")
    @ResponseBody
    public Boolean initHistoryDataStock(@RequestBody StockInitHistoryDataDTO initHistoryDataDTO){
        return stockService.initHistoryDataStock(initHistoryDataDTO);
    }
}

