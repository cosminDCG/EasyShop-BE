create table if not exists Wishlist(
wish_id int auto_increment,
item_id int not null,
user_id int not null,
current_price varchar (255) not null,
expected_price varchar (255) not null,
primary key(wish_id)
);