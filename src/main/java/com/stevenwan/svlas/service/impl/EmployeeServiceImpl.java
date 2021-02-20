package com.stevenwan.svlas.service.impl;

import com.stevenwan.svlas.entity.EmployeeEntity;
import com.stevenwan.svlas.mapper.EmployeeMapper;
import com.stevenwan.svlas.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-20
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeeEntity> implements EmployeeService {

}
