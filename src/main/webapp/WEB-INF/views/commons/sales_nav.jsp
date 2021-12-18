<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-expand-lg navbar-light"
   style="background-color: #6c737e;">


  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav ml-auto mr-auto">
      
      <li class="nav-item dropdown  ml-4 mr-4 pl-4 pr-4">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="color:white">
                 발주관리
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown" style="background-color: #e8ecf1">
          <a class="dropdown-item" href="${pageContext.request.contextPath }/sales/store_order_reservation_page.do">발주서작성</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath }/sales/store_order_view_page.do">발주 확인</a>

        </div>
      </li>
      
      
      <li class="nav-item dropdown ml-4 mr-4 pl-4 pr-4">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="color:white">
       재고관리
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown" style="background-color: #e8ecf1">
          <a class="dropdown-item" href="${pageContext.request.contextPath }/sales/product_page.do">재고조회</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath }/sales/allproduct_page.do">제품조회</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath }/sales/store_in_view_page.do">입고조회</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath }/sales/store_in_page.do">입고등록</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath }/sales/store_out_view_page.do">출고조회</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath }/sales/store_out_page.do">출고등록</a>

        </div>
      </li>
      
      <li class="nav-item dropdown  ml-4 mr-4 pl-4 pr-4">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="color:white">
                고객주문관리
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown" style="background-color: #e8ecf1">
          <a class="dropdown-item" href="${pageContext.request.contextPath }/sales/customer_view_page.do">고객조회</a>
        <a class="dropdown-item" href="${pageContext.request.contextPath }/sales/customer_reservation_page.do">고객주문등록</a>
        <a class="dropdown-item" href="${pageContext.request.contextPath }/sales/customer_reservation_view_page.do">고객주문조회</a>

        </div>
      </li>
      
      
      <li  class="mt-2 ml-4 mr-4 pl-4 pr-4"><a href="${pageContext.request.contextPath }/sales/main_page.do" style="color:white">부서게시판</a></li>
  
    </ul>
  </div>
</nav>