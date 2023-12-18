package com.spring.javaProjectS.service;

import com.spring.javaProjectS.vo.MemberVO;

public interface MemberService {

	public MemberVO getMemberIdSearch(String mid);

	public MemberVO getMemberNickSearch(String nickName);

	public int setMemberJoin(MemberVO vo);

}