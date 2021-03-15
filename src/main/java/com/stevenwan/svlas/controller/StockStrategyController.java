package com.stevenwan.svlas.controller;


import com.stevenwan.svlas.common.HsjcConstant;
import com.stevenwan.svlas.dto.stock.StockUserStrategyDTO;
import com.stevenwan.svlas.dto.stock.StockUserStrategyUpdateDTO;
import com.stevenwan.svlas.entity.StockStrategyEntity;
import com.stevenwan.svlas.service.StockStrategyService;
import com.stevenwan.svlas.service.StockUserStrategyService;
import com.stevenwan.svlas.util.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    private StockStrategyService stockStrategyService;

    /**
     * 新增用户股票策略
     *
     * @param stockUserStrategyDTO
     * @return
     */
    @PostMapping("/addStockStrategy")
    public Boolean addStockStrategy(@RequestBody StockUserStrategyDTO stockUserStrategyDTO) {
        return stockUserStrategyService.saveUserStrategy(stockUserStrategyDTO);
    }


    /**
     * 更新用户股票策略
     *
     * @param userStrategyUpdateDTO
     * @return
     */
    @PostMapping("/updateStockStrategy")
    public Boolean updateStockStrategy(@RequestBody @Validated StockUserStrategyUpdateDTO userStrategyUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        StockStrategyEntity stockStrategyEntity = stockStrategyService.getById(userStrategyUpdateDTO.getId());
        ObjectUtils.isNullThrowsExcetion(stockStrategyEntity, "错误的 id");
        stockStrategyEntity.setStatus(HsjcConstant.STOCK_STRATEGY_STATUS_EXCUTEING);
        stockStrategyEntity.setPriceAnchor(userStrategyUpdateDTO.getPriceAnchor());
        stockStrategyEntity.setPerPriceVolatility(userStrategyUpdateDTO.getPerPriceVolatility());

        return stockStrategyService.updateById(stockStrategyEntity);
    }

    /**
     * 删除股票策略
     * @param id
     * @return
     */
    @GetMapping("deleteStockStrategy")
    public Boolean deleteStockStrategy(@RequestParam Long id) {
        return stockUserStrategyService.deleteStockStrategy(id);
    }
}

