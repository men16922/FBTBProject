<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script type="text/javascript">
	function getFormatDate(date){
	    var year = date.getFullYear().toString(); 
		year=year.substring(2,4);              //yyyy
	    var month = (1 + date.getMonth());          //M
	    month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
	    var day = date.getDate();                   //d
	    day = day >= 10 ? day : '0' + day;          //day 두자리로 저장
	    
	    var hours = date.getHours(); // 시
	    hours = hours >= 10 ? hours : '0' + hours;
	    var minutes = date.getMinutes();  // 분
	    minutes = minutes >= 10 ? minutes : '0' + minutes;
	    var seconds = date.getSeconds();  // 초
	    seconds = seconds >= 10 ? seconds : '0' + seconds;
	    return  year + '-' + month + '-' + day + '&nbsp&nbsp&nbsp&nbsp&nbsp' + hours + ':' + minutes + ':' + seconds;       //'-' 추가하여 yyyy-mm-dd 형태 생성 가능
	}
   
   function sendData(){

      const urlParams = new URLSearchParams(window.location.search);
      
      var store_order_res_no = urlParams.get("store_order_res_no");
      var emp_name = urlParams.get("emp_name");
      var store_order_start_date = urlParams.get("store_order_start_date");
      var store_order_final_date = urlParams.get("store_order_final_date");
      var sort = document.getElementById("sort").value;
      var currPage = urlParams.get("currPage");
      if(currPage==null)
         currPage=1;
      
         var xmlhttp = new XMLHttpRequest();
         xmlhttp.onreadystatechange =  function(){
                              
            if(xmlhttp.readyState==4 && xmlhttp.status == 200){
               
               
               
               //JSON 형태로 받은 리스트
               
               var proList = JSON.parse(xmlhttp.responseText);
        
             for(var i=0; i<$('#prolist tr').length-1; i++){   
                     f_del_file();
               }  
             
       
             var innerHtml="";
             var i=0;
               for(pro of proList){

            	  
                  innerHtml +="<tr class=\"www\">";
                  
                   var storeorderresno= pro.storeOrderReservationVo.store_order_res_no;
                   
                   innerHtml += "<td><a href=\"javascript:remove_tr("+i+"); javascript:add_tr('"+storeorderresno+"',"+i+");\">" + storeorderresno + "</a></td>";
                   
                 
                   var prodate= new Date(pro.storeOrderReservationVo.store_order_res_date);
                   prodate= getFormatDate(prodate);
                   innerHtml += "<td>"+prodate+"</td>";
                  var empname= pro.employeeVo.emp_name;
                  innerHtml += "<td>"+empname+"</td>";
                  
            
                  innerHtml+="</tr>";
                  i+=1;
               }
            
        
               document.getElementById("tbody").innerHTML = innerHtml;
               
               var xxx= document.getElementsByClassName('ttt');
               for(var i=0; i<xxx.length; i++){
                  var x= xxx[i].href.substring(0,66);
                  if(store_order_res_no==null)
                     store_order_res_no="";
                  x+="&store_order_res_no="+store_order_res_no;
                  if(emp_name==null)
                     emp_name="";
                  x+="&emp_name="+emp_name;
                  if(store_order_start_date==null)
                     store_order_start_date="";
                  x+="&store_order_start_date="+store_order_start_date;
                  if(store_order_final_date==null)
                     store_order_final_date="";
                  x+="&store_order_final_date="+store_order_final_date;
                  x+="&sort="+sort;
                   xxx[i].href= x;
                }
         
            }                                 
                              
         };
         
         
         var requestUrl = "${pageContext.request.contextPath}/sales/storeorderview_search_process.do?";
         if(store_order_res_no==null)
            store_order_res_no="";
         requestUrl += "store_order_res_no=" + store_order_res_no;
         if(emp_name==null)
            emp_name="";
         requestUrl += "&emp_name=" + emp_name;
         if(store_order_start_date==null)
            store_order_start_date="";
         requestUrl += "&store_order_start_date=" + store_order_start_date;
         if(store_order_final_date==null)
            store_order_final_date="";
         requestUrl += "&store_order_final_date=" + store_order_final_date;
         requestUrl += "&sort=" + sort;
         requestUrl += "&currPage=" + currPage;
         
         
         
         xmlhttp.open("get",requestUrl, true);                                  
         xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
         xmlhttp.send();      
      
   
   
      
         

   }
   
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
   
   
   function fn_spread(i)
   {
	
	   if($(".prolist2").eq(i).is(":visible")){
           $(".prolist2").eq(i).css("display","none");
       }
       else{
           $(".prolist2").eq(i).css("display","block");
       }
   
   }
   
   function remove_tr(i){
	   var qqq="qqq"+(i);
	  //$("#tbody").find(".qq").remove();
	   //$("#tbody").find("."+qqq).remove();
   }
   
   function add_tr(store_order_res_no,i)
   {
	   

	
	   var qqq="qqq"+i;
	   var innerHtml="<tr class=\"bg-light qq\" style=\"font-weight:bold;\"><td>발주상세번호</td><td>제품명</td><td>발주수량</td><td>발주상태</td><td>승인여부</td></tr>"
		
		   const urlParams = new URLSearchParams(window.location.search);
	      
	      var store_order_res_no = store_order_res_no;
			
	      
	         var xmlhttp = new XMLHttpRequest();
	         xmlhttp.onreadystatechange =  function(){
	                              
	            if(xmlhttp.readyState==4 && xmlhttp.status == 200){
	   
	            	var proList = JSON.parse(xmlhttp.responseText);
	            		
	            	
	            	
	                for(pro of proList){

	                   
	                	innerHtml +="<tr class=\"bg-light qq\" \">";
	                
	                   var storeorderdetailno= pro.storeOrderDetailVo.store_order_detail_no;
	                    innerHtml += "<td><a style=\"color:orange\" href=\"${pageContext.request.contextPath}/order/read_detail_order_page.do?store_order_detail_no="+storeorderdetailno+"\">"+storeorderdetailno+"</td>";
	             
	                   var proname= pro.productVo.product_name;
	                   innerHtml += "<td>"+proname+"</td>";
	                   if(pro.processManagementVo!=null){
	                   var cqty=pro.processManagementVo.confirm_qty;
	                   if(cqty!=null){
	                	   innerHtml += "<td>"+cqty+"</td>";
	                   }
	                   }
	                   else{
	                   var qty= pro.storeOrderDetailVo.first_order_qty;
	                   innerHtml += "<td>"+qty+"</td>";
	                   }
	                   var flag=pro.processStatusVo.flag;
	                   var process=pro.process_listVo.process_name;
	                   if(flag=='Y')
	                	{
	                	   innerHtml += "<td>"+process+"</td>";
	               		}
	                   else{
	                	   innerHtml += "<td>발주취소</td>";
	                   }
	                   innerHtml += "<td>"+flag+"</td>";
	                   innerHtml+="</tr>";
	                }
	               
	          
	                //$('#tbody > tr').eq(i).after(innerHtml);
	                
	                
	                
	                if($('.www').eq(i).next().attr("class") != 'bg-light qq')	                
	                	$('.www').eq(i).after(innerHtml);
	                          
	                
	                
	                
	            }                                 
	                              
	         };
	         
	         
	         var requestUrl = "${pageContext.request.contextPath}/sales/storeorderview_detail.do?";
	         if(store_order_res_no==null)
	            store_order_res_no="";
	         requestUrl += "store_order_res_no=" + store_order_res_no;
	
	         
	         
	         xmlhttp.open("get",requestUrl, true);                                  
	         xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	         xmlhttp.send();      
	   
	   
	   
	   
   }
   
   function sendData2(store_order_res_no,i){

	      const urlParams = new URLSearchParams(window.location.search);
	      
	      var store_order_res_no = store_order_res_no,i;
			
	      
	         var xmlhttp = new XMLHttpRequest();
	         xmlhttp.onreadystatechange =  function(){
	                              
	            if(xmlhttp.readyState==4 && xmlhttp.status == 200){
	   
	            	var proList = JSON.parse(xmlhttp.responseText);
	             
	            	var innerHtml="";
	                for(pro of proList){

	                   innerHtml +="<tr>";
	                   
	                
	                   var storeorderdetailno= pro.storeOrderDetailVo.store_order_detail_no;
	                    innerHtml += "<td><a href=\"${pageContext.request.contextPath}/order/read_detail_order_page.do?store_order_detail_no="+storeorderdetailno+"\">"+storeorderdetailno+"</td>";
	             
	                   var proname= pro.productVo.product_name;
	                   innerHtml += "<td>"+proname+"</td>";
	                   if(pro.processManagementVo!=null){
	                   var cqty=pro.processManagementVo.confirm_qty;
	                   if(cqty!=null){
	                	   innerHtml += "<td>"+cqty+"</td>";
	                   }
	                   }
	                   else{
	                   var qty= pro.storeOrderDetailVo.first_order_qty;
	                   innerHtml += "<td>"+qty+"</td>";
	                   }
	                   var flag=pro.processStatusVo.flag;
	                   var process=pro.process_listVo.process_name;
	                   if(flag=='Y')
	                	{
	                	   innerHtml += "<td>"+process+"</td>";
	               		}
	                   else{
	                	   innerHtml += "<td>발주취소</td>";
	                   }
	                   innerHtml += "<td>"+flag+"</td>";
	                   innerHtml+="</tr>";
	                }
	             
	         
	               // document.getElementsByClassName("tbody2")[i].innerHTML = innerHtml;
	                
	            }                                 
	                              
	         };
	         
	         
	         var requestUrl = "${pageContext.request.contextPath}/sales/storeorderview_detail.do?";
	         if(store_order_res_no==null)
	            store_order_res_no="";
	         requestUrl += "store_order_res_no=" + store_order_res_no;
	
	         
	         
	         xmlhttp.open("get",requestUrl, true);                                  
	         xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	         xmlhttp.send();      
	      
	   
	   
	      
	         

	   }
   </script>

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
                        <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">발주 확인</span></div>
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
                                            <a style="color: gray; font-size: 14px" href="#">발주확인</a>
                                          
                                        </li>
                                    </ul>             
                                </div>      
                            </div>
                        </div>
                    </div>
                    <!--1. 타이틀, 네비게이터 끝-->

    <form action="./store_order_view_page.do" method="get">
    <div class="row">
    <div class="col-1"></div>
    <div class="col">
    
    <table class="table bg-light">
                              <tbody>
                                 <tr>
                                    <th>발주번호</th>
                                    <td>
                                    <input placeholder="발주번호를 입력해주세요." name="store_order_res_no" type="text" class="form-control" size="30">
                                    </td>
                                    <th>처리사원</th>
                                    <td>
                                    <input placeholder="처리사원을 입력해주세요." id = "emp_name" name="emp_name" type="text" class="form-control">
                                    </td>   
                                 </tr>
                                 <tr>
                                    <th>발주일자</th>
                                    <td scope="col"><input placeholder="입고날짜를 입력해주세요."
                                       name="store_order_start_date" id="store_order_start_date" type="date"
                                       class="form-control form-control-sm"></td>
                                    <td colspan="2"><input placeholder="입고날짜를 입력해주세요."
                                       name="store_order_final_date" id="store_order_final_date" type="date"
                                       class="form-control form-control-sm" style="width:300px;"></td>
                                    
                                 </tr>
                                 
                                 <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><input type="submit" value="검색"
                                       class="btn btn-dark btn-block btn-sm" style="width:100px; float:right;"></td>
                                 </tr>
                              </tbody>
                           </table>
    
    
    </div> 
    <div class="col-1"></div>
    </div>
    

   <div class="row mt-5">
   	<div class="col"></div>
   	<div class="col-3">정렬
   		<select class="mt-1" name="sort" id="sort" onchange="sendData()">
           <option value=1 selected>발주번호순
            <option value=2>발주날짜빠른순
            <option value=3>발주날짜느린순
         </select></div>
   	</div>
   	</form>
   
      <div class="row">
      <div class="col">
   		
      <div class="row"> <!-- 테이블 -->
               <div class="col">
                  <table class="table table-hover shadow-sm p-3 mb-5 bg-white rounded" id="prolist">
                     <thead class="shadow-none p-3 mb-5 bg-light rounded">
                        <tr>
                        <td>발주번호</td>
                        <td>발주일자</td><td>담당자</td>
                        <td></td><td></td>
                        </tr>
                     </thead>
                     <tbody id="tbody">
                        <c:forEach items="${store_order_viewlist}" var="xxx" varStatus="i">
                           <tr class="www">
                              <td><a href="javascript:remove_tr(${i.index}); javascript:add_tr('${xxx.storeOrderReservationVo.store_order_res_no}',${i.index});">${xxx.storeOrderReservationVo.store_order_res_no}</a>
                           	
                              </td>
                              <td><fmt:formatDate value="${xxx.storeOrderReservationVo.store_order_res_date}" pattern="yy-MM-dd"/> &nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${xxx.storeOrderReservationVo.store_order_res_date}" pattern="HH:mm:ss"/></td>                    
                              <td>${xxx.employeeVo.emp_name}</td>                                                                                 
                          	
                           </tr>
                           
                           </c:forEach>
                     </tbody>
                  
                  </table>
               </div>
            </div>
            
            <div class="row mt-3"> <!-- 버튼들... -->
               <div class="col-8"> <!-- 페이지 버튼 -->
                  <nav aria-label="Page navigation example">
                     <ul class="pagination">
                    
                      <li class="page-item<c:if test="${beginPage-1 <= 0}"> disabled</c:if>"><a class="ttt page-link" href="./store_order_view_page.do?currPage=${beginPage-1 }&store_order_res_no=${param.store_order_res_no}&emp_name=${param.emp_name}&store_order_start_date=${param.store_order_start_date}&store_order_final_date=${param.store_order_final_date}&sort=${param.sort}">이전</a></li>
                       <c:forEach begin="${beginPage }" end="${endPage }" var="i">
                          <li class="page-item<c:if test="${currPage==i}"> active</c:if>"><a class="ttt page-link" href="./store_order_view_page.do?currPage=${i }&store_order_res_no=${param.store_order_res_no}&emp_name=${param.emp_name}&store_order_start_date=${param.store_order_start_date}&store_order_final_date=${param.store_order_final_date}&sort=${param.sort}">${i}</a></li>
                       </c:forEach>
                      <li class="page-item<c:if test="${endPage+1 >= (totalCount-1)/10+1 }"> disabled</c:if>"><a class="ttt page-link" href="./store_order_view_page.do?currPage=${endPage +1 }&store_order_res_no=${param.store_order_res_no}&emp_name=${param.emp_name}&store_order_start_date=${param.store_order_start_date}&store_order_final_date=${param.store_order_final_date}&sort=${param.sort}">다음</a></li>
                    <!-- 
                      <li class="page-item disabled"><a class="page-link" href="#">이전</a></li>
                      <li class="page-item active"><a class="page-link" href="#">4</a></li>
                    -->
                    </ul>
   
                  </nav>
               </div>
               <div class="col">
               </div>
               <div class="col">
                  
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

<jsp:include page="../commons/alert_unread_message.jsp"></jsp:include>
	 <jsp:include page="../sales/sales_alert.jsp"></jsp:include> 
	 <jsp:include page="../sales/sales_alert2.jsp"></jsp:include> 

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>