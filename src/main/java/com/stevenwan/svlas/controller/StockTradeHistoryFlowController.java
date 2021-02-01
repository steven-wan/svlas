package com.stevenwan.svlas.controller;


import com.stevenwan.svlas.dto.stock.StockInitHistoryDataDTO;
import com.stevenwan.svlas.service.StockTradeHistoryFlowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 股票交易历史流水表 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/stockTradeHistoryFlow")
@AllArgsConstructor
public class StockTradeHistoryFlowController {
    private StockTradeHistoryFlowService stockTradeHistoryFlowService;

    @PostMapping("/initHistoryDataStock")
    @ResponseBody
    public Boolean initHistoryDataStock(@RequestBody StockInitHistoryDataDTO initHistoryDataDTO) {
        return stockTradeHistoryFlowService.initHistoryDataStock(initHistoryDataDTO);
    }
}

