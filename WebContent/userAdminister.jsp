<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理</title>
</head>
<body>
<div class="main-contents">
	<div class="header">
		<a href="signup">ユーザー新規登録</a>

	</div>

	<br /><br />
		<div class="users">
		<table border="2" bgcolor="#cccccc">
				<tr>
				<th width="200">ログインID</th>
				<th width="200">氏名</th>
				<th width="200">ユーザー編集</th>
				<th width="200">アカウント</th>
				</tr>
				</table>
			<c:forEach items="${users}" var="user">
			<table border="1">
				<tr>
				<td align="center" width="200"><div class="login-id"><c:out value="${user.loginId}" /><br /></div></td>
				<td align="center" width="200"><div class="name"><c:out value="${user.name}" /></div></td>
				<td align="center" width="200"><div class="edit"><a href="http://www.yahoo.co.jp" target="_blank">編集する</a></div></td>
				<td align="center" width="200"><input type="submit" value="停止">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="起動"></td>
				</tr>
				</table>
			</c:forEach>

		</div>
		<br />
		<a href="./">戻る</a>
</div>
<div class="copyright">Copyright(c)Ryosuke Ando</div>
</body>
</html>