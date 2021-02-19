package com.stevenwan.svlas.controller;


import com.stevenwan.svlas.common.ResultData;
import com.stevenwan.svlas.dto.hsjc.NucleicAcidRequestSelectDTO;
import com.stevenwan.svlas.service.NucleicAcidReportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 核酸报告 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-04
 */
@RestController
@RequestMapping("/nucleicAcidReport")
@AllArgsConstructor
public class NucleicAcidReportController {

    private NucleicAcidReportService nucleicAcidReportService;
    /**
     * 查询核算结果列表
     *
     * @param requestSelectDTO
     * @return
     */
    @PostMapping("/selectNucleicAcidReportList")
    public ResultData selectNucleicAcidReportList(@RequestBody NucleicAcidRequestSelectDTO requestSelectDTO) {
        return ResultData.data(nucleicAcidReportService.selectNucleicAcidReportList(requestSelectDTO.getOpenId()));
    }
}

