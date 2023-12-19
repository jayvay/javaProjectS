package com.spring.javaProjectS.service;

import java.util.List;

import com.spring.javaProjectS.vo.BoardVO;

public interface BoardService {

	public List<BoardVO> getBoardList();

	public int setBoardInput(BoardVO vo);

	public BoardVO getBoardContent(int idx);

	
}
