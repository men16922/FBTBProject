<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
   href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
   integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
   crossorigin="anonymous">
<script> //사원번호 생성 스크립트

   
  
function test(){
   var year = new Date().getFullYear().toString(); 
      year=year.substring(2,4);
   var month= (new Date().getMonth() + 1).toString();
   if(month.length==1){
      month="0"+month;
   }
   var day= new Date().getDate().toString();
   if(day.length==1){
      day="0"+day;
   }
   
   var max_storeresno=${max_storeresno +1};
   max_storeresno=max_storeresno.toString();
   
   if(max_storeresno.length==1){
      max_storeresno="00"+max_storeresno;
   }
   else if(max_storeresno.length==2){
      max_storeresno="0"+max_storeresno;
   }
   if(parseInt(max_storeresno)<1000)
      {
      max_storeresno="A"+max_storeresno;
      }
   else{
      max_storeresno="B"+max_storeresno;
   }
   
   var date=max_storeresno.concat(year,month,day);
   document.getElementById("order_no").innerHTML=date;
   
   document.getElementById("store_order_res_no").value=date;

}  
  
</script>
<script type="text/javascript">
/*    function add_div() {
      
      var div1 = document.createElement('div');
      div1.innerHTML = document.getElementById('pro_form').innerHTML;
      div1.setAttribute("style", "margin-top:10px");
      document.getElementById('field1').appendChild(div1);
      
      
      var div2 = document.createElement('div');
      div2.innerHTML = document.getElementById('qty_form').innerHTML;
      div2.setAttribute("style", "margin-top:17px");
      document.getElementById('field2').appendChild(div2);
      
      /* var div3 = document.createElement('div');
      var deletebutton = document.getElementById('delete');
      deletebutton.setAttribute('class', 'delete');
      deletebutton.removeAttribute("style");
      div3.innerHTML = deletebutton.innerHTML;

      document.getElementById('field3').appendChild(div3); 
   } */
   

   
   function del_row(obj){
      if(typeof(obj) == "object") {
         $(obj).closest("tr").remove();
      } else {
         return false;
      }
      
   }
   
   
   function add_table(){
   
    
       var innerHtml ="<th scope=\"row\" class=\"align-middle\" style=\"background-color: #DCDCDC;\">제품</th>";
       innerHtml+="<td  class=\"align-middle\" style=\"border-right:hidden;\">";
       
       
     
       <c:if test="${!empty store_product }">
    
      
       innerHtml+="<select name=\"product_no\" id=\"product_no\" class=\"form-control product_no\"><option value=\"\">제품을 선택해주세요.</option>";
         
       <c:forEach var="data" items="${store_product }">
            innerHtml+="<option value=\"${data.productVo.product_no}\">${data.productVo.product_name}</option>";
         </c:forEach>
         </c:if>
         innerHtml+="</select></td>";
      
         
         innerHtml+="<td class=\"align-middle\" style=\"border-right:hidden;\">";
         innerHtml+="<input placeholder=\"발주수량을 입력해주세요.\" name=\"first_order_qty\" class=\"first_order_qty\"";
         innerHtml+="type=\"text\" class=\"form-control\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\"></td>";
         innerHtml += "<td><input type=\"button\" onclick=\"javascript:del_row(this);\" class=\"btn btn-light btn-light btn-sm\" value=\"삭제하기\"></td>"
         var tr = document.createElement("tr")
         tr.innerHTML= innerHtml;
       document.getElementById("tbody").appendChild(tr);
   }
   
   
   
  /*  function delete_div() {
         
     
        var product_no= document.getElementsByClassName('product_no');
         if(product_no.length==1){
           document.getElementById("delete").setAttribute("style","display:none");
            return;
         }
           document.getElementsByClassName('first_order_qty')[product_no.length-1].remove();
           document.getElementsByClassName('product_no')[product_no.length-1].remove();
        
        
      } */

      function numbercheck(){
         var frm = document.getElementById("frm");
         var checknum = document.getElementById("store_order_res_no").value;
         
         var first_order_qty= document.getElementsByClassName('first_order_qty');
         var product_no= document.getElementsByClassName('product_no');
     
      /*    var emp_rank = ${sessionUser.employeeRankVo.rank_no };
         if(emp_rank >5){
            alert("대리 이상만 발주등록을 할 수 있습니다.");
            return;
         } */
         
         
         if(checknum==0){
            alert("발주번호를 생성하세요");
            return;
         }
         
         for(var i=0; i<product_no.length; i++){
            
           if(first_order_qty[i].value=="" || product_no[i]=="" || Number(first_order_qty[i].value)<=0 || Number(first_order_qty[i].value)>9999999){
            alert("제품과 수량을 선택하세요");
            return;
         }  
         }
         
      frm.submit();
         
         
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
                        <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">발주서 작성</span></div>
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
                                            <a style="color: gray; font-size: 14px" href="#">발주서작성</a>
                                           
                                        </li>
                                    </ul>             
                                </div>      
                            </div>
                        </div>
                    </div>
                    <!--1. 타이틀, 네비게이터 끝-->

          <form id="frm" action="./store_order_reservation_process.do" method="get">
   <div class="row">
    <div class="col-1"></div>
    <div class="col">          
   <table class="table table-bordered bg-light text-center" >
  <thead>
    <tr>
      <th scope="col" class="align-middle" style="background-color: #DCDCDC" > 발주번호</th>
      <th scope="col"><div id="order_no"></div></th>
      <th scope="col" class="align-middle" style="background-color: #DCDCDC;" > 
                   <a href="javascript:test();">발주번호 생성</a>
                   <input type="hidden" id="store_order_res_no" name="store_order_res_no" value=0></th>
      
    </tr>
  </thead>
  <tbody id="tbody">
     
     <tr>
      <th scope="row" class="align-middle" style="background-color: #DCDCDC;">발주자</th>
      <td class="align-middle" style="border-right:hidden;">
           ${sessionUser.employeeVo.emp_name }
      </td>
    <td class="align-middle" style="border-right:hidden;" > </td>
         
    </tr>
  
    <tr>
      <th scope="row" class="align-middle" style="background-color: #DCDCDC;">제품</th>
      <td  class="align-middle" style="border-right:hidden;">
           <c:if test="${!empty store_product }">
                     <select name="product_no" id="product_no" class="form-control product_no">
                        <option value="">제품을 선택해주세요.</option>
                        <c:forEach var="data" items="${store_product }">
                           <option value="${data.productVo.product_no }">
                              ${data.productVo.product_name }</option>
                        </c:forEach>
                     </select>
                  </c:if>
      </td>
   <td class="align-middle" style="border-right:hidden;">
   <input placeholder="발주수량을 입력해주세요." name="first_order_qty" class="first_order_qty"
                     type="text" class="form-control" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
   </td>
         
    </tr>


    
  </tbody>
</table>


</div>
<div class="col-1"></div>
</div>            
            
            
            
         
            
         
         
         
           <%--  <div class="row mt-3" >
               <div class="col-1"></div>
               <div class="col mx-3" id="pro_form">
                  <c:if test="${!empty store_product }">
                     <select name="product_no" id="product_no" class="form-control product_no">
                        <option value="">제품을 선택해주세요.</option>
                        <c:forEach var="data" items="${store_product }">
                           <option value="${data.productVo.product_no }">
                              ${data.productVo.product_name }</option>
                        </c:forEach>
                     </select>
                  </c:if>
             
               </div>
               
               
               
               <div class="col mx-3 mt-1" id="qty_form">
                  <input placeholder="발주수량을 입력해주세요." name="first_order_qty" class="first_order_qty"
                     type="text" class="form-control" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
               </div>
               <div class="col-2"></div>
            </div>
            
            <div class="row" >
                 <div class="col-1"></div>
               <div class="col mx-3" id="field1"></div>
               <div class="col mx-3" id="field2"></div>
               <div class="col-2" id="field3"></div>
            </div> --%>
            
            <div class="row mt-3 ml-5">
               <div class="col">
               <div class="row">
               <div class="col-2"></div>
               <div class="col">
               
               </div>
               <div class="col">
                     
                </div>
                <div class="col-2">
                <input type="button" value="추가하기"
                     class="btn btn-sm btn-outline-secondary btn-lg btn-block text-primary"
                     onclick="add_table(); show();">
                </div>
                <div class="col-1">
                     
                </div>
                </div>
                
               <div class="row">
               <div class="col-2">
               
               </div>
               <div class="col">
                  
               </div>
               
            
                <div class="col-2" >
         
               </div>
               <div class="col-1">
                     
                </div>
               </div>
            </div>
            </div>
         
         
            <div class="row mb-3">
               <div class="col"></div>
               <div class="col"></div>
               <div class="col-2">
               <input type="button" value="발주등록" class="btn btn-primary btn-block" id="frm" onclick="numbercheck()"
                     style="margin-top: 30px;">
               </div>
               <div class="col-1"></div>
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

<div style="display:none">   
<div class="col-1" style="display:none">
<input type="button" value="삭제하기"
 class="btn btn-outline-secondary btn-lg btn-block"
onclick="delete_div()" >
  </div>
  
  
  </div>

<jsp:include page="../commons/alert_unread_message.jsp"></jsp:include>
    <jsp:include page="../sales/sales_alert.jsp"></jsp:include> 
    <jsp:include page="../sales/sales_alert2.jsp"></jsp:include> 
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
      integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
      crossorigin="anonymous"></script>
   <script
      src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
      integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
      crossorigin="anonymous"></script>
   <script
      src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
      integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
      crossorigin="anonymous"></script>
</body>
</html>