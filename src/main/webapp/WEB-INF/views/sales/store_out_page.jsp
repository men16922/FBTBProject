<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

<script type="text/javascript">
   var paramValueNo = 0;
   function numberFormat(inputNumber) {
	   return inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
   
   function sendData(){
      
      if(paramValueNo <= 0){
         alert("물품을 선택해 주세요!!");
         return;
      }
      
      var paramValueAmount = document.getElementById("out_item_qty").value;
      if(paramValueAmount=="" || Number(paramValueAmount)<=0 || Number(paramValueAmount)>9999999){
 			alert("제품과 수량을 선택하세요");
 			return;
 		}  	
      var frm = document.createElement("form");
      frm.setAttribute("action","./store_out_process.do");
      frm.setAttribute("method","post");
      
      var inputNo = document.createElement("input");
      inputNo.setAttribute("type","hidden");
      inputNo.setAttribute("name","product_no_2");
      inputNo.value = paramValueNo;
      
      frm.appendChild(inputNo);
      
      var inputAmount = document.createElement("input");
      inputAmount.setAttribute("type","hidden");
      inputAmount.setAttribute("name","out_item_qty");
      inputAmount.value = paramValueAmount;
      
      frm.appendChild(inputAmount);
      
      document.body.appendChild(frm);
      
      frm.submit();
   }
   
   

</script>

<script type="text/javascript">

   function sendDeptNo(){
      var inputValue = document.getElementById("product_type_no").value; 
      var xmlhttp = new XMLHttpRequest();
      xmlhttp.onreadystatechange =  function(){
                           
         if(xmlhttp.readyState==4 && xmlhttp.status == 200){
            
            //JSON 형태로 받은 리스트
            var proList = JSON.parse(xmlhttp.responseText);
      
            //셀렉트 박스 불러오기
            var product_no = document.getElementById("product_no");
      
            //팀이름 바꿀 때마다 셀렉트 박스 안에 값들 리무브 차일드 해주기
            var length = product_no.childNodes.length;
            for(var i=0; i<length; i++){
               product_no.removeChild(product_no.childNodes[0]);
            }
       
               
            
             //JSON으로 받아온 리스트(proList) for문 돌려서 옵션태그 추가하기
            for(pro of proList){
               var option = document.createElement("option");
               
               option.innerText = pro.product_name;
               option.value = pro.product_no;
               
               product_no.appendChild(option);
            
            }
            storeoutsearch();
         }                                 
                           
      };
      
                           //컨트롤러에서 이 명령어 처리
      xmlhttp.open("post","${pageContext.request.contextPath }/sales/sendproducttypeno.do", true);                                  
      xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
      xmlhttp.send("product_type_no="+inputValue);

      
   }


</script>

<script type="text/javascript">
   function storeoutsearch(){
      
      var pro_no=document.getElementById("product_no").value;
      
      //var replyContent = 
      //   document.getElementById("product_no").innerText;
      
      
      
       var xmlhttp= new XMLHttpRequest();
      
      xmlhttp.onreadystatechange= function(){
         if(xmlhttp.readyState==4 && xmlhttp.status == 200){
            var proList = JSON.parse(xmlhttp.responseText);
      
                var product_name = proList.productVo.product_name;
                var product_price = proList.productVo.product_price;
                var product_no= proList.productVo.product_no;
                if(proList.productFileVo!=null){
                var productimg= proList.productFileVo.product_file_link_path;
                document.getElementById("productimg").src="/upload/"+productimg;
                }
                else{
                   document.getElementById("productimg").src="${pageContext.request.contextPath}/resources/img/shopping.png";
                }
            document.getElementById("product_name").innerHTML = product_name;
            document.getElementById("product_price").innerHTML = numberFormat(product_price);
            document.getElementById("product_no_2").innerHTML = product_no;
            document.getElementById("product_no_2").value = product_no;
         
            paramValueNo = product_no;
            
             
         }
      };
      
      xmlhttp.open("post", "./sendproductno.do", true);
      xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
      xmlhttp.send("product_no=" + pro_no); 
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
                        <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">출고등록</span></div>
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
                                            <a style="color: gray; font-size: 14px" href="#">출고등록</a>
                                           
                                        </li>
                                    </ul>             
                                </div>      
                            </div>
                        </div>
                    </div>
                    <!--1. 타이틀, 네비게이터 끝-->
	 <div class="row">
    <div class="col-1"></div>
    <div class="col">
    
    <table class="table bg-light">
                              <tbody>
                                 <tr>
                                    <th>제품유형</th>
                                    <th scope="col"><div class="col mx-3" id="protype_form">
										<c:if test="${!empty store_productlist }">
										   <select name="product_type_no" id="product_type_no" class="form-control" onchange="sendDeptNo()">
											  <option value="">제품유형을 선택해주세요.</option>
											  <c:forEach var="data" items="${store_productlist }">
												 <option value="${data.productTypeVo.product_type_no }">
													${data.productTypeVo.product_type_name }</option>
											  </c:forEach>
										   </select>
										</c:if>
								   
									 </div></th>
                                    <th scope="col"><div class="col mx-3" id="pro_form">               
										   <select onchange="storeoutsearch()" name="product_no" id="product_no" class="form-control">
											  <option value="">제품을 선택해주세요.</option>
											  
										   </select>          
									 </div>  
                                 </tr>
                            
                              </tbody>
                           </table>
      
    </div>
   
    </div>
	 
		

 
 <div class="row mb-3">
 <div class="col">
 <div class="mt-5 ml-1 px-1 py-3" >
	<img id="productimg" src="${pageContext.request.contextPath}/resources/img/shopping.png" width="300" height="300" style="vertical-align:middle  overflow: hidden;
			 display: flex;
			 align-items: center;
			 justify-content: center;
			 margin:0px auto;
			 ">
 </div>
 </div>
 
 <div class="col">
 <div class="row mt-5"></div>
 <div class="row mt-5">
 <div class="col" style="background-color:#e8ecf1">
	
 
	
	<div class="row mt-5">
	<div class="col-3 mx-3">
	제품번호
	</div>
	<div id ="product_no_2" class="col mx-3">
	
	</div>
	
	</div>
	
	<div class="row mt-1">
	<div class="col-3 mx-3">
	제품명
	</div>
	<div id ="product_name"  class="col mx-3">
	
	</div>
	
	</div>
	
	
	
	<div class="row mt-1">
	<div class="col-3 mx-3">
	처리사원
	</div>
	<div id="emp_name" class="col mx-3">
	${sessionUser.employeeVo.emp_name}
	</div>
	
	</div>
	
	<div class="row mt-1">
	<div class="col-3 mx-3">
	가격
	</div>
	<div id="product_price" class="col mx-3">
	
	</div>
	
	</div>
	
	<div class="row mt-1">
	<div class="col-3 mx-3">
	수량
	</div>
	<div class="col mx-3">
	<input onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" id="out_item_qty" placeholder="수량을 입력해주세요." name="out_item_qty" type="text" class="form-control">
	</div>
	
	</div>
	
	<div class="row mt-3">
		<div class="col"></div>
		<div class="col-3 mx-3">
		<input type="button" value="출고등록" class="btn btn-primary btn-block" onclick="sendData()">
		</div>
	</div>
	
 
	
	</div>
 	</div>
	
	
	
	
  </div> 
                        
                </div>        
            </div>
        <!--메인기능 내용끝-->

    </div>
         <!--  메인기능 나오는 곳 끝 -->

         

      </div>
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