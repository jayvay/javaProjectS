<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>uidForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	let str = '';
  	let cnt = 0;
  	
  	function uuidCheck() {
			$.ajax({
				url : "${ctp}/study/uuid/uidForm",
				type : "post",
				success : function(res) {
					cnt ++;
					str += cnt + " : " + res + "<br/>";
					$("#demo").html(str);
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
  <h2>UUID</h2>
  <div>
  	UUID(Universally Unique Identifier)는 네트워크상에서 고유성이 보장되는 id를 만들기 위한 규약으로,<br/> 
  	32자리의 16진수(128비트)로 표현된다. (표시 : 8-4-4-4-12 자리로 표현)  	
  </div>
  <br/>
  <div>
  	<input type="button" value="UUID생성" onclick="uuidCheck()" class="btn btn-warning"/>
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