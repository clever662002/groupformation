<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="domain.model.group.Group" %>
<%@page import="java.util.List" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Group</title>
</head>
<body>
  
  	<c:choose>
	<c:when test="${isGroupMember == \"false\"}">
		<p>You are not in a group yet.</p>
	</c:when>
	<c:otherwise>
	<div>
		<h1>My Group</h1>
		<p>Name: ${group.name} <br>
		Description: ${group.description} </p>
	</div>
	</c:otherwise>
	</c:choose>
		
</body>
</html>