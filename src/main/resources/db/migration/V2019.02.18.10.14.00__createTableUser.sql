create table if not exists user(
user_id int auto_increment,
first_name varchar (255) not null,
last_name varchar (255) not null,
email varchar (255) not null,
address varchar (255) not null,
city varchar (255) not null,
phone_number varchar (255) not null,
password varchar(255) not null,
photo varchar(500),
role varchar(255),
join_date date,
primary key(user_id)
);