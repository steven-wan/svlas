package com.stevenwan.svlas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stevenwan.svlas.entity.StockUserStrategyEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户股票策略表 Mapper 接口
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface StockUserStrategyMapper extends BaseMapper<StockUserStrategyEntity> {

    @Select("select * from stock_user_strategy where strategy_id=#{strategyId}")
    StockUserStrategyEntity selectByStrategyId(@Param("strategyId") Long strategyId);
}
