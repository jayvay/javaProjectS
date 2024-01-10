<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<script>
	'use strict';
	
	function memberDelete() {
		let ans = confirm("정말로 탈퇴하시겠습니까?");
		if(ans) {
			ans = confirm("탈퇴 후 1개월동안 같은 아이디로 다시 가입할 수 없습니다.");
			if(ans) {
				$.ajax({
					url : "${ctp}/member/memberDelete",
					type : "post",
					success : function(res) {
						if(res == "1") {
							alert("언젠가 다시 만나길 바라겠습니다")
							location.href = "${ctp}/member/login";
						}
						else {
							alert("회원 탈퇴 실패, 다시 시도하세요");
						}
					},
					error : function() {
						alert("전송 오류");
					}
				});
			}
		}
	}
</script>
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-white w3-card">
    <a class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right" href="javascript:void(0)" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
    <a href="${ctp}/" class="w3-bar-item w3-button w3-padding-large">HOME</a>
    <!-- <a href="http://192.168.50.52:9090/javaProjectS" class="w3-bar-item w3-button w3-padding-large">HOME</a> -->
    <a href="${ctp}/guest/guestList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">GUEST</a>
    <c:if test="${!empty sLevel}">
	    <a href="${ctp}/board/boardList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">BOARD</a>
	    <a href="${ctp}/pds/pdsList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">PDS</a>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">STUDY1 <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/user/userList" class="w3-bar-item w3-button">UserList1</a>
	        <a href="${ctp}/user2/user2List" class="w3-bar-item w3-button">UserList2</a>
	        <a href="${ctp}/study/ajax/ajaxForm" class="w3-bar-item w3-button">AjaxTest</a>
	        <a href="${ctp}/study/uuid/uidForm" class="w3-bar-item w3-button">랜덤(UUID)</a>
	        <a href="${ctp}/study/password/sha256" class="w3-bar-item w3-button">암호화(SHA256)</a>
	        <a href="${ctp}/study/password/aria" class="w3-bar-item w3-button">암호화(Aria)</a>
	        <a href="${ctp}/study/password/bCryptPassword" class="w3-bar-item w3-button">암호화(Security)</a>
	        <a href="${ctp}/study/mail/mailForm" class="w3-bar-item w3-button">메일연습</a>
	        <a href="${ctp}/study/fileUpload/fileUpload" class="w3-bar-item w3-button">파일업로드</a>
	      </div>
	    </div>
 			<div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">STUDY2 <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/study/kakao/kakaomap" class="w3-bar-item w3-button">카카오맵</a>
	        <a href="${ctp}/study/chart/chart" class="w3-bar-item w3-button">차트연습1</a>
	        <a href="${ctp}/study/chart2/chart2" class="w3-bar-item w3-button">차트연습2</a>
	        <a href="#" class="w3-bar-item w3-button">스케줄링(Quartz)</a>
	        <a href="${ctp}/study/captcha/randomAlphaNumeric" class="w3-bar-item w3-button">랜덤알파뉴메릭</a>
	        <a href="${ctp}/study/captcha/captcha" class="w3-bar-item w3-button">캡차연습</a>
	        <a href="${ctp}/study/qrCode/qrCode" class="w3-bar-item w3-button">QR코드연습</a>
	        <a href="${ctp}/study/thumbnail/thumbnailForm" class="w3-bar-item w3-button">썸네일연습</a>
	        <a href="${ctp}/study/crawling/jsoup" class="w3-bar-item w3-button">크롤링(jsoup)</a>
	        <a href="${ctp}/study/crawling/selenium" class="w3-bar-item w3-button">크롤링(selenium)</a>
	        <a href="${ctp}/study/transaction/transaction" class="w3-bar-item w3-button">트랜잭션연습</a>
	        <a href="${ctp}/errorPage/errorMain" class="w3-bar-item w3-button">Error연습</a>
	      </div>
	    </div>
	    <div class="w3-dropdown-hover">
		    <button class="w3-padding-large w3-button" title="More">SHOP <i class="fa fa-caret-down"></i></button>     
		    <div class="w3-dropdown-content w3-bar-block w3-card-4">
		      <a href="${ctp}/dbShop/dbProductList" class="w3-bar-item w3-button">상품리스트</a>
		      <a href="${ctp}/dbShop/dbCartList" class="w3-bar-item w3-button">장바구니</a>
		      <a href="${ctp}/member/memberPwdCheck/p" class="w3-bar-item w3-button">주문/배송현황</a>
		      <a href="${ctp}/member/memberUpdate" class="w3-bar-item w3-button">결제</a>
		      <a href="javascript:memberDelete()" class="w3-bar-item w3-button">Q&A</a>
		      <a href="javascript:memberDelete()" class="w3-bar-item w3-button">1:1문의</a>
	    	</div>
	  	</div>
	    <div class="w3-dropdown-hover">
		    <button class="w3-padding-large w3-button" title="More">MyPage <i class="fa fa-caret-down"></i></button>     
		    <div class="w3-dropdown-content w3-bar-block w3-card-4">
		      <a href="${ctp}/user/userList" class="w3-bar-item w3-button">일정관리</a>
		      <a href="${ctp}/user2/user2List" class="w3-bar-item w3-button">회원리스트</a>
		      <a href="${ctp}/member/memberPwdCheck/p" class="w3-bar-item w3-button">비밀번호변경</a>
		      <a href="${ctp}/member/memberUpdate" class="w3-bar-item w3-button">회원정보수정</a>
		      <a href="javascript:memberDelete()" class="w3-bar-item w3-button">회원탈퇴</a>
		      <c:if test="${sLevel == 0}">
		      	<a href="${ctp}/admin/adminMain" class="w3-bar-item w3-button">관리자</a>
		      </c:if>
	    	</div>
	  	</div>
	  </c:if>	
  	<c:if test="${empty sLevel}">
	  	<a href="${ctp}/member/login" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Login</a>
	  	<a href="${ctp}/member/join" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Join</a>
  	</c:if>
  	<c:if test="${!empty sLevel}">
  		<a href="${ctp}/member/logout" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Logout</a>
  		<a href="${ctp}/member/kakaoLogout" class="w3-bar-item w3-button w3-padding-large w3-hide-small">카카오Logout</a>
    </c:if>
    <a href="javascript:void(0)" class="w3-padding-large w3-hover-red w3-hide-small w3-right"><i class="fa fa-search"></i></a>
  </div>
