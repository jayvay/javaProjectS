<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberPwdCheck.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
    'use strict';
    
    $(function() {
    	$("#pwdDemo").hide();
    });
    
    function pwdCheck() {
    	let pwd = $("#pwd").val().trim();
    	if(pwd == "") {
    		alert("비밀번호를 입력하세요");
    		$("#pwd").focus();
    		return false;
    	}
    	
    	$.ajax({
    		url  : "${ctp}/member/memberPwdCheck",
    		type : "post",
    		data : {pwd : pwd},
    		success : function(res){
    			if(res == "1") {
	    			if('${pwdFlag}' == "p") {
	    				$("#pwdDemo").show();
	    				$("#pwdForm").hide();
	    			}
	    			else location.href = "${ctp}/member/memberUpdate";
    			}
    			else {
    				alert("비밀번호가 틀렸습니다");
    				$("#pwd").focus();
    			}
    		},
    		error : function() {
    			alert('전송오류');
    		}
    	});
    }
    
    function pwdChange() {
    	let pwdNew = $("#pwdNew").val();
    	let pwdNewCheck = $("#pwdNewCheck").val();
    	
    	if(pwdNew == "" || pwdNewCheck == "") {
    		alert("변경할 비밀번호를 입력하세요");
    		$("#pwdNew").focus();
    		return false;
    	}
    	else if(pwdNew != pwdNewCheck) {
    		alert("새로 입력한 비밀번호가 다릅니다");
    		$("#pwdNew").focus();
    		return false;
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/member/memberPwdChange",
    		data : {pwdNew : pwdNew},
    		success : function(res) {
    			if(res == "1") {
    				alert("비밀번호가 변경되었습니다\n다시 로그인 하세요");
    				location.href = "${ctp}/member/logout";
    			}
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
  </script>
  <style>
    th {
      text-align: center;
      background-color: #eee;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <div id="pwdForm">
	  <form method="post">
		  <table class="table table-bordered m-0">
			  <tr>
			    <td colspan="2" class="text-center">
			      <h3>비밀번호 확인</h3>
			      <div>(회원 정보 수정을 하기위해 기본 비밀번호를 확인합니다.)</div>
			    </td>
			  </tr>
			  <tr>
			    <th>비밀번호</th>
			    <td><input type="password" name="pwd" id="pwd" class="form-control" autofocus required /></td>
			  </tr>
			</table>
			<table class="table table-borderless text-center">
			  <tr>
			    <td>
			      <!-- 
			      <input type="button" value="비밀번호변경" onclick="pwdReCheck()" class="btn btn-success mr-2" />
			      <input type="submit" value="회원정보변경" class="btn btn-primary mr-2" />
			      -->
			      <input type="button" value="비밀번호확인" onclick="pwdCheck()" class="btn btn-success mr-2" />
			      <input type="reset" value="다시입력" class="btn btn-info mr-2" />
			      <input type="button" value="돌아가기" onclick="location.href='${ctp}/member/memberMain';" class="btn btn-warning" />
			    </td>
			  </tr>
		  </table>
		  <%-- <input type="hidden" name="pwdFlag" value="${pwdFlag}"/> --%>
		</form>
	</div>
	<div id="pwdDemo">
	  <form>
	    <h3 class="text-center">비밀번호 변경</h3>
		  <table class="table table-bordered">
		    <tr>
		      <th>변경할 비밀번호를 입력하세요</th>
		      <td><input type="password" name="pwdNew" id="pwdNew" class="form-control"/></td>
		    </tr>
		    <tr>
		      <th>비밀번호 확인</th>
		      <td><input type="password" name="pwdNewCheck" id="pwdNewCheck" class="form-control"/></td>
		    </tr>
		  </table>
			<table class="table table-borderless text-center">
		    <tr>
		      <td colspan="2">
		        <div class="row">
			        <div class="col"><input type="button" value="비밀번호변경하기" onclick="pwdChange()" class=" btn btn-success form-control"/></div>
			        <div class="col"><input type="reset" value="다시입력" class=" btn btn-info form-control"/></div>
			        <div class="col"><input type="button" value="돌아가기" onclick="location.href='${ctp}/member/memberPwdCheck/p';" class=" btn btn-secondary form-control"/></div>
		        </div>
		      </td>
		    </tr>
		  </table>
	  </form>
	</div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>