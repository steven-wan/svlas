package com.stevenwan.svlas.service.impl;

import com.stevenwan.svlas.entity.StockUserInfoEntity;
import com.stevenwan.svlas.entity.StockUserInfoRecordEntity;
import com.stevenwan.svlas.mapper.StockUserInfoRecordMapper;
import com.stevenwan.svlas.service.StockUserInfoRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户股票市值变更表 服务实现类
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@Service
public class StockUserInfoRecordServiceImpl extends ServiceImpl<StockUserInfoRecordMapper, StockUserInfoRecordEntity> implements StockUserInfoRecordService {
    @Override
    public StockUserInfoRecordEntity findByCodeAndDate(String code, String date) {
        return baseMapper.findByCodeAndDate(code,date);
    }
}
