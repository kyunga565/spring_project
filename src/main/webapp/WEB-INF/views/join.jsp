<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
label{width:150px; float: left;font-weight: bold;}
.btn{border:1px solid black; background-color: white; text-decoration: none;}
#btn{margin:0 auto; width:300px;}
#checkID{text-decoration: none; font-size: 12px; color:green; margin-left: 5px;}
form{margin-left: 100px;}
.check{font-size: 12px; color:red; visibility: hidden;}
</style>
<script type="text/javascript">
$("form[name='f1']").submit(function() {
	if($("input[name='userpw0']").val() != $("input[name='userpw']").val()){
		var $next = $("input[name='userpw']").nextAll(".check").eq(1)
		$next.css("visibility","visible")
		return false
	}
})
</script>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div id="body">
		<form action="join" method="post" name="f1" enctype="multipart/form-data">
			<label for="">ID</label><input type="text" name="userid" /><a href="" id="checkID">중복확인</a> <span class="check"> 사용가능한아이디입니다.</span><br>
			<label for="">NAME</label><input type="text" name="username" /><br>
			<label for="">PW</label><input type="text" name="userpw0" /><br>
			<label for="">PW CONFIRM</label><input type="text" name="userpw" /><span class="check">사용가능한비밀번호입니다.</span><br>
			<label for="">EMAIL</label><input type="text" name="useremail" placeholder="XXX@naver.com"/><br>
			<label for="">TEL</label><input type="text" name="usertel" /><br><br>
			
			<p id="btn">
				<button type="submit" class="btn">가입하기</button>
			</p>
		</form>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>