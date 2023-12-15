package com.spring.javaProjectS.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javaProjectS.service.GuestService;
import com.spring.javaProjectS.vo.GuestVO;

@Controller
@RequestMapping("/guest")
public class GuestController {

	@Autowired
	GuestService guestService;
	
	@RequestMapping(value = "/guestList", method = RequestMethod.GET)
	public String guestListGet(Model model,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "3", required = false) int pageSize) {
		
		//3. 총 레코드 건 수를 구한다.(sql 명령어 중 count함수 사용)
		int totRecCnt = guestService.getTotRecCnt();
		
		//4. 총 페이지 건 수를 구한다.
		int totPage = (totRecCnt % pageSize) == 0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		
		//5. 현재 페이지에 출력할 '시작 인덱스 번호'를 구한다.
		int startIndexNo = (pag - 1) * pageSize;
		
		//6. 현재 화면에 표시될 시작 번호를 구한다.
		int curScrStartNo = totRecCnt - startIndexNo;
		
		
		//블록페이징 처리 (시작블록의 번호를 0번으로 처리했다)
		//1. 블록의 크기 결정 (여기선 3으로 해보자)
		int blockSize = 3;
		
		//2. 현재 페이지가 속한 블록 번호를 구한다. (예:총 레코드 개수가 38개일 때 1,2,3페이지는 0블록/ 4,5,6페이지는 1블록
		int curBlock = (pag - 1) / blockSize;
		
		//3. 마지막 블록을 구한다.
		int lastBlock = (totPage - 1) / blockSize;
		
		//지정된 페이지의 자료를 요청한 한 페이지의 분량만큼 가져온다.
		List<GuestVO> vos = guestService.guestList(startIndexNo, pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		return "guest/guestList";
	}
	
	@RequestMapping(value = "/guestInput", method = RequestMethod.GET)
	public String guestInputGet(Model model) {
		return "guest/guestInput";
	}
	
	@RequestMapping(value = "/guestInput", method = RequestMethod.POST)
	public String guestInputPost(GuestVO vo) {
		
		int res = guestService.guestInput(vo);
		
		if(res != 0) return "redirect:/message/guestInputOk";
		else return "redirect:/message/guestInputNo";
	}	
	
	@RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
	public String adminLoginGet() {
		return "guest/adminLogin";
	}
	
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public String adminLoginPost(HttpSession session, 
			@RequestParam(name="mid", defaultValue = "guest", required = false) String mid,
			@RequestParam(name="pwd", defaultValue = "", required = false) String pwd) {
		
		int res = guestService.adminLogin(mid, pwd);
		
		if(res != 0) {
			session.setAttribute("sAdmin", "adminOk");
			return "redirect:/message/adminLoginOk";
		}
		else return "redirect:/message/adminLoginNo";
	}
	
	@RequestMapping(value = "/adminLogout", method = RequestMethod.GET)
	public String adminLogoutGet(HttpSession session) {
		session.invalidate();
		return "redirect:/message/adminLogoutOk";
	}
	
	@RequestMapping(value = "/guestDelete", method = RequestMethod.GET)
	public String guestDeleteGet(int idx) {
		
		int res = guestService.guestDelete(idx);
		
		if(res != 0) return "redirect:/message/guestDeleteOk";
		return "redirect:/message/guestDeleteNo";
	}
}
