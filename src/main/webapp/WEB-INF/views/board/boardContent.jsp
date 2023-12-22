<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>boardContent.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <style>
    th {
      text-align: center;
      background-color: #eee;
    }
  </style>
  <script>
    'use strict';
    
    // 조회수, 좋아요 하다 말았음!!!! -- 1222
    function goodCheck() {
    	$.ajax({
    		url  : "${ctp}/board/boardGood",
    		type : "post",
    		data : {idx : ${vo.idx}},
    		success:function(res) {
    			if(res == "0") alert('이미 좋아요 버튼을 클릭하셨습니다.');
    			else location.reload();
    		},
    		error : function() {
    			alert("전송 오류!!");
    		}
    	});
    }
    
    // 좋아요 증가(중복허용)
    function goodCheckPlus() {
    	$.ajax({
    		url  : "${ctp}/board/boardGood",
    		type : "post",
    		data : {
    			idx : ${vo.idx},
    			goodCnt : +1
    		},
    		success:function() {
    			location.reload();
    		},
    		error : function() {
    			alert("전송 오류!!");
    		}
    	});
    }
    
    // 좋아요 감소(중복허용)
    function goodCheckMinus() {
    	$.ajax({
    		url  : "${ctp}/board/boardGood",
    		type : "post",
    		data : {
    			idx : ${vo.idx},
    			goodCnt : -1
    		},
    		success:function() {
    			location.reload();
    		},
    		error : function() {
    			alert("전송 오류!!");
    		}
    	});
    }
    
    // 게시글 삭제
    function boardDelete() {
    	let ans = confirm("현재 게시글을 삭제 하시겠습니까?");
    	if(ans) location.href = "boardDelete?idx=${vo.idx}&pag=${pag}&pageSize=${pageSize}";
    }
    
    // 원본글에 댓글 달기
    function replyCheck() {
			let content = $("#content").val();
			if(content.trim() == "") {
				alert("댓글을 입력하세요");
				$("#content").focus();
				return false;
			}
    	
			let query = {
					boardIdx : ${vo.idx},
					mid : '${sMid}',
					nickName : '${sNickName}',
					hostIp : '${pageContext.request.remoteAddr}',
					content : content
			}
			
    	$.ajax({
				url : "${ctp}/board/boardReplyInput",
				type : "post",
				data : query,
				success : function(res) {
					if(res == "1") {
						alert("댓글이 입력되었습니다.");
						location.reload();
					}
					else {
						alert("댓글 입력 실패~");
					}
				},
				error : function() {
					alert("전송 오류");
				}
				
			});
		}
    
    //댓글 삭제
    function replyDelete(idx) {
			let ans = confirm("선택한 댓글을 삭제하시겠습니까?");
			if(!ans) return false;
			
			$.ajax ({
				url : "${ctp}/board/boardReplyDelete",
				type : "post",
				data : {idx : idx},
				success : function(res) {
					if(res == "1") {
						alert("댓글이 삭제되었습니다.");
						location.reload();
					}
					else alert("댓글 삭제 실패");
				},
				error : function() {
					alert("전송 실패")
				}
			});
		}
    
    // 신고하기 창
    function compCheck() {
    	let url = "complaintInput.ad?idx=${vo.idx}";
    	window.open(url,"nWin","width=580px,height=500px");
    }
    
    $(function() {
    	$(".replyCloseBtn").hide();
    });
    //대댓글 등록 폼 보여주기
    function replyShow(idx) {
    	$("#replyShowBtn"+idx).hide();
    	$("#replyCloseBtn"+idx).show();
			$("#replyDemo"+idx).slideDown(200);
		}
    //대댓글 등록 폼 감추기
    function replyClose(idx) {
    	$("#replyShowBtn"+idx).show();
    	$("#replyCloseBtn"+idx).hide();
			$("#replyDemo"+idx).slideUp(200);
		}
   
		//대댓글 입력
		function reReplyCheck(idx, re_step, re_order) {
			let content = $("#contentRe"+idx).val();
			if(content.trim() == ""){
				alert("답글을 입력하세요");
				$("#contentRe"+idx).focus();
				return false;
			}
			
			let query = {
					boardIdx : ${vo.idx},
					re_step : re_step,
					re_order : re_order,
					mid : '${sMid}',
					nickName : '${sNickName}',
					hostIp : '${pageContext.request.remoteAddr}',
					content : content
			}
			
			$.ajax({
				url : "${ctp}/board/boardReReplyInput",
				type : "post",
				data : query,
				success : function(res) {
					if(res == "1") {
						alert("답글이 입력되었습니다.");
						location.reload();
					}
					else {
						alert("답글 입력 실패~");
					}
				},
				error : function() {
					alert("전송 오류");
				}
			});
		}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <table class="table table-borderless m-0 p-0">
    <tr>
      <td><h2 class="text-center">글 내 용 보 기</h2></td>
    </tr>
  </table>
  <table class="table table-bordered">
    <tr>
      <th>글쓴이</th>
      <td>${vo.nickName}</td>
      <th>글쓴날짜</th>
      <td>${fn:substring(vo.WDate, 0, 16)}</td>
    </tr>
    <tr>
      <th>글제목</th>
      <td colspan="3">${vo.title}</td>
    </tr>
    <tr>
      <th>전자메일</th>
      <td>
        <c:if test="${empty vo.email}">없음</c:if>
        <c:if test="${!empty vo.email}">${vo.email}</c:if>
      </td>
      <th>조회수</th>
      <td>${vo.readNum}</td>
    </tr>
    <tr>
      <th>홈페이지</th>
      <td>
        <c:if test="${empty vo.homePage || (fn:indexOf(vo.homePage,'http://') == -1 && fn:indexOf(vo.homePage,'https://') == -1) || fn:length(vo.homePage) <= 10}">없음</c:if>
        <c:if test="${!empty vo.homePage && (fn:indexOf(vo.homePage,'http://') != -1 || fn:indexOf(vo.homePage,'https://') != -1) && fn:length(vo.homePage) > 10}"><a href="${vo.homePage}" target="_blank">${vo.homePage}</a></c:if>
      </td>
      <th>좋아요</th>
      <td><font color="red"><a href="javascript:goodCheck()">❤</a></font>(${vo.good}) / <a href="javascript:goodCheckPlus()">👍</a><a href="javascript:goodCheckMinus()">👎</a></td>
    </tr>
    <tr>
      <th>글내용</th>
      <td colspan="3" style="height:220px">${fn:replace(vo.content, newLine, "<br/>")}</td>
    </tr>
  </table>
  <table class="table table-borderless m-0 p-0">
    <tr>
      <td class="text-left">
        <c:if test="${param.flag != 'search'}"><input type="button" value="돌아가기" onclick="location.href='boardList?pag=${pag}&pageSize=${pageSize}';" class="btn btn-warning"/> &nbsp;</c:if>
        <c:if test="${param.flag == 'search'}"><input type="button" value="돌아가기" onclick="location.href='boardSearch?pag=${pag}&pageSize=${pageSize}&search=${param.search}&searchString=${param.searchString}';" class="btn btn-warning"/> &nbsp;</c:if>
        <c:if test="${sMid == vo.mid || sLevel == 0}">
        	<input type="button" value="수정하기" onclick="location.href='boardUpdate?idx=${vo.idx}&pag=${pag}&pageSize=${pageSize}';" class="btn btn-info"/> &nbsp;
        	<input type="button" value="삭제하기" onclick="boardDelete()" class="btn btn-danger"/>
        </c:if>
      </td>
      <td class="text-right"><a href="javascript:compCheck()" class="btn btn-danger">신고하기</a></td>
    </tr>
  </table>
  <hr/>
  
  <!-- 이전글/다음글 처리 -->
  <table class="table table-borderless">
  	<tr>
  		<td>
  			<c:if test="${!empty nextVO.title}">
	  			<a href="boardContent?idx=${nextVO.idx}&pag=${pag}&pageSize=${pageSize}">다음글 : ${nextVO.title}</a><br/>
  			</c:if>
  			<c:if test="${!empty preVO.title}">
  				<a href="boardContent?idx=${preVO.idx}&pag=${pag}&pageSize=${pageSize}">이전글 : ${preVO.title}</a><br/>
  			</c:if>
  		</td>
  	</tr>
  </table> 
