DROP TABLE IF EXISTS `quartz_scheduler_jobs`;
CREATE TABLE `quartz_scheduler_jobs`
(
    `id`                   bigint(64)  NOT NULL COMMENT 'ID',
    `job_name`          varchar(30) DEFAULT NULL COMMENT '任务名称',
    `job_group_name` varchar(30)      NOT NULL COMMENT '任务所属组名称',
    `job_desc`          varchar(100) DEFAULT NULL COMMENT '任务描述',
    `status`          int(2)       DEFAULT NULL COMMENT '状态： 0 - 代表正在执行  1 - 已删除  2 - 暂停',
    `trigger_type`          int(2)       NOT NULL COMMENT '触发器类型： 0 - simple  1 - cron',
    `corn_expression` varchar(50)      DEFAULT NULL COMMENT 'corn 表达式',
    `simple_interval_time` int(2)      DEFAULT NULL COMMENT 'simple 触发器时间间隔',
    `simple_interval_time_type` int(2)      DEFAULT NULL COMMENT 'simple 触发器时间间隔类型： 1 - seconds 2 - milliseconds  3 - minutes  4 - hours',
    `simple_repeat_nums` int(2)      DEFAULT NULL COMMENT 'simple 循环次数 0 无限循环',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `create_user`   bigint(64)   DEFAULT NULL COMMENT '创建人',
    `update_user`   bigint(64)   DEFAULT NULL COMMENT '修改人',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='任务表';



