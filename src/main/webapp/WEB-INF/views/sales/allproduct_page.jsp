<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

<script type="text/javascript">
	
	function numberFormat(inputNumber) {
	   return inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	
	function sendData(){

		const urlParams = new URLSearchParams(window.location.search);
		
		var product_no = urlParams.get("product_no");
		var product_type_no = urlParams.get("product_type_no");
		var product_name = urlParams.get("product_name");
		var sort= document.getElementById("sort").value;

		
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
	            for(pro of proList){
	        	

	            	
	            	innerHtml +="<tr>";
	                var prono= pro.productVo.product_no;
	                innerHtml += "<td>"+prono+"</td>";
	                var protype=pro.productTypeVo.product_type_name;
	                innerHtml += "<td>"+protype+"</td>";
            		var proname= pro.productVo.product_name;
            		
            		innerHtml += "<td><a href=\"./read_product_page.do?product_no="+prono+"\">" +proname+"</a></td>";
            		var prodetail= pro.productVo.product_detail;
            		innerHtml += "<td>"+prodetail+"</td>";
            		var proprice = numberFormat(pro.productVo.product_price); 
            		innerHtml += "<td>"+proprice+"</td>";
            		innerHtml+="</tr>";
	            }
				
	            //$('#prolist > tbody:last').html(innerHtml); 
	            document.getElementById("tbody").innerHTML = innerHtml;
	            
	      		//아래 있는....페이지 링크 수정....
	          	var xxx= document.getElementsByClassName('ttt');
	          	for(var i=0; i<xxx.length; i++){
	         		var x= xxx[i].href.substring(0,60);
	         		if(product_no==null)
	         			 product_no="";
	         		x+="&product_no="+product_no;
	         		if(product_type_no==0)
	      	    	  product_type_no="0";
	         		x+="&product_type_no="+product_type_no;
	         		 if(product_name==null)
	       	    	  product_name="";
	         		x+="&product_name="+product_name;
	         		x+="&sort="+sort;
	          		xxx[i].href= x;
	          	}
	           // + sort;
	         }                                 
	                           
	      };
	      
	      
	      var requestUrl = "${pageContext.request.contextPath }/sales/allproduct_search_process.do?";
	      if(product_no==null)
	    	  product_no="";
	      requestUrl += "product_no=" + product_no;
	      if(product_type_no==null)
	    	  product_type_no="";
	      requestUrl += "&product_type_no=" + product_type_no;
	      if(product_name==null)
	    	  product_name="";
	      requestUrl += "&product_name=" + product_name;
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

    function sendprotype(){

		const urlParams = new URLSearchParams(window.location.search);
		
		var protype= document.getElementById("product_type_no").value;
		var sort=1;
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
	            for(pro of proList){
	        	
	            	
	            	innerHtml +="<tr>";
	                var prono= pro.productVo.product_no;
	                innerHtml += "<td>"+prono+"</td>";
	                var protype=pro.productTypeVo.product_type_name;
	                innerHtml += "<td>"+protype+"</td>";
            		var proname= pro.productVo.product_name;
            		innerHtml += "<td><a href=\"./read_product_page.do?product_no="+prono+"\">" +proname+"</td>";
            		var prodetail= pro.productVo.product_detail;
            		innerHtml += "<td>"+prodetail+"</td>";
            		var proprice = pro.productVo.product_price; 
            		innerHtml += "<td>"+proprice+"</td>";
            		innerHtml+="</tr>";
	            }
				
	            //$('#prolist > tbody:last').html(innerHtml); 
	            document.getElementById("tbody").innerHTML = innerHtml;
	            
	      		//아래 있는....페이지 링크 수정....
	          	var xxx= document.getElementsByClassName('ttt');
	          	for(var i=0; i<xxx.length; i++){
	      
	          		xxx[i].href= "#";
	          	}
	           // + sort;
	         }                                 
	                           
	      };
	      
	      
	      var requestUrl = "${pageContext.request.contextPath }/sales/allproduct_protype_process.do?";

	      if(protype==null)
	    	  protype="";
	      requestUrl += "&product_type_no2=" + protype;
	      requestUrl += "&sort=" + sort;
	      requestUrl += "&currPage=" + currPage;
	      
	      
	      
	      xmlhttp.open("get",requestUrl, true);                                  
	      xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	      xmlhttp.send();		
	}
    
    function searchprice(price){
		const urlParams = new URLSearchParams(window.location.search);
			
			
			
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
		            for(pro of proList){
		        
		            	innerHtml +="<tr>";
		                var prono= pro.productVo.product_no;
		                innerHtml += "<td>"+prono+"</td>";
		                var protype=pro.productTypeVo.product_type_name;
		                innerHtml += "<td>"+protype+"</td>";
	            		var proname= pro.productVo.product_name;
	            		innerHtml += "<td><a href=\"./read_product_page.do?product_no="+prono+"\">" +proname+"</td>";
	            		var prodetail= pro.productVo.product_detail;
	            		innerHtml += "<td>"+prodetail+"</td>";
	            		var proprice = pro.productVo.product_price; 
	            		innerHtml += "<td>"+proprice+"</td>";
	            		innerHtml+="</tr>";
		            }
					
		            //$('#prolist > tbody:last').html(innerHtml); 
		            document.getElementById("tbody").innerHTML = innerHtml;
		            
		      		//아래 있는....페이지 링크 수정....
		          	var xxx= document.getElementsByClassName('ttt');
		          	for(var i=0; i<xxx.length; i++){
		      
		          		xxx[i].href= "#";
		          	}
		           // + sort;
		         }                                 
		                           
		      };
		      
		      
		      var requestUrl = "${pageContext.request.contextPath }/sales/allproduct_price_process.do?";

		      if(price==null)
		    	  price="";
		      requestUrl += "&price=" + price;
		      requestUrl += "&currPage=" + currPage;
		      
		      
		      
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
                <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">재고관리</h2></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/sales/product_page.do">재고조회</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/sales/allproduct_page.do">제품조회</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/sales/store_in_view_page.do">입고조회</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/sales/store_in_page.do">입고등록</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/sales/store_out_view_page.do">출고조회</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/sales/store_out_page.do">출고등록</a></h5></li>
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
                        <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">제품조회</span></div>
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
                                            <a style="color: gray; font-size: 14px" href="#">재고관리</a>
                                            >
                                            <a style="color: gray; font-size: 14px" href="#">제품조회</a>
                                   
                                        </li>
                                    </ul>             
                                </div>      
                            </div>
                        </div>
                    </div>
                    <!--1. 타이틀, 네비게이터 끝-->

   <form action="./allproduct_page.do" method="get">
    <div class="row">
    <div class="col-1"></div>
    <div class="col">
    
    <table class="table bg-light">
                              <tbody>
                                 <tr>
                                    <th>제품유형</th>
                                    <td>
                                    <select id="product_type_no" name="product_type_no" class="form-control" onChange="sendprotype()">
						                      <option value=0>유형선택</option>
						                      <option value=1>전기밥솥</option>
						                      <option value=2>전자레인지/오븐</option>
						                      <option value=3>가스레인지</option>
						                      <option value=4>믹서기/원액기</option>
						                      <option value=5>커피머신</option>
						                      <option value=6>토스트기</option>
						                      <option value=7>전기포트</option>
						                      <option value=8>전기그릴/팬</option>
						                      <option value=9>식기세척기/살균건조기</option>
						                      <option value=10>정수기</option>
						                      
						         </select>
                                    </td>
                                    <th>제품이름</th>
                                    <td>
                                    <input placeholder="제품이름을 입력해주세요." name="product_name" id = "product_name" type="text" class="form-control">
                                    </td>   
                                 </tr>
                                 <tr>
                                    <th>가격대</th>
                                    <td style="text-align:center" colspan="3"> 
									 <a href="javascript:searchprice(50000);"  style="color:#7393a7">5만원 이하</a> &nbsp;&nbsp;&nbsp;&nbsp;
								   <a href="javascript:searchprice(100000);" style="color:#7393a7">5~10만원</a>	&nbsp;&nbsp;&nbsp;&nbsp;
								    <a href="javascript:searchprice(150000);" style="color:#7393a7">10~20만원</a>		&nbsp;&nbsp;&nbsp;&nbsp;
								      <a href="javascript:searchprice(200001);" style="color:#7393a7">20만원 이상</a>	&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
                                    
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
            <option value=1 selected>제품번호순
            <option value=2>제품이름순
            <option value=3>제품가격높은순
            <option value=4>제품가격낮은순
         </select></div>
   	</div>
 
   </form>
      <div class="row mt-1"> <!-- 테이블 -->
               <div class="col">
                  <table class="table table-hover shadow-sm p-3 mb-5 bg-white rounded" id="prolist">
                     <thead class="shadow-none p-3 mb-5 bg-light rounded">
                        <tr>
                        <td>제품번호</td><td>유형</td><td>제품이름</td><td>제품상세</td>
                        <td>제품가격</td>
                        </tr>
                     </thead>
                     <tbody id ="tbody">
                        <c:forEach items="${product_list}" var="xxx">
                           <tr>
                              <td>${xxx.productVo.product_no}</td>
                              <td>${xxx.productTypeVo.product_type_name}</td>
                              <td><a href="./read_product_page.do?product_no=${xxx.productVo.product_no}">${xxx.productVo.product_name}</td>
                              <td>${xxx.productVo.product_detail}</td>							
                               <td><fmt:formatNumber value="${xxx.productVo.product_price}" pattern="#,###"/></td>								
                                                      
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
                    
                      <li class="page-item<c:if test="${beginPage-1 <= 0}"> disabled</c:if>"><a class="ttt page-link" href="./allproduct_page.do?currPage=${beginPage-1 }&product_no=${param.product_no}&product_name=${param.product_name}&sort=${param.sort}">이전</a></li>
                       <c:forEach begin="${beginPage }" end="${endPage }" var="i">
                          <li class="page-item<c:if test="${currPage==i}"> active</c:if>"><a class="ttt page-link" href="./allproduct_page.do?currPage=${i}&product_no=${param.product_no}&product_name=${param.product_name}&sort=${param.sort}">${i}</a></li>
                       </c:forEach>
                      <li class="page-item<c:if test="${endPage+1 >= (totalCount-1)/10+1 }"> disabled</c:if>"><a class="ttt page-link" href="./allproduct_page.do?currPage=${endPage+1 }&product_no=${param.product_no}&product_name=${param.product_name}&sort=${param.sort}">다음</a></li>
                  
                    </ul>
   
                  </nav>
               </div>
               <div class="col">
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