package com.spring.javaProjectS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaProjectS.common.ARIAUtil;
import com.spring.javaProjectS.common.SecurityUtil;
import com.spring.javaProjectS.service.StudyService;
import com.spring.javaProjectS.vo.KakaoAddressVO;
import com.spring.javaProjectS.vo.MailVO;
import com.spring.javaProjectS.vo.UserVO;

@Controller
@RequestMapping("/study")
public class StudyController {

	@Autowired
	StudyService studyService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender;
	
	@RequestMapping(value = "/ajax/ajaxForm", method = RequestMethod.GET)
	public String ajaxFormGet() {
		
		return "study/ajax/ajaxForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest1", method = RequestMethod.POST)
	public String ajaxTest1Get(int idx) {
		System.out.println("idx : " + idx);
		
		return idx+"";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest2", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String ajaxTest2Post(String str) {
		System.out.println("str : " + str);
		
		return str;
	}

	@RequestMapping(value = "/ajax/ajaxTest3_1", method = RequestMethod.GET)
	public String ajaxTest3_1Get(String str) {
		return "study/ajax/ajaxTest3_1";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_1", method = RequestMethod.POST)
	public String[] ajaxTest3_1Post(String dodo) {
		//String[] strArray = new String[100];
		//strArray = studyService.getCityStringArray(dodo);
		//return strArray;
		return studyService.getCityStringArray(dodo);
	}

	@RequestMapping(value = "/ajax/ajaxTest3_2", method = RequestMethod.GET)
	public String ajaxTest3_2Get() {
		return "study/ajax/ajaxTest3_2";
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_2", method = RequestMethod.POST)
	public ArrayList<String> ajaxTest3_2Post(String dodo) {
		return studyService.getCityArrayList(dodo);
	}
	
	@RequestMapping(value = "/ajax/ajaxTest3_3", method = RequestMethod.GET)
	public String ajaxTest3_3Get() {
		return "study/ajax/ajaxTest3_3";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_3", method = RequestMethod.POST)
	public HashMap<Object, Object> ajaxTest3_3Post(String dodo) {
		
		ArrayList<String> vos = studyService.getCityArrayList(dodo);
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("city", vos);
		
		return map;
	}
	
	@RequestMapping(value = "/ajax/ajaxTest4_1", method = RequestMethod.GET)
	public String ajaxTest4_1Get() {
		return "study/ajax/ajaxTest4_1";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest4_1", method = RequestMethod.POST)
	public UserVO ajaxTest4_1Post(String mid) {
		return studyService.getUserSearchVO(mid);
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest4_2", method = RequestMethod.POST)
	public List<UserVO> ajaxTest4_2Post(String mid) {
		return studyService.getUserSearchVOS(mid);
	}
	
	@RequestMapping(value = "/uuid/uidForm", method = RequestMethod.GET)
	public String uidFormGet() {
		return "study/uuid/uidForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "/uuid/uidForm", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String uidFormPost() {
		UUID uid = UUID.randomUUID();
		return uid.toString();
	}
	
	@RequestMapping(value = "/password/sha256", method = RequestMethod.GET)
	public String sha256Get() {
		return "study/password/sha256";
	}
	
	@ResponseBody
	@RequestMapping(value = "/password/sha256", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String sha256Post(String pwd) {
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,8);
		
		SecurityUtil security = new SecurityUtil();
		String encPwd = security.encryptSHA256(pwd + salt);
		
		pwd = "원본 비밀번호 : " + pwd + " / salt키 : " + salt + " / 암호화된 비밀번호 : " + encPwd;
		
		return pwd;
	}

	@RequestMapping(value = "/password/aria", method = RequestMethod.GET)
	public String ariaGet() {
		return "study/password/aria";
	}
	
	@ResponseBody
	@RequestMapping(value = "/password/aria", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String ariaPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,8);
		
		String encPwd = "";
		String decPwd = "";
		
		encPwd = ARIAUtil.ariaEncrypt(pwd + salt);
		decPwd = ARIAUtil.ariaDecrypt(encPwd);
		
		pwd = "원본 비밀번호 : " + pwd + " / salt키 : " + salt + " / 암호화된 비밀번호 : " + encPwd + " / 복호화된 비밀번호 : " + decPwd;
		
		return pwd;
	}
	
	@RequestMapping(value = "/password/bCryptPassword", method = RequestMethod.GET)
	public String bCryptPasswordGet() {
		return "study/password/bCryptPassword";
	}
	
	//bCryptPasswordEncoder 암호화
	@ResponseBody
	@RequestMapping(value = "/password/bCryptPassword", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String bCryptPasswordPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		String encPwd = "";
		encPwd = passwordEncoder.encode(pwd);
		
		pwd = "원본 비밀번호 : " + pwd + " / 암호화된 비밀번호 : " + encPwd;
		
		return pwd;
	}

	//메일 전송폼 호출
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.GET)
	public String mailFormGet() {
		return "study/mail/mailForm";
	}

	//메일 전송하기
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.POST)
	public String mailFormPost(MailVO vo, HttpServletRequest request) throws MessagingException {
		String toMail = vo.getToMail();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		//메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		//메일 보관함에 회원이 보내 온 메세지들의 정보를 모두 저장한 후 작업 처리한다
		messageHelper.setTo(toMail);
		messageHelper.setSubject(title);
		messageHelper.setText(content);
		
		//메세지 보관함의 내용(content)에 발신자의 필요한 정보를 추가로 담아 전송시킨다 (스팸으로 걸리지 않기 위해)
		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>JavaProjectS 에서 보냅니다</h3><hr><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";	//<img src=""> 의 src 주소는 ''말고 ""로 써야 함
		content += "<p>방문하기 : <a href='49.142.157.251:9090/cjgreen'>JavaProject</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);	//content를 이걸로 바꾸어 보관함에 다시 저장한다
		
		//본문에 기재된 그림 파일의 경로와 파일명을 별도로 표시한 후 다시 보관함에 저장한다
		//윈도우의 '/'은 자바에서 '\\'이다
//		FileSystemResource file = new FileSystemResource("D:\\JavaProject\\springframework\\works\\javaProjectS\\src\\main\\webapp\\resources\\images\\main.jpg");
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		messageHelper.addInline("main.jpg", file);
		
		//첨부파일 보내기
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/chicago.jpg"));
		messageHelper.addAttachment("chicago.jpg", file);

		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.zip"));
		messageHelper.addAttachment("main.zip", file);
		
		//메일 전송하기
		mailSender.send(message);
		
		return "redirect:/message/mailSendOk";
	}
	
	@RequestMapping(value = "/fileUpload/fileUpload", method = RequestMethod.GET)
	public String fileUploadGet(HttpServletRequest request, Model model) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study"); //실제 서버의 경로(매핑 경로 아님)
		
		String[] files = new File(realPath).list(); //list 메소드로 폴더에 있는 파일 모두의 정보를 읽어옴
		
		model.addAttribute("files", files);
		model.addAttribute("filesCnt", files.length);
		
		return "study/fileUpload/fileUpload";
	}
	
	@RequestMapping(value = "/fileUpload/fileUpload", method = RequestMethod.POST)
	public String fileUploadPost(MultipartFile fName, String mid) {
		
		int res = studyService.fileUpload(fName, mid);
		
		
		if(res == 1) return "redirect:/message/fileUploadOk";
		else return "redirect:/message/fileUploadNo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/fileUpload/fileDelete", method = RequestMethod.POST)
	public String fileDeletePost(HttpServletRequest request,
			@RequestParam(name="file", defaultValue = "", required=false) String fName) {
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
		
		int res = 0;
		File file = new File(realPath + fName);

		if(file.exists()) {
			file.delete();
			res = 1;
		}
		
		return res + "";
	}
	
	@RequestMapping(value = "/fileUpload/fileDownload", method = RequestMethod.GET)
	public void fileDownloadGet(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name="file", defaultValue = "", required=false) String file) throws IOException {
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
		
		File DownloadFile = new File(realPath + file);
		
		String downFileName = new String(file.getBytes("UTF-8"), "8859_1");	//windows : 8859_1
		//mime..
		response.setHeader("Content-Disposition", "attachment:filename=" + downFileName);
		
		FileInputStream fis = new FileInputStream(DownloadFile);
		
		ServletOutputStream sos = response.getOutputStream();
		
		byte[] bytes = new byte[2048];
		int data = 0;
		while((data = fis.read(bytes, 0, bytes.length)) != -1) {
			sos.write(bytes, 0, data);
		}
			sos.flush();
			sos.close();
			fis.close();
	}
	
