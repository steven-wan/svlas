DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `sex`         int(2)      DEFAULT NULL COMMENT '性别 1 男，0 女',
    `name`        varchar(50) DEFAULT NULL COMMENT '姓名',
	    `personal_id`        varchar(50) DEFAULT NULL COMMENT '个人标识唯一ID 一般用于存放微信端的 openId',
		  `idcard_no`        varchar(50) DEFAULT NULL COMMENT '身份证号码',
    `mobile`      varchar(15) DEFAULT NULL COMMENT '电话',
	`company_id`          bigint(64) NOT NULL COMMENT '公司ID',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',

    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='员工表';
  
  DROP TABLE IF EXISTS `nucleic_acid_report`;
CREATE TABLE `nucleic_acid_report`
(
    `id`          int(11) NOT NULL  AUTO_INCREMENT COMMENT 'ID',
    `employee_id`          bigint(64) NOT NULL COMMENT '员工ID',
    `detect_hospital_name`        varchar(50) DEFAULT NULL COMMENT '检测医院名称',
		  `detect_no`        varchar(50) DEFAULT NULL COMMENT '检测编号',
    `detect_time` datetime    DEFAULT NULL COMMENT '检测时间',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
  `detect_result`        int(2)       DEFAULT NULL COMMENT '检测结果 1 阳性，0 阴性',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='核酸报告';
  
  
    DROP TABLE IF EXISTS `nucleic_acid_request`;
CREATE TABLE `nucleic_acid_request`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `employee_id`          bigint(64) NOT NULL COMMENT '员工ID',
    `request_time` datetime    DEFAULT NULL COMMENT '检测申请时间',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
  `request_status`        int(2)       DEFAULT NULL COMMENT '申请状态 0 申请中 1 已上报 2 取消申请',
    `remark`        varchar(100) DEFAULT NULL COMMENT '备注 拒绝原因',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='核酸检测申请表';
  