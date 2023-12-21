package com.spring.javaProjectS.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

public @Data class UserVO {

	private int idx;
	
	@NotEmpty(message = "ID가 입력되지 않았습니다 /midEmpty")	//공백
	@Size(min = 4, max = 20, message = "아이디 길이가 잘못되었습니다 /midSizeNo") //문자 수
	private String mid;
	
	@NotEmpty(message = "이름이 입력되지 않았습니다 /nameEmpty")	//공백
	@Size(min = 2, max = 20, message = "성명 길이가 잘못되었습니다 /nameSizeNo") //문자 수
	private String name;
	
	@Range(min = 19, max = 99, message = "가입 가능한 나이의 범위를 벗어났습니다 /ageRangeNo")	//숫자 범위
	private int age;
	
	private String address;
	
}
