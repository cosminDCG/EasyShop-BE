create table if not exists rep(
rep_id int auto_increment,
user_id int not null,
shop varchar(50) not null,
primary key(rep_id)
);