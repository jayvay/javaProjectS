<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ajaxTest4_1.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
	  'use strict';
	  
	 	function fCheck1() {
			let mid = document.getElementById("mid").value;
			if(mid.trim() == "") {
				alert("검색할 아이디를 입력하세요");
				document.getElementById("mid").focus();
				return false;
			}
			
			$.ajax({
				url : "${ctp}/study/ajax/ajaxTest4_1", 
				type : "post",
				data : {mid : mid},
				success : function(vo) {
					//console.log(vo);
					let str = '<h5><b>vo로 전송된 자료 출력</b></h5>';
					if(vo != '') {
						str += '아이디 : ' + vo.mid + '<br/>';
						str += '이름 : ' + vo.name + '<br/>';
						str += '나이 : ' + vo.age + '<br/>';
						str += '주소 : ' + vo.address;
					}
					else str += '<b>없는 회원입니다</b>';
					$("#demo").html(str);
				},
				error : function() {
					alert("전송 오류");
				}
			});
		}
	 	
	 	function fCheck2() {
	 		let mid = document.getElementById("mid").value;
			if(mid.trim() == "") {
				alert("검색할 아이디를 입력하세요");
				document.getElementById("mid").focus();
				return false;
			}
			
			$.ajax({
				url : "${ctp}/study/ajax/ajaxTest4_2", 
				type : "post",
				data : {mid : mid},
				success : function(vos) {
					//console.log(vo);
					let str = '<h5><b>vos로 전송된 자료 출력</b></h5>';
					if(vos == '') {
						str += '<b>없는 회원입니다</b>';	
					}
					else {
						str += '<table class="table table-bordered text-center">';
						str += '<tr class="table-dark text-dark"><th>아이디</th><th>이름</th><th>나이</th><th>주소</th></tr>';
						for(let i=0; i<vos.length; i++){
							str += '<tr>';
							str += '<td>' + vos[i].mid + '</td>';
							str += '<td>' + vos[i].name + '</td>';
							str += '<td>' + vos[i].age + '</td>';
							str += '<td>' + vos[i].address + '</td>';
							str += '</tr>';
						}
						str += '<tr><td colspan="4" class="m-0 p-0"></td></tr>';
						str += '</table>';
					}
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
  <h2>AJax 회원 개별검색(vo) (ajaxTest4_1.jsp)</h2>
  <hr/>
  <form>
  	<div class="mb-3">
  		아이디 : <input type="text" name="mid" id="mid" autofocus class="form-control" />
  	</div>
  	<div>
			<input type="button" value="아이디전체일치(vo)" onclick="fCheck1()" class="btn btn-light ml-2 mr-2" />
			<input type="button" value="아이디부분일치(vos)" onclick="fCheck2()" class="btn btn-light ml-2 mr-2" />
			<input type="button" value="다시입력" onclick="location.reload()" class="btn btn-light ml-2 mr-2" />
			<input type="button" value="돌아가기" onclick="location.href='ajaxForm';" class="btn btn-light" />
		</div>
  </form>
  <div id="demo" class="mt-4"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>