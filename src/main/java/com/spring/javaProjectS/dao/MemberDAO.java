package com.spring.javaProjectS.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.javaProjectS.vo.MemberVO;

public interface MemberDAO {

	public MemberVO getMemberIdSearch(@Param("mid") String mid);

	public MemberVO getMemberNickSearch(@Param("nickName") String nickName);

	public int setMemberJoin(@Param("vo") MemberVO vo);

	public void setMemberPwdUpdate(@Param("mid") String mid, @Param("imsiPwd") String imsiPwd);

	public int setPwdChangeOk(@Param("mid") String mid, @Param("pwd") String pwd);

}
