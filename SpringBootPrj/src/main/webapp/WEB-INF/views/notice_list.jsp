<%@ page language="java" contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>notice list</title>
<link rel="stylesheet" href="/resources/css/notice.css" type="text/css" />
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript" src="/resources/js/notice_list.js"></script>
</head>
<body>
	<div class="header">header</div>
	<div class="logout">
		<input class="write_button" type="button" id="userLogout" name="userLogout" value="로그아웃" />
	</div>
	<div class="nav">navigator</div>
	<div class="content">
		<table id="notice">
			<caption>게시판</caption>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>내용</th>
					<c:if test="${grade eq 'manager'}">
						<th>글삭제</th>
					</c:if>
				</tr>
			</thead>
			<tbody id="noticeList">
				<c:forEach items="${noticeList}" var="notice">
					<tr>
						<td>${notice.idnotice}</td>
						<td><c:choose>
								<c:when test="${grade eq 'manager'}">
									<a href="/servlet/notice_update?idnotice=${notice.idnotice}">${notice.subject}</a>
								</c:when>
								<c:otherwise>
							${notice.subject}
							</c:otherwise>
							</c:choose></td>
						<td>${notice.content}</td>
						<c:if test="${grade eq 'manager'}">
							<td><a
								href="/servlet/notice_delete?idnotice=${notice.idnotice}">글삭제</a></td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="write_gap"></div>
		<div>
			<div class="filebox">
				<input class="upload-name" value="첨부파일" placeholder="첨부파일">
				<label for="file">파일찾기</label> <input type="file" id="file">
			</div>
			<c:if test="${grade eq 'manager'}">
				<div class="write">
					<input class="write_button" type="button" id="noticeWrite" name="noticeWrite" value="글쓰기" />
				</div>
			</c:if>
		</div>
		<div class="write_gap"></div>
	</div>
	<div class="footer">footer</div>
</body>
</html>