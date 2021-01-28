package com.stevenwan.svlas.service.impl;

import com.stevenwan.svlas.entity.UserEntity;
import com.stevenwan.svlas.mapper.UserMapper;
import com.stevenwan.svlas.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

}
