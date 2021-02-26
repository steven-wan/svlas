package com.stevenwan.svlas.controller;


import cn.hutool.core.date.DateUtil;
import com.stevenwan.svlas.dto.stock.StockUserInfoAddDTO;
import com.stevenwan.svlas.entity.StockUserInfoEntity;
import com.stevenwan.svlas.entity.StockUserInfoRecordEntity;
import com.stevenwan.svlas.service.StockUserInfoRecordService;
import com.stevenwan.svlas.service.StockUserInfoService;
import com.stevenwan.svlas.util.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
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


    private final StockUserInfoRecordService stockUserInfoRecordService;

    @PostMapping("/addStockUserInfo")
    @ResponseBody
    public Boolean addStockUserInfo(@RequestBody StockUserInfoAddDTO stockUserInfoAddDTO) {
        StockUserInfoEntity entity = new StockUserInfoEntity();
        BeanUtils.copyProperties(stockUserInfoAddDTO, entity);
        entity.setCreateTime(DateUtil.date());
        entity.setUpdateTime(DateUtil.date());
        stockUserInfoService.save(entity);

        StockUserInfoRecordEntity recordEntity = new StockUserInfoRecordEntity();
        BeanUtils.copyProperties(entity, recordEntity, new String[]{"id"});

        return stockUserInfoRecordService.save(recordEntity);
    }

    @GetMapping("/deleteStockUserInfo")
    @ResponseBody
    public Boolean deleteStockUserInfo(@Param("id") Long id) {
        StockUserInfoEntity entity = stockUserInfoService.getById(id);
        ObjectUtils.isNullThrowsExcetion(entity, "错误的 id");
        entity.setUpdateTime(DateUtil.date());
        return stockUserInfoService.removeById(entity);
    }
}

