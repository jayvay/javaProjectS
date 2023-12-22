package com.spring.javaProjectS.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaProjectS.dao.BoardDAO;
import com.spring.javaProjectS.dao.PdsDAO;

@Service
public class PageProcess {
	
	@Autowired
	BoardDAO boardDAO;
	
	@Autowired
	PdsDAO pdsDAO;
	
	public PageVO totRecCnt(int pag, int pageSize, String section, String part, String searchString) {
		PageVO vo = new PageVO();
		
		int totRecCnt = 0;
		String search = "";
		
		if(section.equals("board")) {
			if(part.equals("")) totRecCnt = boardDAO.totRecCnt();
			else {
			 	search = part;
			 	totRecCnt = boardDAO.totRecCntSearch(search, searchString);
			}
		}
		else if(section.equals("pds")) totRecCnt = pdsDAO.totRecCnt(part);
		
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
		
		vo.setPag(pag);
		vo.setPageSize(pageSize);
		vo.setTotRecCnt(totRecCnt);
		vo.setTotPage(totPage);
		vo.setStartIndexNo(startIndexNo);
		vo.setCurScrStartNo(curScrStartNo);
		vo.setBlockSize(blockSize);
		vo.setCurBlock(curBlock);
		vo.setLastBlock(lastBlock);
		vo.setPart(part);
		vo.setSearch(search);
		vo.setSearchString(searchString);
		
		return vo;
	}
	
}
