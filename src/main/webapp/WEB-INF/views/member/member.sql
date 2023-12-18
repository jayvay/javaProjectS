show tables;

CREATE TABLE member2 (
	idx 			int not null auto_increment,	 /* 회원 고유번호 */
	mid 			varchar(30) not null, 				 /* 회원 아이디(중복제외) */
	pwd			  varchar(100) not null, 				 /* 회원 비밀번호(SHA256 암호화 처리) */
	nickName 	varchar(20) not null,					 /* 회원 별명(중복제외/수정가능) */
	name 			varchar(20) not null,				   /* 회원 성명 */
	gender 		char(2) not null default '여자', /* 회원 성별 */
	birthday 	datetime default now(),				 /* 회원 생일 */
	tel 			varchar(15),									 /* 전화번호 : 010-1234-5678 */
	address 	varchar(100),								 	 /* 주소(다음 API 활용) */
	email 		varchar(60) not null,					 /* 이메일(아이디/비밀번호 분실시 사용-형식체크필수) */
	homePage 	varchar(60),									 /* 홈페이지(블로그)주소 */
	job 			varchar(20),									 /* 직업 */
	hobby 		varchar(100),								   /* 취미(여러 개 선택 가능, 구분자 : '/' */
	photo 		varchar(100) default 'noImage.jpg',	/* 회원 사진 */
	content   text,													 /* 회원 자기소개 */
	userInfor  char(3) default '공개',				 /* 회원 정보 공개 여부(공개/비공개) */
	userDel   char(2) default 'NO',					 /* 회원 탈퇴 신청 여부(NO:현재 활동중, OK:탈퇴신청중) */
	point     int default 100,						 	 /* 회원 누적 포인트(가입시 100p, 1회 방문시 10p씩 증가(1일 최대 50p까지 줌), 물건 구매시 100원당 1p 증가) */
	level     int default 1,								 /* 회원 등급 (0:관리자 , 1:준회원, 2:정회원, 3:우수회원, (4:운영자)) */
	visitCnt  int default 0,								 /* 총 방문 횟수 */
	startDate datetime default now(), 			 /* 최초 가입일 */
	lastDate  datetime default now(),				 /* 마지막 접속일 */
	todayCnt  int default 0,								 /* 오늘 방문한 횟수 */
--	salt char(8) not null,							   /* 비밀번호 보안 */
	
--	primary key(idx , mid) 기본키는 여러 개 줄 수 있다
	primary key(idx),
	unique key(mid)
);

DESC member2;
SELECT * FROM member2;

INSERT INTO member2 VALUES (default,'admin','1234','조삼달','신혜선','여자',default,'010-1234-5678','제주 / / /','samdal@gmail.com','','사진작가','사진찍기','noImage.jpg','',default,default,default,default,default,default,default,default);
