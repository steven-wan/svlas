package com.stevenwan.svlas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stevenwan.svlas.entity.StockUserInfoRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户股票市值变更表 Mapper 接口
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface StockUserInfoRecordMapper extends BaseMapper<StockUserInfoRecordEntity> {

    @Select("select * from stock_user_info_record where code=#{code} and DATE_FORMAT(create_time,'%Y-%m-%d') =#{date}")
    StockUserInfoRecordEntity findByCodeAndDate(@Param("code") String code, @Param("date") String date);
}
