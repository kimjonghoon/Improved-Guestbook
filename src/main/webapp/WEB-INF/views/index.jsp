<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Home</title>
<link type="text/css" rel="stylesheet" href="/resources/stylesheets/main.css" />
</head>
<body>

<%@ include file="./inc/main-menu.jsp"%>

<%
    UserService userService = UserServiceFactory.getUserService();
%>

	<security:authorize access="isAuthenticated()">
		<security:authentication property="principal.nickname" var="nickname" />
		<security:authentication property="principal.userId" var="userId" />
	</security:authorize>

	<c:choose>
		<c:when test="${empty nickname}">
			<a href="<%=userService.createLoginURL("/") %>">Sign in</a>
		</c:when>
		<c:otherwise>
        ${nickname } <a href="/logout?url=/">Sign out</a>
		</c:otherwise>
	</c:choose>

	<hr style="clear: both;" />

	<article>
		<h1>Home</h1>
		<p>Home page</p>
	</article>

</body>
</html>