package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author steven-wan
 * @desc
 * @date 2021-03-15 11:11
 */
@Data
public class StockUserInUpdateDTO {
    /**
     * ID
     */
    @NotNull(message = "id is null")
    private Long id;

    /**
     * 成本价
     */
    @NotNull(message = "costPrice is null")
    private BigDecimal costPrice;


    /**
     * 股数
     */
    @NotNull(message = "nums is null")
    private Integer nums;
}
