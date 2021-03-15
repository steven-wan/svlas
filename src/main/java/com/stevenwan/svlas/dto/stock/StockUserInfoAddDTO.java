package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-26 10:17
 */
@Data
public class StockUserInfoAddDTO {
    /**
     * 股票代码
     */
    @NotBlank(message = "code is null")
    private String code;

    /**
     * 用户ID
     */
    @NotNull(message = "userId is null")
    private Long userId;

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
