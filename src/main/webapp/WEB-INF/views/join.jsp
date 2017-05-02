<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div id="body">
		<form action="join" method="post" name="f1" enctype="multipart/form-data">
			<label for="">ID</label><input type="text" name="userid" /><button type="button" id="checkID">중복확인</button>
			<span class="checktrue"> 사용가능한 아이디입니다.</span><span class="checkfalse"> 사용불가능한 아이디입니다.</span><br>
			<label for="">NAME</label><input type="text" name="username" /><br>${userVO.userid }
			<label for="">PW</label><input type="text" name="userpw0" /><br>
			<label for="">PW CONFIRM</label><input type="text" name="userpw" /> <span class="checkpw"> 비밀번호를 확인하세요.</span><br>
			<label for="">EMAIL</label><input type="text" name="useremail" placeholder="XXX@naver.com"/><br>
			<label for="">TEL</label><input type="text" name="usertel" /><br><br>
			
			<p id="btn">
				<button type="submit" class="btn">가입하기</button>
			</p>
		</form>
	</div>
	<jsp:include page="footer.jsp" />
</body>

<style type="text/css">
	#checkID{text-decoration: none; font-size: 12px; color:green; margin-left: 5px; background-color: white;border:none;}
	.checkpw{font-size: 12px; color:red; visibility: hidden;}
	.checktrue{font-size: 12px; color:red; visibility: hidden;}
	.checkfalse{font-size: 12px; color:red; visibility: hidden;}
	form {margin-left: 100px;}
</style>
<script type="text/javascript">
$("form[name='f1']").submit(function() {
	if($("input[name='userpw0']").val() != $("input[name='userpw']").val()){
		var $next = $("input[name='userpw']").nextAll(".checkpw").eq(0)
		$next.css("visibility","visible")
		return false
	}
})
$("#checkID").click(function(e){
	e.preventDefault();
	var id = $("input[name='userid']").val() 
	var result;
	$.ajax({
		url:"idCheck/"+id,
		data:id,
		type:"get",
		dataType:"text",
		success:function(data){
			if(data == "success"){
				result = true 
				$(".checktrue").css("visibility","visible")
			}else{
				result = false
				$(".checkfalse").css("visibility","visible")
			}
		}
	})
	return result
}) 
</script>
</html>