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

select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.ppId ppId, pp.weight weight, pp.price price, pp.discount discount,pp.shopId shopId,pp.unit unit from productsPricing pp,products p where   pp.productId = p.productId and pp.ppId in(select ppId from Cart where cartId=1)



select * from usersCart

select count(*) count from cart c where cartId in (select cartId from usersCart where userId='9515f508-7661-5c6c-883a-d2860f0b51f3');


select count(ppId) from cart where i

select * from favourites

select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.ppId ppId, pp.shopId shopId,pp.weight weight, pp.price price, pp.discount discount from productsPricing pp,products p, favourites f where  p.productId = pp.productId and pp.ppId = f.ppId and f.userId =  '9515f508-7661-5c6c-883a-d2860f0b51f3';




ALTER TABLE productsPricing
DROP FOREIGN KEY productspricing_ibfk_1;

SELECT concat('alter table ',table_schema,'.',table_name,' DROP FOREIGN KEY ',constraint_name,';')
FROM information_schema.table_constraints
WHERE constraint_type='FOREIGN KEY' AND table_schema='groceryv0.2'


alter table groceryv0.2.productsPricing DROP FOREIGN KEY productspricing_ibfk_1;


insert into productDetails values(9,'asdfsfdkdksdafdasfdsfd iewr ','dfffffffff','dfd,dfsf','akkk334,4555dffif,ddf');

select * from favourites;


select * from productDetails;

select * from productsPricing;


select * from products p, productDetails pd where p.productId = pd.productId

insert into shopsHome(shopId,categoryId,priority) values(1,1,3);

insert into shopsHome(shopId,categoryId,priority) values(1,3,3);

insert into shopsHome(shopId,categoryId,priority) values(2,3,1);

insert into shopsHome(shopId,categoryId,priority) values(2,1,2);

insert into shopsHome(shopId,categoryId,priority) values(2,1,2);
insert into shopsHome(shopId,categoryId,priority) values(1,1,1);

insert into shopsHome(shopId,categoryId,priority) values(1,3,3);

select * from productCategory;



select * from shopsHome

update shops set shopId=2 where shopId=3;

select * from productCategory

select * from productSubCategory

select sc.categoryId categoryId, sc.subCategoryId subCategoryId, sc.subCategoryName,c.categoryName categoryName from productSubCategory sc,productCategory c 
where sc.categoryId = c.categoryId 


select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,p.weight weight from products p where p.categoryId=1


 select p.productId productId,p.categoryId categoryId,p.productName productName,p.weight weight, pp.price price, pp.discount discount from  productsPricing pp,products p
 where pp.shopId = 2  and  p.productId = pp.productId;
 
 Select * from productsPricing
 
 delete from productsPricing;
 delete from products;
 delete from productsParent;
 delete from productSubCategory;
 delete from productCategory;
 
 select * from user
 
 select * from productSubCategory
 
 select * from shopsHome
 
 update shopsHome set shopId=3 where shopId=2
 
 select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.weight weight, pp.price price, pp.discount discount,pp.shopId shopId,pp.unit unit from productsPricing pp,products p where pp.shopId = 2 and  pp.productId = p.productId
 
 
 
 delete from user
 
 
 select * from user
 
 select * from userSessions
 
 
 select * from productsPricing;
 
 
 
 select * from shopsHome 
 
 update shopsHome set priority=1 where shopId=1 and categoryId = 3
 
 select * from shops
 
 
 delete from productsPricing where productId=12 and shopId=1
 
 select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.weight weight, pp.price price, pp.discount discount,pp.shopId shopId,pp.unit unit from productsPricing pp,products p where pp.shopId = 1 and  pp.productId=p.productId
 
 
 select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.weight weight, pp.price price, pp.discount discount from products p, productsPricing pp where pp.shopId = 1 and p.productId = pp.productId
 
 
 
 select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage,pp.weight weight, pp.price price, pp.discount discount from productsPricing pp,products p where pp.shopId = 1 and  p.categoryId= 1 and  p.productId = pp.productId
 
 select * from products
 
 select p.categoryId categoryId,p.productId productId,p.productName productName,p.productDescription productDescription,p.smallImage smallImage,p.largeImage largeImage from products p where p.categoryId=3
 