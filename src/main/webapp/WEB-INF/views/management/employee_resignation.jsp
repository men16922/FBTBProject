<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<script>
function test5(){
	var sel = document.getElementById("dept_no");
	var dept_no = sel.selectedOptions[0].value;
	
	var xmlhttp = new XMLHttpRequest();
	
	//호출 후 값을 받았을때... 처리 로직....
	xmlhttp.onreadystatechange = function(){
		if(xmlhttp.readyState==4 && xmlhttp.status == 200){
			//alert(xmlhttp.responseText);
			var datas = JSON.parse(xmlhttp.responseText);
			
			var selBranch = document.getElementById("branch_no");
			
			var len = selBranch.childNodes.length;
			
			for(i = 0 ; i < len ; i++){
				selBranch.removeChild(selBranch.childNodes[0]);
				
			}
			var op = document.createElement("option");
			
			op.value = 0;
			op.innerText = "지점선택";
			selBranch.appendChild(op);
			for(data of datas){
				
				var op = document.createElement("option");
				
				op.value = data.branchVo.branch_no;

				op.innerText = data.branchVo.branch_name;
				
				selBranch.appendChild(op);
				
			}
			
		}
	};
	
	xmlhttp.open("get","./test5.do?dept_no=" + dept_no , true);
	xmlhttp.setRequestHeader("Content-type",
	"application/x-www-form-urlencoded");

	xmlhttp.send();	
	
	
	
}


//==================================================================
	   function getFormatDate(date){
       var year = date.getFullYear().toString(); 
      	year=year.substring(-1,4);              //yyyy
       var month = (1 + date.getMonth());          //M
       month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
       var day = date.getDate();                   //d
       day = day >= 10 ? day : '0' + day;          //day 두자리로 저장
       /*
       var hours = date.getHours(); // 시
       hours = hours >= 10 ? hours : '0' + hours;
       var minutes = date.getMinutes();  // 분
       minutes = minutes >= 10 ? minutes : '0' + minutes;
       var seconds = date.getSeconds();  // 초
       seconds = seconds >= 10 ? seconds : '0' + seconds;
       */
       //return  year + '.' + month + '.' + day + '.' + hours + '.' + minutes + '.' + seconds;      
       //'-' 추가하여 yyyy-mm-dd 형태 생성 가능
       return  year + '-' + month + '-' + day;

   }


//================================================================
		function sendData(){

		const urlParams = new URLSearchParams(window.location.search);
		
		//var frm = document.getElementById("frm"); 
		
		var dept_no = document.getElementById("dept_no").value;
		var branch_no = document.getElementById("branch_no").value;
		var rank_no = document.getElementById("rank_no").value;
		var emp_no = urlParams.get("emp_no");
		var emp_name = urlParams.get("emp_name");
		var start_date = document.getElementById("start_date").value;
		var orderby = document.getElementById("orderby").value;
		var curr_page = urlParams.get("curr_page");
	
		if(curr_page==null)
			curr_page=1;
		
	      var xmlhttp = new XMLHttpRequest();
	      xmlhttp.onreadystatechange =  function(){
	                           
	         if(xmlhttp.readyState==4 && xmlhttp.status == 200){
	            
	        	 
	        	 
	            //JSON 형태로 받은 리스트
	            
	            var proList = JSON.parse(xmlhttp.responseText);
	  	
	          for(var i=0; i<$('#prolist tr').length-1; i++){	
	            		f_del_file();
	            }  
	          
	    
	          var innerHtml="";
	            for(pro of proList){

	            	innerHtml +="<tr style='text-align:center'>";
	                var emp_no= pro.employeeVo.emp_no;
	                innerHtml += "<td>"+emp_no+"</td>";
            		var emp_name= pro.employeeVo.emp_name;
            		innerHtml += "<td><a href='./employee_confirm.do?emp_no="+emp_no+"'>"+emp_name+"</td>";
            		
            		var branch_name= pro.branchVo.branch_name;
            		innerHtml += "<td>"+branch_name+"</td>";
            		var rank_name= pro.employeeRankVo.rank_name;	
            		innerHtml += "<td>"+rank_name+"</td>";
            		var start_date= new Date(pro.employeeVo.start_date);
            		var prodate= getFormatDate(start_date);
            		innerHtml += "<td>"+prodate+"</td>";
            		innerHtml+="</tr>";
	            }
	            
	            
	            
	          
        		
				
	            //$('#prolist > tbody:last').html(innerHtml); 
	            document.getElementById("tbody").innerHTML = innerHtml;
	            
	            var xxx= document.getElementsByClassName('ttt');
	            for(var i=0; i<xxx.length; i++){
	         		var x= xxx[i].href.substring(0,53);
	         			         		
	         		var dept_no = document.getElementById("dept_no").value;
	          		var branch_no = document.getElementById("branch_no").value;
	          		var rank_no = document.getElementById("rank_no").value;
	          		var emp_no = urlParams.get("emp_no");
	          		var emp_name = urlParams.get("emp_name");
	          		var start_date = document.getElementById("start_date").value;
	          		var orderby = document.getElementById("orderby").value;
	          		var curr_page = urlParams.get("curr_page");
	          		
	          		
	         		if(dept_no==null)
	         			dept_no="";
	         		x+="dept_no="+dept_no;
	         		if(branch_no==null)
	         			branch_no="";
	         		x+="&branch_no="+branch_no;
	         		x+="&rank_no="+rank_no;
	         		if(emp_name==null)
	         			emp_name="";
	         		x+="&emp_name="+emp_name;
	         		
	         		x+="&emp_no="+emp_no;
	         		if(start_date==null)
	         			start_date="";
	         		x+="&start_date="+start_date;
	         		x+="&curr_page="+curr_page;
	         		x+="&orderby="+orderby;

	          		xxx[i].href= x;
	          	}
	         }                                 
	                           
	      };
	      
	      
	      var requestUrl = "${pageContext.request.contextPath }/management/employee_view_process.do?";

	      requestUrl += "dept_no=" + dept_no;
	      requestUrl += "&branch_no=" + branch_no;
	      requestUrl += "&rank_no=" + rank_no;
	      requestUrl += "&emp_name=" + emp_name;
	      requestUrl += "&emp_no=" + emp_no;
	      requestUrl += "&start_date=" + start_date;
	      requestUrl += "&curr_page=" + curr_page;
	      requestUrl += "&orderby=" + orderby;
	      
	      xmlhttp.open("get",requestUrl, true);                                  
	      xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	      xmlhttp.send();		
		
	
	
	      function f_del_file()
	      {
	         var lo_table = document.getElementById("prolist"); // 테이블지정
	         var li_rows  = lo_table.rows.length; // 테이블 row 개수(Tr의 개수)
	         var li_row_index = li_rows -1; // 테이블 row 즉 Tr의 고유 인덱스를 지정함

	         // tr이 하나도 없을때는 삭제하지 않는다.
	         if(li_row_index >= 0)
	         {
	            lo_table.deleteRow(li_row_index);
	         }  
	      }
	      
	     
	     //frm.submit(); //값을 DB로 전달
	}
	
	
		


