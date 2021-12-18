<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

   var count2 = 0;
   var isConfirm = false;
   
   
   //x눌렀는지 확인용
   function checkedConfirm(){
      isConfirm = true;
   }
   
   
   var productionMessageAlert = setInterval(function() {
         
         var xmlhttp = new XMLHttpRequest();
         xmlhttp.onreadystatechange = function() {

            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
               
               count2 = xmlhttp.responseText;   
               
               //count 갯수 1이상이면 무조건 알림
               if(count2 >=1 && isConfirm == false){
            	   compCount(count2);   
                  
               }
               
               //count 갯수 1이상이지만 X를 눌렀다면  stop
               if(count2>=1 && isConfirm == true){   
                  stopInterval();
               }
                  
               if(count2 < 1){
                  stopInterval();
               }
            
                  

            }

         };

         xmlhttp.open("post", "${pageContext.request.contextPath }/message/productionMessageAlert.do", true);
         xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
         xmlhttp.send();
       
   }, 1000);
   
   
   
   
   function compCount(option){
               
      stopInterval2();
      
       //div 만들기
       if(!!$("#g_toast_container2")){
          $("#g_toast_container2").remove();
        }
        var optionDefault = {"title": "메시지 알림", "contents": count2}
        option = $.extend(optionDefault, option);
       
        //class="fixed-bottom" 
       var toastDiv='';
        toastDiv += '<div id="g_toast_container2" aria-live="polite" aria-atomic="true" class="position-fixed" style="min-height: 290px; position: absolute; bottom:0; right:0px;">';
        toastDiv += '<div id="g_toast2" class="toast" role="alert" aria-live="assertive" aria-atomic="true">';
        toastDiv += '  <div class="toast-header">';
        toastDiv += '    <i class="fas fa-bell"></i>';
        toastDiv += '    <strong class="mr-auto ml-3">' + "메시지 알림" + '</strong>';
        toastDiv += '    <small class="text-muted ml-3">2 seconds ago</small>';
        toastDiv += '    <button type="button" onclick="checkedConfirm()" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">';
        toastDiv += '      <span aria-hidden="true">&times;</span>';
        toastDiv += '    </button>';
        toastDiv += '  </div>';
        toastDiv += '  <div class="toast-body ml-3">' + "미입고 부품발주가  " + '<span style="color:blue; font-weight: bold">'+option.contents+'</span>' + "  건 있습니다."+"    ";
          toastDiv += '<a href="${pageContext.request.contextPath }/production/none_in_item_page.do"><input type="button" class="btn btn-light btn-sm" value="입고"></a>';
          toastDiv += '  </div>';
        toastDiv += '</div>';
         toastDiv += '</div>';
         
   
       //function 호출(toast보여주는)
         $("body").append(toastDiv);
        // 좌우 중앙에 토스트 윈도우를 위치 시킴
      //   $("#g_toast_container").css({"left": ((($(window).width() - $("#g_toast_container").outerWidth()) / 2) + $(window).scrollLeft() + "px")});
        // 디폴트 옵션에서 delay만 2000 (delay의 디폴트는 500)
      $("#g_toast2").toast({"animation": true, "autohide": true,"delay": 10000000});
      $("#g_toast2").toast('show');
      
      
   
   }

   function stopInterval2() {
      
        clearInterval(productionMessageAlert);
        
   }
   

</script>
