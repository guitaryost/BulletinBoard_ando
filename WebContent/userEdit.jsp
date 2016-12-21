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

<form action="" method="post"><br />
	<div><font size="5">【ユーザー編集】</font></div>
	<br />
	<a href="./">TOPに戻る</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="administer">ユーザー管理画面に戻る</a>
	<br />
	<br />
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul style ="list-style:none;">
			<c:forEach items="${errorMessages}" var="message">
				<li><font color="#ff0000"><c:out value="${message}" /></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<table class="userEdit" border="2" bgcolor="#f5f5f5">
  <tr>
    <th><label>ログインID</label></th>
    <td><input name="loginId" value="${editUser.loginId}" id="loginId"/><font size="-1"> (6～20文字の半角英数字)</font></td>
  </tr>
  <tr>
    <th><label>パスワード</label></th>
    <td><input name="password" type="password" id="password"/><font size="-1"> (6～255文字の半角文字<font size="-1" color="red">※記号も入力できます</font>)</font></td>
  </tr>
  <tr>
    <th><label>パスワード(確認用)</label></th>
    <td><input name="password_confirm" type="password" id="password_confirm"/></td>
  </tr>
  <tr>
    <th><label>氏名</label></th>
    <td><input name="name" value="${editUser.name}" id="name"/><font size="-1"> (10文字以下で入力)</font></td>
  </tr>
  <tr>
    <th><label>所属</label></th>
    <td>
    	<c:if test="${editUser.id != loginUser.id}">
    		<select name="branch" size="1">
				<c:forEach var="branch" items="${branches}">
					<c:if test="${editUser.branchId == branch.id}">
						<option value="${branch.id}"selected><c:out value="${branch.name}" /></option>
					</c:if>
					<c:if test="${editUser.branchId != branch.id}">
						<option value="${branch.id}"><c:out value="${branch.name}" /></option>
					</c:if>
				</c:forEach>
			</select>
		</c:if>
		<c:if test="${editUser.id == loginUser.id}">
			<input type='hidden' name='branch' value="${editUser.branchId}">
		</c:if>
	</td>
  </tr>
  <tr>
    <th><label>部署・役職</label></th>
    <td>
    <c:if test="${editUser.id != loginUser.id}">
	    <select name="department" size="1">
			<c:forEach var="department" items="${departments}">
				<c:if test="${editUser.departmentId == department.id}">
				<option value="${editUser.departmentId}"selected><c:out value="${department.name}" /></option></c:if>
				<c:if test="${editUser.departmentId != department.id}">
				<option value="${department.id}"><c:out value="${department.name}" /></option></c:if>
			</c:forEach>
		</select>
	</c:if>
	<c:if test="${editUser.id == loginUser.id}">
		<input type='hidden' name='department' value="${editUser.departmentId}">
	</c:if>
	</td>
  </tr>
 </table>
<br />
	<input type="submit" value="編集する" />
<br />
<br />

</form>
<div class="copyright">Copyright(c)Ryosuke Ando</div>
</div>
</body>
</html>