package com.spring.javaProjectS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaProjectS.vo.BoardReplyVO;
import com.spring.javaProjectS.vo.BoardVO;

public interface BoardDAO {

	public int setBoardInput(@Param("vo") BoardVO vo);

	public BoardVO getBoardContent(@Param("idx") int idx);

	public int totRecCnt();

	public List<BoardVO> getBoardList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	public int setBoardDelete(@Param("idx") int idx);

	public int setBoardUpdate(@Param("vo") BoardVO vo);

	public BoardVO getPreNextSearch(@Param("idx") int idx, @Param("str") String str);

	public BoardReplyVO getBoardReplySearch(@Param("boardIdx") int boardIdx);

	public int setBoardReplyInput(@Param("replyVO") BoardReplyVO replyVO);

	public List<BoardReplyVO> getBoardReplyList(@Param("idx") int idx);

	public void setRe_orderUpdate(@Param("boardIdx") int boardIdx, @Param("re_order") int re_order);

	public void setReadNumPlus(@Param("idx") int idx);

	public int totRecCntSearch(@Param("search") String search, @Param("searchString") String searchString);

	public List<BoardVO> getBoardSearchList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("search") String search, @Param("searchString") String searchString);

}
