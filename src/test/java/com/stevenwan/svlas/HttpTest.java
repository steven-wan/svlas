package com.stevenwan.svlas;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.stevenwan.svlas.dto.stock.TencentStockModel;

import java.math.BigDecimal;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-24 17:00
 */
public class HttpTest {
    public static void main(String[] args) {
//        String s = HttpUtil.get("https://qt.gtimg.cn/?q=s_sh600519");
//        System.out.println("s = " + s.substring(s.indexOf("=")+1));


        //sh600519  hz01610
        String responseDatas = HttpUtil.get("https://qt.gtimg.cn/?q=s_hk01610");

        System.out.println(responseDatas);
        String[] strings = responseDatas.split("~");
        System.out.println(strings[1]);
        System.out.println(strings[2]);
        System.out.println(strings[3]);
        System.out.println(strings[5]);
        TencentStockModel stockModel = new TencentStockModel();
        stockModel.setCode(strings[2]);
        stockModel.setCodeName(strings[1]);
        stockModel.setPrice(new BigDecimal(strings[3]));
        stockModel.setPerPriceVolatility(new BigDecimal(strings[5]));

        System.out.println("JSON.toJSONString(stockModel) = " + JSON.toJSONString(stockModel));


    }
}
