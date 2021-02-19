package com.stevenwan.svlas.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.common.HsjcConstant;
import com.stevenwan.svlas.dto.hsjc.NucleicAcidRequestAndEmployeeAddDTO;
import com.stevenwan.svlas.dto.hsjc.NucleicAcidRequestSelectRetDTO;
import com.stevenwan.svlas.entity.EmployeeEntity;
import com.stevenwan.svlas.entity.NucleicAcidRequestEntity;
import com.stevenwan.svlas.mapper.NucleicAcidRequestMapper;
import com.stevenwan.svlas.service.EmployeeService;
import com.stevenwan.svlas.service.NucleicAcidRequestService;
import com.stevenwan.svlas.service.SmsService;
import com.stevenwan.svlas.util.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 核酸检测申请表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-04
 */
@Service
@AllArgsConstructor
public class NucleicAcidRequestServiceImpl extends ServiceImpl<NucleicAcidRequestMapper, NucleicAcidRequestEntity> implements NucleicAcidRequestService {
    private EmployeeService employeeService;

    private SmsService smsService;

    @Override
    public Boolean nucleicAcidRequestAndAddEmployee(NucleicAcidRequestAndEmployeeAddDTO nucleicAcidRequestAndEmployeeAddDTO) {
        verifyCode(nucleicAcidRequestAndEmployeeAddDTO.getMobile(), nucleicAcidRequestAndEmployeeAddDTO.getVerificatCode());

        EmployeeEntity employeeEntity = employeeService.findByOpenId(nucleicAcidRequestAndEmployeeAddDTO.getPersonalId());
        if (ObjectUtils.isNotNull(employeeEntity)) {
            return nucleicAcidRequest(employeeEntity.getId());
        }
        return addEmplyoyeeAndNucleicAcidReqeuest(nucleicAcidRequestAndEmployeeAddDTO);
    }

    @Override
    public NucleicAcidRequestEntity findByEmployeeId(Long employeeId) {
        return baseMapper.findByEmployeeId(employeeId);
    }

    @Override
    public Boolean cancelNucleicAcidRequest(Long id) {
        NucleicAcidRequestEntity nucleicAcidRequestEntity = baseMapper.selectById(id);
        ObjectUtils.isNullThrowsExcetion(nucleicAcidRequestEntity, "错误的 ID");
        if (!HsjcConstant.NUCLEIC_ACID_REQUEST_STATUS_REQUESTING.equals(nucleicAcidRequestEntity.getRequestStatus())) {
            throw new RuntimeException("预约状态有问题，不能取消");
        }
        nucleicAcidRequestEntity.setRequestStatus(HsjcConstant.NUCLEIC_ACID_REQUEST_STATUS_CANCEL);
        nucleicAcidRequestEntity.setRequestTime(DateUtil.date());
        nucleicAcidRequestEntity.setRemark("个人原因拒绝");
        return updateById(nucleicAcidRequestEntity);
    }

    @Override
    public List<NucleicAcidRequestSelectRetDTO> selectNucleicAcidRequestList(String openId, Integer requestStatus) {
        return baseMapper.selectNucleicAcidRequestList(openId, requestStatus);
    }

    @Transactional
    public Boolean addEmplyoyeeAndNucleicAcidReqeuest(NucleicAcidRequestAndEmployeeAddDTO nucleicAcidRequestAndEmployeeAddDTO) {
        ObjectUtils.isNullThrowsExcetion(nucleicAcidRequestAndEmployeeAddDTO.getName(), "name is null");
        ObjectUtils.isNullThrowsExcetion(nucleicAcidRequestAndEmployeeAddDTO.getIdcardNo(), "idcardNo is null");
        ObjectUtils.isNullThrowsExcetion(nucleicAcidRequestAndEmployeeAddDTO.getCompanyId(), "companyId is null");

        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(nucleicAcidRequestAndEmployeeAddDTO, employeeEntity);
        employeeEntity.setCreateTime(DateUtil.date());
        employeeService.save(employeeEntity);
        return nucleicAcidRequest(employeeEntity.getId());
    }

    /**
     * 预期核酸申请
     *
     * @param employeeId
     * @return
     */
    private Boolean nucleicAcidRequest(Long employeeId) {
        NucleicAcidRequestEntity nucleicAcidRequestEntity = baseMapper.findByEmployeeId(employeeId);
        if (ObjectUtils.isNotNull(nucleicAcidRequestEntity)) {
            throw new RuntimeException("已经在申请中，不能重复申请");
        }
        NucleicAcidRequestEntity entity = new NucleicAcidRequestEntity();
        entity.setEmployeeId(employeeId);
        entity.setCreateTime(DateUtil.date());
        entity.setRequestStatus(HsjcConstant.NUCLEIC_ACID_REQUEST_STATUS_REQUESTING);
        return save(entity);
    }

    /**
     * 短信验证
     *
     * @param verifyCode
     * @param mobile
     */
    private void verifyCode(String mobile, String verifyCode) {
        smsService.verify(mobile, verifyCode);
    }
}
