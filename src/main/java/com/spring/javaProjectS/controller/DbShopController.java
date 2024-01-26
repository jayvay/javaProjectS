package com.spring.javaProjectS.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaProjectS.service.DbShopService;
import com.spring.javaProjectS.service.MemberService;
import com.spring.javaProjectS.vo.DbBaesongVO;
import com.spring.javaProjectS.vo.DbCartVO;
import com.spring.javaProjectS.vo.DbOrderVO;
import com.spring.javaProjectS.vo.DbPaymentVO;
import com.spring.javaProjectS.vo.DbProductVO;
import com.spring.javaProjectS.vo.MemberVO;

@Controller
@RequestMapping("/dbShop")
public class DbShopController {

	@Autowired
	DbShopService dbShopService; 
	
	@Autowired
	MemberService memberService;
	
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
	
	//진열된 상품클릭시 해당상품의 상세정보 보여주기(사용자(고객)화면에서 보여주기)
	@RequestMapping(value="/dbProductContent", method=RequestMethod.GET)
	public String dbProductContentGet(int idx, Model model) {
		DbProductVO productVo = dbShopService.getDbShopProduct(idx);			// 상품의 상세정보 불러오기
		List<DbProductVO> optionVos = dbShopService.getDbShopOption(idx);	// 옵션의 모든 정보 불러오기
		
		model.addAttribute("productVo", productVo);
		model.addAttribute("optionVos", optionVos);
		
		return "dbShop/dbProductContent";
	}
	
	//상품 상세정보창에서 '장바구니' 버튼 눌렀을 때 
	@RequestMapping(value="/dbProductContent", method=RequestMethod.POST)
	public String dbProductContentPost(DbCartVO vo, HttpSession session, String flag) {
		String mid = (String) session.getAttribute("sMid");
		DbCartVO resVo = dbShopService.getDbCartProductOptionSearch(vo.getProductName(), vo.getOptionName(), mid);
		System.out.println("vo.getOptionIdx():" + vo.getOptionIdx());
		
		int res = 0;
		if(resVo != null) {
			String[] voOptionNums = vo.getOptionNum().split(",");
			String[] resOptionNums = resVo.getOptionNum().split(",");
			int[] nums = new int[99];
			String strNums = "";
			for(int i=0; i<voOptionNums.length; i++) {
				nums[i] += (Integer.parseInt(voOptionNums[i]) + Integer.parseInt(resOptionNums[i]));
				strNums += nums[i];
				if(i < nums.length - 1) strNums += ",";
			}
			vo.setOptionNum(strNums);
			res = dbShopService.dbShopCartUpdate(vo);
		}
		else {
			res = dbShopService.dbShopCartInput(vo);
		}
		
		if(res != 0) {
			if(flag.equals("order")) {
				return "redirect:/message/cartOrderOk";
			}
			else {
				return "redirect:/message/cartInputOk";
			}
		}
		else return "redirect:/message/cartOrderNo";
	}
	
	//장바구니 보기
	@RequestMapping(value="/dbCartList", method=RequestMethod.GET)
	public String dbCartGet(HttpSession session, DbCartVO vo, Model model) {
		String mid = (String) session.getAttribute("sMid");
		List<DbCartVO> vos = dbShopService.getDbCartList(mid);
		
		if(vos.size() == 0) {
			return "redirect:/message/cartEmpty";
		}
		
		model.addAttribute("cartListVOS", vos);
		return "dbShop/dbCartList";
	}
	
	//장바구니에서 주문 취소한 상품을 장바구니에서 삭제시켜주기
	@ResponseBody
	@RequestMapping(value="/dbCartDelete", method=RequestMethod.POST)
	public String dbCartDeleteGet(int idx) {
		int res = dbShopService.dbCartDelete(idx);
		return res + "";
	}
	
	//장바구니에서 '주문하기' 버튼을 클릭시에 처리할 부분
	@RequestMapping(value="/dbCartList", method=RequestMethod.POST)
	public String dbCartListPost(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(name="baesong", defaultValue="0", required=false) int baesong) {
		String mid = (String) session.getAttribute("sMid");
		
		// 주문한 상품에 대한 '고유번호'를 만들어준다.
		DbOrderVO maxIdx = dbShopService.getOrderMaxIdx();
		int idx = 1;
		if(maxIdx != null) idx = maxIdx.getMaxIdx() + 1;

		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String orderIdx = sdf.format(today) + idx;
		
		// 장바구니에서 구매를 위해 선택한 모든 항목들은 배열로 넘어온다.
		String[] idxChecked = request.getParameterValues("idxChecked");
		
		DbCartVO cartVo = new DbCartVO();
		List<DbOrderVO> orderVOS = new ArrayList<DbOrderVO>();
		 
		for(String strIdx : idxChecked) {
		  cartVo = dbShopService.getCartIdx(Integer.parseInt(strIdx));
		  DbOrderVO orderVo = new DbOrderVO();
		  orderVo.setProductIdx(cartVo.getProductIdx());
		  orderVo.setProductName(cartVo.getProductName());
		  orderVo.setMainPrice(cartVo.getMainPrice());
		  orderVo.setThumbImg(cartVo.getThumbImg());
		  orderVo.setOptionName(cartVo.getOptionName());
		  orderVo.setOptionPrice(cartVo.getOptionPrice());
		  orderVo.setOptionNum(cartVo.getOptionNum());
		  orderVo.setTotalPrice(cartVo.getTotalPrice());
		  orderVo.setCartIdx(cartVo.getIdx());
		  orderVo.setBaesong(baesong);
		
		  orderVo.setOrderIdx(orderIdx); 
		  orderVo.setMid(mid);
		
		  orderVOS.add(orderVo);
		}
		session.setAttribute("sOrderVOS", orderVOS);
 
		// 배송처리를 위한 현재 로그인한 아이디에 해당하는 고객의 정보를 member2에서 가져온다.
		MemberVO memberVO = memberService.getMemberIdSearch(mid);
		model.addAttribute("memberVO", memberVO);
		
		return "dbShop/dbOrder";
	}
	
