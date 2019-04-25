create table if not exists orders(
order_id int auto_increment,
user_id int not null,
state varchar (255) not null,
price decimal(10,2),
delivery_address varchar(750),
delivery_person varchar(250),
billing_address varchar(750),
billing_person varchar(250),
cash_pay varchar(250),
data date,
primary key(order_id)
);