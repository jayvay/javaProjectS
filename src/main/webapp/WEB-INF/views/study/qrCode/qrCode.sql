show tables;

create table qrCode (
	idx int not null auto_increment primary key,
	mid varchar(30) not null,
	name varchar(30) not null,
	email varchar(50) not null,
	movieName varchar(50) not null,
	movieDate varchar(50) not null,
	movieTime varchar(50) not null,
	movieAdult int not null,
	movieChild int not null,
	purchaseDate varchar(50) not null,
	qrCodeName varchar(100) not null
	
);

desc qrCode;