<%@ page language="java" contentType="text/html" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>notice write</title>
<link rel="stylesheet" href="/resources/css/notice.css" />
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript" src="/resources/js/notice_write.js"></script>
</head>
<body>
	<div class="header">header</div>
	<div class="nav">navigator</div>
	<div class="content">
	<div class="write_gap"></div>
	<div class="write_insert">
		<form name="insertForm" method="post" action="/servlet/insert_notice">
		<input class="write_edit" type="text" id="subject" name="subject" value="" placeholder="" title="제목"/>
		<input class="write_edit" type="text" id="content" name="content" value="" placeholder="" title="내용"/>
		<input class="write_button" type="button" id="insertNotice" name="insertNotice" value="확인" />
		</form>
	</div>
	<div class="write_gap"></div>
	</div>
	<div class="footer">footer</div>
</body>
</html>