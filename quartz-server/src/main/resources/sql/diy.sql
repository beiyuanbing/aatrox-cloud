-- auto-generated definition
-- 也可以加一个执行明细的表用于查看每次执行情况
create table T_APP_QUARTZ
(
    quartzId       int auto_increment comment '主键'
        primary key,
    jobName        varchar(50) charset utf8  null comment '任务名称',
    jobGroup       varchar(50) charset utf8  null comment '任务分组',
    description    varchar(100) charset utf8 null comment '任务描述',
    status         varchar(20) charset utf8  null comment '任务状态',
    startTime      varchar(50)               null comment '任务开始时间',
    cronExpression varchar(50) charset utf8  null comment 'corn表达式',
    invokeParam    varchar(50) charset utf8  null comment '需要传递的参数',
    triggerDesc    varchar(50) charset utf8  null comment '触发器描述',
    remark         varchar(100) charset utf8 null comment '备注'
)
    comment '任务信息';

