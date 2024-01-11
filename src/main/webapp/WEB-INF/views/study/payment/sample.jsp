<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>sample.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
  <script>
    var IMP = window.IMP;
    IMP.init("imp05878652");	//내 식별코드 - 가맹점 식별코드

   // function requestPay() {
      IMP.request_pay(
        {
          pg: "html5_inicis.INIpayTest",
          pay_method: "card",
          merchant_uid: "javaProjectS_" + new Date().getTime(),
          name: "${vo.name}",
          amount: '${vo.amount}',
          buyer_email: "${vo.buyer_email}",
          buyer_name: "${vo.buyer_name}",
          buyer_tel: "${vo.buyer_tel}",
          buyer_addr: "${vo.buyer_addr}",
          buyer_postcode: "${vo.buyer_postcode}",
        },
        function (rsp) {
          // callback
          //rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
          if(rsp.success) {
        	  alert("결제가 완료되었습니다.");
        	  location.href="${ctp}/dbShop/payment/paymentOk";
          }
          else {
        	  alert("결제가 취소되었습니다.");
        	  location.href="${ctp}/dbShop/payment/payment";
          }
        }
      );
    //  }
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<p><img src="${ctp}/images/payment.gif"/></p>
<!--   <button onclick="requestPay()" class="btn btn-success">결제하기</button> -->
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>