<%@ page language="java" contentType="text/html" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>notice update</title>
<link rel="stylesheet" href="/resources/css/notice.css" />
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript" src="/resources/js/notice_update.js"></script>
</head>
<body>
	<div class="header">header</div>
	<div class="nav">navigator</div>
	<div class="content">
	<div class="write_gap"></div>
	<div class="write_insert">
		<form name="updateForm" method="post" action="/servlet/update_notice">
		<input class="write_edit" type="text" id="idnotice" name="idnotice" value="${notice.idnotice}" placeholder="" title="번호" readonly/>
		<input class="write_edit" type="text" id="subject" name="subject" value="${notice.subject}" placeholder="" title="제목"/>
		<input class="write_edit" type="text" id="content" name="content" value="${notice.content}" placeholder="" title="내용"/>
		<input class="write_button" type="button" id="updateNotice" name="updateNotice" value="확인" />
		</form>
	</div>
	<div class="write_gap"></div>
	</div>
	<div class="footer">footer</div>
</body>
</html>