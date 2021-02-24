package com.stevenwan.svlas;

import cn.hutool.extra.mail.MailUtil;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-24 11:22
 */
public class MailsSendTest {
    public static void main(String[] args) {
        MailUtil.send("951520698@qq.com", "测试", "邮件来自Hutool测试", false);
    }
}
