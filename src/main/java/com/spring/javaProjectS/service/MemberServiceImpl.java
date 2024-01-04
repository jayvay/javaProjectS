package com.spring.javaProjectS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaProjectS.dao.MemberDAO;
import com.spring.javaProjectS.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	@Override
	public MemberVO getMemberIdSearch(String mid) {
		return memberDAO.getMemberIdSearch(mid);
	}

	@Override
	public MemberVO getMemberNickSearch(String nickName) {
		return memberDAO.getMemberNickSearch(nickName);
	}

	@Override
	public int setMemberJoin(MemberVO vo) {
		//사진 처리
		vo.setPhoto("noImage.jpg");
		
		return memberDAO.setMemberJoin(vo);
	}
	
	@Override
	public List<String> getMemberMIdsSearch(String email) {
		return memberDAO.getMemberMIdsSearch(email);
	}
	
	@Override
	public void setMemberPwdSearchUpdate(String mid, String imsiPwd) {
		memberDAO.setMemberPwdSearchUpdate(mid, imsiPwd);
	}
	
	@Override
	public int setPwdUpdate(String mid, String pwdNew) {
		return memberDAO.setPwdUpdate(mid, pwdNew);
	}

	@Override
	public int setMemberUpdate(MemberVO vo) {
		return memberDAO.setMemberUpdate(vo);
	}

	@Override
	public int setMemberDelete(String mid) {
		return memberDAO.setMemberDelete(mid);
	}

	@Override
	public MemberVO getMemberKakaoLoginSearch(String nickName, String email) {
		return memberDAO.getMemberKakaoLoginSearch(nickName, email);
	}

	@Override
	public void setKakaoMemberInput(String mid, String pwd, String nickName, String email) {
		memberDAO.setKakaoMemberInput(mid, pwd, nickName, email);
		
	}

}
