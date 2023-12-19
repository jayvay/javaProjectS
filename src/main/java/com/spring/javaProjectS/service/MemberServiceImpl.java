package com.spring.javaProjectS.service;

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
	public int setPwdChangeOk(String mid, String pwd) {
		return memberDAO.setPwdChangeOk(mid, pwd);
	}

	@Override
	public void setMemberPwdUpdate(String mid, String imsiPwd) {
		memberDAO.setMemberPwdUpdate(mid, imsiPwd);
	}
}
