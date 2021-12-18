<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<div class="card">
   <div class="card-header"
      style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 20px;">To
      Do List</div>
   <div class="card-body">
      <p class="card-text">● 미처리 고객주문 <a href="${pageContext.request.contextPath}/sales/customer_reservation_view_page.do">${customerreservationcount}</a>건</p>
      <p class="card-text">● 미입고 리스트 <a href="${pageContext.request.contextPath}/sales/store_in_page.do">${storeincount}</a>건</p>
   </div>
</div>