</script>
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
               <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">사원관리</h2></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/employee_view.do">사원조회</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/employee_add.do">사원등록</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/employee_resignation.do">퇴사자관리</a></h5></li>
               
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
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">퇴사자 관리</span></div>
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
                                        <a style="color: gray; font-size: 14px" href="#">사원관리</a>
                                        >
                                        <a style="color: gray; font-size: 14px" href="${pageContext.request.contextPath }/management/employee_resignation.do">퇴사자 관리</a>
                                       
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

<form action="./employee_resignation.do" method="post">


            <!-- 테이블 내용 -->
               <div class="row mt-5">
                  <div class="col-1"></div>
                  <div class="col">
                  <table class="table bg-light">
                     <tbody>
                     <tr>
                        <th>부서/지점</th>
                        <td>
		<select id="dept_no" name="dept_no" class="form-control form-control-sm" onchange="test5()">
					    <option value="">부서선택</option>
					    <option value="10" <c:if test="${param.dept_no==10}">selected="selected"</c:if>>관리부</option>
					    <option value="21" <c:if test="${param.dept_no==21}">selected="selected"</c:if>>물류1팀</option>
					    <option value="22" <c:if test="${param.dept_no==22}">selected="selected"</c:if>>물류2팀</option>
					    <option value="23" <c:if test="${param.dept_no==23}">selected="selected"</c:if>>물류3팀</option>
					    <option value="30" <c:if test="${param.dept_no==30}">selected="selected"</c:if>>영업부</option>
					    <option value="40" <c:if test="${param.dept_no==40}">selected="selected"</c:if>>생산부</option>
		</select>			 
                        </td>
                        <td>
		<select id="branch_no" name="branch_no" class="form-control form-control-sm">
				<option value="">지점선택</option>
					    
		</select>
		</td>
                     </tr>
                     
                     <tr>
                        <th>직급/직원명</th>
                        <td>
                   <select name="rank_no" class="form-control form-control-sm">
					    <option value="">직급선택</option>
					    <option value="7" <c:if test="${param.rank_no==7}">selected="selected"</c:if>>사원</option>
					    <option value="6" <c:if test="${param.rank_no==6}">selected="selected"</c:if>>주임</option>
					    <option value="5" <c:if test="${param.rank_no==5}">selected="selected"</c:if>>대리</option>
					    <option value="4" <c:if test="${param.rank_no==4}">selected="selected"</c:if>>과장</option>
					    <option value="3" <c:if test="${param.rank_no==3}">selected="selected"</c:if>>부장</option>
					    <option value="2" <c:if test="${param.rank_no==2}">selected="selected"</c:if>>부사장</option>
					    <option value="1" <c:if test="${param.rank_no==1}">selected="selected"</c:if>>사장</option>
					    
		</select>
		  </td>
                        <td>	<input placeholder="직원명을 입력해주세요." name="emp_name" type="text" class="form-control form-control-sm" value=${param.emp_name }></td>
                     </tr>
                     

                     <tr>
                        <th>사번</th>
                        <td>
                   <input placeholder="사원번호를 입력해주세요." name="emp_no" type="text" class="form-control form-control-sm" value=${param.emp_no }>
		  </td>
                        <td></td>
                     </tr>

	  	<tr>
        <th class="shadow-none p-3 mb-5 bg-light rounded">입사날짜</th>
        <td>
          <input type="date" name="start_date" id="date" class="form-control form-control-sm" value=${param.start_date }>시작일
		</td>
		  
		  <td>
		<input type="date" name="end_date" id="date" class="form-control form-control-sm" value=${param.end_date }>종료일
		</td>
	            
		
       </tr>
					
					
					<tr>
					<th></th>
					<td></td>
					<td><input type="submit" value="검색" class="btn btn-dark btn-block btn-sm" style="width:100px; float:right;"></td>
					</tr>



                     
                     </tbody>
                  </table>
                  
                     <!-- 버튼들 -->
                     <div class="row mt-3 mb-5">
                        <div class="col"></div>
                        <div class="col"></div>
                        <div class="col"></div>
                     </div>
                     
                  </div>
                  <div class="col-1"></div>
               </div>
               
            </form>

	
	<div class="row"> <!-- 테이블 -->
				<div class="col">
					<table class="table table-hover shadow-sm p-3 mb-5 bg-white rounded">
						<thead class="shadow-none p-3 mb-5 bg-light rounded">
							<tr style="text-align:center">
							<td>사원번호</td><td>이름</td><td>부서</td><td>직급</td><td>입사일</td><td></td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="aaaa" items="${boardList_employee_main }">
							<tr style="text-align:center">
								<td>${aaaa.employeeVo.emp_no }</td>
								<td>${aaaa.employeeVo.emp_name }</td>
								<td>${aaaa.branchVo.branch_name }</td>
								<td>${aaaa.employeeRankVo.rank_name }</td>
							<td><fmt:formatDate value="${aaaa.employeeVo.start_date}" pattern="yyyy-MM-dd"/></td>
							<td><a class="btn btn-warning btn-sm-block" 
							href="./employee_resignation_confirm.do?
							emp_code=${aaaa.employeeVo.emp_code }&
							emp_name=${aaaa.employeeVo.emp_name }&
							emp_no=${aaaa.employeeVo.emp_no }">퇴사신청</a></td>
								
								<!-- 날짜 형식 변환 출력 -->
							</tr>
						</c:forEach>
					</tbody>
					
					</table>
				</div>
			</div>
			
		<div class="row"> <!-- 버튼들... -->
				<div class="col"></div>
				<div class="col"> <!-- 페이지 버튼 -->
					<div class="row d-inline-flex ml-4 pl-4	">
					<div class="col d-inline-flex">
					
					<nav aria-label="Page navigation example" class="d-inline-flex">
					<ul class="pagination d-inline-flex">
					  
					      <li class="page-item<c:if test="${beginPage-1 <= 0}"> active</c:if>">
					    <a class="page-link" href="./employee_resignation.do?dept_no=${param.dept_no}&branch_no=${param.branch_no}&rank_no=${param.rank_no}&emp_name=${param.emp_name}&emp_no=${param.emp_no}&start_date=${param.start_date}&curr_page=${beginPage-1 }">이전</a></li>
					  	<c:forEach begin="${beginPage }" end="${endPage }" var="i">
					  		<li class="page-item<c:if test="${curr_page==i}"> active</c:if>">
					  		<a class="page-link" href="./employee_resignation.do?dept_no=${param.dept_no}&branch_no=${param.branch_no}&rank_no=${param.rank_no}&emp_name=${param.emp_name}&emp_no=${param.emp_no}&start_date=${param.start_date}&curr_page=${i}">${i}</a></li>
					  	</c:forEach>
					    <li class="page-item<c:if test="${endPage+1 >= (totalCount-1)/10+1 }"> active</c:if>">
					    <a class="page-link" href="./employee_resignation.do?dept_no=${param.dept_no}&branch_no=${param.branch_no}&rank_no=${param.rank_no}&emp_name=${param.emp_name}&emp_no=${param.emp_no}&start_date=${param.start_date}&curr_page=${endPage+1 }">다음</a></li>
					  <!-- 
					    <li class="page-item disabled"><a class="page-link" href="#">이전</a></li>
					    <li class="page-item active"><a class="page-link" href="#">4</a></li>
				     -->
					  </ul>

					</nav>
					</div>
					</div>
					
				</div>
				<div class="col"></div>
			</div>	</c:if>


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