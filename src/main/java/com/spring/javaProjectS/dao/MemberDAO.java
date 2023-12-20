package com.spring.javaProjectS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaProjectS.vo.MemberVO;

public interface MemberDAO {

	public MemberVO getMemberIdSearch(@Param("mid") String mid);

	public MemberVO getMemberNickSearch(@Param("nickName") String nickName);

	public int setMemberJoin(@Param("vo") MemberVO vo);

	public List<String> getMemberMIdsSearch(String email);
	
	public void setMemberPwdSearchUpdate(@Param("mid") String mid, @Param("imsiPwd") String imsiPwd);

	public int setPwdUpdate(@Param("mid") String mid, @Param("pwdNew") String pwdNew);

	public int setMemberUpdate(@Param("vo") MemberVO vo);

	public int setMemberDelete(@Param("mid") String mid);


}
