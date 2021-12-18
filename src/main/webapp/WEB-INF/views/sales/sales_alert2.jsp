<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

   var count3 = 0;
   
   var isConfirm = false;
   
   
   //x눌렀는지 확인용
   function checkedConfirm(){
      isConfirm = true;
   }
   
   var countMessageAlert3 = setInterval(function() {
         
          
          var xmlhttp = new XMLHttpRequest();
          xmlhttp.onreadystatechange = function() {

             if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                
                count3 = xmlhttp.responseText;   
                
                //count 갯수 1이상이면 무조건 알림
                if(count3>=1 && isConfirm == false){
                   sendUnreadCount3(count3);   
                   
                }
                
                //count 갯수 1이상이지만 X를 눌렀다면  stop
                if(count3>=1 && isConfirm == true){   
                   stopInterval3();
                }
                   
                if(count3 < 1){
                   stopInterval3();
                }
             
                   

             }

          };

          xmlhttp.open("post", "${pageContext.request.contextPath }/message/countMessageAlert3.do", true);
          xmlhttp.setRequestHeader("Content-type",
                "application/x-www-form-urlencoded");
          xmlhttp.send();
       
            
    }, 1000);
   
   
    function sendUnreadCount3(option){
       
         stopInterval3();
         
          //div 만들기
          if(!!$("#g_toast_container3")){
             $("#g_toast_container3").remove();
           }
           var optionDefault = {"title": "메시지 알림", "contents": count3}
           option = $.extend(optionDefault, option);

           //class="fixed-bottom" 
          var toastDiv='';
    
           toastDiv += '<div id="g_toast_container3" aria-live="polite" aria-atomic="true" class="position-fixed" style="min-height: 280px; position: absolute; bottom:0; right:0px;">';
           toastDiv += '<div id="g_toast3" class="toast" role="alert" aria-live="assertive" aria-atomic="true">';
           toastDiv += '  <div class="toast-header">';
           toastDiv += '    <i class="fas fa-bell"></i>';
           toastDiv += '    <strong class="mr-auto ml-3">' + "메시지 알림" + '</strong>';
           toastDiv += '    <small class="text-muted ml-3">2 seconds ago</small>';
           toastDiv += '    <button type="button" onclick="checkedConfirm()" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">';
           toastDiv += '      <span aria-hidden="true">&times;</span>';
           toastDiv += '    </button>';
           toastDiv += '  </div>';
           toastDiv += '  <div class="toast-body ml-3">' + "미처리된 입고등록이  " + '<span style="color:blue; font-weight: bold">'+option.contents+'</span>' + "  건 있습니다."+"    ";
           toastDiv += '<a href="${pageContext.request.contextPath }/sales/store_in_page.do"><input type="button" class="btn btn-light btn-sm" value="입고"></a>';
           toastDiv += '  </div>';
           toastDiv += '</div>';
           
            toastDiv += '</div>';
            
      
          //function 호출(toast보여주는)
            $("body").append(toastDiv);
           // 좌우 중앙에 토스트 윈도우를 위치 시킴
          //  $("#g_toast_container").css({"height": ((($(window).height() - $("#g_toast_container").outerWidth()) / 2) + $(window).scrollLeft() + "px")});
           // 디폴트 옵션에서 delay만 2000 (delay의 디폴트는 500)
         $("#g_toast3").toast({"animation": true, "autohide": true,"delay": 10000000});
         $("#g_toast3").toast('show');

      
      }
      
  function stopInterval3() {
         
       clearInterval(countMessageAlert3);
  }
  
  </script>