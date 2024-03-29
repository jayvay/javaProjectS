package com.spring.javaProjectS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

	@RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
	public String msgGet(Model model, @PathVariable String msgFlag, String mid,
			@RequestParam(name = "temp", defaultValue = "", required = false) String temp,
			@RequestParam(name = "idx", defaultValue = "0", required = false) int idx,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {

		//Study
		if(msgFlag.equals("userDeleteOk")) {
			model.addAttribute("msg", "유저가 삭제되었습니다.");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("userDeleteNo")) {
			model.addAttribute("msg", "유저 삭제 실패");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("user2DeleteOk")) {
			model.addAttribute("msg", "유저2가 삭제되었습니다.");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("user2DeleteNo")) {
			model.addAttribute("msg", "유저2 삭제 실패");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("validatorError")) {	//validator를 사용한 백엔드 유효성 검사
			model.addAttribute("msg", "user 등록 실패 - " + temp);
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("user2InputOk")) {
			model.addAttribute("msg", "유저2가 추가되었습니다.");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("user2InputNo")) {
			model.addAttribute("msg", "유저2 추가 실패");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("user2UpdateOk")) {
			model.addAttribute("msg", "회원정보가 수정되었습니다.");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("user2UpdateNo")) {
			model.addAttribute("msg", "회원정보 추가 실패");
			model.addAttribute("url", "user2/user2List");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("msg", "메일이 성공적으로 전송되었습니다.");
			model.addAttribute("url", "study/mail/mailForm");
		}
		else if(msgFlag.equals("fileUploadOk")) {
			model.addAttribute("msg", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("fileUploadNo")) {
			model.addAttribute("msg", "파일 업로드가 실패하였습니다.");
			model.addAttribute("url", "study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("thumbnailOk")) {
			model.addAttribute("msg", "썸네일 이미지가 생성되었습니다.");
			model.addAttribute("url", "study/thumbnail/thumbnailForm");
		}
		else if(msgFlag.equals("thumbnailNo")) {
			model.addAttribute("msg", "썸네일 이미지 생성이 실패하였습니다.");
			model.addAttribute("url", "study/thumbnail/thumbnailForm");
		}
		else if(msgFlag.equals("validateNo")) {
			model.addAttribute("msg", "백엔드 체크(validate) 오류");
			model.addAttribute("url", "/");
		}
		
		
		//Guest
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("msg", "글이 등록되었습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("msg", "글 등록 실패");
			model.addAttribute("url", "guest/guestInput");
		}
		else if(msgFlag.equals("adminLoginOk")) {
			model.addAttribute("msg", "관리자 인증이 되었습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("adminLoginNo")) {
			model.addAttribute("msg", "관리자 인증 실패");
			model.addAttribute("url", "guest/adminLogin");
		}
		else if(msgFlag.equals("guestDeleteOk")) {
			model.addAttribute("msg", "글이 삭제 되었습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestDeleteNo")) {
			model.addAttribute("msg", "글 삭제 실패");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("adminLogoutOk")) {
			model.addAttribute("msg", "관리자 로그아웃 되었습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		
		//Member
		else if(msgFlag.equals("loginOk")) {
			model.addAttribute("msg", mid + " 님, 다시 만나 반갑습니다.");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("loginNo")) {
			model.addAttribute("msg", "로그인이 실패하였습니다.");
			model.addAttribute("url", "member/login");
		}
		else if(msgFlag.equals("logoutOk")) {
			model.addAttribute("msg", mid + " 님, 또 만나요!");
			model.addAttribute("url", "member/login");
		}
		else if(msgFlag.equals("joinOk")) {
			model.addAttribute("msg", "회원가입 완료, 환영합니다!");
			model.addAttribute("url", "member/login");
		}
		else if(msgFlag.equals("joinNo")) {
			model.addAttribute("msg", "회원가입이 실패하였습니다.");
			model.addAttribute("url", "member/join");
		}
		else if(msgFlag.equals("idCheckNo")) {
			model.addAttribute("msg", "이미 사용 중인 아이디입니다.");
			model.addAttribute("url", "member/join");
		}
		else if(msgFlag.equals("nickCheckNo")) {
			model.addAttribute("msg", "이미 사용 중인 닉네임입니다.");
			model.addAttribute("url", "member/join");
		}
		else if(msgFlag.equals("memberUpdateOk")) {
			model.addAttribute("msg", "회원정보가 수정되었습니다.");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("idCheckNo")) {
			model.addAttribute("msg", "회원정보수정이 실패했습니다.");
			model.addAttribute("url", "member/memberUpdate");
		}
		
		//Board
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("msg", "게시판에 글이 등록되었습니다.");
			model.addAttribute("url", "board/boardList");
		}
		else if(msgFlag.equals("boardInputNo")) {
			model.addAttribute("msg", "게시판에 글 등록이 실패하였습니다.");
			model.addAttribute("url", "board/boardInput");
		}
		else if(msgFlag.equals("boardUpdateOk")) {
			model.addAttribute("msg", "글이 수정되었습니다.");
			model.addAttribute("url", "board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardUpdateNo")) {
			model.addAttribute("msg", "게시글 수정이 실패하였습니다.");
			model.addAttribute("url", "board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardDeleteOk")) {
			model.addAttribute("msg", "글이 삭제되었습니다.");
			model.addAttribute("url", "board/boardList?pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardDeleteNo")) {
			model.addAttribute("msg", "게시글 삭제가 실패하였습니다.");
			model.addAttribute("url", "board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		
		//pds
		else if(msgFlag.equals("pdsInputOk")) {
			model.addAttribute("msg", "자료가 등록되었습니다.");
			model.addAttribute("url", "pds/pdsList");
		}
		else if(msgFlag.equals("pdsInputNo")) {
			model.addAttribute("msg", "자료 등록이 실패하였습니다.");
			model.addAttribute("url", "pds/pdsInput");
		}
		
		
		//Interceptor
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("msg", "관리자 외 출입금지입니다.");
			model.addAttribute("url", "/");
		}
		else if(msgFlag.equals("memberLevelNo")) {
			model.addAttribute("msg", "해당 등급은 접근할 수 없습니다.");
			model.addAttribute("url", "/");
		}
		else if(msgFlag.equals("memberNo")) {
			model.addAttribute("msg", "로그인 후 사용하세요.");
			model.addAttribute("url", "member/login");
		}
		
		//admin
		else if(msgFlag.equals("dbProductInputOk")) {
			model.addAttribute("msg", "상품이 등록되었습니다.");
			model.addAttribute("url", "dbShop/dbShopList");
		}
		else if(msgFlag.equals("dbProductInputNo")) {
			model.addAttribute("msg", "상품 등록을 실패하였습니다.");
			model.addAttribute("url", "dbShop/dbProduct");
		}
		else if(msgFlag.equals("dbOptionInputOk")) {
			model.addAttribute("msg", "옵션이 등록되었습니다.");
			model.addAttribute("url", "dbShop/dbOption");
		}
		else if(msgFlag.equals("dbOptionInputNo")) {
			model.addAttribute("msg", "옵션 등록을 실패하였습니다.");
			model.addAttribute("url", "dbShop/dbOption");
		}
		
		//shop
		else if(msgFlag.equals("cartOrderOk")) {
			model.addAttribute("msg", "장바구니에 상품이 추가되었습니다.\\n주문창으로 이동합니다.");
			model.addAttribute("url", "/dbShop/dbCartList");
		}
		else if(msgFlag.equals("cartOrderNo")) {
			model.addAttribute("msg", "장바구니 추가를 실패하였습니다.");
			model.addAttribute("url", "/dbShop/dbCartList");
		}
		else if(msgFlag.equals("cartInputOk")) {
			model.addAttribute("msg", "장바구니에 상품이 추가되었습니다.");
			model.addAttribute("url", "/dbShop/dbProductList");
		}
		else if(msgFlag.equals("cartEmpty")) {
			model.addAttribute("msg", "장바구니가 비어있습니다.");
			model.addAttribute("url", "/dbShop/dbProductList");
		}
		
		return "include/message";
	}
	

}
