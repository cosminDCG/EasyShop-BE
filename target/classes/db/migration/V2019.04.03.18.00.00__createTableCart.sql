create table if not exists cart(
cart_id int auto_increment,
order_id int not null,
item_id int not null,
quantity int not null,
primary key(cart_id)
);