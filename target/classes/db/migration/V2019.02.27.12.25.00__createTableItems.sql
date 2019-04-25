create table if not exists items(
item_id int auto_increment,
name varchar (700) not null,
description varchar (999) not null,
category varchar (255) not null,
price varchar (255) not null,
shop varchar (255) not null,
photo varchar(500) not null,
primary key(item_id)
);