<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
</head>

<body>
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
	<div class="row">
	<div class="col-1" style="background-color: #f9f9fa"></div>
	<!--  사이드바 시작  -->
         <div class="col-2"
            style="padding: 0; border-right: 2px solid #e8ecf1;">
            <ul class="list-group list-group-flush">
               <!--  수정할 부분 시작 -->
              <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">부서게시판</h2></li>
               <!--  수정할 부분 끝 -->
            </ul>
         </div>
         <!--  사이드바 끝   -->
	
		<!--  메인 기능 나오는 곳 -->
         <div class="col pl-5 pr-5">
            <!--  페이지 이름  -->
            <div class="row">
                <div class="col" style="background-color:white">
                <!--1. 타이틀, 네비게이터 시작-->
                <div class="row mt-3">
                    <!--타이틀-->
                  <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">관리부 게시판</span></div>
              	  <div class="col"></div>
              	  <!---네비게이터-->
                    <div class="col-7">
                        <div class="row">                                   
                            <div class="col"></div>      
                        </div>
                        <div class="row"> 
                            <div class="col-2"></div> 
                            <div class="col mt-5" style="display:inline;">     
                                <ul style="list-style:none; margin-top: 10px; margin-bottom: 5px; margin-left: 100px; text-decoration-color: gray">
                                    <li>
                                     
                                    </li>
                                </ul>             
                            </div>      
                        </div>
                    </div>
                </div>
         
         <form action="./main_page.do" method="get">
	<div class="row my-3">
	   <div class="col"></div>
	   <div class="col-6">
		   <div class="row">
			   <div class="col-4">
				   <select name="search_type" class="form-control">
				   <option value="">검색</option>
				   <option value="1">제목</option>
				   <option value="2">내용</option>
				   <option value="3">글쓴이</option>

				   </select>
			   </div>
			   <div class="col">
				   <input name="searchWord" type="text" class="form-control">
			   </div>
			   <div class="col-3">
				   <input type="submit" class="btn btn-primary btn-block" value="검색">
			   </div>					
			</div>	
	   </div>
   </div>
</form>

  
		
		
		
		<div class="row mt-3 mb-5"><!-- 테이블  -->
					<div class="col">
						<table class="table table-hover shadow-sm p-3 mb-5 bg-white rounded">
							<thead class="shadow-none p-3 mb-5 bg-light rounded">
								<tr>
									<td class="text-center"></td>
									<td class="text-center">글번호</td>
									<td class="text-center">제목</td>
									<td class="text-center">작성자</td>
									<td class="text-center">작성일</td>
									<td class="text-center">조회수</td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<c:if test="${!empty noticelist}">
									<c:forEach items="${noticelist }" var="data">
									<tr style="font-weight:bold" bgcolor="#e8ecf1">
										<td class="text-center" >
											<c:if test="${data.departmentBoardVo.check_notice eq 'Y'.charAt(0) }">[공지]</c:if>
										</td>
										<td class="text-center">${data.departmentBoardVo.dept_board_no }</td>
										<td class="text-center">
										<a href="${pageContext.request.contextPath }/management/board_read_page.do?dept_board_no=${data.departmentBoardVo.dept_board_no }">${data.departmentBoardVo.dept_board_title }</a></td>
										<td class="text-center">${data.employeeVo.emp_name }</td>
										<td class="text-center">
											<fmt:formatDate value="${data.departmentBoardVo.dept_board_writedate }" pattern="yyyy-MM-dd"/></td>
										<td class="text-center">${data.departmentBoardVo.dept_board_view }</td>
									</tr>
									
									</c:forEach>
									</c:if>
									<c:forEach items="${dataList }" var="data">
										<tr>
										<td class="text-center">
											<c:if test="${data.departmentBoardVo.check_notice eq 'Y'.charAt(0) }">[공지]</c:if>
										</td>
										<td class="text-center">${data.departmentBoardVo.dept_board_no }</td>
										<td class="text-center">
										<a href="${pageContext.request.contextPath }/management/board_read_page.do?dept_board_no=${data.departmentBoardVo.dept_board_no }">${data.departmentBoardVo.dept_board_title }</a></td>
										<td class="text-center">${data.employeeVo.emp_name }</td>
										<td class="text-center">
											<fmt:formatDate value="${data.departmentBoardVo.dept_board_writedate }" pattern="yyyy-MM-dd"/></td>
										<td class="text-center">${data.departmentBoardVo.dept_board_view }</td>
									</tr>
								</c:forEach>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
		<div class="row mt-3">
					<!-- 버튼위치 -->
					<div class="col"></div>
					<div class="col"> <!-- 페이지 버튼 -->
						<nav aria-label="Page navigation example">
							<ul class="pagination">
							<li class="page-item<c:if test="${beginPage-1<=0 }"> disabled</c:if>"><a class="page-link" href="./main_page.do?currPage=${beginPage-1 }&searchWord=${param.searchWord}">이전</a></li>
								<c:forEach begin="${beginPage }" end="${endPage }" var="i">
									<!-- 띄어쓰기 주의 -->
									<li class="page-item<c:if test="${currPage==i }"> active</c:if>">
									<a class="page-link" href="./main_page.do?currPage=${i }&searchWord=${param.searchWord}">${i }</a></li>
								</c:forEach> 
							<li class="page-item<c:if test="${endPage+1 > (totalCount-1)/10+1 }"> disabled</c:if>"><a class="page-link" href="./main_page.do?currPage=${endPage+1 }&searchWord=${param.searchWord}">다음</a></li>
							</ul>
						</nav>
					</div>
					<div class="col-2">
						<a class="btn btn-primary btn-sm btn-block"
							href="${pageContext.request.contextPath }/management/board_write_page.do">글쓰기</a>
					</div>
				</div>
			</div>
			</div>
               
                    
          </div>
            
          <!--  메인기능 나오는 곳 끝 -->
		 <div class="col-1" style="background-color: #f9f9fa"></div>
		
		</div>	
	</div>


        

      	
      	 
      	 
			<!--  footer  -->
<jsp:include page="../commons/footer.jsp"></jsp:include>
<jsp:include page="../commons/alert_unread_message.jsp"></jsp:include>
	
	

	
	
	
	
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>