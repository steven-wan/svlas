package com.stevenwan.svlas.dto.stock;

import com.stevenwan.svlas.entity.StockUserInfoEntity;
import lombok.Data;

import java.util.Objects;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-01 17:53
 */
@Data
public class StockUserInfoJobDTO extends StockUserInfoEntity {

    /**
     * 类型 FUND 基金，STOCK 股票，期货，黄金
     */
    private String type;


    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        TencentStockModel that = (TencentStockModel) o;
        return Objects.equals(super.code.substring(2), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.code);
    }
}
