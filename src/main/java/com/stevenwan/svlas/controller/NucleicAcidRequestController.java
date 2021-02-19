package com.stevenwan.svlas.controller;


import com.stevenwan.svlas.common.BaseEntity;
import com.stevenwan.svlas.common.ResultData;
import com.stevenwan.svlas.dto.hsjc.NucleicAcidRequestAndEmployeeAddDTO;
import com.stevenwan.svlas.dto.hsjc.NucleicAcidRequestSelectDTO;
import com.stevenwan.svlas.service.NucleicAcidRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 核酸检测申请表 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-04
 */
@RestController
@RequestMapping("/nucleicAcidRequest")
@AllArgsConstructor
public class NucleicAcidRequestController {

    private NucleicAcidRequestService nucleicAcidRequestService;

    /**
     * 预约检测申请（如果没有注册需要先员工注册再去预期申请）
     *
     * @param nucleicAcidRequestAndEmployeeAddDTO
     * @return
     */
    @PostMapping("/nucleicAcidRequestAndAddEmployee")
    public ResultData nucleicAcidRequestAndAddEmployee(@Valid @RequestBody NucleicAcidRequestAndEmployeeAddDTO nucleicAcidRequestAndEmployeeAddDTO) {
        return ResultData.data(nucleicAcidRequestService.nucleicAcidRequestAndAddEmployee(nucleicAcidRequestAndEmployeeAddDTO));
    }

    /**
     * 取消预约检测申请
     *
     * @param cancel
     * @return
     */
    @PostMapping("/cancelNucleicAcidRequest")
    public ResultData cancelNucleicAcidRequest(@Valid @RequestBody BaseEntity cancel) {
        return ResultData.data(nucleicAcidRequestService.cancelNucleicAcidRequest(cancel.getId()));
    }

    /**
     * 查询预约申请列表
     *
     * @param requestSelectDTO
     * @return
     */
    @PostMapping("/selectNucleicAcidRequestList")
    public ResultData selectNucleicAcidRequestList(@Valid @RequestBody NucleicAcidRequestSelectDTO requestSelectDTO) {
        return ResultData.data(nucleicAcidRequestService.selectNucleicAcidRequestList(requestSelectDTO.getOpenId(), requestSelectDTO.getRequestStatus()));
    }
}

