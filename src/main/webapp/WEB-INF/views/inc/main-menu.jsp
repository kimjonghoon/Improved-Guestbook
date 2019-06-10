<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<a href="/">Home</a>
<a href="/guestbook">Guestbook</a>

<security:authorize access="hasRole('ROLE_ADMIN')">
	<a href="/admin">Admin</a>
</security:authorize>

<hr style="clear: both;" />