create table if not exists bill(
bill_id int auto_increment,
shop varchar(50) not null,
bill_value decimal(10,2) not null,
payed int not null,
payed_by varchar(100),
emited_date date not null,
pay_date date,
primary key(bill_id)
);