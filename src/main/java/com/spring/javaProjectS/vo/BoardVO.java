package com.spring.javaProjectS.vo;

import lombok.Data;

@Data
public class BoardVO {
	private int idx;
	private String mid;
	private String nickName;
	private String email;
	private String homePage;
	private String title;
	private String content;
	private int readNum;
	private String hostIp;
	private String openSw;
	private String wDate;
	private int good;
	
	private int hour_diff; //게시글 올리고부터 24시간 new.gif 이미지 표시를 위한 변수
	private int date_diff; //게시글 1일 이전인지 체크하기 위한 변수
	private int replyCnt;	 //댓글의 개수를 저장하는 변수
	
}