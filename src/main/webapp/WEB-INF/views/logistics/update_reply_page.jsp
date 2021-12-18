<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글수정</title>

<script type="text/javascript">


	function updateReply(){
		
		const urlParams = new URLSearchParams(window.location.search);
		
		var dept_board_reply_no = urlParams.get("dept_board_reply_no");
		var dept_board_no = urlParams.get("dept_board_no");
		var replyContent = 
			document.getElementById("reply_content").value;
		
		var xmlhttp= new XMLHttpRequest();
		
		xmlhttp.onreadystatechange= function(){
			if(xmlhttp.readyState==4 && xmlhttp.status == 200){
				window.opener.location.reload();
			}
		};
		
		xmlhttp.open("post", "${pageContext.request.contextPath }/logistics/update_reply_process.do", true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("dept_board_reply_no=" + dept_board_reply_no + "&dept_board_reply_content=" +replyContent);
		
		
	}
</script>
</head>
<body>
<div class="row mt-3" > <!--댓글 입력 창-->
		<div class="col-8">
			<textarea id="reply_content" class="form-control"></textarea>
		</div>
	
		<div class="col">
			<input onclick="updateReply()" type="button" class="btn btn-primary" value="수정">
		</div>
		<div class="col">
			<input type="button" value="닫기" onclick="self.close()" class="btn btn-primary">
		</div>

	</div>
</body>
</html>