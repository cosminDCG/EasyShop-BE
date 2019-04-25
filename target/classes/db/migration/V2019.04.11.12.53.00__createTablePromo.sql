create table if not exists promo(
promo_id int auto_increment,
user_id int not null,
promo_code varchar(500) not null,
promo_percent int not null,
state varchar(250) not null,
primary key(promo_id)
);