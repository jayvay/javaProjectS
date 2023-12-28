<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>captcha.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <style>
  	#captchaImage {
  		width : 250px;
  		height : 50px;
  		border : 3px dotted #A3C552;
  		text-align : center;
  		padding : 5px;
  	}
  </style>
  <script>
  	'use strict';
  	
  	$(document).ready(function() {
			//captcha 이미지를 새로 만들기
			$("#refreshBtn").click(function(e) {
				$.ajax({
					url : "${ctp}/study/captcha/captchaImage",
					type : "post",
					async : false, //비동기를 끔(captcha 이미지 느려서 동기식으로 바꿔줌)
					success : function() {
						/* $("#captchaImage").load(location.href + ' #captchaImage'); */
						/* $("#chapchaCode").load(location.href + ' #chapchaCode');  // 부분 리로드가 안돼... */
						location.reload();
					},
					error : function() {
						alert("전송 오류");
					}
				});
			});
			
			//captcha 문자 확인하기
			$("#confirmBtn").click(function(e) {
				e.preventDefault(); //새로고침(F5) 막기
				
				let strCaptcha = $("#strCaptcha").val();
				
				$.ajax({
					url : "${ctp}/study/captcha/captcha",
					type : "post",
					data : {strCaptcha : strCaptcha}
				}).done(function(res) {
						if(res != "1") alert("로봇으로 의심됩니다. 작업을 계속 진행할 수 없습니다");
						else alert("로봇이 아닙니다. 작업을 계속 진행할 수 있습니다");
				});
			});
			
		});
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>Captcha 연습</h2>
  <pre>
  	CAPTCHA 는 기계는 인식할 수 없으나, 사람은 쉽게 인식할 수 있는 텍스트나 이미지를 통해서 사람과 기계를 구별하는 프로그램이다.
  </pre>
  <hr/>
  <form name="myform">
  	<p id="chapchaCode">다음 코드를 입력해주세요 : <img src="${ctp}/data/study/captcha.png" id="captchaImage"/></p>
  	<p>
  		<input type="text" name="strCaptcha" id="strCaptcha" />
  		<input type="button" value="확인" id="confirmBtn" />
  		<input type="button" value="새로고침" id="refreshBtn" />
  	</p>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>