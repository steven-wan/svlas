package com.stevenwan.svlas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stevenwan.svlas.dto.hsjc.NucleicAcidRequestAndEmployeeAddDTO;
import com.stevenwan.svlas.dto.hsjc.NucleicAcidRequestSelectRetDTO;
import com.stevenwan.svlas.entity.NucleicAcidRequestEntity;

import java.util.List;

/**
 * <p>
 * 核酸检测申请表 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-04
 */
public interface NucleicAcidRequestService extends IService<NucleicAcidRequestEntity> {

    /**
     * 预约检测申请（如果没有注册需要先员工注册再去预期申请）
     *
     * @param nucleicAcidRequestAndEmployeeAddDTO
     * @return
     */
    Boolean nucleicAcidRequestAndAddEmployee(NucleicAcidRequestAndEmployeeAddDTO nucleicAcidRequestAndEmployeeAddDTO);

    /**
     * 根据员工信息查询核酸申请信息
     *
     * @param employeeId
     * @return
     */
    NucleicAcidRequestEntity findByEmployeeId(Long employeeId);

    /**
     * 取消预约申请
     *
     * @param id
     * @return
     */
    Boolean cancelNucleicAcidRequest(Long id);

    /**
     * 查询申请列表
     *
     * @param openId
     * @param requestStatus
     * @return
     */
    List<NucleicAcidRequestSelectRetDTO> selectNucleicAcidRequestList(String openId, Integer requestStatus);
}
