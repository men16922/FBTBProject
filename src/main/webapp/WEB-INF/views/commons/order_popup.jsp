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
   
<script type="text/javascript">

   function getFormatDate(date){
       var year = date.getFullYear().toString(); 
      year=year.substring(2,4);              //yyyy
       var month = (1 + date.getMonth());          //M
       month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
       var day = date.getDate();                   //d
       day = day >= 10 ? day : '0' + day;          //day 두자리로 저장
       
       var hours = date.getHours()+9; // 시
       hours = hours >= 10 ? hours : '0' + hours;
       var minutes = date.getMinutes();  // 분
       minutes = minutes >= 10 ? minutes : '0' + minutes;
       var seconds = date.getSeconds();  // 초
       seconds = seconds >= 10 ? seconds : '0' + seconds;
       return  year + '/' + month + '/' + day + '&nbsp&nbsp&nbsp&nbsp&nbsp' + hours + ':' + minutes + ':' + seconds;       //'-' 추가하여 yyyy-mm-dd 형태 생성 가능
   }
   </script>
</head>
<body>
   <br><br>
   <h5>&nbsp;발주 상태 업데이트</h5><br>
   <table class="table table-hover shadow-sm p-3 mb-5 bg-white rounded">
      <thead class="shadow-none p-3 mb-5 bg-light rounded">
         <tr>
            <td class="text-center">발주상세번호</td>
            <td class="text-center">현재상태</td>
            <td class="text-center">승인여부</td>
            <td class="text-center">승인날짜</td>
         </tr>
      </thead>
      <tbody>
         <c:forEach items="${list }" var="data">
            <tr>
               <td class="text-center">${data.processStatusVo.store_order_detail_no }</td>
               <td class="text-center">${data.processListVo.process_name }</td>
               <td class="text-center">${data.processStatusVo.flag }</td>
               <td class="text-center"><fmt:formatDate value="${data.processStatusVo.confirm_date}" pattern="yy-MM-dd"/> &nbsp; &nbsp;<fmt:formatDate value="${data.processStatusVo.confirm_date}" pattern="hh:mm:ss"/></td>
            </tr>
         </c:forEach>
      </tbody>
   </table>
   
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