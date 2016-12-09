<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー登録</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
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
<form action="signup" method="post"><br />

	<label for="login_id">ログインID</label>
	<input name="login_id" type="text" id="login_id"/> <br />

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/> <br />

	<label for="name">名前</label>
	<input name="name" value="${signupUser.name}" id="name"/><br />

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
	<a href="administer">戻る</a>

</form>

<div class="copyright">Copyright(c)Ryosuke Ando</div>
</div>
</body>
</html>
