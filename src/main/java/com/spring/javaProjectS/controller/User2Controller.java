package com.spring.javaProjectS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaProjectS.service.UserService;
import com.spring.javaProjectS.vo.UserVO;

import lombok.val;

@Controller
@RequestMapping("/user2")
public class User2Controller {
	
	@Autowired
	UserService userService;
	
	//user 전체 리스트
	@RequestMapping(value = "/user2List", method = RequestMethod.GET)
	public String user2ListGet(Model model) {
		
		List<UserVO> vos = userService.getUser2List();
		
		model.addAttribute("vos", vos);
		
		return "study/user/user2List";
	}
	
	//user 추가
	@RequestMapping(value = "/user2List", method = RequestMethod.POST)
	public String user2ListPost(@Validated UserVO vo, BindingResult bindingResult, Model model) {

		//validator를 사용한 백엔드 유효성 검사
		System.out.println("vo : " + vo);
		System.out.println("error : " + bindingResult.hasErrors()); //true면 에러가 있는 거고, false면 에러가 없는 것
		
		if(bindingResult.hasFieldErrors()) {
			List<ObjectError> list = bindingResult.getAllErrors();

			String temp = "";
			for(ObjectError e : list) {
				System.out.println("메세지 : " + e.getDefaultMessage());
				temp = e.getDefaultMessage().substring(0, e.getDefaultMessage().indexOf("/")+1);
				if(temp.equals("midEmpty") || temp.equals("midSizeNo") || temp.equals("nameEmpty") || temp.equals("nameSizeNo") || temp.equals("ageRangeNo")) break;
				else temp = "나이가 입력되지 않았습니다";
			}
			System.out.println("temp : " + temp);
			
			model.addAttribute("temp", temp);
			
			return "redirect:/message/validatorError";
		}
		
		//유효성 검사 후 DB에 저장
		int res = userService.setUser2Input(vo); 
		
		if(res != 0) return "redirect:/message/user2InputOk";
		else return "redirect:/message/user2InputNo";
	}
	
	//user 이름으로 개별 검색
	@RequestMapping(value = "/user2Search", method = RequestMethod.GET)
	public String user2SearchGet(Model model, String name) {
		
		List<UserVO> vos = userService.getUser2Search(name);
		
		model.addAttribute("vos", vos);
		model.addAttribute("name", name);
		
		return "study/user/user2List";
	}
	
	//user idx로 삭제
	@RequestMapping(value = "/user2Delete", method = RequestMethod.GET)
	public String user2DeleteGet(Model model, int idx) {
		
		int res = userService.setUser2Delete(idx);
		
		if(res != 0) return "redirect:/message/user2DeleteOk";
		else return "redirect:/message/user2DeleteNo";
	}
	
	//user idx로 수정
	@RequestMapping(value = "/user2Update", method = RequestMethod.POST)
	public String user2UpdatePost(UserVO vo) {
		
		int res = userService.setUser2Update(vo);
		
		if(res != 0) return "redirect:/message/user2UpdateOk";
		else return "redirect:/message/user2UpdateNo";
	}
}
