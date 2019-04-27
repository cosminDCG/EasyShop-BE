create table if not exists promo(
ban_id int auto_increment,
banned_by int not null,
banned_user int not null,
end_date date not null,
reason varchar(500) not null,
primary key(ban_id)
);