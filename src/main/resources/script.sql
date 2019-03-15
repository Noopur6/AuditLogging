create table audit_log(auditId int(11) primary key auto_increment,operation varchar(10) not null,detail varchar(100) not null,time_created datetime not null);
create table user(userId int(11) primary key auto_increment,userName varchar(50) not null,address varchar(100) not null,mobile varchar(10) not null);

insert into user values(1,'jack','denmark','1287654567');
insert into user values(2,'jill','cape town','7645673098');
insert into user values(3,'henry','new york','8765478398');
insert into user values(4,'david','paris','4536278934');
insert into user values(5,'lily','venezuela','9087567480');
insert into user values(6,'mary','dubai','7645678345');
insert into user values(7,'emily','chicago','8766453456');
insert into user values(8,'jack','mexico','2132435609');
insert into user values(9,'darwin','las vegas','9878987231');
insert into user values(10,'maria','sweden','9877765342');

select * from user;