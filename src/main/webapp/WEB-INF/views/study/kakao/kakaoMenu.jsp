<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<div>
	<p>
		<a href="${ctp}/study/kakao/kakaoEx1" class="btn btn-light">마커표시/저장</a>
		<a href="${ctp}/study/kakao/kakaoEx2" class="btn btn-light">나의 DB에 저장된 지점명으로 검색</a>
		<a href="${ctp}/study/kakao/kakaoEx3" class="btn btn-light">KakaoDB에 저장된 키워드로 검색</a>
		<a href="${ctp}/study/kakao/kakaoEx4" class="btn btn-light">주변검색</a>
	</p>
</div>