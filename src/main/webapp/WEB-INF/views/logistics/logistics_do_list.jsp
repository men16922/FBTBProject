<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="card">
   <div class="card-header"
      style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 20px;">To
      Do List</div>
   <div class="card-body">
      <p class="card-text">
         ● 1차검토 해야할 주문 <a
            href="${pageContext.request.contextPath }/logistics/first_check_page.do">${firstCheckCntToDo }</a>건
      </p>

      <c:if test="${sessionUser.employeeRankVo.rank_no <= 5 }">
         <p class="card-text">
            ● 출고요청 해야할 주문 <a
               href="${pageContext.request.contextPath }/logistics/last_check_page.do">${lastCheckCntToDo }</a>건
         </p>
         <p class="card-text">
            ● 공장재선택 해야할 주문 <a
               href="${pageContext.request.contextPath }/logistics/reselect_factory_page.do">${reselectCntToDo }</a>건
         </p>
      </c:if>
   </div>
</div>