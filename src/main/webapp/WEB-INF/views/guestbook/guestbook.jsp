<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%
    String guestbookName = request.getParameter("guestbookName");
    if (guestbookName == null) guestbookName = "";
%>
<html>
<head>
<meta charset="UTF-8" />
<title>Guestbook</title>
<link type="text/css" rel="stylesheet" href="/resources/stylesheets/main.css" />
<script type="text/javascript">
function del(key) {
    var check = confirm('Are you sure you want to delete this greeting?');

    if (check) {
        var form = document.getElementById("delForm");
        form.keyString.value = key;
        form.submit();
    }
}
</script>
</head>

<body>

<%@ include file="../inc/main-menu.jsp"%>

<%
    UserService userService = UserServiceFactory.getUserService();
%>

	<security:authorize access="isAuthenticated()">
		<security:authentication property="principal.nickname" var="nickname" />
		<security:authentication property="principal.userId" var="userId" />
	</security:authorize>

	<c:choose>
		<c:when test="${empty nickname}">
			<a
				href="<%=userService.createLoginURL("/guestbook?guestbookName=" + guestbookName)%>">Sign
				in</a>
		</c:when>
		<c:otherwise>
			<c:url var="logoutUrl" value="/guestbook?guestbookName=${param.guestbookName}" />
			${nickname } <a href="/logout?url=${logoutUrl}">Sign out</a>
		</c:otherwise>
	</c:choose>

	<hr style="clear: both;" />

	<c:choose>
		<c:when test="${not empty nickname}">
			<p>Hello, ${fn:escapeXml(nickname)}!</p>
		</c:when>
		<c:otherwise>
			<p>Hello! <strong>Sign in</strong> to include your name with greetings you post.</p>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${empty greetings }">
			<p>Guestbook '${fn:escapeXml(guestbookName)}' has no messages.</p>
		</c:when>
		<c:otherwise>
			<c:forEach var="greeting" items="${greetings }" varStatus="status">
				<c:choose>
					<c:when test="${empty greeting.author_email }">
						<p>
							<b>An anonymous person</b> wrote:
						</p>
					</c:when>
					<c:when test="${greeting.author_id == userId}">
						<p>
							<b>${fn:escapeXml(greeting.author_email )} (You)</b> wrote:
						</p>
					</c:when>
					<c:otherwise>
						<p>
							<b>${fn:escapeXml(greeting.author_email )}</b> wrote:
						</p>
					</c:otherwise>
				</c:choose>
				<blockquote>${fn:escapeXml(greeting.content)}</blockquote>
				<security:authorize access="isAuthenticated() and (#greeting.author_id == principal.userId or hasRole('ROLE_ADMIN'))">
					<blockquote><a href="javascript:del('${greeting.keyString }')">Del</a></blockquote>
				</security:authorize>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	<form action="/guestbook/sign" method="post">
		<div>
			<textarea name="content" rows="3" cols="60"></textarea>
		</div>
		<div>
			<input type="submit" value="Post Greeting" />
		</div>
		<input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>

	<form action="/guestbook" method="get">
		<div>
			<input type="text" name="guestbookName" value="${fn:escapeXml(guestbookName)}" />
		</div>
		<div>
			<input type="submit" value="Switch Guestbook" />
		</div>
	</form>

	<form id="delForm" action="/guestbook/del" method="post" style="display: none;">
		<input type="hidden" name="keyString" />
		<input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>

</body>
</html>