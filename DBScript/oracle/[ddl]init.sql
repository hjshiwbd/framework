drop table demo_user;
create table demo_user
(
id varchar2(50) primary key,
name varchar2(200),
nickname varchar2(200),
password varchar2(200),
birthday date,
gender varchar2(200),
headpic varchar2(200),
registdate date,
lastlogindate date,
status varchar2(10)
);


comment on table demo_user is '用户测试表';
comment on column demo_user.id is '主键';
comment on column demo_user.name is '用户名';
comment on column demo_user.nickname is '昵称';
comment on column demo_user.password is '密码';
comment on column demo_user.gender is '性别';
comment on column demo_user.birthday is '生日';
comment on column demo_user.headpic is '头像';
comment on column demo_user.registdate is '注册日期';
comment on column demo_user.lastlogindate is '最近登录日期';
comment on column demo_user.status is '状态.0否1是';


create sequence seq_demo_user;
