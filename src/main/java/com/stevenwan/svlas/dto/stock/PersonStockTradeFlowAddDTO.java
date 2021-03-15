package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-26 09:51
 */
@Data
public class PersonStockTradeFlowAddDTO {

    /**
     * 用户ID
     */
    @NotNull(message = "{userId is null}")
    private Long userId;

    /**
     * 股票代码
     */
    @NotBlank(message = "{volume is null}")
    private String code;

    /**
     * 交易价格
     */
    @NotNull(message = "{price is null}")
    private BigDecimal price;

    /**
     * 交易股数
     */
    @NotNull(message = "{nums is null}")
    private Integer nums;

    /**
     * 交易原因
     */
    @NotBlank(message = "{volume is null}")
    private String volume;

    /**
     * 操作类型 int 1 买 0 卖
     */
    @NotNull(message = "{opeType is null}")
    private Integer opeType;

    /**
     * 股票持仓：成本价
     */
    @NotNull(message = "{costPrice is null}")
    private BigDecimal costPrice;

    /**
     * 股票持仓：总股数
     */
    @NotNull(message = "{totalNums is null}")
    private Integer totalNums;
}
