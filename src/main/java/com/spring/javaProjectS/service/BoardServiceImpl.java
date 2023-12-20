package com.spring.javaProjectS.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.javaProjectS.dao.BoardDAO;
import com.spring.javaProjectS.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDAO;

	@Override
	public int setBoardInput(BoardVO vo) {
		return boardDAO.setBoardInput(vo);
	}

	@Override
	public BoardVO getBoardContent(int idx) {
		return boardDAO.getBoardContent(idx);
	}

	@Override
	public List<BoardVO> getBoardList(int startIndexNo, int pageSize) {
		return boardDAO.getBoardList(startIndexNo, pageSize);
	}

	//실제로 게시판에 업로드된 사진파일을 복사
	@Override
	public void imgCopy(String content) {
		//				 0         1         2         3         4         5
		// 				 012345678901234567890123456789012345678901234567890
		// <p><img src="/javaProjectS/data/ckeditor/231220120156_7.jpg" />
		// <p><img src="/javaProjectS/data/board/231220120156_7.jpg" />
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 33;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String oFilePath = realPath + "ckeditor/" + imgFile;
			String copyFilePath = realPath + "board/" + imgFile;
			
			fileCopyCheck(oFilePath, copyFilePath);	//data 밑에 있는 ckeditor폴더의 그림파일을 board 폴더로 복사
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);	//이미지가 여러 개일 경우 다음 이미지가 없을 때까지 반복문 실행
			
		}
	}
	
	//파일 복사하는 일반 메소드
	private void fileCopyCheck(String oFilePath, String copyFilePath) {
		try {
			FileInputStream fis = new FileInputStream(new File(oFilePath)); //서버에서 파일을 가져와서 
			FileOutputStream fos = new FileOutputStream(new File(copyFilePath));	//복사한 파일을 업로드
			
			byte[] bytes = new byte[2048];
			int cnt = 0;
			while((cnt = fis.read(bytes)) != -1) {
				fos.write(bytes, 0, cnt);
			}
			fos.flush();
			fos.close();	//나중에 연 것부터 닫는다
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//게시글 수정시 이미지 백업
	@Override
	public void imgBackup(String content) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 30;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String oFilePath = realPath + "board/" + imgFile;
			String copyFilePath = realPath + "ckeditor/" + imgFile;
			
			fileCopyCheck(oFilePath, copyFilePath);	//data 밑에 있는 board폴더의 그림파일을 ckeditor 폴더로 복사
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);	//이미지가 여러 개일 경우 다음 이미지가 없을 때까지 반복문 실행
		}
	}
	
	//서버에 저장된 이미지 삭제
	@Override
	public void imgDelete(String content) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 30;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String oFilePath = realPath + "board/" + imgFile;
			
			fileDeleteCheck(oFilePath);	//data 밑에 있는 board 폴더의 그림파일을 삭제
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);	//이미지가 여러 개일 경우 다음 이미지가 없을 때까지 반복문 실행
		}
	}

	//이미지 삭제하는 일반 메소드
	private void fileDeleteCheck(String oFilePath) {
		File delFile = new File(oFilePath);
		if(delFile.exists()) delFile.delete();
	}

	@Override
	public int setBoardDelete(int idx) {
		return boardDAO.setBoardDelete(idx);
	}

	@Override
	public int setBoardUpdate(BoardVO vo) {
		return boardDAO.setBoardUpdate(vo);
	}

	
	
}
