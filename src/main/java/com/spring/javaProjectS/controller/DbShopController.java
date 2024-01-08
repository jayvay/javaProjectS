package com.spring.javaProjectS.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaProjectS.service.DbShopService;
import com.spring.javaProjectS.vo.DbProductVO;

@Controller
@RequestMapping("/dbShop")
public class DbShopController {

	@Autowired
	DbShopService dbShopService; 
	
	//상품분류등록 폼 호출 
	@RequestMapping(value = "/dbCategory", method = RequestMethod.GET)
	public String dbCategoryGet(Model model) {
		
		List<DbProductVO> mainVOS = dbShopService.getCategoryMain();
		List<DbProductVO> middleVOS = dbShopService.getCategoryMiddle();
		List<DbProductVO> subVOS = dbShopService.getCategorySub();
		model.addAttribute("mainVOS", mainVOS);
		model.addAttribute("middleVOS", middleVOS);
		model.addAttribute("subVOS", subVOS);
		
		return "admin/dbShop/dbCategory";
	}
	
	//대분류 등록하기
	@ResponseBody
	@RequestMapping(value = "/categoryMainInput", method = RequestMethod.POST)
	public String categoryMainInputPost(DbProductVO vo) {
		//현재 등록하려는 대분류명이 기존에 생성된 대분류명인지 확인한다
		DbProductVO productVO = dbShopService.getCategoryMainOne(vo.getCategoryMainCode(), vo.getCategoryMainName());
		
		if(productVO != null) return "0";
		
		int res = dbShopService.setCategoryMainInput(vo);
		
		return res + "";
	}
	
	//대분류 삭제하기
	@ResponseBody
	@RequestMapping(value = "/categoryMainDelete", method = RequestMethod.POST)
	public String categoryMainDeletePost(DbProductVO vo) {
		//기존에 생성된 대분류 항목에 중분류 데이터가 있는지 확인한다
		DbProductVO productVO = dbShopService.getCategoryMiddleOne(vo);
		
		if(productVO != null) return "0";
		
		int res = dbShopService.setCategoryMainDelete(vo.getCategoryMainCode());
		
		return res + "";
	}
	
	//중분류 등록하기
	@ResponseBody
	@RequestMapping(value = "/categoryMiddleInput", method = RequestMethod.POST)
	public String categoryMiddleInputPost(DbProductVO vo) {
		//기존에 생성된 중분류인지 확인한다
		DbProductVO productVO = dbShopService.getCategoryMiddleOne(vo);
		
		if(productVO != null) return "0";
		
		int res = dbShopService.setCategoryMiddleInput(vo);
		
		return res + "";
	}

	//중분류 삭제하기
	@ResponseBody
	@RequestMapping(value = "/categoryMiddleDelete", method = RequestMethod.POST)
	public String categoryMiddleDeletePost(DbProductVO vo) {
		//기존의 중분류 항목에 하위 항목(소분류) 데이터가 있는지 확인한다
		DbProductVO productVO = dbShopService.getCategorySubOne(vo);
		
		if(productVO != null) return "0";
		
		int res = dbShopService.setCategoryMiddleDelete(vo.getCategoryMiddleCode());
		
		return res + "";
	}
	
	//대분류에 맞춰 바뀌는 중분류
	@ResponseBody
	@RequestMapping(value = "/categoryMiddleName", method = RequestMethod.POST)
	public List<DbProductVO> categoryMiddleNamePost(String categoryMainCode) {
		return dbShopService.getCategoryMiddleName(categoryMainCode);
	}
	
	//소분류 등록하기
	@ResponseBody
	@RequestMapping(value = "/categorySubInput", method = RequestMethod.POST)
	public String categorySubInputPost(DbProductVO vo) {
		//기존에 생성된 소분류인지 확인한다
		DbProductVO productVO = dbShopService.getCategorySubOne(vo);
		
		if(productVO != null) return "0";
		
		int res = dbShopService.setCategorySubInput(vo);
		
		return res + "";
	}
	
	//상품 등록 폼 호출
	@RequestMapping(value = "/dbProduct", method = RequestMethod.GET)
	public String dbProductGet(Model model) {
		List<DbProductVO> mainVOS = dbShopService.getCategoryMain();
		model.addAttribute("mainVOS", mainVOS);
		return "admin/dbShop/dbProduct";
	}
	
	//중분류에 맞춰 바뀌는 소분류
	@ResponseBody
	@RequestMapping(value = "/categorySubName", method = RequestMethod.POST)
	public List<DbProductVO> categorySubNamePost(String categoryMainCode, String categoryMiddleCode) {
		return dbShopService.getCategorySubName(categoryMainCode, categoryMiddleCode);
	}
	
	//소분류 삭제하기
	@ResponseBody
	@RequestMapping(value = "/categorySubDelete", method = RequestMethod.POST)
	public String categorySubDeletePost(DbProductVO vo) {
		//기존의 소분류 항목에 하위 항목(상품) 데이터가 있는지 확인한다
		DbProductVO productVO = dbShopService.getCategoryProductName(vo);
		
		if(productVO != null) return "0";
		
		int res = dbShopService.setCategorySubDelete(vo.getCategorySubCode());
		
		return res + "";
	}
	
	//관리자 상품 등록시 ckeditor 에 사진 첨부할 경우 dbShop 폴더에 저장, 저장한 파일을 브라우저 textarea 상자에 보여준다
	@ResponseBody
	@RequestMapping("/imageUpload")
	public void imageUploadGet(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String originalFilename = upload.getOriginalFilename();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		originalFilename = sdf.format(date) + "_" + originalFilename;
		
		byte[] bytes = upload.getBytes();
		
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/dbShop/");
		OutputStream outStr = new FileOutputStream(new File(uploadPath + originalFilename));
		outStr.write(bytes);
		
		PrintWriter out = response.getWriter();
		String fileUrl = request.getContextPath() + "/data/dbShop/" + originalFilename;
		out.println("{\"originalFilename\":\""+originalFilename+"\",\"uploaded\":1,\"url\":\""+fileUrl+"\"}");
		
		out.flush();
		outStr.close();
	}
	
	//상품 등록 처리
	@RequestMapping(value = "/dbProduct", method = RequestMethod.POST)
	public String dbProductPost(MultipartFile file, DbProductVO vo) {
		//이미지 파일을 업로드할 때 dbshop 폴더에서 dbshop/product 폴더로 복사 및 DB에 저장
		int res = dbShopService.imgCheckProductInput(file, vo);
		
		if(res != 0) return "redirect:/message/dbProductInputOk";
		return "admin/dbShop/dbProductInputNo";
	}
}
