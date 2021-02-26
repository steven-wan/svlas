package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-26 10:03
 */
@Data
public class FundAutoPlanUpdateDTO extends FundAutoPlanAddDTO{
    /**
     * id
     */
    @NotNull(message = "id is null")
    private Long id;

}
