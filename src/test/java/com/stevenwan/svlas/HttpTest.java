package com.stevenwan.svlas;

import cn.hutool.http.HttpUtil;
import com.stevenwan.svlas.dto.stock.StockStrategyJobDTO;
import com.stevenwan.svlas.dto.stock.TencentStockModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-24 17:00
 */
public class HttpTest {
    public static void main(String[] args) {
//        String s = HttpUtil.get("https://qt.gtimg.cn/?q=s_sh600519");
//        System.out.println("s = " + s.substring(s.indexOf("=")+1));


//               String s = HttpUtil.get("https://qt.gtimg.cn/?q=s_sz161005");
//
//        String s1 = "1~贵州茅台~600519~2189.00~-118.00~-5.11~82116~1819503~~27498.17~GP-A";
//        String[] strings = s.split("~");
//        System.out.println(strings[1]);
//        System.out.println(strings[2]);
//        System.out.println(strings[3]);
//        System.out.println(strings[5]);
//
//        System.out.println("sz161005".substring(2));
//        String a = "v_s_sh600519=\"1~贵州茅台~600519~2150.00~-39.00~-1.78~59726~1298215~~27008.25~GP-A\"; v_s_sh601318=\"1~中国平安~601318~87.14~3.36~4.01~1897490~1639656~~15929.40~GP-A\";";
//        String s = HttpUtil.get("https://qt.gtimg.cn/?q=s_sz161005,s_sh601318,s_sh601166,s_sh601009");
//
//        String[] split = s.split(";");
//        for (String obj : split) {
//            String substring = obj.substring(obj.indexOf("=") + 1);
//            String[] strings = substring.split("~");
//
//            if (!strings[0].equals("\n")) {
//                System.out.println(strings[1]);
//                System.out.println(strings[2]);
//                System.out.println(strings[3]);
//                System.out.println(strings[5]);
//            }
//
//        }
//        StockStrategyJobDTO jobDTO = new StockStrategyJobDTO();
//        jobDTO.setCode("abcd");
//
//        TencentStockModel model = new TencentStockModel();
//        model.setCode("abc");
//        System.out.println(jobDTO.equals(model));

//        String responseDatas = HttpUtil.get("https://qt.gtimg.cn/?q=s_sz161005,s_sh601318,s_sh601166,s_sh601009");
//        List<TencentStockModel> stockModelList = new ArrayList<>();
//
//        String[] split = responseDatas.split(";");
//        for (String obj : split) {
//            String substring = obj.substring(obj.indexOf("=") + 1);
//            String[] strings = substring.split("~");
//
//            if (!strings[0].equals("\n")) {
//                TencentStockModel stockModel = new TencentStockModel();
//                stockModel.setCode(strings[2]);
//                stockModel.setCodeName(strings[1]);
//                stockModel.setPrice(new BigDecimal(strings[3]));
//                stockModel.setPerPriceVolatility(new BigDecimal(strings[5]));
//                stockModelList.add(stockModel);
//            }
//        }
//        stockModelList.forEach(System.out::println);
        List<StockStrategyJobDTO> strategyJobDTOList =new ArrayList<>();
        StockStrategyJobDTO a = new StockStrategyJobDTO();
        a.setCode("sh600615");
        strategyJobDTOList.add(a);
        StockStrategyJobDTO b = new StockStrategyJobDTO();
        b.setCode("sh600616");
        strategyJobDTOList.add(b);
        String codeList = strategyJobDTOList.stream().map(stockStrategyJobDTO -> "s_".concat(stockStrategyJobDTO.getCode())).collect(Collectors.joining(","));
        System.out.println(codeList);
    }
}
