package com.spring.javaProjectS.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.javaProjectS.service.MemberService;
import com.spring.javaProjectS.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender;
	
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
	
	//카카오 로그인
	@RequestMapping(value = "/kakaoLogin", method = RequestMethod.GET)
	public String kakaoLoginGet(HttpSession session, 
			HttpServletRequest request, HttpServletResponse response,
			String nickName, String email, String accessToken) {
	
		//카카오 로그인한 회원이 회원 리스트에 있는지 확인 (이메일의 '@' 기준으로 앞 아이디 부분을 DB 정보와 비교)
		MemberVO vo = memberService.getMemberKakaoLoginSearch(nickName, email);
		
		//카카오 로그인한 회원이 비회원이었다면, 자동으로 회원 가입 처리한다 (필수입력사항 : 아이디, 닉네임, 이메일) - 성명은 닉네임으로 처리
		if(vo == null) {
			//아이디 
			String mid = email.substring(0, email.indexOf("@"));
			
			//기존에 같은 아이디가 있다면 가입할 수 없다
			MemberVO vo2 = memberService.getMemberIdSearch(mid);
			if(vo2 != null) return "redirect:/message/idCheckNo";
			
			//임시 비밀번호 발급 후 메일로 전송
			UUID uid = UUID.randomUUID();
			String pwd = uid.toString().substring(0,8);
			session.setAttribute("sImsiPwd", pwd);
			
			//임시 비밀번호를 암호화
			pwd = passwordEncoder.encode(pwd);
			
			//임시 비밀번호를 메일로 전송
			
			//위의 정보로 회원가입 처리
			memberService.setKakaoMemberInput(mid, pwd, nickName, email);
			
			//회원 정보를 vo에 담아준다
			vo = memberService.getMemberIdSearch(mid);
		}	
			
		//세션 저장
		String sStrLevel = "";
		if(vo.getLevel() == 0) sStrLevel = "관리자";
		else if(vo.getLevel() == 1) sStrLevel = "우수회원";
		else if(vo.getLevel() == 2) sStrLevel = "정회원";
		else if(vo.getLevel() == 3) sStrLevel = "준회원";
		
		session.setAttribute("sAccessToken", accessToken);
		session.setAttribute("sMid", vo.getMid());
		session.setAttribute("sNickName", vo.getNickName());
		session.setAttribute("sLevel", vo.getLevel());
		session.setAttribute("sStrLevel", sStrLevel);
		
		return "redirect:/message/loginOk?mid="+ vo.getMid();
	}
	
	//카카오 로그아웃
	@RequestMapping(value = "/kakaoLogout", method = RequestMethod.GET)
	public String kakaoLogoutGet(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		String accessToken = (String) session.getAttribute("sAccessToken");
		
		String requestUrl = "https://kapi.kakao.com/v1/user/unlink";
		
		try {	//커넥션 객체와 연관
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer" + accessToken);
			
			//카카오에 정상 처리되면 responseCode 값은 200
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
					
			//정상 처리 후 카카오에서는 id를 보내준다. 아래 코드는 확인해보기 위해서 적어본다.
      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String id = "", data = "";
      while ((data = br.readLine()) != null) id += data;
      System.out.println("id : " + id);
      
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	
	//비밀번호 변경 전 비밀번호 확인 폼
	@RequestMapping(value = "/memberPwdCheck/{pwdFlag}", method = RequestMethod.GET)
	public String memberPwdCheckGet(@PathVariable String pwdFlag, Model model) {
		model.addAttribute("pwdFlag", pwdFlag);
		return "member/memberPwdCheck";
	}
	
	//비밀번호 변경 전 비밀번호 확인
	@ResponseBody
	@RequestMapping(value = "/memberPwdCheck", method = RequestMethod.POST)
	public String memberPwdCheckPost(String pwd, HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO vo = memberService.getMemberIdSearch(mid);
		
		if(passwordEncoder.matches(pwd, vo.getPwd())) {
			return "1";
		}
		else return "0";
	}
	
	//비밀번호 변경
	@ResponseBody
	@RequestMapping(value = "/memberPwdChange", method = RequestMethod.POST)
	public String memberPwdCheckOkPost(String pwdNew, HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		System.out.println("ㅡㅡ");
		pwdNew = passwordEncoder.encode(pwdNew);	//새로운 비밀번호 암호화
		int res = memberService.setPwdUpdate(mid, pwdNew);
		
		if(res != 0) return "1";
		else return "0";
	}
	
	//회원정보수정 폼
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.GET)
	public String memberUpdateGet(Model model, HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO vo = memberService.getMemberIdSearch(mid);
		model.addAttribute("vo", vo);
		return "member/memberUpdate";
	}
	
	//회원정보수정
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.POST)
	public String memberUpdatePost(MemberVO vo, HttpSession session) {
		String nickName = (String) session.getAttribute("sNickName");
		if(memberService.getMemberNickSearch(vo.getNickName()) != null && !nickName.equals(vo.getNickName())) {
			return "redirect:/message/nickCheckNo";
		}
		
		int res = memberService.setMemberUpdate(vo);
		if(res != 0) {
			session.setAttribute("sNickName", vo.getNickName());
			return "redirect:/message/memberUpdateOk";
		}
		else return "redirect:/message/memberUpdateNo";
	}
	
	//아이디 찾기
	@ResponseBody
	@RequestMapping(value = "/memberIdSearch", method = RequestMethod.POST)
	public String memberIdSearchPost(String email) {
		List<String> mids = memberService.getMemberMIdsSearch(email);
		String res = "";
		for(String mid : mids) {
			res += mid + "/";
		}
		if(mids.size() == 0) return "0";
		else return res;
	}
	
	//비밀번호 찾기
	@ResponseBody
	@RequestMapping(value = "/memberPwdSearch", method = RequestMethod.POST)
	public String memberPwdSearchPost(String mid, String email) throws MessagingException {
		MemberVO vo = memberService.getMemberIdSearch(mid);
		if(vo != null && vo.getEmail().equals(email)) {
		//정보 확인 후 임시 비밀번호 발급 받아 이메일로 발송하는 코드
			UUID uid = UUID.randomUUID();
			String imsiPwd = uid.toString().substring(0,8);
			
			//발급 받은 임시 비밀번호를 암호화 후 DB에 저장
			memberService.setMemberPwdSearchUpdate(mid, passwordEncoder.encode(imsiPwd));
			
			//발급된 임시 비밀번호를 이메일로 전송
			String title = "임시 비밀번호를 확인하세요.";
			String mailFlag = "임시 비밀번호 : " + imsiPwd;
			String res = mailSend(email, title, mailFlag);
			
			if(res == "1") return "1";
		}
		return "0";
	}
	
	
	//회원가입 이메일 인증
	@RequestMapping(value = "/joinEmail", method = RequestMethod.POST)
	public String joinEmailPost(String email) throws MessagingException {
		UUID uid = UUID.randomUUID();
		String str = uid.toString().substring(0,8);
		
		String title = "회원가입을 위한 이메일 인증입니다.";
		String mailFlag = "인증번호 : " + str;
		String res = mailSend(email, title, mailFlag);
		
		return "1";
	}
	
