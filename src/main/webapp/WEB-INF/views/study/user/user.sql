show tables;

create table user (
	idx int not null auto_increment primary key,
	mid varchar(20) not null,
	name varchar(20) not null,
	age int default 20,
	address varchar(10)
);

desc user;

select * from user;

insert into user values (default, 'aaa', '에이', 22, '부산');
insert into user values (default, 'samdal', '조삼달', 33, '제주');
insert into user values (default, 'yongyong', '조용필', 33, '제주');

select * from user where name like concat('%', ,'%') order by idx desc;
