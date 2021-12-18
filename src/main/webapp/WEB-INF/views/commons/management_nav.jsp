<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

   <div class="row">
   <div class="col">
   <nav class="navbar navbar-expand-lg navbar-light" style="background-color:#6c737e;">


  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav ml-auto mr-auto">
      <li class="nav-item dropdown ml-5 mr-3 pl-5 pr-3">
      <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="color:white">
                  사원관리
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown" style="background-color: #e8ecf1">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/management/employee_view.do" style="color:black">사원조회</a>
                     <a class="dropdown-item" href="${pageContext.request.contextPath}/management/employee_add.do" style="color:black">사원등록</a>
                     <a class="dropdown-item" href="${pageContext.request.contextPath}/management/employee_resignation.do" style="color:black">퇴사자관리</a>
        </div>
        
      </li>
      <li class="nav-item dropdown  ml-3 mr-3 pl-3 pr-3">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="color:white">
                  부서관리
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown" style="background-color: #e8ecf1">

                     <a class="dropdown-item" href="${pageContext.request.contextPath}/management/store_view.do" style="color:black">매장관리</a>
                     <a class="dropdown-item" href="${pageContext.request.contextPath}/management/factory_view.do" style="color:black">공장관리</a>
                     <a class="dropdown-item" href="${pageContext.request.contextPath}/management/logistics_management.do" style="color:black">물류관리</a>
                     <a class="dropdown-item" href="${pageContext.request.contextPath}/management/logistics_add.do" style="color:black">물류지점등록</a>
        </div>
      </li>
      
      <li class="nav-item dropdown  ml-3 mr-3 pl-3 pr-3">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="color:white">
                상품관리
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown" style="background-color: #e8ecf1">
                     <a class="dropdown-item" href="${pageContext.request.contextPath}/management/product_view.do" style="color:black">상품조회</a>
                     <a class="dropdown-item" href="${pageContext.request.contextPath}/management/product_add.do" style="color:black">상품등록</a>

        </div>
      </li>
      
      
        <li class="nav-item dropdown ml-3 mr-5 pl-3 pr-5">
        <a class="nav-link" href="${pageContext.request.contextPath}/management/main_page.do" id="navbarDropdown" role="button" aria-haspopup="true" aria-expanded="false" style="color:white">
                부서게시판
        </a>

      </li>
    </ul>
  </div>
</nav>
   </div>
   </div>