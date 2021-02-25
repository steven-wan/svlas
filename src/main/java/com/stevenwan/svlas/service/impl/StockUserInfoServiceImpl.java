package com.stevenwan.svlas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stevenwan.svlas.entity.StockUserInfoEntity;
import com.stevenwan.svlas.mapper.StockUserInfoMapper;
import com.stevenwan.svlas.service.StockUserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户股票市值信息表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Service
public class StockUserInfoServiceImpl extends ServiceImpl<StockUserInfoMapper, StockUserInfoEntity> implements StockUserInfoService {

    @Override
    public StockUserInfoEntity findByCode(String code) {
        return baseMapper.findByCode(code);
    }
}
