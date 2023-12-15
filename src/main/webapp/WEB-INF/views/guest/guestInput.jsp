<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>guestInput.jsp</title>
 	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
	<div class="container">
		<h2>방명록 작성</h2>
		<hr/>
		<form name="myform" method="post">
		<table class="table table-bordered text-center">
			<tr>
				<th>작성자</th>
				<td><input type="text" name="name" id="name" class="form-control" autofocus required /></td>
			</tr>
			<tr>
				<th>메일주소</th>
				<td><input type="text" name="email" class="form-control" required /></td>
				<th>홈페이지</th>
				<td><input type="text" name="homePage" value="https://" class="form-control" required /></td>
			</tr>
			<tr>
				<td colspan="4"><textarea name="content" rows="6" class="form-control" required ></textarea></td>
			</tr>
		</table>
		<hr/>
		<div class="row">
			<div class="col text-left"><a href="${ctp}/GuestList"><input type="button" value="취소" class="btn btn-outline-danger"/></a></div>
			<div class="col text-right">
				<input type="reset" value="다시입력" class="btn btn-outline-info"/>
				<input type="submit" value="등록" class="btn btn-info"/>
			</div>
		</div>
			<input type="hidden" name="hostIp" value="<%=request.getRemoteAddr()%>"/>
		</form>
	</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>