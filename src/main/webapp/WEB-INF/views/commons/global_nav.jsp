<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
<a href="${pageContext.request.contextPath}/employee/homepage.do"><img id="logo" src="${pageContext.request.contextPath}/resources/img/image1.png" width="300" height="100"></a>

   <!-- <a class="navbar-brand" href="#">Home</a>-->


      
      <div class="collapse navbar-collapse" id="navbarNavDropdown">
      <ul class="navbar-nav ml-auto">
      
 	
      <c:choose>
         <c:when test="${!empty sessionUser }">
            <ul class="navbar-nav" style="list-style: none">
               <li class="nav-item dropdown mr-auto active"><a
                  class="nav-link dropdown-toggle" href="#"
                  id="navbarDropdownMenuLink" role="button" data-toggle="dropdown"
                  aria-haspopup="true" aria-expanded="false">
	                  
	                   ${sessionUser.branchVo.branch_name } &nbsp; ${sessionUser.employeeRankVo.rank_name } &nbsp; ${sessionUser.employeeVo.emp_name } 님 </a>
	                  
                  <div class="dropdown-menu"
                     aria-labelledby="navbarDropdownMenuLink">
                     <a class="dropdown-item" style="text-align: center" href="${pageContext.request.contextPath }/employee/update_password_page.do">마이페이지</a>
                  </div>
                </li>
               <li class="mr-5"></li>
               <!-- 없으면 dropdown-menu 선택 시 가로 스크롤바 생김 -->
            </ul>
         </c:when>
         <c:otherwise>
            <ul class="navbar-nav">
               <li class="nav-item ml-auto mr-5">
                  <a class="nav-link" style="text-align: right" href="${pageContext.request.contextPath }/employee/login_page.do">로그인</a>
               </li>
            </ul>
         </c:otherwise>
      </c:choose>

          
	     <c:if test="${!empty sessionUser }">
         <li class="nav-item mr-2">
         <a class="nav-link" href="${pageContext.request.contextPath }/freeboard/list_freeboard_page.do">자유게시판
               <span class="sr-only">(current)</span>
         </a>
         </li>
         
         <li class="nav-item mr-2"><a class="nav-link" href="${pageContext.request.contextPath }/message/list_message_page.do">쪽지</a></li>
	     <li class="nav-item mr-5"><a class="nav-link" href="${pageContext.request.contextPath }/employee/logout_process.do">로그아웃</a></li>
	     </c:if>
         
      </ul>
   </div>
   
</nav>