</div>

<!-- Navbar on small screens (remove the onclick attribute if you want the navbar to always show on top of the content when clicking on the links) -->
<div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-top" style="margin-top:46px">
  <a href="${ctp}/guest/guestList" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">GUEST</a>
  <c:if test="${!empty sLevel}">
	  <a href="${ctp}/board/boardList" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">BOARD</a>
	  <a href="${ctp}/pds/pdsList" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">PDS</a>
	  <div class="w3-dropdown-hover">
	    <button class="w3-padding-large w3-button" title="More">STUDY1 <i class="fa fa-caret-down"></i></button>     
	    <div class="w3-dropdown-content w3-bar-block w3-card-4">
	      <a href="${ctp}/user/userList" class="w3-bar-item w3-button">UserList1</a>
	      <a href="${ctp}/user2/user2List" class="w3-bar-item w3-button">UserList2</a>
	      <a href="${ctp}/study/ajax/ajaxForm" class="w3-bar-item w3-button">AjaxTest</a>
	      <a href="${ctp}/study/uuid/uidForm" class="w3-bar-item w3-button">랜덤(UUID)</a>
	      <a href="${ctp}/study/password/sha256" class="w3-bar-item w3-button">암호화(SHA256)</a>
	      <a href="${ctp}/study/password/aria" class="w3-bar-item w3-button">암호화(Aria)</a>
	      <a href="${ctp}/study/password/bCryptPassword" class="w3-bar-item w3-button">암호화(Security)</a>
	      <a href="${ctp}/study/mail/mailForm" class="w3-bar-item w3-button">메일연습</a>
	      <a href="${ctp}/study/fileUpload/fileUpload" class="w3-bar-item w3-button">파일업로드</a>
	    </div>
	  </div>
	  <div class="w3-dropdown-hover">
	    <button class="w3-padding-large w3-button" title="More">STUDY2 <i class="fa fa-caret-down"></i></button>     
	    <div class="w3-dropdown-content w3-bar-block w3-card-4">
	      <a href="${ctp}/user/userList" class="w3-bar-item w3-button">test1</a>
	      <a href="${ctp}/user2/user2List" class="w3-bar-item w3-button">test2</a>
	      <a href="#" class="w3-bar-item w3-button">test3</a>
	    </div>
	  </div>
	</c:if>
	<c:if test="${empty sLevel}">
	  	<a href="${ctp}/member/login" class="w3-bar-item w3-button w3-padding-large">Login</a>
	  	<a href="${ctp}/member/join" class="w3-bar-item w3-button w3-padding-large">Join</a>
	</c:if>
	<c:if test="${!empty sLevel}">
		<a href="${ctp}/member/logout" class="w3-bar-item w3-button w3-padding-large">Logout</a>
  </c:if>
</div>
<script>

	// Used to toggle the menu on small screens when clicking on the menu button
	function myFunction() {
	  var x = document.getElementById("navDemo");
	  if (x.className.indexOf("w3-show") == -1) {
	    x.className += " w3-show";
	  } else { 
	    x.className = x.className.replace(" w3-show", "");
	  }
	}
	
	// When the user clicks anywhere outside of the modal, close it
	var modal = document.getElementById('ticketModal');
	window.onclick = function(event) {
	  if (event.target == modal) {
	    modal.style.display = "none";
	  }
	}
</script>