package com.stevenwan.svlas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stevenwan.svlas.entity.StockEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 股票表 Mapper 接口
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
public interface StockMapper extends BaseMapper<StockEntity> {
    @Select("select * from stock where code =#{code}")
    StockEntity findByCode(@Param("code") String code);
}
