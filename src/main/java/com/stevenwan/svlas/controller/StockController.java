package com.stevenwan.svlas.controller;


import com.stevenwan.svlas.dto.stock.StockDTO;
import com.stevenwan.svlas.entity.StockEntity;
import com.stevenwan.svlas.service.StockService;
import com.stevenwan.svlas.service.StockUserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/stock")
@AllArgsConstructor
public class StockController {

    private StockService stockService;


    private StockUserInfoService stockUserInfoService;

    @PostMapping("/addStock")
    public Boolean addStock(@RequestBody StockDTO stockDTO) {
        StockEntity entity = new StockEntity();
        BeanUtils.copyProperties(stockDTO, entity);
        return stockService.save(entity);
    }

    @GetMapping("/getStockRate")
    public Boolean getStockRate(@RequestParam("userId") Long userId) {
        stockUserInfoService.getStockUserInfo(userId);
        return Boolean.TRUE;
    }
}

