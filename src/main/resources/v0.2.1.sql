drop table  IF EXISTS user;
create table user(userId varchar(100) not null primary key,userName varchar(100),address varchar(400),email varchar(100),password varchar(200),
landmark varchar(200),pin varchar (6),phoneNumber varchar(20),how varchar(100),src varchar(10),session varchar(200));

drop table  IF EXISTS userSessions;
create table userSessions(userId varchar(100) ,session varchar(200));

drop table  IF EXISTS brand;
create table brand(brandId bigint not null auto_increment primary key,brandName varchar(100),brandIcon varchar(100));

drop table  IF EXISTS productCategory;
create table productCategory(categoryId bigint not null auto_increment primary key, categoryName varchar(200));

drop table  IF EXISTS productSubCategory;
create table productSubCategory(categoryId bigint not null,subCategoryId bigint not null auto_increment primary key, subCategoryName varchar(200));


drop table  IF EXISTS products;
create table products(productId bigint not null auto_increment primary key,
categoryId bigint,subCategoryId bigint,
productName varchar(200), productDescription varchar(400) , smallImage varchar(100), largeImage varchar(100) );


drop table IF EXISTS productDetails;
create table productDetails(productId bigint not null,details TEXT,ingrediants TEXT,directions TEXT,warning TEXT);

drop table if exists productsPricing;  
create table productsPricing(ppId bigint not null auto_increment primary key,shopId bigint not null , productId bigint not null ,
weight double,price double, discount double ,isAvailable  tinyint(1) default 1,stock int(5),unit varchar(10),
foreign key products(productId) references products(productId));

drop table if exists favourites;  
create table favourites(userId varchar(100) not null,ppId bigint not null);

drop table  IF EXISTS shops;
create table shops(shopId bigint auto_increment not null primary key,shopName varchar(100),shopAddress varchar(400),pin varchar(6),smallImage varchar(100),largeImage varchar(100));

drop table  IF EXISTS shopsHome;
create table shopsHome(shopId bigint,categoryId bigint,priority int);

drop table  IF EXISTS usersCart;
create table usersCart(cartId bigint not null auto_increment primary key,userId varchar(100) not null,status enum('INIT','CHECKOUT','PAID','DELIVERED') default 'INIT' ,message TEXT);

drop table  IF EXISTS cart;
create table cart(cartId bigint not null,ppId  bigint not null,count int);


select * from productCategory

1-lulu

insert into shopsHome(shopId,categoryId,priority) values(1,2,1);
insert into shopsHome(shopId,categoryId,priority) values(1,5,2);
insert into shopsHome(shopId,categoryId,priority) values(1,6,3);
insert into shopsHome(shopId,categoryId,priority) values(1,1,4);


insert into shopsHome(shopId,categoryId,priority) values(2,1,1);
insert into shopsHome(shopId,categoryId,priority) values(2,6,2);
insert into shopsHome(shopId,categoryId,priority) values(2,2,3);
insert into shopsHome(shopId,categoryId,priority) values(2,5,4);

insert into shopsHome(shopId,categoryId,priority) values(3,8,1);
insert into shopsHome(shopId,categoryId,priority) values(3,7,2);

select * from user
