<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#dropBox {width: 400px; height: 300px; border: 1px dotted gray; overflow: auto;}
#dropBox img{width:100px;height:130px;margin:10px;}
#dropBox div.item{width:120px;float: left;position: relative;margin:5px; border:1px dotted gray;}
#dropBox button.del{position: absolute;right: 3px;top: 3px;font-weight: bold;}
input[type='submit'],input[type='reset']{border:1px solid black; background-color: white; font-size: 12px;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<style type="text/css">
#addpic{font-size: 12px;color:green; text-decoration: none; margin-left: 400px; padding:10px;}
</style>
</head>
<body>
<jsp:include page="header.jsp" />
<div id="body">
	<form action="addAttach" id="form1" method="post" enctype="multipart/form-data">
		<fieldset>
			<input type="file" name="imagefiles" multiple="multiple" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="업로드" />
			<input type="reset" value="취소" />
		</fieldset>
	</form>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>