package com.spring.javaProjectS.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaProjectS.dao.PdsDAO;
import com.spring.javaProjectS.vo.PdsVO;

@Service
public class PdsServiceImpl implements PdsService {

	@Autowired
	PdsDAO pdsDAO;

	@Override
	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part) {
		return pdsDAO.getPdsList(startIndexNo, pageSize, part);
	}

	@Override
	public int setPdsInput(PdsVO vo, MultipartHttpServletRequest mfile) {
		int res = 0;
		try {
			List<MultipartFile> files = mfile.getFiles("file");			
			String oFileNames = "";
			String sFileNames = "";
			int fileSizes = 0; 
			
			for(MultipartFile f : files) {
				String oFileName = f.getOriginalFilename();
				String sFileName = saveFileName(oFileName);
				
				writeFile(f, sFileName);
				
				oFileNames += oFileName + "/";
				sFileNames += sFileName + "/";
				fileSizes += f.getSize();
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);
			
			vo.setFName(oFileNames);
			vo.setFSName(sFileNames);
			vo.setFSize(fileSizes);
			
			res = pdsDAO.setPdsInput(vo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	//서버 메모리(아직 서버가 아님)에 올라와 있는 파일의 정보를 실제 서버 파일 시스템에 저장하는 일반 메소드
	private void writeFile(MultipartFile file, String sFileName) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);
		byte[] bytes = file.getBytes();
		fos.write(bytes);
	}

	//파일 이름 중복 방지를 위해 서버에 저장될 파일명을 만드는 일반 메소드
	private String saveFileName(String oFileName) {
		String fileName = "";
		Calendar cal = Calendar.getInstance();	//Calendar 객체는 싱글톤
		fileName += cal.get(Calendar.YEAR);
		fileName += cal.get(Calendar.MONTH);
		fileName += cal.get(Calendar.DATE);
		fileName += cal.get(Calendar.HOUR);
		fileName += cal.get(Calendar.MINUTE);
		fileName += cal.get(Calendar.SECOND);
		fileName += cal.get(Calendar.MILLISECOND);
		fileName += "_" + oFileName;
		return fileName;
	}

	@Override
	public int setPdsDownNumPlus(int idx) {
		return pdsDAO.setPdsDownNumPlus(idx);
	}

	@Override
	public PdsVO getPdsSearch(int idx) {
		return pdsDAO.getPdsSearch(idx);
	}

	@Override
	public int setPdsDelete(PdsVO vo) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		String[] fSNames = vo.getFSName().split("/");
		
		//서버에 저장된 파일을 삭제
		for(int i=0; i<fSNames.length; i++) {
			new File(realPath + fSNames[i]).delete();
		}
		
		return pdsDAO.setPdsDelete(vo.getIdx());
	}

	@Override
	public PdsVO getIdxSearch(int idx) {	//12월27일 여기 하다 말았음!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// TODO Auto-generated method stub
		return null;
	}
}
