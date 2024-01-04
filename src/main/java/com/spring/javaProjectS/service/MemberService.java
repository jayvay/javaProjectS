package com.spring.javaProjectS.service;

import java.util.List;

import com.spring.javaProjectS.vo.MemberVO;

public interface MemberService {

	public MemberVO getMemberIdSearch(String mid);

	public MemberVO getMemberNickSearch(String nickName);

	public int setMemberJoin(MemberVO vo);

	public List<String> getMemberMIdsSearch(String email);

	public void setMemberPwdSearchUpdate(String mid, String imsiPwd);

	public int setPwdUpdate(String mid, String pwdNew);

	public int setMemberUpdate(MemberVO vo);

	public int setMemberDelete(String mid);

	public MemberVO getMemberKakaoLoginSearch(String nickName, String email);

	public void setKakaoMemberInput(String mid, String pwd, String nickName, String email);


}