</div>
<br/>  
<!-- 댓글 처리 -->
<div class="container"><b>댓글</b>
	<!-- 댓글 리스트 보여주기 -->
	<table class="table table-hover">
		<tr>
			<th>작성자</th>
			<th class="text-left">내용</th>
			<th>날짜</th>
			<th>접속IP</th>
			<th>답글</th>
		</tr>
		<c:forEach var="replyVO" items="${replyVOS}" varStatus="st">
			<tr>
				<td>
					<c:if test="${replyVO.re_step >= 1}">
						<c:forEach var="i" begin="1" end="${replyVO.re_step}">&nbsp;&nbsp;&nbsp;&nbsp;</c:forEach>└
					</c:if>
					${replyVO.nickName}
					<c:if test="${replyVO.mid == sMid || sLevel == 0}">
						<a href="javascript:replyDelete(${replyVO.idx})" title="댓글삭제">❌</a>
					</c:if>
				</td>
				<td class="text-left">${fn: replace(replyVO.content, newLine, "<br/>")}</td>
				<td class="text-center">${fn: substring(replyVO.WDate, 0,10)}</td>
				<td class="text-center">${replyVO.hostIp}</td>
				<td>
					<a href="javascript:replyShow(${replyVO.idx})" id="replyShowBtn${replyVO.idx}" class="text-center badge badge-light">답글</a>
					<a href="javascript:replyClose(${replyVO.idx})" id="replyCloseBtn${replyVO.idx}" class="replyCloseBtn text-center badge badge-secondary">접기</a>
				</td>
			</tr>
			<tr>
				<td colspan="5" class="m-0 p-0">
					<div id="replyDemo${replyVO.idx}" style="display:none">
						<table class="table table-center">
							<tr>
								<td style="width:85%" class="text-left">
									<textarea rows="3" name="contentRe" id="contentRe${replyVO.idx}" class="form-control">@${replyVO.nickName}</textarea>
								</td>
								<td style="width:15%" class="text-right">
									<br/>
									<p>작성자 : ${sNickName}</p>
									<p><input type="button" value="등록" onclick="reReplyCheck(${replyVO.idx},${replyVO.re_step},${replyVO.re_order})" class="btn btn-warning btn-sm"/></p>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr><td colspan="5" class="m-0 p-0"></td></tr>
		</c:forEach>
	</table>
	<!-- 댓글 입력창 -->
	<form name="replyForm">
		<table class="table table-center">
			<tr>
				<td style="width:85%" class="text-left">
					글 내용 : 
					<textarea rows="4" name="content" id="content" class="form-control"></textarea>
				</td>
				<td style="width:15%">
					<br/>
					<p style="font-size:13px">작성자 : ${sNickName}</p>
					<p><input type="button" value="댓글달기" onclick="replyCheck()" class="btn btn-info btn-sm" /></p>
				</td>
			</tr>
		</table>
	</form>  
</div>	  
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>