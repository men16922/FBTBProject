<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="card">
  <div class="card-header"
      style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 20px;">
    TO DO LIST
  </div>
  <ul class="list-group list-group-flush">
    <li class="list-group-item">
    	<!-- 공장발주 -->
    	<p class="card-text" style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-size: 18px;">
    		● 미입고 부품 발주&nbsp;<a href="${pageContext.request.contextPath }/production/none_in_item_page.do"><c:out value="${param.factoryOrderCount }"></c:out></a>건
    	</p>
    </li>
    <li class="list-group-item">
    	<!-- 상품발주 -->
    	<p class="card-text" style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-size: 18px;">
    		● 출고요청 등록이 필요한 발주&nbsp;<a href="${pageContext.request.contextPath }/production/delivery_request_page.do"><c:out value="${param.productReqCount }"></c:out></a>건
    	</p>
    	<p class="card-text" style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-size: 18px;">
    		● 배송등록이 필요한 발주&nbsp;<a href="${pageContext.request.contextPath }/production/delivery_ready_page.do"><c:out value="${param.productReadyCount }"></c:out></a>건
    	</p>
    </li>
  </ul>
</div>