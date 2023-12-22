show tables;

create table pds2 (
	idx int not null auto_increment,/* 자료실 고유번호 */
	mid varchar(30) not null,				/* 글쓴이 아이디 */
	nickName varchar(30) not null, 	/* 글쓴이 닉네임 */
	fName varchar(250) not null, 		/* 업로드한 파일명 */
	fSName varchar(250) not null, 	/* 실제 파일서버에 저장되는 파일명 */
	fSize int not null, 						/* 업로드된 파일의 총 사이즈 */
	title varchar(100) not null, 		/* 파일을 업로드하는 글의 제목 */
	part varchar(20) not null,			/* 파일 분류 (학습/여행/음식/기타) */
	pwd varchar(100) not null,			/* 비밀번호(sha256 암호화 처리) */
	fDate datetime default now(),		/* 파일 업로드한 날짜 */
	downNum int default 0,					/* 파일 다운로드 횟수 */
	openSw char(6) default '공개',		/* 파일 공개,비공개 여부*/
	content text, 									/* 파일의 상세설명 */
	hostIp varchar(50) not null,		/* 업로드한 클라이언트의 IP 주소*/
	primary key(idx)								/* 기본 키 */
);

desc pds;
select * from pds order by idx desc;

drop table pds;

alter table pds drop column star;
alter table pds drop column review;

/* 리뷰테이블 */
create table review (
	idx int not null auto_increment, /* 리뷰 고유번호 */
	part varchar(20) not null, 			 /* 분류(board:게시판, pds:자료실) */
	partIdx int not null,						 /* 해당 분야의 고유번호 */
	mid varchar(30) not null,				 /* 리뷰 글쓴이 */
	star int not null default 0,		 /* 별점 부여 점수 */
	content text,										 /* 리뷰 내용 */
	rDate datetime default now(),		 /* 리뷰 올린 날짜 */
	primary key(idx)
);

desc review;
drop table review;