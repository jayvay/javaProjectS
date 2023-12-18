<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>aria.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	let str = '';
  	let cnt = 0;
  	
  	function ariaCheck() {
			let pwd = $("#pwd").val();
			
			$.ajax({
				url : "${ctp}/study/password/aria",
				type : "post",
				data : {pwd : pwd},
				success : function(res) {
					cnt ++;
					str += cnt + " : " + res + "<br/>";
					$("#demo").html(str);
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
  <h2>ARIA</h2>
  <div>
  	ARIA : Academy(학계) Research Institute(연구소) Agency(정부기관)
  	ARIA 암호화 방식은 경량환경 및 하드웨어 구현을 위해 최적화된 알고리즘으로, Involutional SPN 구조를 갖는 범용블록 암호화 알고리즘이다.<br/>
  	ARIA가 사용하는 연산은 대부분 XOR과 같은 단순한 바이트 단위 연산으로, 블록 크기는 128비트(총 비트 수 : 256비트 = 64문자)이다.<br>
  	ARIA 암호화 방식은 복호화가 가능하다. 
  </div>
  <br/>
  <div>
  	<input type="text" name="pwd" id="pwd" autofocus />
  	<input type="button" value="ARIA암호화" onclick="ariaCheck()" class="btn btn-warning"/>
  	<input type="button" value="다시하기" onclick="location.reload()" class="btn btn-warning"/>
  </div>
  <hr/>
  <div>
  	<div>출력 결과 : </div>
  	<span id="demo"></span>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>