package com.stevenwan.svlas;

import cn.hutool.http.HttpUtil;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-24 17:00
 */
public class HttpTest {
    public static void main(String[] args) {
//        String s = HttpUtil.get("https://qt.gtimg.cn/?q=s_sh600519");
//        System.out.println("s = " + s.substring(s.indexOf("=")+1));



               String s = HttpUtil.get("https://qt.gtimg.cn/?q=s_sz161005");

        String s1 = "1~贵州茅台~600519~2189.00~-118.00~-5.11~82116~1819503~~27498.17~GP-A";
        String[] strings = s.split("~");
        System.out.println(strings[1]);
        System.out.println(strings[2]);
        System.out.println(strings[3]);
        System.out.println(strings[5]);

        System.out.println("sz161005".substring(2));
    }
}
