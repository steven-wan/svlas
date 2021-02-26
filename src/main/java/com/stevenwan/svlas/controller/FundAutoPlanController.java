package com.stevenwan.svlas.controller;


import cn.hutool.core.date.DateUtil;
import com.stevenwan.svlas.common.HsjcConstant;
import com.stevenwan.svlas.dto.stock.FundAutoPlanAddDTO;
import com.stevenwan.svlas.dto.stock.FundAutoPlanUpdateDTO;
import com.stevenwan.svlas.entity.FundAutoPlanEntity;
import com.stevenwan.svlas.entity.FundAutoPlanRecordEntity;
import com.stevenwan.svlas.service.FundAutoPlanRecordService;
import com.stevenwan.svlas.service.FundAutoPlanService;
import com.stevenwan.svlas.util.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 基金定投表 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/fundAutoPlan")
@RequiredArgsConstructor
public class FundAutoPlanController {

    private final FundAutoPlanService fundAutoPlanService;

    private final FundAutoPlanRecordService fundAutoPlanRecordService;

    @PostMapping("/addFundAutoPlan")
    @ResponseBody
    public Boolean addFundAutoPlan(@RequestBody FundAutoPlanAddDTO fundAutoPlanAddDTO) {
        FundAutoPlanEntity entity = new FundAutoPlanEntity();
        BeanUtils.copyProperties(fundAutoPlanAddDTO, entity);
        entity.setCreateTime(DateUtil.date());
        entity.setUpdateTime(DateUtil.date());
        entity.setStatus(HsjcConstant.FUND_AUTO_PLAN_STATUS_EXCUTEING);
        fundAutoPlanService.save(entity);

        FundAutoPlanRecordEntity recordEntity = new FundAutoPlanRecordEntity();
        BeanUtils.copyProperties(entity, recordEntity, new String[]{"id"});

        return fundAutoPlanRecordService.save(recordEntity);
    }

    @PostMapping("/updateFundAutoPlan")
    @ResponseBody
    public Boolean updateFundAutoPlan(@Valid @RequestBody FundAutoPlanUpdateDTO fundAutoPlanUpdateDTO) {
        FundAutoPlanEntity fundAutoPlanEntity = fundAutoPlanService.getById(fundAutoPlanUpdateDTO.getId());
        ObjectUtils.isNullThrowsExcetion(fundAutoPlanEntity, "错误的 id");
        BeanUtils.copyProperties(fundAutoPlanUpdateDTO, fundAutoPlanEntity, new String[]{"id"});
        fundAutoPlanEntity.setUpdateTime(DateUtil.date());
        fundAutoPlanService.updateById(fundAutoPlanEntity);

        FundAutoPlanRecordEntity recordEntity = new FundAutoPlanRecordEntity();
        BeanUtils.copyProperties(fundAutoPlanEntity, recordEntity, new String[]{"id"});
        return fundAutoPlanRecordService.save(recordEntity);
    }

    @GetMapping("/deleteFundAutoPlan")
    @ResponseBody
    public Boolean deleteFundAutoPlan(@Param("id") Long id) {
        FundAutoPlanEntity fundAutoPlanEntity = fundAutoPlanService.getById(id);
        ObjectUtils.isNullThrowsExcetion(fundAutoPlanEntity, "错误的 id");
        fundAutoPlanEntity.setUpdateTime(DateUtil.date());
        fundAutoPlanEntity.setStatus(HsjcConstant.FUND_AUTO_PLAN_STATUS_STOP);

        return fundAutoPlanService.updateById(fundAutoPlanEntity);
    }
}

