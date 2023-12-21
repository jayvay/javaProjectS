package com.spring.javaProjectS.service;

import java.util.List;

import com.spring.javaProjectS.vo.BoardReplyVO;
import com.spring.javaProjectS.vo.BoardVO;

public interface BoardService {

	public int setBoardInput(BoardVO vo);

	public BoardVO getBoardContent(int idx);

	public List<BoardVO> getBoardList(int startIndexNo, int pageSize);

	public void imgCopy(String content);

	public void imgDelete(String content);

	public int setBoardDelete(int idx);

	public void imgBackup(String content);

	public int setBoardUpdate(BoardVO vo);

	public BoardVO getPreNextSearch(int idx, String str);

	public BoardReplyVO getBoardReplySearch(int boardIdx);

	public int setBoardReplyInput(BoardReplyVO replyVO);

	public List<BoardReplyVO> getBoardReplyList(int idx);

	public void setRe_orderUpdate(int boardIdx, int re_order);

	public void setReadNumPlus(int idx);

	public List<BoardVO> getBoardSearchList(int startIndexNo, int pageSize, String search, String searchString);


	
}
