<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>boardInput.jsp</title>
 	<script src="${ctp}/ckeditor/ckeditor.js"></script>
 	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
 	<style>
 		th {
 			text-align : center;
 			background-color : white;
 		}
 	</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
	<div class="container">
		<h2 class="text-center">게시글 작성</h2>
		<form name="myform" method="post">	<!-- ckeditor가 알아서  -->
			<table class="table table-bordered">
				<tr>
					<th>글쓴이</th>
					<td>${sNickName}</td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type="text" name="title" id="title" placeholder="제목을 입력하세요" autofocus required class="form-control"/></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><input type="email" name="email" id="email" placeholder="이메일을 입력하세요" class="form-control"/></td>
				</tr>
				<tr>
					<th>홈페이지</th>
					<td><input type="text" name="homePage" id="homePage" value="https://" placeholder="홈페이지를 입력하세요" class="form-control"/></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea rows="6" name="content" id="CKEDITOR" class="form-control" required></textarea></td>
					<script>	<!-- -->
						CKEDITOR.replace("content",{
							height:480,
							filebrowserUploadUrl:"${ctp}/imageUpload"    /* 파일(이미지) 업로드 시 매핑 경로 */
						});
					</script>
				</tr>
				<tr>
					<th>공개여부</th>
					<td>
						<input type="radio" name="openSw" value="OK" checked />공개 &nbsp;
						<input type="radio" name="openSw" value="NO" />비공개 
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<input type="submit" value="작성완료" class="btn btn-success" /> &nbsp; 
						<input type="reset" value="다시입력" class="btn btn-warning" /> &nbsp; 
						<input type="button" value="돌아가기" onclick="location.href='boardList';" class="btn btn-info" /> &nbsp; 
					</td>
				</tr>
			</table>
			<input type="hidden" name="hostIp" value="${pageContext.request.remoteAddr}" />
			<input type="hidden" name="mid" value="${sMid}"/>
			<input type="hidden" name="nickName" value="${sNickName}"/>
		</form>
	</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>