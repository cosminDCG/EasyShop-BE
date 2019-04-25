create table if not exists review(
review_id int auto_increment,
item_id int not null,
user_id int not null,
first_name varchar (255) not null,
last_name varchar (255) not null,
photo varchar(500),
comment varchar(999) not null,
review int not null,
reply_to int not null,
data varchar(255) not null,
primary key(review_id)
);