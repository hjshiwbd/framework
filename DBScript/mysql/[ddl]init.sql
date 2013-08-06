create database iispace;
use iispace;

create table demo_user
(
id int primary key auto_increment,
name varchar(200),
nickname varchar(200),
password varchar(200),
birthday date,
gender varchar(200),
headpic varchar(200),
registdate date,
lastlogindate date,
status varchar(10)
);
