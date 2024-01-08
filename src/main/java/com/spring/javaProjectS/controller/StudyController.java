package com.spring.javaProjectS.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaProjectS.common.ARIAUtil;
import com.spring.javaProjectS.common.SecurityUtil;
import com.spring.javaProjectS.service.StudyService;
import com.spring.javaProjectS.vo.Chart2VO;
import com.spring.javaProjectS.vo.KakaoAddressVO;
import com.spring.javaProjectS.vo.MailVO;
import com.spring.javaProjectS.vo.QrCodeVO;
import com.spring.javaProjectS.vo.TransactionVO;
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
	
	//모든 파일 삭제
	@ResponseBody
	@RequestMapping(value = "/fileUpload/fileDeleteAll", method = RequestMethod.POST)
	public String fileDeleteAllPost(HttpServletRequest request, 
			@RequestParam(name="file", defaultValue = "", required = false) String fName) {
		int res = 0;
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
		File targetFolder = new File(realPath);
		
		if(!targetFolder.exists()) {
			System.out.println(targetFolder + " 경로가 존재하지 않습니다");
			return res + "";
		}
		
		File[] files = targetFolder.listFiles();
		if(files.length != 0) {
			for(File file : files) {
				if(!file.isDirectory()) {
					file.delete();
				}
			}
			res = 1;
		}
		
		return res + "";
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
	
	//차트 연습 - 다양한 차트 그려보기
	@RequestMapping(value = "/chart/chart", method = RequestMethod.GET)
	public String chartGet(Model model,
			@RequestParam(name="part", defaultValue = "", required = false) String part) {
		model.addAttribute("part", part);
		return "study/chart/chart";
	}
	
	//차트 연습2 - 다양한 차트 분석
	@RequestMapping(value = "/chart2/chart2", method = RequestMethod.GET)
	public String chart2Get(Model model, Chart2VO vo) {
		model.addAttribute("vo", vo);
		return "study/chart2/chart2";
	}
	
	//차트 연습2 - 다양한 차트 분석
	@RequestMapping(value = "/chart2/chart2", method = RequestMethod.POST)
	public String chart2Post(Model model, Chart2VO vo) {
		model.addAttribute("vo", vo);
		return "study/chart2/chart2";
	}
	
	 //차트연습(최근방문횟수 분석처리)	
	@RequestMapping(value = "/chart2/visitCount", method = RequestMethod.GET)
	public String chart2Get(Model model,
			@RequestParam(name="part", defaultValue="", required=false) String part) {
		//System.out.println("part  " + part);
		List<Chart2VO> vos = studyService.getVisitCount();	// 최근 8일간 방문한 총 횟수를 가져온다.
		String[] visitDates = new String[8];
		int[] visitDays = new int[8];
		int[] visitCounts = new int[8];
		for(int i=0; i<visitDates.length; i++) {
			visitDates[i] = vos.get(i).getVisitDate().replaceAll("-", "").substring(4);
			visitDays[i] = Integer.parseInt(vos.get(i).getVisitDate().toString().substring(8));
			visitCounts[i] = vos.get(i).getVisitCount();
		}
		
		model.addAttribute("title", "최근 8일간 방문횟수");
		model.addAttribute("subTitle", "최근 8일동안 방문한 해당일자 방문자 총수를 표시합니다.");
		model.addAttribute("visitCount", "방문횟수");
		model.addAttribute("legend", "일일 방문 총횟수");
		model.addAttribute("topTitle", "방문날짜");
		model.addAttribute("part", part);
		model.addAttribute("visitDates", visitDates);
		model.addAttribute("visitDays", visitDays);
		model.addAttribute("visitCounts", visitCounts);
		return "study/chart2/chart2";
	}
	
	
	//randomAlphaNumeric 폼 : 알파벳과 숫자를 랜덤하게 출력
	@RequestMapping(value = "/captcha/randomAlphaNumeric", method = RequestMethod.GET)
	public String randomAlphaNumericGet() {
		return "study/captcha/randomAlphaNumeric";
	}
	
	//randomAlphaNumeric 출력
	@ResponseBody
	@RequestMapping(value = "/captcha/randomAlphaNumeric", method = RequestMethod.POST)
	public String randomAlphaNumericPost() {
		String res = RandomStringUtils.randomAlphanumeric(64);
		return res;
	}
	
	//captcha 폼 : 사람과 기계를 구별하기 위해 사용 
	@RequestMapping(value = "/captcha/captcha", method = RequestMethod.GET)
	public String captchaGet() {
		return "study/captcha/captcha";
	}
	
	//captcha 이미지 만들기 
	@ResponseBody
	@RequestMapping(value = "/captcha/captchaImage", method = RequestMethod.POST)
	public String captchaImagePost(HttpSession session, HttpServletRequest request) {
		//시스템에 설정된 폰트 출력해보기
//		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
//		for(Font f : fonts) {
//			System.out.println(f.getName());
//		}
		
		try {
			String randomString = RandomStringUtils.randomAlphanumeric(5); //알파뉴메릭문자 5개를 가져온다
			System.out.println("randomString : " + randomString);
			session.setAttribute("sCaptcha", randomString);
			
			Font font = new Font("Jokerman", Font.ITALIC, 30);
			FontRenderContext frc = new FontRenderContext(null, true, true); //랜더링을 하겠다
			Rectangle2D bounds = font.getStringBounds(randomString, frc); //사각형 2차원
			int w = (int) bounds.getWidth();
			int h = (int) bounds.getHeight();
			
			//이미지로 생성하기
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics(); //껍데기
			
			g.fillRect(0, 0, w, h);
			g.setColor(new Color(0, 156, 240));
			g.setFont(font);
			//<<-여기서 각종 랜더링 명령어에 의한 captcha 문자 작업을 하는데 생략함
			g.drawString(randomString, (float)bounds.getX(), (float)-bounds.getY());
			g.dispose();
			
			String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
			ImageIO.write(image, "png", new File(realPath + "captcha.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "study/captcha/captcha";
	}

	//captcha 확인하기 
	@ResponseBody
	@RequestMapping(value = "/captcha/captcha", method = RequestMethod.POST)
	public String captchaPost(String strCaptcha, HttpSession session) {
		if(strCaptcha.equals(session.getAttribute("sCaptcha").toString())) return "1";
		else return "0";
	}
	
	// QR Code 폼 보기
	@RequestMapping(value = "/qrCode/qrCode", method = RequestMethod.GET)
	public String qrCodeGet() {
		return "study/qrCode/qrCode";
	}
	
	// QR Code 개인 정보 등록 폼 보기
	@RequestMapping(value = "/qrCode/qrCodeEx1", method = RequestMethod.GET)
	public String qrCodeEx1Get() {
		return "study/qrCode/qrCodeEx1";
	}
	
	// QR Code 개인 정보 QR 코드 생성
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeEx1", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String qrCodeEx1Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		String qrCodeName = studyService.setQrCodeCreate1(realPath, vo);
		return qrCodeName;
	}

	// QR Code2 소개할 사이트 주소 등록 폼
	@RequestMapping(value = "/qrCode/qrCodeEx2", method = RequestMethod.GET)
	public String qrCodeEx2Get() {
		return "study/qrCode/qrCodeEx2";
	}
	
	// QR Code2 소개할 사이트 QR 코드 생성
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeEx2", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String qrCodeEx2Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		String qrCodeName = studyService.setQrCodeCreate2(realPath, vo);
		return qrCodeName;
	}
	
	// QR Code3 영화 예매 등록 폼
	@RequestMapping(value = "/qrCode/qrCodeEx3", method = RequestMethod.GET)
	public String qrCodeEx3Get() {
		return "study/qrCode/qrCodeEx3";
	}
	
	// QR Code3 영화 예매 정보 QR 코드 생성
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeEx3", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String qrCodeEx3Post(HttpServletRequest request, QrCodeVO vo) {
		String movieTemp = vo.getMid() + "_";
		movieTemp += vo.getMovieName() + "_";
		movieTemp += vo.getMovieDate() + "_";
		movieTemp += vo.getMovieTime() + "_A";
		movieTemp += vo.getMovieAdult() + "_C";
		movieTemp += vo.getMovieChild();
		vo.setMovieTemp(movieTemp);
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		String qrCodeName = studyService.setQrCodeCreate3(realPath, vo);
		return qrCodeName;
	}
	
	// QR Code4 영화 예매 정보를 DB에 저장할 폼
	@RequestMapping(value = "/qrCode/qrCodeEx4", method = RequestMethod.GET)
	public String qrCodeEx4Get() {
		return "study/qrCode/qrCodeEx4";
	}
	
	// QR Code4 영화 예매 정보 QR 코드 생성 및 DB에 저장
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeEx4", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String qrCodeEx4Post(HttpServletRequest request, QrCodeVO vo) {
		String movieTemp = vo.getMid() + "_";
		movieTemp += vo.getMovieName() + "_";
		movieTemp += vo.getMovieDate() + "_";
		movieTemp += vo.getMovieTime() + "_A";
		movieTemp += vo.getMovieAdult() + "_C";
		movieTemp += vo.getMovieChild();
		vo.setMovieTemp(movieTemp);
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		String qrCodeName = studyService.setQrCodeCreate4(realPath, vo);
		return qrCodeName;
	}
	
	// QR Code4 영화 예매 정보를 DB에서 검색하여 가져오기
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeSearch", method = RequestMethod.POST)
	public QrCodeVO qrCodeSearchPost(String qrCode) {
		return studyService.getQrCodeSearch(qrCode);
	}
	
	// 썸네일 연습 폼
	@RequestMapping(value = "/thumbnail/thumbnailForm", method = RequestMethod.GET)
	public String thumbnailFormGet() {
		return "study/thumbnail/thumbnailForm";
	}
	
	// 썸네일 이미지 처리하기
	@RequestMapping(value = "/thumbnail/thumbnailForm", method = RequestMethod.POST)
	public String thumbnailFormPost(MultipartFile file) {
		int res = studyService.setThumbnailCreate(file);
		if(res == 1) return "redirect:/message/thumbnailOk";
		else return "redirect:/message/thumbnailNo";
	}
	
	//jsoup을 이용한 웹 크롤링 폼
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.GET)
	public String crawlingJsoupGet() {
		return "study/crawling/jsoup";
	}
	
	//jsoup을 이용한 웹 크롤링
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.POST)
	public ArrayList<String> crawlingJsoupPost(String url, String selector) throws Exception {	//io 등 여러 개 걸려서 제일 큰 Exception 으로 처리..
		Connection conn = Jsoup.connect(url); //org.jsoup.Connection 으로 올려야 함
		
		Document document = conn.get();
		
		//Elements selects = document.select("div.cjs_t");	
		//Elements selects = document.select("div.cjs_news_mw");	
		//Elements selects = document.select("div.mod_vw_thumb link_thumb");	
		Elements selects = document.select(selector);	
		//System.out.println("selects : " + selects);
		
		ArrayList<String> vos = new ArrayList<String>();
		int i = 0;
		for(Element select : selects) {
			i++;
			//System.out.println(i + " : " + select);
			//System.out.println(i + " : " + select.text());
			System.out.println(i + " : " + select.html());
			vos.add(i + " : " + select.html());
		}
		
		return vos;
	}
	
	//jsoup을 이용한 웹 크롤링 (네이버 검색어로 검색결과 처리하기)
	@ResponseBody
	@RequestMapping(value = "/crawling/naverSearch", method = RequestMethod.POST)
	public ArrayList<String> crawlingnaverSearchPost(String search, String searchSelector) throws Exception {	//io 등 여러 개 걸려서 제일 큰 Exception 으로 처리..
		Connection conn = Jsoup.connect(search); //org.jsoup.Connection 으로 올려야 함
		
		Document document = conn.get();
		Elements selects = document.select(searchSelector);	
		
		ArrayList<String> vos = new ArrayList<String>();
		int i = 0;
		for(Element select : selects) {
			i++;
			System.out.println(i + " : " + select.html());
			vos.add(i + " : " + select.html());
		}
		
		return vos;
	}
	
	// jsoup를 이용한 웹크롤링 처리하기(네이버 이미지 검색어로 검색결과 처리하기)
	@ResponseBody
	@RequestMapping(value = "/crawling/naverImageSearch", method = RequestMethod.POST)
	public ArrayList<String> crawlingNaverImageSearchPost(String search, String searchImageSelector) throws Exception {
		Connection conn = Jsoup.connect(search);
		
		Document document = conn.get();
		System.out.println("document : " + document);
		Elements selects = document.select(searchImageSelector);
		System.out.println("searchImageSelector : " + selects);
		
		ArrayList<String> vos = new ArrayList<String>();
		int i = 0;
		for(Element select : selects) {
			i++;
			System.out.println(i + " : " + select.html());
			vos.add(i + " : " + select.html());
		}
		
		return vos;
	}
	
  // 크롤링폼 보기(selenium)
  @RequestMapping(value = "/crawling/selenium", method = RequestMethod.GET)
  public String crawlingSeleniumGet() {
    return "study/crawling/selenium";
  }

  // 크롤링하기(selenium)
  @ResponseBody
  @RequestMapping(value = "/crawling/selenium", method = RequestMethod.POST)
  public List<HashMap<String, Object>> crawlingSeleniumPost(HttpServletRequest request) {
    List<HashMap<String, Object>> array = new ArrayList<HashMap<String, Object>>();

    try {
      String realPath = request.getSession().getServletContext().getRealPath("/resources/crawling/");
      System.setProperty("webdriver.chrome.driver", realPath + "chromedriver.exe");  
      //https://googlechromelabs.github.io/chrome-for-testing/#stable에서 Version: 120.0.6099.109 (r1217362)의 chromedriver(win64) URL 드래그 후 크롬에 입력하면 다운받아짐


      WebDriver driver = new ChromeDriver();
      driver.get("http://www.cgv.co.kr/movies/");

      /* ---------- 응용 실습 ------------------- */

      // 현재 상영작만 보기 클릭처리
      WebElement btnMore = driver.findElement(By.id("chk_nowshow"));
      btnMore.click();


      // 더보기 버튼을 클릭했을때 더 많은 영화목록 보여주기 처리..
      btnMore = driver.findElement(By.className("link-more"));
      btnMore.click();


      // 화면이 더 열리는 동안 시간 지연시켜주기(3초)
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      // List로 처리해서 프론트로 넘겨주도록 하자.
      List<WebElement> elements = driver.findElements(By.cssSelector(".sect-movie-chart ol li"));
      for(WebElement element : elements){
        HashMap<String, Object> map = new HashMap<String, Object>();
        String image="<img src='" + element.findElement(By.tagName("img")).getAttribute("src") + "' width='300px'  />";
        String link =element.findElement(By.tagName("a")).getAttribute("href");
        String title="<a href='"+link+"' target='_blank'>" + element.findElement(By.className("title")).getText() + "</a>";
        String percent=element.findElement(By.className("percent")).getText();
        map.put("title", title);
        map.put("image", image);
        map.put("link", link);
        map.put("percent", percent);
        array.add(map);
      }

    } catch (Exception e) {
      System.out.println("CGV Crawling error : " + e.toString());
    }
    //System.out.println("array : " + array);
    return array;
  }
  
  @RequestMapping(value = "/transaction/transaction", method = RequestMethod.GET)
  public String transactionGet(Model model) {
  	List<TransactionVO> vos = studyService.getTransactionList();
  	List<TransactionVO> vos2 = studyService.getTransactionList2();
  	model.addAttribute("vos", vos);
  	model.addAttribute("vos2", vos2);
  	return "study/transaction/transaction";
  }
  
  //트랜잭션을 통해 803,804를 하나의 작업 단위로 묶어준다
  @Transactional
  @RequestMapping(value = "/transaction/transaction", method = RequestMethod.POST)
  public String transactionPost(Model model, TransactionVO vo) {
  	studyService.setTransactionUser1Input(vo);
  	studyService.setTransactionUser2Input(vo);
  	return "redirect:/study/transaction/transaction";
  }
  
  //user와 user2의 일괄 삽입 작업 처리
  @ResponseBody
  @RequestMapping(value = "/transaction/transaction2", method = RequestMethod.POST)
  public String transaction2Post(Model model, @Validated TransactionVO vo, BindingResult bindingResult) {
//	public String transaction2Post(Model model, 
//			String mid,
//			String name,
//			int age,
//			String address,
//			String job) {
  	if(bindingResult.hasFieldErrors()) return "redirect:/message/validateNo";
  	
  	System.out.println("vo : " + vo);
  	//System.out.println("mid : " + mid);
  	
  	studyService.setTransactionUserInput(vo);
  	//studyService.setTransactionUserInput2(mid,name,age,address,job);
  	
  	return "redirect:/study/transaction/transaction";
  }
}
