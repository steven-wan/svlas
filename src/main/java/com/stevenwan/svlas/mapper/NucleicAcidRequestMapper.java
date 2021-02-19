package com.stevenwan.svlas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stevenwan.svlas.dto.hsjc.NucleicAcidRequestSelectRetDTO;
import com.stevenwan.svlas.entity.NucleicAcidRequestEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 核酸检测申请表 Mapper 接口
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-04
 */
public interface NucleicAcidRequestMapper extends BaseMapper<NucleicAcidRequestEntity> {

    @Select("select * from nucleic_acid_request where employee_id =#{employeeId} and request_status= 1")
    NucleicAcidRequestEntity findByEmployeeId(@Param("employeeId") Long employeeId);

    List<NucleicAcidRequestSelectRetDTO> selectNucleicAcidRequestList(String openId, Integer requestStatus);
}