	// 결제시스템(결제창 호출) - 결제 API이용
	@RequestMapping(value="/payment", method=RequestMethod.POST)
	public String paymentPost(DbOrderVO orderVo, DbPaymentVO paymentVO, DbBaesongVO baesongVO, HttpSession session, Model model) {
		model.addAttribute("paymentVO", paymentVO);
		
		session.setAttribute("sPaymentVO", paymentVO);
		session.setAttribute("sBaesongVO", baesongVO);
		
		return "dbShop/paymentOk";
	}
	
	// 결제완료후 주문내역을 '주문테이블'에 저장처리한다. - 주문/결제된 물품은 장바구니에서 제거시켜준다. 사용한 세션은 제거시킨다.
	@Transactional
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/paymentResult", method=RequestMethod.GET)
	public String paymentResultGet(HttpSession session, DbPaymentVO receivePaymentVO, Model model) {
		List<DbOrderVO> orderVOS = (List<DbOrderVO>) session.getAttribute("sOrderVOS");
		DbPaymentVO paymentVO = (DbPaymentVO) session.getAttribute("sPaymentVO");
		DbBaesongVO baesongVO = (DbBaesongVO) session.getAttribute("sBaesongVO");
		
		session.removeAttribute("sBaesongVO");
		
		for(DbOrderVO vo : orderVOS) {
			vo.setIdx(Integer.parseInt(vo.getOrderIdx().substring(8)));	
			vo.setOrderIdx(vo.getOrderIdx());
			vo.setMid(vo.getMid());							
			
			dbShopService.setDbOrder(vo);		// 주문/결제 처리된 내용을 주문테이블(dbOrder)에 저장시킨다.
			dbShopService.setDbCartDeleteAll(vo.getCartIdx());	// 주문이 완료되었기에 장바구니테이블에서 주문한 내역을 삭제한다.
			
		}
		// 주문된 정보중 누락된 정보를 배송테이블에 담기위한 처리작업
		baesongVO.setOIdx(orderVOS.get(0).getIdx());
		baesongVO.setOrderIdx(orderVOS.get(0).getOrderIdx());
		baesongVO.setAddress(paymentVO.getBuyer_addr());
		baesongVO.setTel(paymentVO.getBuyer_tel());
		
		
		int totalBaesongOrder = 0;
		for(int i=0; i<orderVOS.size(); i++) {
			totalBaesongOrder += orderVOS.get(i).getTotalPrice();
		}
		// 총 주문금액이 5만원 미만이면, 배송비를 3000원으로 추가시킨다.
		if(totalBaesongOrder < 50000) baesongVO.setOrderTotalPrice(totalBaesongOrder + 3000);
		else baesongVO.setOrderTotalPrice(totalBaesongOrder);
		
		dbShopService.setDbBaesong(baesongVO);	// 배송내역을 배송테이블(dbBaesong)에 저장한다.
		dbShopService.setMemberPointPlus((int)(baesongVO.getOrderTotalPrice() * 0.01), orderVOS.get(0).getMid());
		
		paymentVO.setImp_uid(receivePaymentVO.getImp_uid());
		paymentVO.setMerchant_uid(receivePaymentVO.getMerchant_uid());
		paymentVO.setPaid_amount(receivePaymentVO.getPaid_amount());
		paymentVO.setApply_num(receivePaymentVO.getApply_num());
		
		// 오늘 주문에 들어간 정보들을 확인해주기위해 다시 session에 담아서 넘겨주고 있다.
		session.setAttribute("sPaymentVO", paymentVO);
		session.setAttribute("orderTotalPrice", baesongVO.getOrderTotalPrice());
		
		return "redirect:/message/paymentResultOk";
	}
	
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value="/paymentResultOk", method=RequestMethod.GET)
	public String paymentResultOkGet(HttpSession session, Model model) {
		List<DbOrderVO> orderVOS = (List<DbOrderVO>) session.getAttribute("sOrderVOS");
		model.addAttribute("orderVOS", orderVOS);
		session.removeAttribute("sOrderVOS");
		
		int totalBaesongOrder = dbShopService.getTotalBaesongOrder(orderVOS.get(orderVOS.size()-1).getOrderIdx());
		model.addAttribute("totalBaesongOrder", totalBaesongOrder);
		
		return "dbShop/paymentResult";
	}
	
	@RequestMapping(value="/dbOrderBaesong", method=RequestMethod.GET)
	public String dbOrderBaesongGet(String orderIdx, Model model) {
		List<DbBaesongVO> vos = dbShopService.getOrderBaesong(orderIdx);
		
		DbBaesongVO vo = vos.get(0);
		String payMethod = "";
		if(vo.getPayment().substring(0,1).equals("C")) payMethod = "카드결제";
		else payMethod = "은행(무통장)결제";
		
		model.addAttribute("payMethod", payMethod);
		model.addAttribute("vo", vo);
		
		return "dbShop/dbOrderBaesong";
	}
	
	
}
