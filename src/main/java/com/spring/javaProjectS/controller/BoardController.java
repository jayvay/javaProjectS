package com.spring.javaProjectS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaProjectS.pagination.PageProcess;
import com.spring.javaProjectS.pagination.PageVO;
import com.spring.javaProjectS.service.BoardService;
import com.spring.javaProjectS.vo.BoardReplyVO;
import com.spring.javaProjectS.vo.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	PageProcess pageProcess;
	
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String boardListGet(Model model,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
		
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "board", "", "");
		
		List<BoardVO> vos = boardService.getBoardList(pageVO.getStartIndexNo(), pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
		return "board/boardList";
	}
	
	//글쓰기 폼
	@RequestMapping(value = "/boardInput", method = RequestMethod.GET)
	public String boardInputGet() {
		return "board/boardInput";
	}
	
	//게시글 등록
	@RequestMapping(value = "/boardInput", method = RequestMethod.POST)
	public String boardInputPost(BoardVO vo) {
		//content에 이미지가 저장되어 있다면 저장된 이미지만(게시할 이미지만) 골라서 /resources/data/board/ 폴더에 저장한다
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCopy(vo.getContent());
		
		//이미지들의 모든 복사 작업을 마치면, ckeditor 폴더에서 board 폴더로 경로를 변경한다.('/data/ckeditor/' ==> '/data/board')
		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		
		//content 의 변경된 내용을 DB에 저장한다
		int res = boardService.setBoardInput(vo);
		
		if(res == 1) return "redirect:/message/boardInputOk";
		else return "redirect:/message/boardInputNo";
	}
	
	//게시글 보기
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContentGet(int idx, Model model,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
		
		//조회수 증가
		boardService.setReadNumPlus(idx);
		
		BoardVO vo = boardService.getBoardContent(idx);
		
		//이전글/다음글 가져오기
		BoardVO preVO = boardService.getPreNextSearch(idx, "preVO");
		BoardVO nextVO = boardService.getPreNextSearch(idx, "nextVO");
		model.addAttribute("preVO", preVO);
		model.addAttribute("nextVO", nextVO);
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		//댓글(대댓글) 추가
		List<BoardReplyVO> replyVOS = boardService.getBoardReplyList(idx);
		model.addAttribute("replyVOS", replyVOS);
		
		return "board/boardContent";
	}
	
	//게시글 삭제
	@RequestMapping(value = "/boardDelete", method = RequestMethod.GET)
	public String boardDeleteGet(int idx, 
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
			
		//게시글에 사진이 있다면 서버에 저장된 사진파일 먼저 삭제한다
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(vo.getContent());
		
		
		//DB에서 게시글을 삭제한다
		int res = boardService.setBoardDelete(idx);
		
		if(res == 1) return "redirect:/message/boardDeleteOk?idx="+idx+"&pag="+pag+"&pageSize="+pageSize;
		else return "redirect:/message/boardDeleteNo?idx="+idx+"&pag="+pag+"&pageSize="+pageSize;
	}
	
	//게시글 수정하기 폼
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.GET)
	public String boardUpdateGet(int idx, Model model,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
		
		//수정하기 창으로 이동하면서 게시글에 올렸던 현재폴더(board)의 그림파일을 ckeditor 폴더로 복사한다
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgBackup(vo.getContent());
		
		//DB에서 게시글 내용을 가져와서 수정하기 창에 보여준다
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		return "board/boardUpdate";
	}
	
	//게시글 수정하기
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.POST)
	public String boardUpdatePost(Model model, BoardVO vo,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
		
		//원본 게시글에 그림 파일이 있었다면!!(참고로 원본글와 수정글이 완전히 동일하면 수정 처리할 필요가 없음)
		//DB에 저장된 원본 자료를 불러온다
		BoardVO originalVO = boardService.getBoardContent(vo.getIdx());
		
		//originalVO의 content와 수정 자료의 content가 다르면 수정 처리한다
		if(!originalVO.getContent().equals(vo.getContent())) {
			//수정하기 버튼을 클릭하면, board 폴더에서 해당 게시글의 그림 파일을 모두 삭제한다 (원본의 사진 파일은 수정하기 폼에 들어오면서 ckeditor 폴더에 저장해둠)
			if(originalVO.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(originalVO.getContent());
			
			//그림 파일의 경로를 board 에서 ckeditor로 변경한다
			vo.setContent(vo.getContent().replace("/data/board/", "/data/ckeditor/")); 
			
			//처음으로 글을 게시하는 꼴이 됨
			//수정한 content에 이미지가 있다면 이미지를 ckeditor 폴더에서 board 폴더로 복사
			boardService.imgCopy(vo.getContent());
			
			//ckeditor 폴더에서 board 폴더로 경로를 변경한다('/data/ckeditor/' ==> '/data/board')
			vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		}
		//수정된 vo를 DB에 Update 한다
		int res = boardService.setBoardUpdate(vo);
		
		model.addAttribute("idx", vo.getIdx());
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		if(res == 1) return "redirect:/message/boardUpdateOk";
		else return "redirect:/message/boardUpdateNo";
	}
	
	//원본글에 단 댓글 입력(부모댓글)
	@ResponseBody
	@RequestMapping(value = "/boardReplyInput", method = RequestMethod.POST)
	public String boardReplyInputPost(BoardReplyVO replyVO) {
		//부모댓글의 re_step = 0, re_order = 1 이다. 단, 원본글의 첫 번째 부모댓글은 re_order=1이고, 2번째 이상은 마지막에 등록한 부모댓글의 re_order+1 이다
		BoardReplyVO pReplyVO = boardService.getBoardReplySearch(replyVO.getBoardIdx());
		
		if(pReplyVO == null) {
			replyVO.setRe_order(1);
		}
		else {
			replyVO.setRe_order(pReplyVO.getRe_order() + 1);
		}
		
		replyVO.setRe_step(0);
		
		int res = boardService.setBoardReplyInput(replyVO);
		
		return res + "";
	}
	
	//(부모)댓글에 단 댓글 입력(대댓글)
	@ResponseBody
	@RequestMapping(value = "/boardReReplyInput", method = RequestMethod.POST)
	public String boardReReplyInputPost(BoardReplyVO replyVO) {
		//대댓글의 re_step은 부모댓글re_step+1 
		replyVO.setRe_step(replyVO.getRe_step()+1);
		
		//re_order는 부모의re_order보다 큰 댓글은 모두 +1 
		boardService.setRe_orderUpdate(replyVO.getBoardIdx(), replyVO.getRe_order());
		
	  //자신의 re_order를 +1 한다
		replyVO.setRe_order(replyVO.getRe_order() + 1);
		
		int res = boardService.setBoardReplyInput(replyVO);
		
		return res + "";
	}
	
	//게시글 검색
	@RequestMapping(value = "/boardSearch", method = RequestMethod.GET)
	public String boardSearchGet(Model model, String search,
			@RequestParam(name = "searchString", defaultValue = "", required = false) String searchString,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
		
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "board", search, searchString);
		List<BoardVO> vos = boardService.getBoardSearchList(pageVO.getStartIndexNo(), pageSize, search, searchString);
		
		String searchTitle = "";
		if(pageVO.getSearch().equals("title")) searchTitle = "제목";
		else if(pageVO.getSearch().equals("name")) searchTitle = "글쓴이";
		else searchTitle = "내용";
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("searchTitle", searchTitle);
		model.addAttribute("searchCnt", vos.size());
		
		return "board/boardSearchList";
	}
	
	//좋아요 증가/취소
	@ResponseBody
	@RequestMapping(value = "/boardGood", method = RequestMethod.POST)
	public void boardGoodPost(int idx, String goodCnt) {
		boardService.setBoardGood(idx, goodCnt);
	}
}
