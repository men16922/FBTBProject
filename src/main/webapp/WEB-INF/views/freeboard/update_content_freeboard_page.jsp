<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<meta charset="UTF-8">
<title>게시글 수정</title>
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
                        <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">게시글 수정</span></div>
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
                    <!--1. 타이틀, 네비게이터 끝-->

                    <form action="${pageContext.request.contextPath }/freeboard/update_content_freeboard_process.do" method="post" enctype="multipart/form-data">
		
	<input type="hidden" value="${freeBoardData.freeBoardVo.freeboard_no }" name="freeboard_no">
	 <!-- 게시글 작성 내용 -->	
	<div class="row mt-5 mb-5">
	<div class="col">
		<!-- 게시글 상세 -->
		<div class="row mb-3 shadow-none p-3 mb-5 bg-white" style="border: 1px solid #7393a7; border-right-color:white; border-left-color:white;">
		<div class="col">
			<div class="row mb-2">
				<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">글번호</div>
				<div class="col-4 pt-2">${freeBoardData.freeBoardVo.freeboard_no }</div>
				<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">작성자</div>
				<div class="col-4 pt-2">${freeBoardData.freeBoardVo.freeboard_writer }</div>
			</div>
			
			<div class="row mb-2">
				<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">직급</div>
				<div class="col-4 pt-2">${freeBoardData.employeeRankVo.rank_name }</div>
				<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">지점명</div>
				<div class="col-4 pt-2">${freeBoardData.branchVo.branch_name }</div>
			</div>

			<div class="row mb-2">
			
				<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">작성일</div>
				<div class="col-4 pt-2">
				<fmt:formatDate value="${freeBoardData.freeBoardVo.freeboard_writedate }" pattern="yy.MM.dd.hh.mm.ss"/>
				</div>
				<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">조회수</div>
				<div class="col-4 pt-2">${freeBoardData.freeBoardVo.freeboard_view }</div>
				
			</div>
			
			<div class="row mb-2">
				<div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">제목</div>
				<div class="col pt-2">
				<input type="text" value="${freeBoardData.freeBoardVo.freeboard_title }" class="form-control" name="freeboard_title">
				</div>
			</div>
			
			<div class="row mb-2">
				<div class="col pt-2 pb-2 bg-light" style="text-align:center; height:auto">내용</div>
			</div>
			
			     <div class="row">
					<c:if test="${!empty freeBoardData.freeBoardFileVoList}">
					<c:forEach items="${freeBoardData.freeBoardFileVoList }" var="fileVo">
						<img src="/upload/${fileVo.freeboard_file_link_path }" width="300" height="300"> 
						<br>	
					</c:forEach>
				</c:if>
				</div>
				
			<div class="row">
				<div class="col">
					<textarea rows="10" cols="80" class="form-control" name="freeboard_content">${freeBoardData.freeBoardVo.freeboard_content }</textarea>
				</div>
				
			</div>
			
		</div>
		</div>
		<!-- 게시글 상세 끝 -->
		

		<!-- 버튼들 -->
		<div class="row">
			<div class="col">
					<!-- multiple -> 다중선택할 수 있음 -->
					<div class="form-group">
					<input type="file" id="exampleFormControlFile1" class="form-control-file" name="board_upload_files" multiple accept="image/*"><br>
					</div>
				</div>
			<div class="col-3">
				<a href="${pageContext.request.contextPath }/freeboard/list_freeboard_page.do"><input type="button" class="btn btn-secondary btn-block" value="목록으로"></a>
			</div>
			<div class="col-3">
			<input type="submit" class="btn btn-primary btn-block" value="수정완료">
			</div>
		</div>		
		
			
	</div>
	</div>

</form>
                        
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