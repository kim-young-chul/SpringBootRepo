<%@ page language="java" contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>my error page</title>
</head>
<body>
	<h1>my error page ...</h1>

	<h2>timestamp</h2>
	<p>${timestamp}</p>

	<h2>status</h2>
	<p>${status}</p>

	<h2>error</h2>
	<p>${error}</p>

	<h2>message</h2>
	<p>${message}</p>

	<h2>path</h2>
	<p>${path}</p>

	<h2>binding-errors</h2>
	<p>${binding-errors}</p>

	<h2>exception</h2>
	<p>${exception}</p>

	<h2>trace</h2>
	<p>${trace}</p>

</body>
</html>
