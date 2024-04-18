create database tenon default character set utf8mb4 collate utf8mb4_unicode_ci;

use tenon;

create table if not exists tenon_menu
(
    id           bigint auto_increment comment '主键'
        primary key,
    label        varchar(64)                          not null comment '名称',
    permission   varchar(128)                         not null comment '权限',
    icon         varchar(128)                         not null comment '图标',
    href         varchar(128)                         not null comment '链接',
    status       decimal(1) default 1                 not null comment '状态',
    created_time datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    updated_time datetime   default CURRENT_TIMESTAMP null comment '更新时间',
    parent_id    bigint                               null comment '父 ID',
    menu_type    decimal(1) default 0                 not null comment '菜单类型(0: 菜单, 1: 按钮)',
    sort         decimal(4) default 50                not null comment '排序'
)
    comment '菜单表';

create table if not exists tenon_operate_log
(
    id             bigint auto_increment comment '主键'
        primary key,
    trace_id       varchar(64)              null comment '链路追踪 ID',
    operator       varchar(64)              null comment '用户',
    type           varchar(50)              null comment '模块',
    sub_type       varchar(50)              null comment '操作分类',
    content        varchar(2000)            null comment '操作内容',
    extra          varchar(512)             null comment '扩展字段',
    request_method varchar(16)              null comment '请求方法名',
    request_url    varchar(512)             null comment '请求地址',
    java_method    varchar(512)             null comment 'java方法名',
    user_agent     varchar(200)             null comment '浏览器ua',
    created_time   datetime default (now()) null comment '创建时间',
    tenant         varchar(512)             null comment '租户编号',
    biz_no         varchar(512)             null comment '业务编号',
    user_ip        varchar(128)             null comment '用户 IP',
    fail           tinyint                  null comment '成功 or 失败'
)
    comment '操作日志表';

create index tenon_operate_log_search_index
    on tenon_operate_log (type asc, sub_type asc, created_time desc);

create table if not exists tenon_role
(
    id           bigint auto_increment comment '主键'
        primary key,
    label        varchar(128)             not null comment '名称',
    permission   varchar(128)             not null comment '权限',
    status       int      default 1       null comment '状态',
    created_time datetime default (now()) null comment '创建时间',
    updated_time datetime default (now()) null comment '更新时间',
    constraint tenon_role_id_uindex
        unique (id),
    constraint tenon_role_permission_uindex
        unique (permission)
)
    comment '角色表';

create table if not exists tenon_role_menu
(
    id_tenon_role bigint not null comment '角色表主键',
    id_tenon_menu bigint not null comment '菜单表主键',
    constraint tenon_role_menu_uindex
        unique (id_tenon_role, id_tenon_menu)
)
    comment '角色菜单表';

create table if not exists tenon_user
(
    id               bigint auto_increment comment '主键'
        primary key,
    account          varchar(32)              not null comment '账号',
    password         varchar(64)              null comment '密码',
    nickname         varchar(128)             null comment '昵称',
    avatar           varchar(512)             null comment '头像',
    email            varchar(64)              null comment '邮箱',
    phone            varchar(11)              null comment '手机号',
    status           int      default 1       null comment '状态',
    created_time     datetime default (now()) null comment '注册时间',
    last_login_time  datetime                 null comment '最后登录时间',
    real_name        varchar(128)             null comment '真实姓名',
    last_online_time datetime                 null comment '最后在线时间',
    constraint tenon_user_id_uindex
        unique (id)
)
    comment '系统用户表';

create table if not exists tenon_user_role
(
    id_tenon_user bigint not null comment '用户表主键',
    id_tenon_role bigint not null comment '角色表主键',
    constraint tenon_user_role_id_tenon_user_id_tenon_role_uindex
        unique (id_tenon_user, id_tenon_role)
)
    comment '用户权限表';


