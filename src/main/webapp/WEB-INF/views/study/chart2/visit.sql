show tables;

create table visit (
	visitDate datetime not null default now(),
	visitCnt int not null default 1
);

drop table visit;
delete from visit;

select * from visit;

insert into visit values (date(now()), default);
insert into visit values (default, default);
insert into visit values ('2023-12-27', 8);
insert into visit values ('2023-12-26', 5);
insert into visit values ('2023-12-24', 19);
insert into visit values ('2023-12-23', 2);
insert into visit values ('2023-12-22', 4);
insert into visit values ('2023-12-21', 6);
insert into visit values ('2023-12-20', 12);
insert into visit values ('2023-12-19', 21);
insert into visit values ('2023-12-17', 18);

select * from visit order by visitDate desc limit 7;
select substring(visitDate, 1,10) as visitDate, visitCnt from visit order by visitDate desc limit 7;