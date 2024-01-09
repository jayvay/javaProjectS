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
	
	//등록된 상품 리스트
	@RequestMapping(value = "/dbShopList", method = RequestMethod.GET)
	public String dbShopListGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part) {
		//소분류명을 가져온다
		List<DbProductVO> subTitleVOS = dbShopService.getSubTitle();
		model.addAttribute("subTitleVOS", subTitleVOS);
		
		//전체 상품 리스트 가져오기
		List<DbProductVO> productVOS = dbShopService.getDbShopList(part);
		model.addAttribute("productVOS", productVOS);
		return "admin/dbShop/dbShopList";
	}
	
	//관리자에서 진열된 상품을 클릭했을 때 해당 상품의 상세내역 보여주기
	@RequestMapping(value = "/dbShopContent", method = RequestMethod.GET)
	public String dbShopContentGet(Model model, int idx) {
		//상품 1건의 정보를 불러온다
		DbProductVO productVO = dbShopService.getDbShopProduct(idx);
		List<DbProductVO> optionVOS = dbShopService.getDbShopOption(idx);
		
		model.addAttribute("productVO", productVO);
		model.addAttribute("optionVOS", optionVOS);
		return "admin/dbShop/dbShopContent";
	}
	
	//옵션 등록창 보여주기(관리자 왼쪽 메뉴에서 선택시 처리)
	@RequestMapping(value = "/dbOption", method = RequestMethod.GET)
	public String dbOptionGet(Model model) {
		List<DbProductVO> mainVOS = dbShopService.getCategoryMain();
		model.addAttribute("mainVOS", mainVOS);
		
		return "admin/dbShop/dbOption";
	}
	
	//옵션 등록시 소분류 선택하면 상품 목록 가져오기
	@ResponseBody
	@RequestMapping(value = "/categoryProductName", method = RequestMethod.POST)
	public List<DbProductVO> categoryProductNamePost(String categoryMainCode, String categoryMiddleCode, String categorySubCode) {
		return dbShopService.getCategoryProductList(categoryMainCode, categoryMiddleCode, categorySubCode);
	}

	//옵션 등록시 상품 목록 중 상품 하나를 선택하면 해당 상품의 정보 가져오기
	@ResponseBody
	@RequestMapping(value = "/getProductInfor", method = RequestMethod.POST)
	public DbProductVO getProductInforPost(String productName) {
		return dbShopService.getProductInfor(productName);
	}
	
	//옵션 보기에서 '옵션보기' 버튼을 클릭하면 해당 상품의 옵션 가져오기
	@ResponseBody
	@RequestMapping(value = "/getOptionList", method = RequestMethod.POST)
	public List<DbProductVO> getOptionListPost(int productIdx) {
		return dbShopService.getOptionList(productIdx);
	}
	
	//옵션 등록
	@RequestMapping(value = "/dbOption", method = RequestMethod.POST)
	public String dbOptionPost(Model model, DbProductVO vo, String[] optionName, int[] optionPrice) {
		int res = 0;
		for(int i=0; i<optionName.length; i++) {
			res = dbShopService.getOptionSearch(vo.getProductIdx(), optionName[i]);
			if(res != 0) continue;
			
			//동일한 옵션이 없다면 vo에 현재 옵션 이름과 가격을 저장 후 
			vo.setProductIdx(vo.getProductIdx());
			vo.setOptionName(optionName[i]);
			vo.setOptionPrice(optionPrice[i]);
			
			res = dbShopService.setDbOptionInput(vo);
		}
		
		if(res != 0) return "redirect:/message/dbOptionInputOk";
		else return "redirect:/message/dbOptionInputNo";
	}
	
	/* 이 위는 관리자 */
	/*----------------------------------------------------------------------------------------------------*/
	/* 여기부터 SHOP 사용자 */
	
	//상품 리스트
	@RequestMapping(value = "/dbProductList", method = RequestMethod.GET)
	public String dbProductListGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part) {
		//소분류명을 가져온다
		List<DbProductVO> subTitleVOS = dbShopService.getSubTitle();
		model.addAttribute("subTitleVOS", subTitleVOS);
		model.addAttribute("part", part);
		
		//전체 상품 리스트 가져오기
		List<DbProductVO> productVOS = dbShopService.getDbShopList(part);
		model.addAttribute("productVOS", productVOS);
		return "dbShop/dbProductList";
	}
}
