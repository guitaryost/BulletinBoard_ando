<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
</head>
<body>
<div class="main-contents"></div>


<form action="login" method="post"><br />
<div class="header">
	<div><center><font size="5">【掲示板】</font></center></div>
	<br />
</div>
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul style ="list-style:none;">
			<c:forEach items="${errorMessages}" var="message">
				<li><center><FONT color="#ff0000"><c:out value="${message}" /></FONT></center>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
	<center>ログインID<input name="loginId" value="${loginId}" id="loginId" /></center>
<br />
	<center>パスワード<input  name="password" type="password" id="password"  /></center>
<br />
<br />
	<c:remove var="loginId" scope="session"/>
	<center><input type="submit" value="ログイン" /></center><br />
</form>
<div class="copyright"><center>Copyright(c)Ryosuke Ando</center></div>
</body>
</html>