show tables;

/*대분류*/
create table categoryMain (
	categoryMainCode char(1) not null, 			/*대분류코드(A,B,C,..)=> 영문 대문자 1자(중복불허) */
	categoryMainName varchar(20) not null,  /*대분류명(회사명 => 삼성/현대/LG)(중복불허) */
	primary key (categoryMainCode),
	unique key (categoryMainName)
);

/*중분류*/
create table categoryMiddle (
	categoryMainCode char(1) not null, 				/*대분류코드를 외래키로 지정*/
	categoryMiddleCode char(2) not null, 			/*중분류코드(01,02,03,..)=> 문자형 숫자 2자(중복불허) */
	categoryMiddleName varchar(20) not null, 	/*중분류명(제품분류명 => 전자제품/생활가전/차종/의류/신발류)(중복불허) */
	primary key (categoryMiddleCode),
	foreign key (categoryMainCode) references categoryMain(categoryMainCode),
	unique key (categoryMiddleName)
);

/*소분류*/
create table categorySub (
	categoryMainCode char(1) not null, 			/*대분류코드를 외래키로 지정*/
	categoryMiddleCode char(2) not null,	 	/*중분류코드를 외래키로 지정 */
	categorySubCode char(3) not null, 			/*소분류코드(001,002,003,..)=> 문자형 숫자 3자(중복불허) */
	categorySubName varchar(20) not null, 	/*소분류명(상품구분 => 중분류가 전자제품이라면 소분류는 TV/휴대폰/노트북 */
	primary key (categorySubCode),
	foreign key (categoryMainCode) references categoryMain(categoryMainCode),
	foreign key (categoryMiddleCode) references categoryMiddle(categoryMiddleCode)
);

/*세분류(상품테이블)*/
create table dbProduct (
	idx int not null,										 /*상품 고유번호*/
	categoryMainCode char(1) not null, 	 /* 대분류코드를 외래키로 지정 */
	categoryMiddleCode char(2) not null, /* 중분류코드를 외래키로 지정 */
	categorySubCode char(3) not null,		 /* 소분류코드를 외래키로 지정 */
	productCode varchar(20) not null, 	 /* 상품고유코드() 예: A 01 002 5는 삼성 전자제품 휴대폰 5번상품 */
	productName varchar(50) not null,		 /* 상품명(상품모델명) */
	detail varchar(100) not null,				 /* 상품에 대한 간단한 설명(초기화면 메인창에 출력할 간단한 설명) */
	mainPrice int not null,							 /* 상품 기본 가격 */
	fSName varchar(200) not null,				 /* 상품의 기본 사진 (1장 이상 처리시 '/'로 구분한다) */
	content text not null, 							 /* 상품의 상세 설명 - ckeditor를 이용 */
	primary key (idx),
	unique key(productCode, productName), 
	foreign key (categoryMainCode) references categoryMain(categoryMainCode),
	foreign key (categorySubCode) references categorySub(categorySubCode)
);

/*상품 옵션 테이블*/
create table dbOption (
	opIdx int not null auto_increment,	/*옵션 고유번호*/
	productIdx int not null, 					/*dbProduct 테이블의 상품 고유번호 - 외래키로 지정*/
	optionName varchar(50) not null,	/*옵션 이름*/
	optionPrice int not null default 0, /*옵션 가격*/
	primary key(idx),
	foreign key(productIdx) references dbProduct(idx)
);

desc categoryMain;

