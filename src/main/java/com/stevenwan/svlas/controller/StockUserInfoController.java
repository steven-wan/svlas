package com.stevenwan.svlas.controller;


import cn.hutool.core.date.DateUtil;
import com.stevenwan.svlas.dto.stock.StockUserInUpdateDTO;
import com.stevenwan.svlas.dto.stock.StockUserInfoAddDTO;
import com.stevenwan.svlas.entity.StockUserInfoEntity;
import com.stevenwan.svlas.service.StockUserInfoService;
import com.stevenwan.svlas.util.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户股票市值信息表 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/stockUserInfo")
@RequiredArgsConstructor
public class StockUserInfoController {

    private final StockUserInfoService stockUserInfoService;

    @PostMapping("/addStockUserInfo")
    public Boolean addStockUserInfo(@RequestBody @Validated StockUserInfoAddDTO stockUserInfoAddDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        return stockUserInfoService.saveStockUserInfoAndRecord(stockUserInfoAddDTO);
    }

    @GetMapping("/deleteStockUserInfo")
    public Boolean deleteStockUserInfo(@Param("id") Long id) {
        StockUserInfoEntity entity = stockUserInfoService.getById(id);
        ObjectUtils.isNullThrowsExcetion(entity, "错误的 id");
        entity.setUpdateTime(DateUtil.date());
        return stockUserInfoService.removeById(entity);
    }

    @PostMapping("/updateStockUserInfo")
    public Boolean updateStockUserInfo(@RequestBody @Validated StockUserInUpdateDTO userInUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        return stockUserInfoService.updateStockUserInfo(userInUpdateDTO.getCostPrice(), userInUpdateDTO.getNums(), userInUpdateDTO.getId());
    }
}

