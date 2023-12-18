<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>mailForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>메일 보내기</h2>
  <p>(받는 사람의 메일 주소를 정확히 입력하셔야 합니다.)</p>
  <form name="myform" method="post">
  	<table class="table table-bordered">
  		<tr>
  			<th>받는 사람</th>
  			<td><input type="text" name="toMail" value="" placeholder="받는 사람의 이메일을 입력하세요" required autofocus class="form-control" /></td>
  		</tr>
  		<tr>
  			<th>메일 제목</th>
  			<td><input type="text" name="title" placeholder="메일 제목을 입력하세요" required class="form-control" /></td>
  		</tr>
  		<tr>
  			<th>메일 내용</th>
  			<td><textarea rows="7" name="content" placeholder="메일 내용을 입력하세요" required class="form-control" ></textarea></td>
  		</tr>
  		<tr>
  			<td colspan="2" class="text-center">
  				<input type="submit" value="메일전송" class="btn btn-warning mr-2" />
  				<input type="reset" value="다시쓰기" class="btn btn-warning mr-2" />
  				<input type="button" value="돌아가기" onclick="location.href='${ctp}/';" class="btn btn-warning mr-2" />
  			</td>
  		</tr>
  	</table>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>