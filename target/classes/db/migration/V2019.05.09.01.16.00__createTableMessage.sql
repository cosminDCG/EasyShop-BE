create table if not exists message(
message_id int auto_increment,
from_user int not null,
to_user int not null,
text varchar(999) not null,
send_date datetime not null,
primary key(message_id)
);