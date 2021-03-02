DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`
(
    `code`          varchar(10) NOT NULL COMMENT '股票代码',
    `name`          varchar(100) DEFAULT NULL COMMENT '股票名称',
    `industry_type` int(2)      NOT NULL COMMENT '行业类型',
    `type`          varchar(10)       DEFAULT NULL COMMENT '类型 FUND 基金，STOCK 股票，期货，黄金',
    `region`        varchar(10)       DEFAULT NULL COMMENT '区域 A A股，HK 港股，USA 美股',
    `user_id`       bigint(64)  NOT NULL COMMENT '用户ID',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_user`   bigint(64)   DEFAULT NULL COMMENT '修改人',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='股票表';


DROP TABLE IF EXISTS `stock_trade_history_flow`;
CREATE TABLE `stock_trade_history_flow`
(
    `id`                   bigint(64)  NOT NULL COMMENT 'ID',
    `code`                 varchar(10) NOT NULL COMMENT '股票代码',
    `price_close`          decimal(12, 2) DEFAULT NULL COMMENT '收盘价',
    `price_change`         decimal(12, 2) DEFAULT NULL COMMENT '涨跌额',
    `per_price_volatility` decimal(4, 2)  DEFAULT NULL COMMENT '涨跌幅',
    `total_market_price`   decimal(12, 2) DEFAULT NULL COMMENT '总市值 单位亿',
    `turnover`             decimal(12, 2) DEFAULT NULL COMMENT '成交额',
    `volume`               decimal(12, 2) DEFAULT NULL COMMENT '成交量',
    `trade_time`           datetime       DEFAULT NULL COMMENT '交易日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='股票交易历史流水表';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          bigint(64) NOT NULL COMMENT 'ID',
    `sex`         int(2)      DEFAULT NULL COMMENT '性别 男，女',
    `name`        varchar(50) DEFAULT NULL COMMENT '姓名',
    `mobile`      varchar(15) DEFAULT NULL COMMENT '电话',
    `mail_address`      varchar(50) DEFAULT NULL COMMENT '邮件地址',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `update_user` bigint(64)  DEFAULT NULL COMMENT '修改人',
    `update_time` datetime    DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

DROP TABLE IF EXISTS `person_stock_trade_flow`;
CREATE TABLE `person_stock_trade_flow`
(
    `id`         bigint(64)  NOT NULL COMMENT 'ID',
    `user_id`    bigint(64)  NOT NULL COMMENT '用户ID',
    `code`       varchar(10) NOT NULL COMMENT '股票代码',
    `price`      decimal(12, 2) DEFAULT NULL COMMENT '交易价格',
    `nums`       int(10)        DEFAULT NULL COMMENT '交易股数',
    `volume`     varchar(100)   DEFAULT NULL COMMENT '交易原因',
    `trade_time` datetime       DEFAULT NULL COMMENT '交易日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='个人股票交易记录流水';

DROP TABLE IF EXISTS `stock_user_info`;
CREATE TABLE `stock_user_info`
(
    `id`           bigint(64)  NOT NULL COMMENT 'ID',
    `code`         varchar(10) NOT NULL COMMENT '股票代码',
    `user_id`      bigint(64)  NOT NULL COMMENT '用户ID',
    `cost_price`   decimal(12, 2) DEFAULT NULL COMMENT '成本价',
    `current_price`   decimal(12, 2) DEFAULT NULL COMMENT '当前价',
    `nums`         int(10)        DEFAULT NULL COMMENT '股数',
    `profit_price` decimal(12, 2) DEFAULT NULL COMMENT '盈利额',
    `profit_rate`  decimal(12, 2) DEFAULT NULL COMMENT '收益比例',
    `create_time`  datetime       DEFAULT NULL COMMENT '创建日期',
    `update_time`  datetime       DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户股票市值信息表';

DROP TABLE IF EXISTS `stock_user_info_record`;
CREATE TABLE `stock_user_info_record`
(
    `id`           bigint(64)  NOT NULL COMMENT 'ID',
    `code`         varchar(10) NOT NULL COMMENT '股票代码',
    `user_id`      bigint(64)  NOT NULL COMMENT '用户ID',
    `cost_price`   decimal(12, 2) DEFAULT NULL COMMENT '成本价',
    `current_price`   decimal(12, 2) DEFAULT NULL COMMENT '当前价',
    `nums`         int(10)        DEFAULT NULL COMMENT '股数',
    `profit_price` decimal(12, 2) DEFAULT NULL COMMENT '盈利额',
    `profit_rate`  decimal(12, 2) DEFAULT NULL COMMENT '收益比例',
    `create_time`  datetime       DEFAULT NULL COMMENT '创建日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户股票市值变更表';

DROP TABLE IF EXISTS `stock_user_strategy_record`;
CREATE TABLE `stock_user_strategy_record`
(
    `id`            bigint(64) NOT NULL COMMENT 'ID',
    `user_id`       bigint(64) NOT NULL COMMENT '用户ID',
    `strategy_type` int(10)        DEFAULT NULL COMMENT '策略类型 买点，卖出，加仓',
    `nums`          int(10)        DEFAULT NULL COMMENT '股数',
    `strategy_id`   bigint(64)     DEFAULT NULL COMMENT '策略ID',
    `create_time`   datetime       DEFAULT NULL COMMENT '创建日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户股票策略变更表';

DROP TABLE IF EXISTS `stock_user_strategy`;
CREATE TABLE `stock_user_strategy`
(
    `id`            bigint(64) NOT NULL COMMENT 'ID',
    `user_id`       bigint(64) NOT NULL COMMENT '用户ID',
    `strategy_type` int(10)        DEFAULT NULL COMMENT '策略类型 买点，卖出，加仓',
    `nums`          int(10)        DEFAULT NULL COMMENT '股数',
    `strategy_id`   bigint(64)     DEFAULT NULL COMMENT '策略ID',
    `create_time`   datetime       DEFAULT NULL COMMENT '创建日期',
    `update_user`   bigint(64)     DEFAULT NULL COMMENT '修改人',
    `update_time`   datetime       DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户股票策略表';

DROP TABLE IF EXISTS `stock_strategy`;
CREATE TABLE `stock_strategy`
(
    `id`                   bigint(64)  NOT NULL COMMENT 'ID',
    `code`                 varchar(10) NOT NULL COMMENT '股票代码',
    `price_anchor`         decimal(12, 2) DEFAULT NULL COMMENT '锚点：以该价格为锚点',
    `adjust_time`          datetime       DEFAULT NULL COMMENT '时间：调整日期',
    `per_price_volatility` decimal(4, 2)  DEFAULT NULL COMMENT '空间：回调幅度',
    `create_time`          datetime       DEFAULT NULL COMMENT '创建日期',
     `status`          int(2)       DEFAULT 0 COMMENT '状态： 0 - 代表正在执行  1 - 已触发',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='股票策略表';

DROP TABLE IF EXISTS `fund_auto_plan`;
CREATE TABLE `fund_auto_plan`
(
    `id`          bigint(64)  NOT NULL COMMENT 'ID',
    `user_id`     bigint(64)  NOT NULL COMMENT '用户ID',
    `code`        varchar(10) NOT NULL COMMENT '基金代码',
    `price`       decimal(12, 2) DEFAULT NULL COMMENT '定投金额',
    `create_time` datetime       DEFAULT NULL COMMENT '定投日期',
    `update_time` datetime       DEFAULT NULL COMMENT '修改时间',
    `status`          int(2)       DEFAULT 0 COMMENT '状态： 0 - 代表正在执行  1 - 已停止',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='基金定投表';

DROP TABLE IF EXISTS `fund_auto_plan_record`;
CREATE TABLE `fund_auto_plan_record`
(
    `id`          bigint(64)  NOT NULL COMMENT 'ID',
    `user_id`     bigint(64)  NOT NULL COMMENT '用户ID',
    `code`        varchar(10) NOT NULL COMMENT '基金代码',
    `price`       decimal(12, 2) DEFAULT NULL COMMENT '定投金额',
    `create_time` datetime       DEFAULT NULL COMMENT '定投日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='基金定投变更表';

