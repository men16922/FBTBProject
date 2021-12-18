<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>page</title>
<link rel="stylesheet"
   href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
   integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
   crossorigin="anonymous">

</head>

<body>
   <jsp:include page="../commons/global_nav.jsp"></jsp:include>
   <jsp:include page="../commons/management_nav.jsp"></jsp:include>

   <div class="container-fluid">
      <!-- 메인 기능들이 나오는 row -->
      <div class="row">
         <div class="col-1" style="background-color: #f9f9fa"></div>

         <!--  사이드바 시작  -->
         <div class="col-2"
            style="padding: 0; border-right: 2px solid #e8ecf1;">
            <ul class="list-group list-group-flush">
               <!--  수정할 부분 시작 -->
               <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">부서관리</h2></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/store_view.do">매장관리</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/factory_view.do">공장관리</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/logistics_management.do">물류관리</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/logistics_add.do">물류지점등록</a></h5></li>
               
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
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">매장 관리</span></div>
                    <div class="col"></div>
                    <!---네비게이터-->
                    <div class="col-6">
                        <div class="row">                                   
                            <div class="col"></div>      
                        </div>
                        <div class="row"> 
                            <div class="col-2"></div> 
                            <div class="col mt-5" style="display:inline;">     
                                <ul style="list-style:none; margin-top: 10px; margin-bottom: 5px; margin-left: 100px; text-decoration-color: gray">
                                    <li>
                                        <a style="color: gray; font-size: 14px" href="#">부서관리</a>
                                        >
                                        <a style="color: gray; font-size: 14px" href="${pageContext.request.contextPath }/management/store_view.do">매장관리</a>
                                       
                                        
                                    </li>
                                </ul>             
                            </div>      
                        </div>
                    </div>
                </div>
                <!--1. 타이틀, 네비게이터 끝-->

                <!--2. 메인기능 알맹이들!!!!! 여기에 넣기-->
                <!--2. 메인기능 알맹이들!!!!! 여기에 넣기-->
                <!--2. 메인기능 알맹이들!!!!! 여기에 넣기-->
                <!--2. 메인기능 알맹이들!!!!! 여기에 넣기-->

	
