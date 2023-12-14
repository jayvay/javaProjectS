<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>user2List.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	function userUpdate(idx, name, age, address) {
  		$("#myModal .modal-body #idx").val(idx);
  		$("#myModal .modal-body #name").val(name);
  		$("#myModal .modal-body #age").val(age);
  		$("#myModal .modal-body #address").val(address);
		}
  	
  	function userDelete(idx) {
			let ans = confirm("선택된 user를 삭제하시겠습니까?");
			if(!ans) return false;
			else location.href = "user2Delete?idx=" + idx;
		}
  	
  	function nameSearch() {
			let name = document.getElementById("name").value;
			location.href = "user2Search?name=" + name;
		}
  	
  	function fNewForm() {
			let str = '';
			str += '<form method="post">';
			str += '<div>';
			str += '<div>아이디: <input type="text" name="mid" required class="form-control mt-2 mb-2" /></div>';
			str += '<div>이름: <input type="text" name="name" required class="form-control mb-2" /></div>';
			str += '<div>나이: <input type="number" name="age" value="20" min="1" max="99" class="form-control mb-2" /></div>';
			str += '<div>주소: <input type="text" name="address" class="form-control mb-2" /></div>';
			str += '<div><input type="submit" value="추가" class="form-control btn btn-light mb-2" /></div>';
			str += '</div>';
			str += '</form>';
			document.getElementById("demo").innerHTML = str;
		}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>회원리스트2</h2>
  <div class="row mt-4">
  	<div class="col">
  		<input type="button" value="회원추가" onclick="fNewForm()" class="btn btn-primary btn-sm" />
  	</div>
  	<div class="col text-right">
	  	이름검색 : 
	  	<input type="text" name="name" value="${name}" id="name" />
	  	<input type="button" value="검색" onclick="nameSearch()" class="btn btn-primary btn-sm" />
  	</div>
  </div>
  <div id="demo"></div>
  <table class="table table-hover mt-2">
  	<tr class="table-dark text-dark">
  		<th>번호</th>
  		<th>아이디</th>
  		<th>성명</th>
  		<th>나이</th>
  		<th>주소</th>
  		<th>비고</th>
  	</tr>
  	<c:forEach var="vo" items="${vos}" varStatus="st">
  		<tr>
  			<td>${vo.idx}</td>
  			<td>${vo.mid}</td>
  			<td>${vo.name}</td>
  			<td>${vo.age}</td>
  			<td>${vo.address}</td>
  			<td>
  				<a href="#" onclick="userUpdate('${vo.idx}','${vo.name}','${vo.age}','${vo.address}')" class="btn btn-light btn-sm" data-toggle="modal" data-target="#myModal">수정</a>
  				<a href="javascript:userDelete(${vo.idx})" class="btn btn-light btn-sm">삭제</a>
  			</td>
  		</tr>
  	</c:forEach>
  	<tr><td colspan="6" class="m-0, p-0"></td></tr>
  </table>
  <div><a href="${ctp}/" class="btn btn-primary btn-sm">돌아가기</a></div>
</div>

<!-- The Modal -->
<div class="modal fade" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">
    
      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">회원정보수정</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      
      <!-- Modal body -->
      <div class="modal-body">
        <form method="post" action="${ctp}/user2/user2Update">
        	<div>이름 : <input type="text" name="name" id="name" class="form-control mt-2 mb-2" required /></div>
        	<div>나이 : <input type="number" name="age" id="age" min="1" max="99" class="form-control mt-2 mb-2" /></div>
        	<div>주소 : <input type="text" name="address" id="address" class="form-control mt-2 mb-2" /></div>
        	<div><input type="hidden" name="idx" id="idx" /></div>
        	<div><input type="submit" value="수정" class="form-control btn btn-info mt-2 mb-2" /></div>
        </form>
      </div>
      
      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>
      
    </div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>