//메일 전송하는 일반 메소드(컨트롤러 아님)
	public String mailSend(String toMail, String title, String mailFlag) throws MessagingException {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String content = "";
		//메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		//메일 보관함에 회원이 보내 온 메세지들의 정보를 모두 저장한 후 작업 처리한다
		messageHelper.setTo(toMail);
		messageHelper.setSubject(title);
		messageHelper.setText(content);
		
		//메세지 보관함의 내용(content)에 발신자의 필요한 정보를 추가로 담아 전송시킨다 (스팸으로 걸리지 않기 위해)
		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>" + mailFlag + "</h3><hr><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";	//<img src=""> 의 src 주소는 ''말고 ""로 써야 함
		content += "<p>방문하기 : <a href='49.142.157.251:9090/cjgreen'>JavaProject</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);	//content를 이걸로 바꾸어 보관함에 다시 저장한다
		
		//본문에 기재된 그림 파일의 경로와 파일명을 별도로 표시한 후 다시 보관함에 저장한다
		//윈도우의 '/'은 자바에서 '\\'이다
//		FileSystemResource file = new FileSystemResource("D:\\JavaProject\\springframework\\works\\javaProjectS\\src\\main\\webapp\\resources\\images\\main.jpg");
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		messageHelper.addInline("main.jpg", file);
		
		//메일 전송하기
		mailSender.send(message);
		
		return "1";
	}
	
	//회원 탈퇴
	@ResponseBody
	@RequestMapping(value = "/memberDelete", method = RequestMethod.POST)
	public String memberDeletePost(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		
		int res = memberService.setMemberDelete(mid);
		if(res != 0) {
			session.invalidate();
			return "1";
		}
		else return "0";
	}
}
