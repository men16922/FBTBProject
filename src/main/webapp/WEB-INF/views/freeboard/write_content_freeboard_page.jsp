<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<meta charset="UTF-8">
<title>게시글 작성</title>
<script type="text/javascript">

function sub_form(){
	
	
	var frm = document.getElementById("frm");
	
	var freeboard_title = document.getElementById("freeboard_title").value;
	var freeboard_content = document.getElementById("freeboard_content").value;

	if(freeboard_title == ""){
		alert("제목을 입력해주세요.");
		return;
	}else if(freeboard_content == ""){
		alert("내용을 입력해주세요.");
		return;
	}else{
		
		frm.submit();
	}


}



</script>



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
                        <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">게시글 작성</span></div>
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

                    <form id="frm" action="${pageContext.request.contextPath }/freeboard/write_content_freeboard_process.do" method="post" enctype="multipart/form-data">
	<!-- 게시글 작성 내용 -->	
   <div class="row mt-5">
	   <div class="col">
			
			<div class="row mb-3">
			   <div class="col">
			   <div class="input-group">
					 <div class="input-group-prepend">
					   <span class="input-group-text">작성자</span>
					 </div>
						 <input class="form-control" type="text" id="text-input" placeholder="${sessionUser.employeeVo.emp_name }" readonly>
					   <input type="hidden" value="${sessionUser.employeeVo.emp_code }" name="emp_code">
						 <input type="hidden" value="${sessionUser.employeeVo.emp_name }" name="freeboard_writer">
			   </div>
			   </div>
		   </div>
   
			
			
	   
	   
		   <div class="row mb-3">
			   <div class="col">
			   <div class="input-group">
					 <div class="input-group-prepend">
					   <span class="input-group-text">제목</span>
					 </div>
					 <input class="form-control" placeholder="30자 이내로 작성해주세요" type="text" id="freeboard_title" name="freeboard_title">
			   </div>
			   </div>
		   </div>
		   
		   
	   
		   
		   <div class="row mb-3">
			   <!-- <div class="col-2 bg-light" style="text-align: center">내용</div> -->
			   <div class="col">
			   <div class="input-group">
					 <div class="input-group-prepend">
					   <span class="input-group-text">내용</span>
					 </div>
					 <textarea class="form-control" rows="10" cols="90" aria-label="With textarea" id="freeboard_content" name="freeboard_content"></textarea>
					 
			   </div>
			   </div>
			   
		   </div>
		   <div class="row">
			 <div class="col">
			 <div class="input-group mb-3">
			   <div class="input-group-prepend">
				 <span class="input-group-text" id="inputGroupFileAddon01">파일업로드</span>
			   </div>
			   <div class="custom-file">
				 <input type="file" class="custom-file-input" id="inputGroupFile01" aria-describedby="inputGroupFileAddon01" name="freeboard_files" multiple accept="image/*">
				 <label class="custom-file-label" for="inputGroupFile01">파일을 선택해주세요</label>
			   </div>
			 </div>
			 </div>
		  </div>            
			
		   
		   <div class="row">
			   <div class="col"></div>
			   <div class="col-3 mt-3 mb-5">
				   <a href="${pageContext.request.contextPath }/freeboard/list_freeboard_page.do"><input type="button" class="btn btn-secondary btn-block" value="목록으로"></a>
			   </div>
			   <div class="col-3 mt-3 mb-5">
				   <input type="button" class="btn btn-primary btn-block" value="작성완료" onclick="sub_form()">
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