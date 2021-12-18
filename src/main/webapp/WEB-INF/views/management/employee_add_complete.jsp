<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<script language="JavaScript">

history.forward(1)

</script>
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
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">사원등록</span></div>
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
                                        <a style="color: gray; font-size: 14px" href="${pageContext.request.contextPath }/management/employee_add.do">사원등록</a>
                                        >
                                        <a style="color: gray; font-size: 14px" href="#">사원등록 완료</a>
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


<div class="mt-5"></div>
<c:if test="${!empty sessionUser && sessionUser.deptTypeVo.dept_type_no==1}">

<form action="./employee_add_process.do" method="post">
<h3 class="ml-3">${param.emp_name} 님 사원 등록 완료</h3>
<div class="mt-5 px-5 row">
	
	<div class="col mx-5 px-5 bg-light py-3">
	
	
	<div class="row mt-1">
	<div class="col-2 mx-3">
	<h4>사원번호</h4>
	</div>
	<div class="col mx-3">
	<h4>${param.emp_no }</h4>
	</div>
	
	</div>

	<div class="row mt-1">
	<div class="col-2 mx-3">
	<h4>비밀번호</h4>
	</div>
	<div class="col mx-3">
	<h4>${param.emp_residentnum }</h4>
	</div>
	
	</div>
	
	
	<div class="row mt-3">
	<div class="col mx-3">
	
	</div>
	<div class="col mx-3">
	
	</div>
	<div class="col mx-3">
	<br><br><br>
		<a href="./employee_add_complete_mailing_process.do?emp_no=${param.emp_no }&emp_email=${param.emp_email }&emp_residentnum=${param.emp_residentnum }" class="btn btn-primary btn-block">📩  이메일 전송</a>
	</div>
	<br>
	<br>
	
	</div>
	
	
	
	</div>
	
	</div>
	</form></c:if>
	
	<br>
	<br>

</div><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

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