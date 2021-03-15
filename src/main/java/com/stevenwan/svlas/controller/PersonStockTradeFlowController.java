package com.stevenwan.svlas.controller;


import com.stevenwan.svlas.dto.stock.PersonStockTradeFlowAddDTO;
import com.stevenwan.svlas.service.PersonStockTradeFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 个人股票交易记录流水 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/personStockTradeFlow")
@RequiredArgsConstructor
public class PersonStockTradeFlowController {

    private final PersonStockTradeFlowService personStockTradeFlowService;

    /**
     * 添加个人股票交易成交记录
     *
     * @param personStockTradeFlowAddDTO
     * @return
     */
    @PostMapping("/addTradeFlow")
    public Boolean addTradeFlow(@Validated @RequestBody PersonStockTradeFlowAddDTO personStockTradeFlowAddDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        return personStockTradeFlowService.addTradeFlowAndUpdateStockUserInfo(personStockTradeFlowAddDTO);
    }
}

