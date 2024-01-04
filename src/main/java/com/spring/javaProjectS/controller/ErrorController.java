package com.spring.javaProjectS.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/errorPage")
public class ErrorController {

	//에러 연습 폼
	@RequestMapping(value = "/errorMain", method = RequestMethod.GET)
	public String errorMainGet() {
		return "errorPage/errorMain";
	}
	
	// JSP페이지에서 에러 발생시 이동 처리할 폼보여주기
	@RequestMapping(value = "/error1", method = RequestMethod.GET)
	public String error1Get() {
		return "errorPage/error1";
	}
	
	// JSP페이지에서 에러 발생시 공사중 페이지 보여주기 (jsp파일에서 스크립틀릿으로 처리하면 이곳에서 처리할 필요가 없다)
	@RequestMapping(value = "/errorMessage1", method = RequestMethod.GET)
	public String errorMessage1Get() {
		return "errorPage/errorMessage1";
	}
	
	// 404 에러 발생시 공사중 페이지 보여주기
	@RequestMapping(value = "/error404", method = RequestMethod.GET)
	public String error404Get() {
		return "errorPage/error404";
	}
	
	// 405 에러 발생시 공사중 페이지 보여주기
	@RequestMapping(value = "/error405", method = RequestMethod.GET)
	public String error405Get() {
		return "errorPage/error405";
	}
	
	// JSP페이지에서 에러 발생시 공사중 페이지 보여주기 (jsp파일에서 스크립틀릿으로 처리하면 이곳에서 처리할 필요가 없다)
	@RequestMapping(value = "/errorMessage1Get", method = RequestMethod.POST)
	public String errorMessage1GetPost() {
		return "errorPage/errorMessage1";
	}
	
	// 500 에러(서블릿에러) 발생시 공사중 페이지 보여주기
	@RequestMapping(value = "/error500", method = RequestMethod.GET)
	public String error500Get() {
		return "errorPage/error500";
	}
	
	// 500 에러(서블릿에러) 발생시 공사중 페이지 보여주기
	@RequestMapping(value = "/error500Check", method = RequestMethod.GET)
	public String error500CheckGet(HttpSession session) {
		String mid = (String) session.getAttribute("ssMid");
		int su = 100 + Integer.parseInt(mid);
		System.out.println("su : " + su);
		return "errorPage/errorMain";
	}
	
	// 500 에러(서블릿에러) 발생시 공사중 페이지 보여주기
	@RequestMapping(value = "/errorNumberFormat", method = RequestMethod.GET)
	public String errorNumberFormatGet() {
		return "errorPage/errorNumberFormat";
	}
	
	// 500 에러(서블릿에러) 발생시 공사중 페이지 보여주기
	@RequestMapping(value = "/errorNullPointerCheck", method = RequestMethod.GET)
	public String errorNullPointerCheckGet(String name) {
		return "errorPage/errorNullPointer";
	}
}
