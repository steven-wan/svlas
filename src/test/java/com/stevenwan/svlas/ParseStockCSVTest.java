package com.stevenwan.svlas;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.http.HttpUtil;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author steven-wan
 * @desc
 * @date 2021-01-29 15:39
 */
public class ParseStockCSVTest {
    public static void main(String[] args){

        byte[] bytes = HttpUtil.downloadBytes("http://quotes.money.163.com/service/chddata.html?code=0600009&start=20210110&end=20210119&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP");
        final CsvReader reader = CsvUtil.getReader();

        final List<StockCSVModel> result = reader.read(IoUtil.getReader(new ByteArrayInputStream(bytes), Charset.forName("GBK")), StockCSVModel.class);
        result.forEach(System.out::println);

    }
}