	//카카오 지도 API 사용하기
	@RequestMapping(value = "/kakao/kakaomap", method = RequestMethod.GET)
	public String kakaoMapGet() {
		return "study/kakao/kakaoMap";
	}
	
	//카카오 지도 연습1
	@RequestMapping(value = "/kakao/kakaoEx1", method = RequestMethod.GET)
	public String kakaoEx1Get() {
		return "study/kakao/kakaoEx1";
	}
	
	//카카오 지도 연습1 - 선택한 지점명을 DB에 저장하기
	@ResponseBody
	@RequestMapping(value = "/kakao/kakaoEx1", method = RequestMethod.POST)
	public String kakaoEx1Post(KakaoAddressVO vo) {
		KakaoAddressVO kakaoVO = studyService.getKakaoAddressSearch(vo.getAddress());
		
		if(kakaoVO != null) return "0";
		
		studyService.setKakaoAddressInput(vo);
		
		return "1";
	}
	
	//카카오 지도 연습2 - 나의 DB에 저장된 주소목록 가져오기 / 지점검색 추가
	@RequestMapping(value = "/kakao/kakaoEx2", method = RequestMethod.GET)
	public String kakaoEx2Get(Model model,
			@RequestParam(name="address", defaultValue = "", required = false) String address) {
		
		List<KakaoAddressVO> vos = studyService.getKakaoAddressList();
		
		KakaoAddressVO vo = new KakaoAddressVO();
		if(address.equals("")) {
			vo.setLatitude(36.63510627148798); 
			vo.setLongitude(127.4595239897276);
		}
		else vo = studyService.getKakaoAddressSearch(address);
		
		model.addAttribute("vos", vos);
		model.addAttribute("vo", vo);
		
		return "study/kakao/kakaoEx2";
	}
	
	//카카오 지도 연습2 - 나의 DB에 저장된 주소 삭제하기
	@ResponseBody
	@RequestMapping(value = "/kakao/kakaoAddressDelete", method = RequestMethod.POST)
	public String kakaoEx2Post(@RequestParam(name="address", defaultValue = "", required = false) String address) {
		int res = 0;
		
		res = studyService.setKakaoAddressDelete(address);
		
		return res + "";
	}

	//카카오 지도 연습3 - KakaoDB에 저장된 지명으로 검색 후 나의 DB에 주소 저장하기
	@RequestMapping(value = "/kakao/kakaoEx3", method = RequestMethod.GET)
	public String kakaoEx3Get(Model model,
			@RequestParam(name="address", defaultValue = "사창사거리", required = false) String address) {
		model.addAttribute("address", address);
		return "study/kakao/kakaoEx3";
	}
}
