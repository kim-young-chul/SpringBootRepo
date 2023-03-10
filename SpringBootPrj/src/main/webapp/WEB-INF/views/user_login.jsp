<%@ page language="java" contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>user login</title>
<link rel="stylesheet" href="/resources/css/notice.css" type="text/css" />
<script type="text/javascript" src="/resources/js/user_login.js"></script>
<script type="text/javascript" src="/resources/js/lib/hybrid-crypto.min.js"></script>
</head>
<body>
	<input type="hidden" id="base64PublicKey" name="base64PublicKey" value="${base64PublicKey}">
	<form name="loginForm" method="post" action="/servlet/login_confirm" autocomplete="off">
		<input class="write_edit" type="text" id="userid" name="userid" title="아이디" placeholder="" /> 
		<input class="write_edit" type="password" id="userpw" name="userpw" title="비밀번호" placeholder="" />
		<input class="write_button" type="button" id="userLogin" name="userLogin" value="로그인" />
	</form>
</body>
</html>
