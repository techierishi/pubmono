<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1">
		<tbody>
			<tr>
				<Th>album name</th>
				<Th>artist</th>
				<Th>issue date</th>
				<Th>price</th>
				<Th>operation</th>
			</tr>
			<c:if test="${!empty albumlist }">
				<c:forEach items="${albumlist }" var="album">
					<tr>
						<td>${album.title }</td>
						<td>${album.artist }</td>
						<td>${album.dateReleased}</td>
						<td>${album.price }</td>
						<td><A href=""> Edit </a> <A href="'"> delete </a></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</body>
</html>