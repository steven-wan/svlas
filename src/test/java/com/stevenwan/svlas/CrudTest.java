package com.stevenwan.svlas;

import com.stevenwan.svlas.service.FundAutoPlanService;
import com.stevenwan.svlas.service.StockStrategyService;
import com.stevenwan.svlas.service.StockUserInfoService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-26 14:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrudTest {
    @Autowired
    FundAutoPlanService fundAutoPlanService;
    @Autowired
    StockStrategyService stockStrategyService;
    @Autowired
    private StockUserInfoService stockUserInfoService;


}
