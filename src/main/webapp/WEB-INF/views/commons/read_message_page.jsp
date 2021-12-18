<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<meta charset="UTF-8">
<title>쪽지함</title>
</head>
<body>
<!-- navbar -->
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
        	<div class="col-1"></div>
            <!--메인기능 메인col-->
            <div class="col" style="background-color:white">
                <!--1. 타이틀, 네비게이터 시작-->
                <div class="row mt-3">
                    <!--타이틀-->
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">쪽지상세보기</span></div>
                    <div class="col"></div>
                    <!---네비게이터-->
                    <div class="col-6">
                        <div class="row">                                   
                            <div class="col"></div>      
                        </div>
                        <div class="row mb-3"> 
                            <div class="col-3"></div> 
                            <div class="col mt-5" style="display:inline;">     
                                <ul style="list-style:none; margin-top: 10px; margin-bottom: 5px; margin-left: 100px; text-decoration-color: gray">
                                    <li>
                                        <a style="color: gray; font-size: 14px" href="#">쪽지함</a>
                                        >
                                        
                                        <a style="color: gray; font-size: 14px">쪽지상세보기</a>
                                    </li>
                                </ul>             
                            </div>      
                        </div>
                    </div>
                </div>
                <!--1. 타이틀, 네비게이터 끝-->

                <!-- 게시글 작성 내용 -->	
				<div class="row mt-5 mb-5">
				<div class="col">
				
					<!-- 게시글 상세 -->
					<div class="row mb-3 shadow p-3 mb-5 bg-white rounded">
					<div class="col">
					
						<div class="row mb-2">
							<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">보낸사람</div>
							<div class="col-4 pt-2">${contentData.employeeVoSend.emp_name }</div>
							<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">보낸 날짜</div>
							<div class="col-4 pt-2">
							<fmt:formatDate value="${contentData.messageVo.msg_writedate }" pattern="yy.MM.dd.hh.mm.ss"/>
							</div>
						</div>
					    
					    <div class="row mb-2">
							<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">직급</div>
							<div class="col-4 pt-2">${contentData.employeeRankVo.rank_name }</div>
							<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">지점명</div>
							<div class="col-4 pt-2">${contentData.branchVo.branch_name }</div>
						</div>
					
						<div class="row mb-2">
							<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">쪽지 제목</div>
							<div class="col pt-2">${contentData.messageVo.msg_title }</div>
						</div>
						
						<div class="row mb-2">
							<div class="col pt-2 pb-2 bg-light" style="text-align:center; height:auto">쪽지 내용</div>
						</div>
						
						<div class="row">
							<div class="col">
								${contentData.messageVo.msg_content }
							</div>
						</div>
	
					</div>
					</div>
				
				
					<!-- 버튼들 -->
					<div class="row mb-5">
						<div class="col"></div>
				
						<div class="col-2">
							<a href="${pageContext.request.contextPath }/message/reply_message_page.do?msg_no=${contentData.messageVo.msg_no }"><input type="button" class="btn btn-warning btn-block" value="답장"></a>
						</div>
						<c:if test="${contentData.messageVo.receiver == sessionUser.employeeVo.emp_code}">
						<div class="col-2">
							<a href="${pageContext.request.contextPath }/message/delete_message_process.do?msg_no=${contentData.messageVo.msg_no }"><input type="button" style="background-color: #7393a7; text-color: white" class="btn btn-outline-light btn-block" value="삭제"></a>						
						</div>
						</c:if>	
						<div class="col-3">
						    <a href="${pageContext.request.contextPath }/message/list_message_page.do"><input type="button" class="btn btn-secondary btn-block" value="목록으로"></a>
						</div>
					</div>
				
				</div>
				</div>
                    
            </div>    
            <div class="col-1"></div>    
        </div>
        <!--메인기능 내용끝-->

       </div>
         <!--  메인기능 나오는 곳 끝 -->

         <div class="col-1" style="background-color: #f9f9fa"></div>

      </div>

      <!--  footer  -->
      <jsp:include page="../commons/footer.jsp"></jsp:include>
   </div>

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

