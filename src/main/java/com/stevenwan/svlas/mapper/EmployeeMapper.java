package com.stevenwan.svlas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stevenwan.svlas.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 员工表 Mapper 接口
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-04
 */
public interface EmployeeMapper extends BaseMapper<EmployeeEntity> {

    @Select("select * from employee where personal_id=#{openid}")
    EmployeeEntity findByOpenId(@Param("openid") String openid);
}
