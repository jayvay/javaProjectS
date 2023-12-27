package com.spring.javaProjectS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaProjectS.common.SecurityUtil;
import com.spring.javaProjectS.pagination.PageProcess;
import com.spring.javaProjectS.pagination.PageVO;
import com.spring.javaProjectS.service.PdsService;
import com.spring.javaProjectS.vo.PdsVO;

@Controller
@RequestMapping("/pds")
public class PdsController {

	@Autowired
	PdsService pdsService;
	
	@Autowired
	PageProcess pageProcess;
	
	@RequestMapping(value = "/pdsList", method = RequestMethod.GET)
	public String pdsListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part) {

		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "pds", part, "");
		
		List<PdsVO> vos = pdsService.getPdsList(pageVO.getStartIndexNo(), pageSize, part);
		
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("vos", vos);
		
		return "pds/pdsList";
	}
	
	@RequestMapping(value = "/pdsInput", method = RequestMethod.GET)
	public String pdsInputGet(Model model, String part) {
		model.addAttribute("part", part);
		return "pds/pdsInput";
	}
	
	@RequestMapping(value = "/pdsInput", method = RequestMethod.POST)
	public String pdsInputPost(PdsVO vo, MultipartHttpServletRequest file) {
		
		SecurityUtil security = new SecurityUtil();
		
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,4);
		
		String pwd = salt + security.encryptSHA256(vo.getPwd());
		vo.setPwd(pwd);
		
		int res = pdsService.setPdsInput(vo, file);
		
		if(res == 1) return "redirect:/message/pdsInputOk";
		else return "redirect:/message/pdsInputNo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/pdsDownNumPlus", method = RequestMethod.POST)
	public String pdsDownNumPlusPost(int idx) {
		int res = pdsService.setPdsDownNumPlus(idx);
		return res + "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/pdsDelete", method = RequestMethod.POST)
	public String pdsDeletePost(int idx, String pwd) {
		PdsVO vo = pdsService.getPdsSearch(idx);
		//String tempPwd = vo.getPwd().substring(0,4); //salt
		
		int res = 0;
		SecurityUtil security = new SecurityUtil();
		if(security.encryptSHA256(pwd).equals(vo.getPwd().substring(4))) {
			res = pdsService.setPdsDelete(vo);
		}
		
		return res + "";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/pdsTotalDown", method = RequestMethod.GET)
	public String pdsTotalDownGet(HttpServletRequest request, int idx) throws IOException {
		//다운로드 횟수 증가
		pdsService.setPdsDownNumPlus(idx);
		
		//여러 개의 파일을 하나의 zip파일로 압축(통합)하여 다운로드 처리(압축파일의 이름은 '제목.zip')
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds");
		
		PdsVO vo = pdsService.getIdxSearch(idx);
		
		String[] fNames = vo.getFName().split("/");
		String[] fSNames = vo.getFSName().split("/");
		
		String zipPath = realPath + "temp/";
		String zipName = vo.getTitle() + ".zip";
				
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		ZipOutputStream zOut = new ZipOutputStream(new FileOutputStream(zipPath + zipName));
		
		byte[] bytes = new byte[2048];
		
		for(int i=0; i<fNames.length; i++) {
			fis = new FileInputStream(realPath + fSNames[i]);
			fos = new FileOutputStream(realPath + fNames[i]);
			File moveAndRename = new File(zipPath + fNames[i]);
			
			//fis 를 fos 에 쓰기 작업(파일생성)
			int data;
			while((data = fis.read(bytes, 0, bytes.length)) != -1) {
				fos.write(bytes, 0, data);
			}
			fos.flush();
			fos.close();
			fis.close();
			
			//fos 로 생성된 파일을 zip파일에 쓰기 작업
			fis = new FileInputStream(moveAndRename);
			zOut.putNextEntry(new ZipEntry(fNames[i]));
			while((data = fis.read(bytes, 0, bytes.length)) != -1) {
				zOut.write(bytes, 0, data);
			}
			zOut.flush();
			zOut.closeEntry();
			fis.close();
		}
		zOut.close();
		return "redirect:/pds/pdsDownAction?file=" + java.net.URLEncoder.encode(zipName);
	}
	
	
}
