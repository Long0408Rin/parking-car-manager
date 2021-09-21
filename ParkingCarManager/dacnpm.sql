create schema dacnpm;
use dacnpm;
CREATE TABLE Role
(id_role int  primary key unique auto_increment not null,
role_name char(255)
);
create table User
(id_user int primary key unique auto_increment not null,
id_role int not null,
username char(64),
password char(255),
name char(255),
birthday Timestamp,
gender char(255),
email char(255),
phone_number char(10),
address char(255),
foreign key(id_role) references Role(id_role)
);
create table Payment
(
id_payment int primary key AUTO_INCREMENT not null,
payment_type char(255)
);
create table Car
(
plate_number char(255) primary key unique not null,
id_user int not null,
car_brand char(255),
car_name char(255),
car_color char(255),
car_type char(255),
foreign key (id_user)references User(id_user)
);
Create table Parking_Lot
(
id_pl int primary key auto_increment unique not null,
id_user int not null,
pl_name char(255),
address char(255),
district char(255),
slot_amount int,
slot_available int,
foreign key (id_user) references User(id_user)
);
create table Order1
(id_order int  primary key unique auto_increment not null,
id_pl int not null,
plate_number char(255) not null,
id_payment int not null,
time_in Timestamp,
time_out Timestamp,
fee int,
foreign key(id_payment) references  payment(id_payment),
CONSTRAINT `order1_ibfk_2`
  FOREIGN KEY (`id_pl`)
  REFERENCES `dacnpm`.`parking_lot` (`id_pl`)
  ON DELETE CASCADE,
CONSTRAINT `order1_ibfk_3`
  FOREIGN KEY (`plate_number`)
  REFERENCES `dacnpm`.`car` (`plate_number`)
  ON DELETE CASCADE
);