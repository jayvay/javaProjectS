<%@ page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>qrCodeEx4.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	function qrCodeCreate() {
			let mid = $("#mid").val();
			let name = $("#name").val();
			let email = $("#email").val();
			let movieName = $("#movieName").val();
			let movieDate = $("#movieDate").val();
			let movieTime = $("#movieTime").val();
			let movieAdult = $("#movieAdult").val();
			let movieChild = $("#movieChild").val();
			
			if(mid.trim() == "" || name.trim() == "" || email.trim() == "" || movieName.trim() == "" || movieDate.trim() == "" || 
					movieTime.trim() == "" || movieAdult.trim() == "" || movieChild.trim() == "") {
				alert("영화 예매 정보를 입력해주세요");
				$("#name").focus();
				return false;
			}
			
			let query = {
					mid : mid,
					name : name,
					email : email,
					movieName : movieName,
					movieDate : movieDate,
					movieTime : movieTime,
					movieAdult : movieAdult,
					movieChild : movieChild
			}
			
			$.ajax({
				url : "${ctp}/study/qrCode/qrCodeEx4",
				type : "post",
				data : query,
				success : function(res) {
					if(res != "") {
						alert("생성된 QR코드명 : " + res);
						let qrCode = 'QR코드명 : ' + res + '<br/>';
						qrCode += '<img src="${ctp}/qrCode/'+res+'.png"/>';
						$("#demo").html(qrCode);
						$("#qrCodeView").show();
						
					}
					else alert("QR코드 생성 실패");
				},
				error : function() {
					alert("전송 오류");
				}
			});
		}
  	
  	//QR 코드 정보를 DB에서 검색하여 DB 정보 출력
  	function bigoCheck() {
			let qrCode = $("#bigo").val();
			
			$.ajax({
				url : "${ctp}/study/qrCode/qrCodeSearch",
				type : "post",
				data : {qrCode : qrCode},
				success : function(vo) {
					let str = '';
					str += '구매자 아이디 : '+vo.mid+'<br/>';
					str += '구매자 성명 : '+vo.name+'<br/>';
					str += '구매자 이메일 : '+vo.email+'<br/>';
					str += '영화명 : '+vo.movieName+'<br/>';
					str += '상영일자 : '+vo.movieDate+'<br/>';
					str += '상영시간 : '+vo.movieTime+'<br/>';
					str += '성인 : '+vo.movieAdult+'매<br/>';
					str += '어린이 : '+vo.movieChild+'매<br/>';
					str += '티켓구매일자 : '+vo.purchaseDate+'<br/>';
					$("#demoBigo").html(str);
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
  <h2>영회티켓 예매정보 QR코드로 생성하기</h2>
  <div>(생성된 QR코드를 메일로 보내드립니다. QR코드를 입장시 매표소에 제시해 주세요)</div>
  <hr/>
  <form method="post">
	  <table class="table table-bordered">
	  	<tr>
	  		<th>아이디</th>
	  		<td>${sMid}</td>
	  	</tr>
	  	<tr>
	  		<th>성명</th>
	  		<td><input type="text" name="name" id="name" value="${sNickName}" class="form-control" required /></td>
	  	</tr>
	  	<tr>
	  		<th>이메일</th>
	  		<td><input type="text" name="email" id="email" value="samdal@gmail.com" class="form-control" required /></td>
	  	</tr>
	    <tr>
	      <th>영화명선택</th>
	      <td>
	        <select name="movieName" id="movieName" class="form-control" required>
	          <option value="">영화선택</option>
	          <option>서울의봄</option>
	          <option>노량_죽음의바다</option>
	          <option>아쿠아맨과 로스트 킹덤</option>
	          <option>도티와 영원의 탑</option>
	          <option>괴물</option>
	          <option>3일의 휴가</option>
	          <option>말하고 싶은 비밀</option>
	        </select>
	      </td>
	    </tr>
	    <tr>
	      <th>상영일자선택</th>
	      <td><input type="date" name="movieDate" id="movieDate" value="<%=LocalDate.now()%>" class="form-control"/></td>
	    </tr>
	    <tr>
	      <th>상영시간선택</th>
	      <td>
	        <select name="movieTime" id="movieTime" class="form-control" required>
	          <option value="">상영시간선택</option>
	          <option>12시00분</option>
	          <option>14시30분</option>
	          <option>17시00분</option>
	          <option>19시30분</option>
	          <option>22시00분</option>
	        </select>
	      </td>
	    </tr>
	    <tr>
	      <th>인원수</th>
	      <td>
	        성인  <input type="number" name="movieAdult" id="movieAdult" value="1" min="1" class="form-control"/><br/>
	        어린이 <input type="number" name="movieChild" id="movieChild" value="0" min="0" class="form-control"/>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="2" class="text-center">
	        <input type="button" value="티켓예매하기" onclick="qrCodeCreate()" class="btn btn-success mr-2"/>
	        <input type="button" value="다시예매하기" onclick="location.reload()" class="btn btn-warning mr-2"/>
	        <input type="button" value="돌아가기" onclick="location.href='qrCode';" class="btn btn-info"/>
	      </td>
	    </tr>
	  </table>
	  <input type="hidden" name="mid" id="mid" value="${sMid}"/>
  </form>
  <hr/>
  <div>생성된 QR 코드 :<br/>
    <div id="demo"></div>
  </div>
  <hr/>
  <div id="qrCodeView" style="display:none">
  	<h4>입장시 생성된 QR코드와 DB의 자료를 확인한다</h4>
  	<div>
  		<input type="text" name="bigo" id="bigo" class="form-control" />
  		<div class="input-group-append">
      	<input type="button" value="DB 검색" onclick="bigoCheck()" class="btn btn-success"/>
      </div>
  	</div>
  	<div id="demoBigo"></div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>