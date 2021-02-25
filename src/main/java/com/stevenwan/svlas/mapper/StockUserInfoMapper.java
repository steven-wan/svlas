package com.stevenwan.svlas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stevenwan.svlas.entity.StockUserInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户股票市值信息表 Mapper 接口
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface StockUserInfoMapper extends BaseMapper<StockUserInfoEntity> {

    @Select("select * from stock_user_info where code=#{code} ")
    StockUserInfoEntity findByCode(@Param("code") String code);
}
