<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>발주조회</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
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
<!-- 컨테이너 시작 -->
<div class="container-fluid">
      <!-- 메인 기능들이 나오는 row -->
      <div class="row">
         <div class="col-1" style="background-color: #f9f9fa"></div>

         <!--  사이드바 시작  -->
         <div class="col-2"
            style="padding: 0; border-right: 2px solid #e8ecf1;">
            <ul class="list-group list-group-flush">
               <!--  수정할 부분 시작 -->
               <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">발주관리</h2></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/sales/store_order_reservation_page.do">발주서작성</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/sales/store_order_view_page.do">발주 확인</a></h5></li>
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
                        <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">발주상세내역</span></div>
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
                                            <a style="color: gray; font-size: 14px" href="#">발주관리</a>
                                            >
                                            <a style="color: gray; font-size: 14px" href="#">발주조회</a>
                                            >
                                            <a style="color: gray; font-size: 14px" href="#">발주상세내역</a>
                                        </li>
                                    </ul>             
                                </div>      
                            </div>
                        </div>
                    </div>
                    <!--1. 타이틀, 네비게이터 끝-->

                    <div class="mt-5 px-5 row">
   
	<div class="col mx-5 px-5 bg-light py-3">
		 <div class="row">
		 <div class="col">
		   <form action="./store_order_view_process.do" method="get">
				<div class="row mt-5"> <!-- 테이블 -->
				 <div class="col">
					 <table class="table table-hover shadow-sm p-3 mb-5 bg-white rounded" id="prolist">
						 <thead class="shadow-none p-3 mb-5 bg-light rounded">
							 <tr>
							 <td>발주상세번호</td>
							 <td>제품명</td><td>발주수량</td><td>발주상태</td><td>승인여부</td><td>발주취소</td>
							 </tr>
						 </thead>
						 <tbody id="tbody">
					  <c:forEach items="${read_detail_list}" var="xxx">
								 <tr>
									 <td>${xxx.storeOrderDetailVo.store_order_detail_no}
							
									 </td>
									 
									 <td>${xxx.productVo.product_name}</td>						
									 <td>${xxx.storeOrderDetailVo.first_order_qty}</td>
									 <c:if test="${xxx.processStatusVo.flag eq 'Y'.charAt(0)}">																										
									 <td>${xxx.process_listVo.process_name}</td>
									 </c:if>
									 <c:if test="${xxx.processStatusVo.flag eq 'N'.charAt(0)}">																										
									 <td>발주취소</td>
									 </c:if>
									 <td>${xxx.processStatusVo.flag}</td>
									 <c:if test="${xxx.process_listVo.process_no==1 and xxx.processStatusVo.flag eq 'Y'.charAt(0)}">
									 <td><input type="checkbox" name="check" value="${xxx.processStatusVo.process_status_no}"></td>	
									 </c:if>
								 </tr>
								 </c:forEach>
						 </tbody>
					 
					 </table>
				 </div>
			 </div>
					
				<div class="row mt-3"> <!-- 버튼들... -->
				 
				 <div class="col">
				 </div>
				 <div class="col">
					 <input type="submit" value="발주취소" class="btn btn-primary btn-block" >
				 </div>
			 </div>
			 </form>
			 </div>
 
				
				
				
				</div>
				 
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
   
   




<!-- 컨테이너 끝 -->






<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>