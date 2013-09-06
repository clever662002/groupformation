<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="domain.model.group.Group" %>
<%@page import="java.util.List" %>    


			<div>
				<h1>All Groups</h1>
				<p class="successful">${message}</p>
				<p class="failed">${error}</p>
				<table>
					<tr>
						<th>Name</th>
						<th>Description</th>
						<th></th>
					</tr>
					<c:forEach items="${groups}" var="group">
						<tr>
							<td>${group.name}</td>
							<td>${group.description}</td>
							<td><a class="button" href="RemoveGroup/${group.id}">Delete</a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		
		
