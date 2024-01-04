<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>error404.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<h2>현재 시스템 정비 중입니다.(404에러 발생시 보이는 화면)</h2>
  <div>사용에 불편을 드려 죄송합니다.</div>
  <div>빠른 시일 내에 복구하겠습니다!</div>
  <hr/>
  <div><img src="${ctp}/images/la.jpg" width="400px" /></div>
  <hr/>
  <div>
    <a href="${ctp}/errorPage/errorMain" class="btn btn-success">돌아가기</a>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>