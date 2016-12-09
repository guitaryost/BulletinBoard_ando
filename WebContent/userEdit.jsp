<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${user.name}の設定</title>
</head>
<body>
<div class="main-contents">

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<form action="" method="post" enctype="multipart/form-data"><br />
	<label for="name">氏名</label>
	<input name="name" value="${editUser.name}" id="name"/><br />

	<label for="account">ログインID</label>
	<input name="account" value="${editUser.loginId}" /><br />

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/> <br />

	<label for="branch">所属</label>
	<select name="branch" size="1">
		<c:forEach var="branch" items="${branches}">
			<option value="${branch.id}"><c:out value="${branch.name}" /></option>
		</c:forEach>
	</select><br />

	<label for="department">部署・役職</label>
	<select name="department" size="1">
		<c:forEach var="department" items="${departments}">
			<option value="${department.id}"><c:out value="${department.name}" /></option>
		</c:forEach>
	</select><br />

	<input type="submit" value="登録" /> <br />
	<a href="./">戻る</a>
</form>
<div class="copyright">Copyright(c)Satoshi Kimura</div>
</div>
</body>
</html>