<div class="container">
<c:if test="${!empty sessionUser && sessionUser.deptTypeVo.dept_type_no==1 }">

            <form action="./store_view.do" method="post">

            <!-- 테이블 내용 -->
               <div class="row mt-5">
                  <div class="col-2"></div>
                  <div class="col">
                  <table class="table bg-light">
                     <tbody>
                     
                     <tr>
                        <th>매장/담당자</th>
                        <td>
                       <select name="branch_no" class="form-control form-control-sm">
		<option value="">지점선택</option>
			<c:forEach var="cccc" items="${dataList_store }">
							
		<option value="${cccc.branchVo.branch_no }" <c:if test="${param.branch_no==cccc.branchVo.branch_no}">selected="selected"</c:if>>${cccc.branchVo.branch_name }</option>	
		</c:forEach>
		</select>
						 
                        </td>
                        <td><input placeholder="담당자명을 입력해주세요." name="emp_name" type="text" class="form-control form-control-sm" value="${param.emp_name }"></td>
                     </tr>
                     
                     <tr>
                        <th>주소</th>
                        <td colspan="2">
                   <input placeholder="주소를 입력해주세요." name="branch_address" type="text" class="form-control form-control-sm" value="${param.branch_address }">
					 </td>
                        
                     </tr>
                     
                     <tr>
					<th></th>
					<td></td>
					<td><input type="submit" value="검색" class="btn btn-dark btn-block btn-sm" style="width:100px; float:right;"></td>
					</tr>
                     </tbody>
                  </table>
                  
                     
                     
                  </div>
                  <div class="col-2"></div>
               </div>
            </form>


	<div class="row"> <!-- 테이블 -->
				<div class="col">
					<table class="table table-hover shadow-sm p-3 mb-5 bg-white rounded">
						<thead class="shadow-none p-3 mb-5 bg-light rounded">
							<tr style="text-align:center">
							<td>번호</td><td>매장명</td><td>담당자</td><td>주소</td><td>연락처</td><td></td><td></td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="aaaa" items="${boardList_store_main }" varStatus="xxx">
							<tr style="text-align:center">
							<th scope="row">${xxx.index+1 }</th>
								<td><a href="${pageContext.request.contextPath }/management/read_store_page.do?branch_no=${aaaa.branchVo.branch_no }&emp_code=${aaaa.employeeVo.emp_code }">${aaaa.branchVo.branch_name }</a></td>
								<td>${aaaa.employeeVo.emp_name }</td>
								<td>${aaaa.branchVo.branch_address }</td>
								<td>${aaaa.branchVo.branch_phone }</td>
								<td><a class="btn btn-outline-primary btn-sm-block" href="store_modify.do?branch_no=${aaaa.branchVo.branch_no}">수정</a></td>
								<td>
								<c:if test="${aaaa.employeeVo.emp_no==0}">
								<a class="btn btn-primary btn-sm-block" href="store_modify.do?branch_no=${aaaa.branchVo.branch_no}">담당자 등록</a>
								</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
					</table>
				</div>
			</div>
			
						<div class="row"> <!-- 버튼들... -->
				<div class="col"></div>
				<div class="col"> <!-- 페이지 버튼 -->
					<div class="row d-inline-flex ml-4 pl-4">
					<div class="col d-inline-flex">
					
					<nav aria-label="Page navigation example" class="d-inline-flex">
					<ul class="pagination d-inline-flex">
					  
					  
						<li class="page-item<c:if test="${beginPage-1 <= 0}"> active</c:if>">
					    <a class="page-link" href="./store_view.do?dept_no=${param.dept_no}&branch_no=${param.branch_no}&emp_name=${param.emp_name}&branch_address=${param.branch_address}&curr_page=${beginPage-1 }">이전</a></li>
					  	<c:forEach begin="${beginPage }" end="${endPage }" var="i">
					  		<li class="page-item<c:if test="${curr_page==i}"> active</c:if>">
					  		<a class="page-link" href="./store_view.do?dept_no=${param.dept_no}&branch_no=${param.branch_no}&emp_name=${param.emp_name}&branch_address=${param.branch_address}&curr_page=${i}">${i}</a></li>
					  	</c:forEach>
					    <li class="page-item<c:if test="${endPage+1 >= (totalCount-1)/10+1 }"> active	</c:if>">
					    <a class="page-link" href="./store_view.do?dept_no=${param.dept_no}&branch_no=${param.branch_no}&emp_name=${param.emp_name}&branch_address=${param.branch_address}&curr_page=${endPage+1 }">다음</a></li>
					  <!-- 
					    <li class="page-item disabled"><a class="page-link" href="#">이전</a></li>
					    <li class="page-item active"><a class="page-link" href="#">4</a></li>
				     -->
						
				

					  </ul>
			
					</nav>
					
					</div>
					
					</div>
					
					</div>
				<div class="col">
				<div class="col ml-5 text-center">
				<a class="btn btn-primary btn-sm-block" href="${pageContext.request.contextPath }/management/store_add.do">신규 매장 등록</a>
				</div>
				</div>
			</div></c:if>





            </div>        
        </div>
        <!--메인기능 내용끝-->

       </div>
       </div>
         <!--  메인기능 나오는 곳 끝 -->

         <div class="col-1" style="background-color: #f9f9fa"></div>

      </div>

      <!--  footer  -->
      <jsp:include page="../commons/footer.jsp"></jsp:include>
   </div>
<jsp:include page="../commons/alert_unread_message.jsp"></jsp:include>
   <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
      integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
      crossorigin="anonymous"></script>
   <script
      src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
      integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
      crossorigin="anonymous"></script>
   <script
      src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
      integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
      crossorigin="anonymous"></script>
</body>
</html>