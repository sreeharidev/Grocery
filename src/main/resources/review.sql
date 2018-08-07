
drop table IF exists business;
create table business(bId bigint not null auto_increment primary key,
locationId bigint not null,name varchar(200),email varchar(200),
phone varchar(20),website varchar(200),fax varchar(200),category ENUM('Hotel', 'Small Business', 'Product'), 
SubCategory varchar(100));

drop table IF exists location;
create table location(locationId bigint not null auto_increment primary key,
address varchar(200),city varchar(100),state varchar(100),zip varchar(6),
fullzip varchar(10),loc_lat bigint,loc_long bigint);

drop table  IF EXISTS user;
create table user(phoneId varchar(100) not null primary key,userName varchar(100),email varchar(100),password varchar(200)
,how varchar(100),src varchar(10),session varchar(200));

drop table  IF EXISTS userContacts;
create table userContacts(phoneId varchar(20) not null ,contact varchar(20));

drop table  IF EXISTS userHistory;
create table userHistory(phoneId varchar(20) not null ,loc_lat bigint,loc_long bigint,visit_time timestamp);

drop table  IF EXISTS review;
create table review(phoneId varchar(20) not null ,review varchar(20));


