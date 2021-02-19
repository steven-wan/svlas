package com.stevenwan.svlas.service;

import com.stevenwan.svlas.entity.EmployeeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 员工表 服务类
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-04
 */
public interface EmployeeService extends IService<EmployeeEntity> {

    /**
     * 根据 openid 找到绑定的那个人
     * @param openid
     * @return
     */
    EmployeeEntity findByOpenId(String openid);
}
