create schema ecommerce;
use ecommerce;
create table products(PID int auto_increment,
DESCRIPTION text not null,
PRODUCT_NAME varchar(255) not null,
PRICE double not null,
QUANTITY int not null,
primary key(PID)
);
create table users(ID int auto_increment,
USERNAME varchar(255) not null unique,
PASSWORD varchar(255) not null,
ROLE enum("admin","user","guest") not null,
primary key(ID)
);
insert into users(USERNAME, PASSWORD,  ROLE) values ('tejas', 'HappyToHelp@24', 'admin'),
('mahesh', 'mahesh9780', 'admin'),
('ritu', 'ritu123', 'admin'),
('rahul', 'rahul123', 'admin');

