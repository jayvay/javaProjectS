package com.spring.javaProjectS.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaProjectS.service.MemberService;
import com.spring.javaProjectS.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	//로그인 폼
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cMid")) {
					request.setAttribute("mid", cookies[i].getValue());
					break;
				}
			}
		}
		return "member/login";
	}
	
	//회원 로그인
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(HttpSession session, 
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name="mid", defaultValue="go123", required=false) String mid,
			@RequestParam(name="pwd", defaultValue="1234", required=false) String pwd,
			@RequestParam(name="idSave", defaultValue="", required=false) String idSave) {
		
			MemberVO vo = memberService.getMemberIdSearch(mid);
			
			if(vo != null && vo.getUserDel().equals("NO") && passwordEncoder.matches(pwd, vo.getPwd())) {
				//세션 저장
				String sStrLevel = "";
				if(vo.getLevel() == 0) sStrLevel = "관리자";
				else if(vo.getLevel() == 1) sStrLevel = "우수회원";
				else if(vo.getLevel() == 2) sStrLevel = "정회원";
				else if(vo.getLevel() == 3) sStrLevel = "준회원";
				
				session.setAttribute("sMid", mid);
				session.setAttribute("sNickName", vo.getNickName());
				session.setAttribute("sLevel", vo.getLevel());
				session.setAttribute("sStrLevel", sStrLevel);
				
				//쿠키 저장/삭제
				if(idSave.equals("on")) {
					Cookie cookieMid = new Cookie("cMid", mid);
					//cookieMid.setPath("/"); 스프링에서는 디폴트가 자신의 contextroot부터이기 때문에 생략 가능(jsp에선 생략하면 안돼)
					cookieMid.setMaxAge(60*60*24*7);
					response.addCookie(cookieMid);
				}
				else {
					Cookie[] cookies = request.getCookies();
					for(int i=0; i<cookies.length; i++) {
						if(cookies[i].getName().equals("cMid")) {
							cookies[i].setMaxAge(0);
							response.addCookie(cookies[i]);
							break;
						}
					}
				}
				return "redirect:/message/loginOk?mid="+mid;
			}
			else {
				return "redirect:/message/loginNo";
			}
	}
	
	//회원 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutGet(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		session.invalidate();
		return "redirect:/message/logoutOk?mid="+mid;
	}
	
	//로그인 후 메인창
	@RequestMapping(value = "/memberMain", method = RequestMethod.GET)
	public String memberMainGet() {
		return "member/memberMain";
	}

	//회원가입 폼
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String joinGet() {
		return "member/join";
	}
	
	//회원가입
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinPost(MemberVO vo) {
		//아이디 중복 체크
		if(memberService.getMemberIdSearch(vo.getMid()) != null) return "redirect:/message/idCheckNo";
		if(memberService.getMemberNickSearch(vo.getNickName()) != null) return "redirect:/message/nickCheckNo";
		
		//비밀번호 암호화
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		//회원 사진 처리(service 객체에서 처리 후 DB에 저장까지)
		int res = memberService.setMemberJoin(vo);
		
		
		if (res == 1) return "redirect:/message/joinOk";
		else return "redirect:/message/joinNo";
	}
	
	//회원가입 아이디 중복확인
	@ResponseBody
	@RequestMapping(value = "/joinIdCheck", method = RequestMethod.POST)
	public String joinIdCheckPost(String mid) {
		MemberVO vo = memberService.getMemberIdSearch(mid);
		System.out.println("mid : " + mid);
		if(vo != null) return "1";
		else return "0";
	}

	//회원가입 닉네임 중복확인
	@ResponseBody
	@RequestMapping(value = "/joinNickCheck", method = RequestMethod.POST)
	public String joinNickCheckPost(String nickName) {
		MemberVO vo = memberService.getMemberNickSearch(nickName);
		
		if(vo != null) return "1";
		else return "0";
	}
}
