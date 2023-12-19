<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>boardSearchList.jsp</title>
 	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
 	<script>
 		'use strict';
 		
 	
 	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
	<div class="container">
		<table class="table table-borderless p-0 m-0">
			<tr>
				<td colspan="2" class="text-center">
					<h2 class="text-center">게시판 검색 결과</h2>
					(<b>${searchTitle}</b>(으)로 <b>${searchString}</b>(을)를 검색한 결과 <b>${searchCnt}</b>건이 검색되었습니다.)
				</td>
			</tr>
		  <tr>
	      <td class="text-right">
	        <a href="boardList.bo?pag=${pag}&pageSize=${pageSize}" class="btn btn-warning btn-sm">돌아가기</a>
	      </td>
    	</tr>
		</table>
		<table class="table table-hover text-center">
			<tr class="table-dark text-dark">
				<th>글번호</th>
				<th>제목</th>
				<th>글쓴이</th>
				<th>날짜</th>
				<th>조회수(좋아요)</th>
			</tr>
			<c:forEach var="vo" items="${vos}" varStatus="st">
				<tr>
					<td>${searchCnt}</td>
					<td class="text-left">
						<a href="boardContent.bo?idx=${vo.idx}&pag=${pag}&pageSize=${pageSize}&flag=search&search=${search}&searchString=${searchString}">${vo.title}</a>
						<c:if test="${vo.hour_diff <= 24}"><img src="${ctp}/images/new.gif" /></c:if>
					</td>
					<td>${vo.nickName}</td>
					<td> <!-- new.gif가 표시된 글은 날짜에 시간만 나오게, 그렇지 않은 자료는 날짜에 일자만 나오게 -->
						<c:if test="${vo.hour_diff > 24}" >${fn: substring(vo.wDate,0,10)}</c:if>
						<c:if test="${vo.hour_diff <= 24}" >
							${vo.date_diff == 0 ? fn:substring(vo.wDate,11,19) : fn:substring(vo.wDate,0,16)}
						</c:if>
					</td>
					<td>${vo.readNum}(${vo.good})</td>
				</tr>
				<tr><td colspan="5" class="m-0 p-0"></td></tr>
				<c:set var="searchCnt" value="${searchCnt - 1}"/>
			</c:forEach>
		</table>
	</div>
<!-- 블록페이지 시작(1블록의 크기를 3개(3Page)로 한다. 한페이지 기본은 5개 -->
<br/>
<div class="text-center">
  <ul class="pagination justify-content-center">
    <c:if test="${pag > 1}"><li class="page-item"><a class="page-link text-secondary" href="boardSearch.bo?pag=1&pageSize=${pageSize}">첫페이지</a></li></c:if>
  	<c:if test="${curBlock > 0}"><li class="page-item"><a class="page-link text-secondary" href="boardSearch.bo?pag=${(curBlock-1)*blockSize+1}&pageSize=${pageSize}">이전블록</a></li></c:if>
  	<c:forEach var="i" begin="${(curBlock*blockSize)+1}" end="${(curBlock*blockSize)+blockSize}" varStatus="st">
	    <c:if test="${i <= totPage && i == pag}"><li class="page-item active"><a class="page-link bg-secondary border-secondary" href="boardSearch.bo?pag=${i}&pageSize=${pageSize}">${i}</a></li></c:if>
	    <c:if test="${i <= totPage && i != pag}"><li class="page-item"><a class="page-link text-secondary" href="boardSearch.bo?pag=${i}&pageSize=${pageSize}">${i}</a></li></c:if>
  	</c:forEach>
  	<c:if test="${curBlock < lastBlock}"><li class="page-item"><a class="page-link text-secondary" href="boardSearch.bo?pag=${(curBlock+1)*blockSize+1}&pageSize=${pageSize}">다음블록</a></li></c:if>
  	<c:if test="${pag < totPage}"><li class="page-item"><a class="page-link text-secondary" href="boardSearch.bo?pag=${totPage}&pageSize=${pageSize}">마지막페이지</a></li></c:if>
  </ul>
</div>
<!-- 블록페이지 끝 -->
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>