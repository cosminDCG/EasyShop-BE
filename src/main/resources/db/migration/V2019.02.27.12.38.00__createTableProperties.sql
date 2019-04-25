create table if not exists Item_Properties(
property_id int auto_increment,
item_id int not null,
name varchar (255) not null,
description varchar (999) not null,
primary key(property_id)
);