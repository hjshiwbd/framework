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


comment on table demo_user is '�û����Ա�';
comment on column demo_user.id is '����';
comment on column demo_user.name is '�û���';
comment on column demo_user.nickname is '�ǳ�';
comment on column demo_user.password is '����';
comment on column demo_user.gender is '�Ա�';
comment on column demo_user.birthday is '����';
comment on column demo_user.headpic is 'ͷ��';
comment on column demo_user.registdate is 'ע������';
comment on column demo_user.lastlogindate is '�����¼����';
comment on column demo_user.status is '״̬.0��1��';


create sequence seq_demo_user;
