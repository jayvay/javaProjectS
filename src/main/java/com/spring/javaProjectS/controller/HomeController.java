package com.spring.javaProjectS.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//홈 화면
	@RequestMapping(value = {"/","/h"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		//return "redirect:/user/userList";
		//return "redirect:/user2/user2List";
		return "home";
	}
	
	//이미지 업로드
	@RequestMapping(value = "/imageUpload") //, method = RequestMethod.POST 안 쓰면 알아서 찾아서 간다
	public void imageUploadGet(MultipartFile upload,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");	//<- 실제 경로
		String oFileName = upload.getOriginalFilename();
		
		//파일 이름 중복 안 되게 uuid를 사용하거나 날짜+파일이름으로 저장되도록 한다
		//UUID uid = UUID.randomUUID();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		oFileName = sdf.format(date) + "_" + oFileName;
		
		//업로드 시킨 파일이 realPath(서버)에 저장된다
		byte[] bytes = upload.getBytes();	//
		FileOutputStream fos = new FileOutputStream(new File(realPath + oFileName));
		fos.write(bytes);
		
		//사진 미리보기
		PrintWriter out = response.getWriter();
		String fileUrl = request.getContextPath() + "/data/ckeditor/" + oFileName;	//<- 매핑 경로
		out.println("{\"originalFilename\":\""+oFileName+"\",\"uploaded\":1,\"url\":\""+fileUrl+"\"}");	//제이슨 포맷..
		
		out.flush();
		fos.close();
	}
}
