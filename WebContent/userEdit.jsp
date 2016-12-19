<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${editUser.name}の設定</title>
</head>
<body>
<div class="main-contents">

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><font color="#ff0000"><c:out value="${message}" /></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<form action="" method="post"><br />
	<label for="loginId">ログインID</label>
	<input name="loginId" value="${editUser.loginId}" id="loginId"/><br />

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/><br />

	<label for="password_confirm">パスワード(確認用)</label>
	<input name="password_confirm" type="password" id="password_confirm"/><br />

	<label for="name">氏名</label>
	<input name="name" value="${editUser.name}" id="name"/><br />


	<label for="branch">所属</label>
	<select name="branch" size="1">
		<c:forEach var="branch" items="${branches}">
			<c:if test="${editUser.branchId == branch.id}">
			<option value="${branch.id}"selected><c:out value="${branch.name}" /></option></c:if>
			<c:if test="${editUser.branchId != branch.id}">
			<option value="${branch.id}"><c:out value="${branch.name}" /></option></c:if>
		</c:forEach>
	</select><br />

	<label for="department">部署・役職</label>
	<select name="department" size="1">
		<c:forEach var="department" items="${departments}">
			<c:if test="${editUser.departmentId == department.id}">
			<option value="${editUser.departmentId}"selected><c:out value="${department.name}" /></option></c:if>
			<c:if test="${editUser.departmentId != department.id}">
			<option value="${editUser.departmentId}"><c:out value="${department.name}" /></option></c:if>
		</c:forEach>
	</select><br />

	<input type="submit" value="登録" /> <br />
	<a href="administer">戻る</a>
</form>
<div class="copyright">Copyright(c)Ryosuke Ando</div>
</div>
</body>
</html>