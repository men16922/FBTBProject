<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<meta charset="UTF-8">
<title>쪽지 읽기</title>
</head>
<body>
<!-- global navbar -->
<jsp:include page="../commons/global_nav.jsp"></jsp:include>


 <!-- logistics navbar -->
    <c:if test="${sessionUser.deptTypeVo.dept_type_no==1}">
  <jsp:include page="../commons/management_nav.jsp"></jsp:include>
   </c:if>
     <c:if test="${sessionUser.deptTypeVo.dept_type_no==2}">
  <jsp:include page="../logistics/logistics_nav.jsp"></jsp:include>
   </c:if>
  <c:if test="${sessionUser.deptTypeVo.dept_type_no==3}">
  <jsp:include page="../commons/sales_nav.jsp"></jsp:include>
   </c:if>
     <c:if test="${sessionUser.deptTypeVo.dept_type_no==4}">
  <jsp:include page="../commons/production_nav.jsp"></jsp:include>
   </c:if>



<div class="container-fluid">
      <!-- 메인 기능들이 나오는 row -->
      <div class="row">
         <div class="col-1" style="background-color: #f9f9fa"></div>

         <!--  사이드바 시작  -->
         <div class="col-2"
            style="padding: 0; border-right: 2px solid #e8ecf1;">
            <ul class="list-group list-group-flush">
               <!--  수정할 부분 시작 -->
               <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">쪽지함</h2></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/message/list_message_page.do">받은쪽지함</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/message/list_sended_message_page.do">보낸쪽지함</a></h5></li>
               <!--  수정할 부분 끝 -->
            </ul>
         </div>
         <!--  사이드바 끝   -->

      
        <!--  메인 기능 나오는 곳 -->
        <div class="col pl-5 pr-5">
        <!--메인기능 내용시작-->
        <div class="row">
            <!--메인기능 메인col-->
            <div class="col" style="background-color:white">
                <!--1. 타이틀, 네비게이터 시작-->
                <div class="row mt-3">
                    <!--타이틀-->
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">받은쪽지함</span></div>
                    <div class="col"></div>
                    <!---네비게이터-->
                    <div class="col-6">
                        <div class="row">                                   
                            <div class="col"></div>      
                        </div>
                        <div class="row mb-3"> 
                            <div class="col-5"></div> 
                            <div class="col mt-5" style="display:inline;">     
                                <ul style="list-style:none; margin-top: 10px; margin-bottom: 5px; margin-left: 100px; text-decoration-color: gray">
                                    <li>
                                        <a style="color: gray; font-size: 14px" href="#">쪽지함</a>
                                        >
                                        <a style="color: gray; font-size: 14px">받은쪽지함</a>
                                        
                                    </li>
                                </ul>             
                            </div>      
                        </div>
                    </div>
                </div>
                <!--1. 타이틀, 네비게이터 끝-->
				<form action="${pageContext.request.contextPath }/message/list_message_page.do" method="post">
					<div class="row my-3">
						<div class="col"></div>
						<div class="col-6">
							<div class="row">
								<div class="col-4">
									<select name="search_type" class="form-control">
									<option value="">검색</option>
									<option value="1">지점명</option>
									<option value="2">보낸사람</option>
									<option value="3">제목</option>
									</select>
								</div>
								<div class="col">
									<input name="search_word" type="text" class="form-control">
								</div>
		                        <div class="col-3">
		                        	<input type="submit" class="btn btn-primary btn-block" value="검색">
		                        </div>					
					         </div>	
				    	</div>
					</div>
				</form>
				
					<div class="row mt-2"> <!-- 테이블 -->
						<div class="col">
							<table class="table table-hover shadow-sm p-3 mb-5 bg-white rounded">
								<thead class="shadow-none p-3 mb-5 bg-light rounded">
									<tr>
										<th>쪽지번호</th>
										<th>쪽지제목</th>
										<th>지점명</th>
										<th>보낸사람</th>
										<th>날짜</th>
										<th>확인여부</th>
									</tr>
								</thead>
								<tbody>
								
								<c:forEach var="data" items="${messageData }">
									<tr>
										<td>${data.messageVo.msg_no}</td>							
										<td><a href="${pageContext.request.contextPath }/message/read_message_page.do?msg_no=${data.messageVo.msg_no}">${data.messageVo.msg_title}</a></td>												
										<td>${data.branchVoSend.branch_name}</td>
										<td>${data.employeeVoSend.emp_name}</td>													
										<td><fmt:formatDate value="${data.messageVo.msg_writedate}" pattern="yy.MM.dd.hh.mm.ss"/></td>	
										<td>
										<c:choose>
											<c:when test="${data.messageVo.check_read eq 'Y'.charAt(0)}">
												Y
											</c:when>
											<c:otherwise>
												N
											</c:otherwise>
										</c:choose>
										
										
										</td>					
									</tr>	
								</c:forEach>
									
								</tbody>
							
							</table>
						</div>
					</div>
					<div class="row mb-5"> <!-- 버튼들... -->
						<div class="col-8"> <!-- 페이지 버튼 -->
							<nav aria-label="Page navigation example">
							   <ul class="pagination">
		 
		                  <li class="page-item<c:if test="${beginPage-1 <= 0}"> disabled</c:if>">
		                   <a class="page-link" href="${pageContext.request.contextPath }/message/list_message_page.do?currPage=${beginPage-1 }&search_type=${param.search_type}&search_word=${param.search_word}">이전</a></li>
		                    <c:forEach begin="${beginPage }" end="${endPage }" var="i">
		                       <li class="page-item<c:if test="${currPage==i}"> active</c:if>"><a class="page-link" href="${pageContext.request.contextPath }/message/list_message_page.do?currPage=${i}&search_type=${param.search_type}&search_word=${param.search_word}">${i}</a></li>
		                    </c:forEach>
		                   <li class="page-item<c:if test="${endPage+1 >= (totalCount-1)/10+1 }"> disabled</c:if>"><a class="page-link" href="${pageContext.request.contextPath }/message/list_message_page.do?currPage=${endPage+1 }&search_type=${param.search_type}&search_word=${param.search_word}">다음</a></li>
		                 
							  </ul>
		
							</nav>
						</div>
						<div class="col-2"></div>
						
						<div class="col-2">
							<c:if test="${!empty sessionUser }"> 
							<a class="btn btn-primary btn-block" href="${pageContext.request.contextPath }/message/write_message_page.do">쪽지 쓰기</a>
							</c:if>
						</div>
						
		            </div>	
                
                    
            </div>        
        </div>
        <!--메인기능 내용끝-->

       </div>
         <!--  메인기능 나오는 곳 끝 -->

         <div class="col-1" style="background-color: #f9f9fa"></div>

      </div>

      <!--  footer  -->
      <jsp:include page="../commons/footer.jsp"></jsp:include>
   </div>


<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>