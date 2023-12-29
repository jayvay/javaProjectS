<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>qrCode.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>QR Code 연습하기</h2>
  <hr/>
  <div class="row">
    <div class="col"><a href="qrCodeEx1" class="btn btn-success">개인정보등록</a></div>
    <div class="col"><a href="qrCodeEx2" class="btn btn-primary">소개사이트정보등록</a></div>
    <div class="col"><a href="qrCodeEx3" class="btn btn-secondary">티켓예매정보등록</a></div>
    <div class="col"><a href="qrCodeEx4" class="btn btn-info">예매티켓DB등록</a></div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>