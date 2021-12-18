<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>



<nav class="navbar navbar-expand-lg navbar-light"
   style="background-color: #6c737e;">


   <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav ml-auto mr-auto">

         <li class="nav-item dropdown  ml-5 mr-5 pl-3 pr-3"><a
            class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
            role="button" data-toggle="dropdown" aria-haspopup="true"
            aria-expanded="false" style="color: white"> 발주관리 </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown"
               style="background-color: #e8ecf1">
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/product_order_list_page.do">발주조회</a>
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/delivery_request_page.do">생산예정리스트</a>
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/delivery_ready_page.do">배송등록</a>
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/delivery_list_page.do">배송조회</a>

            </div></li>


         <li class="nav-item dropdown ml-5 mr-5 pl-3 pr-3"><a
            class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
            role="button" data-toggle="dropdown" aria-haspopup="true"
            aria-expanded="false" style="color: white"> 재고관리 </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown"
               style="background-color: #e8ecf1">
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/component_page.do">부품조회</a>
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/component_enroll_page.do">부품등록</a>
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/none_in_item_page.do">입고등록</a>
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/in_item_page.do">입고조회</a>
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/out_item_enroll_page.do">출고등록</a>
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/out_item_page.do">출고조회</a>
               
            </div></li>
            
         <li class="nav-item dropdown  ml-5 mr-5 pl-3 pr-3"><a
            class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
            role="button" data-toggle="dropdown" aria-haspopup="true"
            aria-expanded="false" style="color: white"> 부품공급 </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown"
               style="background-color: #e8ecf1">
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/order_enroll_page.do">부품 발주등록</a>
               <a class="dropdown-item"
                  href="${pageContext.request.contextPath }/production/order_list_page.do">발주조회</a>

            </div></li>
         <li class="mt-2 ml-4 mr-4 pl-4 pr-4"><a role="button"
            aria-haspopup="true" aria-expanded="false" style="color: white"
            href="${pageContext.request.contextPath }/production/main_page.do">부서게시판</a>
         </li>
      </ul>
   </div>
</nav>