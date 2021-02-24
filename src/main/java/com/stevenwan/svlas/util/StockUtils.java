package com.stevenwan.svlas.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.http.HttpUtil;
import com.stevenwan.svlas.common.StockCSVModel;
import com.stevenwan.svlas.dto.stock.TencentStockModel;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-01 10:02
 */
public class StockUtils {

    /**
     * 通过网易途径获取原始数据，不能获取港股，美股数据
     *
     * @param url
     * @param code
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<StockCSVModel> stockHistoryDataByNetEase(String url, String code, String startDate, String endDate) {
        ObjectUtils.isNullThrowsExcetion(code, "stock code is null");
        ObjectUtils.isNullThrowsExcetion(url, "url is null");
        if (StringUtils.isBlank(startDate)) {
            startDate = "20000101";
        }
        if (StringUtils.isBlank(endDate)) {
            endDate = DateUtil.format(new Date(), "yyyyMMdd");
        }
        String prefix = code.substring(0, 2);
        String codeNetEase = code.substring(2);
        if ("sh".equals(prefix)) {
            codeNetEase = "0" + codeNetEase;
        } else if ("sz".equals(prefix)) {
            codeNetEase = "1" + codeNetEase;
        } else {
            throw new RuntimeException("网易通道不支持其它数据");
        }

        url = url.replace("CODE", codeNetEase).replace("START", startDate).replace("END", endDate);

        byte[] bytes = HttpUtil.downloadBytes(url);
        CsvReader reader = CsvUtil.getReader();
        return reader.read(IoUtil.getReader(new ByteArrayInputStream(bytes), Charset.forName("GBK")), StockCSVModel.class);
    }


    public static TencentStockModel tencentTimeData(String url, String code) {
        String responseDatas = HttpUtil.get(url.concat(code));

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
        return stockModel;
    }
}
