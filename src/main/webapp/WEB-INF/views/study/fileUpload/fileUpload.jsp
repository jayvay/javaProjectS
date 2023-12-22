<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>fileUpload.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	//파일 업로드
  	function fCheck() {
			let fName = document.getElementById("fName").value;
			let ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
			let maxSize = 1024*1024*20; //업로드 가능한 최대 용량은 20MByte
			
			if(fName.trim() == "") {
				alert("업로드할 파일을 선택하세요");
			}
			
			let fileSize = document.getElementById("fName").files[0].size;
			if(ext != "jpg" && ext != "gif" && ext != "png" && ext != "zip" && ext != "ppt" && ext != "pptx" && ext != "hwp") {
				alert("jpg, gif, png, zip, ppt, pptx, hwp 파일만 업로드할 수 있습니다");
			}
			else if(fileSize > maxSize) {
				alert("업로드 가능한 최대 용량은 20MByte 입니다");
			}
			else {
				myform.submit();
			}	
			
		}
  	
  	//서버의 파일 삭제
  	function fileDelete(file) {
			let ans = confirm("파일을 삭제하시겠습니까?");
			if(!ans) return false;
			
			$.ajax({
				url : "${ctp}/study/fileUpload/fileDelete",
				type : "post",
				data : {file : file},
				success : function(res) {
					if(res == "1") {
						alert("파일이 삭제되었습니다");
						location.reload();
					}
					else alert("파일 삭제 실패");
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
  <h2>파일 업로드 연습</h2>
  <form name="myform" method="post" enctype="multipart/form-data">
  	<p>
  		<input type="text" name="mid" value="${sMid}" />
  	</p>
  	<p>
  		<input type="file" name="fName" id="fName" class="form-control-file border" accept=".jpg,.gif,.png,.zip,.ppt,.pptx,.hwp" />
  	</p>
  	<p>
  		<input type="button" value="파일업로드" onclick="fCheck()" class="btn btn-warning" />
  		<input type="reset" value="다시선택" class="btn btn-warning" />
  	</p>
 	</form>
 	<hr/>
 	<div id="downloadFile">
 		<h3>서버에 저장된 파일 정보(총 : ${filesCnt}건)</h3>
 		<p>저장 경로 : ${ctp}/resources/data/study/*.* </p>
 		<table class="table table-hover text-center">
 			<tr class="table-dark text-dark">
 				<th>번호</th>
 				<th>파일명</th>
 				<th>파일형식</th>
 				<th>비고</th>
 			</tr>
			<c:forEach var="file" items="${files}" varStatus="st">
	 			<tr>
 					<td>${st.count}</td>
 					<td><a href="${ctp}/study/${file}" download>${file}</a></td>
 					<td>
 						<c:set var="fNameArray" value="${fn:split(file, '.')}" />
 						<c:set var="extName" value="${fn:toLowerCase(fNameArray[fn:length(fNameArray)-1])}" />	<!-- 확장자 변수 -->
 						<c:if test="${extName == 'zip'}">압축파일</c:if>
 						<c:if test="${extName == 'ppt'|| extName == 'pptx'}">파워포인트파일</c:if>
 						<c:if test="${extName == 'hwp'}">한글파일</c:if>
 						<c:if test="${extName == 'jpg' || extName == 'gif' || extName == 'png'}">
							<img src="${ctp}/study/${file}" width="150px" />
						</c:if>
 					</td>
 					<td>
 						<input type="button" value="다운로드" onclick="location.href='${ctp}/study/fileUpload/fileDownload?file=${file}';" class="btn btn-light btn-sm" />
 						<input type="button" value="삭제" onclick="fileDelete('${file}')" class="btn btn-light btn-sm" />
 					</td>
				</tr>
			</c:forEach>
			<tr><td colspan="4" class="m-0 p-0"></td></tr>
 		</table>
 	</div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>