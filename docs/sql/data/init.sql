
-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 1, '小万', '13207129593', '951520698@qq.com', NULL, NULL, NULL);

-- ----------------------------
-- Records of fund_auto_plan
-- ----------------------------
INSERT INTO `fund_auto_plan` VALUES (1, 1, 'sh588000', 1000.00, '2021-02-26 03:03:51', '2021-02-26 03:03:51', 0);
INSERT INTO `fund_auto_plan` VALUES (2, 1, 'sz161005', 1500.00, '2021-02-26 03:04:06', '2021-02-26 03:04:06', 0);

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES ('hk01610', '中粮惠康', 2, 'STOCK', 'HK', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('hk01810', '小米集团', 8, 'STOCK', 'HK', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh110072', '广汇转债', 7, 'FUND', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh513050', '中概互联', 8, 'FUND', 'HK', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh515700', '新能车', 7, 'FUND', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh515790', '光伏ETF', 7, 'FUND', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh588000', '科创50', 15, 'FUND', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh600009', '上海机场', 9, 'STOCK', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh600036', '招商银行', 3, 'STOCK', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh600276', '恒瑞医药', 2, 'STOCK', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh600298', '安琪酵母', 1, 'STOCK', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh600436', '片仔癀', 2, 'STOCK', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh601318', '中国平安', 5, 'STOCK', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sh603288', '海通味业', 2, 'STOCK', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sz000002', '万科A', 9, 'STOCK', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sz000858', '五粮液', 2, 'STOCK', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sz002271', '东方雨虹', 9, 'STOCK', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sz161005', '富国天惠', 17, 'FUND', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sz300003', '乐普医疗', 2, 'STOCK', 'A', 1, NULL, NULL, NULL);
INSERT INTO `stock` VALUES ('sz300760', '迈瑞医疗', 2, 'STOCK', 'A', 1, NULL, NULL, NULL);

-- ----------------------------
-- Records of stock_strategy
-- ----------------------------
INSERT INTO `stock_strategy` VALUES (1, 'sh600009', 56.00, NULL, NULL, NULL, 0);
INSERT INTO `stock_strategy` VALUES (2, 'sh600298', 52.00, NULL, NULL, NULL, 0);
INSERT INTO `stock_strategy` VALUES (3, 'sh601318', 80.00, NULL, NULL, NULL, 0);
INSERT INTO `stock_strategy` VALUES (7, 'sz000002', 28.00, NULL, NULL, NULL, 0);
INSERT INTO `stock_strategy` VALUES (8, 'sh600436', 220.00, NULL, NULL, NULL, 0);
INSERT INTO `stock_strategy` VALUES (9, 'sh603288', 150.00, NULL, NULL, NULL, 0);
INSERT INTO `stock_strategy` VALUES (10, 'sz300760', 360.00, NULL, NULL, NULL, 0);
INSERT INTO `stock_strategy` VALUES (14, 'sh600276', 90.00, NULL, NULL, NULL, 0);
INSERT INTO `stock_strategy` VALUES (15, 'sh600036', 43.00, NULL, NULL, NULL, 0);
INSERT INTO `stock_strategy` VALUES (16, 'sz002271', 38.00, NULL, NULL, NULL, 0);
INSERT INTO `stock_strategy` VALUES (17, 'sh110072', 77.00, NULL, NULL, NULL, 0);
INSERT INTO `stock_strategy` VALUES (18, 'sz000858', 180.00, NULL, NULL, NULL, 0);

-- ----------------------------
-- Records of stock_user_info
-- ----------------------------
INSERT INTO `stock_user_info` VALUES (1, 'hk01610', 1, 4.490, 5.680, 3000, NULL, NULL, '2021-03-01 20:08:18', '2021-03-01 20:08:18');
INSERT INTO `stock_user_info` VALUES (2, 'sh515700', 1, 2.043, 2.057, 500, NULL, NULL, '2021-03-01 20:08:18', '2021-03-01 20:08:18');
INSERT INTO `stock_user_info` VALUES (3, 'sz161005', 1, 3.865, 3.893, 800, NULL, NULL, '2021-03-01 20:08:18', '2021-03-01 20:08:18');
INSERT INTO `stock_user_info` VALUES (4, 'sh588000', 1, 1.442, 1.408, 13500, NULL, NULL, '2021-03-01 20:08:18', '2021-03-01 20:08:18');
INSERT INTO `stock_user_info` VALUES (5, 'sh515790', 1, 1.211, 1.177, 18000, NULL, NULL, '2021-03-01 20:08:18', '2021-03-01 20:08:18');
INSERT INTO `stock_user_info` VALUES (6, 'sh513050', 1, 2.386, 2.383, 2000, NULL, NULL, '2021-03-01 20:08:19', '2021-03-01 20:08:19');
INSERT INTO `stock_user_info` VALUES (7, 'sz300003', 1, 32.492, 31.640, 1900, NULL, NULL, '2021-03-01 20:08:19', '2021-03-01 20:08:19');
INSERT INTO `stock_user_info` VALUES (8, 'sz002271', 1, 34.142, 50.680, 500, NULL, NULL, '2021-03-01 20:08:19', '2021-03-01 20:08:19');
INSERT INTO `stock_user_info` VALUES (9, 'sz000002', 1, 27.776, 33.990, 900, NULL, NULL, '2021-03-01 20:08:19', '2021-03-01 20:08:19');
INSERT INTO `stock_user_info` VALUES (10, 'sh601318', 1, 81.741, 84.540, 1000, NULL, NULL, '2021-03-01 20:08:19', '2021-03-01 20:08:19');
INSERT INTO `stock_user_info` VALUES (11, 'sh600298', 1, 53.508, 53.960, 100, NULL, NULL, '2021-03-01 20:08:19', '2021-03-01 20:08:19');
INSERT INTO `stock_user_info` VALUES (12, 'sh600276', 1, 87.259, 99.700, 600, NULL, NULL, '2021-03-01 20:08:19', '2021-03-01 20:08:19');
INSERT INTO `stock_user_info` VALUES (13, 'sh600036', 1, 34.642, 50.640, 200, NULL, NULL, '2021-03-01 20:08:19', '2021-03-01 20:08:19');
INSERT INTO `stock_user_info` VALUES (14, 'sh600009', 1, 65.420, 60.540, 900, NULL, NULL, '2021-03-01 20:08:19', '2021-03-01 20:08:19');
INSERT INTO `stock_user_info` VALUES (16, 'hk01810', 1, 25.350, 26.500, 200, NULL, NULL, '2021-03-01 20:08:20', '2021-03-01 20:08:20');
INSERT INTO `stock_user_info` VALUES (17, 'sh110072', 1, 79.830, 79.660, 20, NULL, NULL, '2021-03-01 20:08:20', '2021-03-01 20:08:20');

-- ----------------------------
-- Records of stock_user_strategy
-- ----------------------------
INSERT INTO `stock_user_strategy` VALUES (2, 1, 1, 100, 1, NULL, NULL, NULL);
INSERT INTO `stock_user_strategy` VALUES (3, 1, 1, 100, 2, NULL, NULL, NULL);
INSERT INTO `stock_user_strategy` VALUES (4, 1, 1, 100, 3, NULL, NULL, NULL);
INSERT INTO `stock_user_strategy` VALUES (5, 1, 1, 100, 7, NULL, NULL, NULL);
INSERT INTO `stock_user_strategy` VALUES (6, 1, 1, 100, 8, NULL, NULL, NULL);
INSERT INTO `stock_user_strategy` VALUES (7, 1, 1, 100, 9, NULL, NULL, NULL);
INSERT INTO `stock_user_strategy` VALUES (8, 1, 1, 100, 10, NULL, NULL, NULL);
INSERT INTO `stock_user_strategy` VALUES (9, 1, 1, 100, 14, NULL, NULL, NULL);
INSERT INTO `stock_user_strategy` VALUES (10, 1, 1, 100, 15, NULL, NULL, NULL);
INSERT INTO `stock_user_strategy` VALUES (11, 1, 1, 100, 16, NULL, NULL, NULL);
INSERT INTO `stock_user_strategy` VALUES (12, 1, 1, 100, 17, NULL, NULL, NULL);
INSERT INTO `stock_user_strategy` VALUES (13, 1, 1, 100, 18, NULL, NULL, NULL);
