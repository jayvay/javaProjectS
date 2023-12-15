<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ajaxTest3_2.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
	  'use strict';
	  
	  $(function() {
			$("#dodo").change(function() {	// $("#dodo").on(change, function(){}); 라고 해도 됨
				let dodo = $("#dodo").val();
				if(dodo == "") {
					alert("지역을 선택하세요");
					return false;
				}
				
				$.ajax({
					url : "${ctp}/study/ajax/ajaxTest3_2",
					type : "post",
					data : {dodo : dodo},
					success : function(res) {
						//alert("res : " + res + ", 크기 : " + res.length);
						console.log(res);
						let str = '<option>도시선택</option>';
						for(let i=0; i<res.length; i++){
							if(res[i] == null) break;
							str += '<option>' + res[i] + '</option>';
						}
						$("#city").html(str);
					},
					error : function() {
						alert("aJax 전송 오류");
					}
				});
			});
		});
	  
	  function fCheck() {
			let dodo = $("#dodo").val();
			let city = $("#city").val();
			
			if(dodo == "" || city == "") {
				alert("도시를 선택하세요");
				return false;
			}
			
			let str = "선택하신 도시는 <b>" + dodo + " " + city + "</b> 입니다. &nbsp;"
			str += '<input type="button" value="다시선택" onclick="location.reload()" class="btn btn-light btn-sm"/>';
			$("#demo").html(str);
		}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>AJax ArrayList 처리 (ajaxTest3_2.jsp)</h2>
  <hr/>
  <form>
  	<h4>도시를 선택하세요</h4>
  	<select name="dodo" id="dodo">
  		<option value="">지역선택</option>
  		<option value="서울">서울</option>
  		<option value="경기">경기</option>
  		<option value="충북">충북</option>
  		<option value="충남">충남</option>
  	</select>
  	<select name="city" id="city">
  		<option>도시선택</option>
  	</select>
		<input type="button" value="선택" onclick="fCheck()" class="btn btn-light ml-2 mr-2" />
		<input type="button" value="돌아가기" onclick="location.href='ajaxForm';" class="btn btn-light" />
  </form>
  <div id="demo" class="mt-4"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>