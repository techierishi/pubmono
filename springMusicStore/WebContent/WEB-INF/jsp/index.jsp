<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function del(id) {
		$.get("/springstore/delUser?id=" + id, function(data) {
			if ("success" == data.result) {
				Alert("deleted successfully");
				window.location.reload();
			} else {
				Alert("delete failed");
			}
		});
	}
</script>
</head>
<body>
	<h6>
		<A href="/springtest/toAddUser"> Add user </a>
	</h6>
	<table border="1">
		<tbody>
			<tr>
				<Th>name</th>
				<Th>mailbox</th>
				<Th>address</th>
				<Th>name</th>
				<Th>operation</th>
			</tr>
			<c:if test="${!empty userList }">
				<c:forEach items="${userList }" var="user">
					<tr>
						<td>${user.username }</td>
						<td>${user.email }</td>
						<td>${user.address}</td>
						<td>${user.name }</td>
						<td><A href="/springstore/getUser?id=${user.id}"> Edit </a>
							<A href="javascript:del ('${user.id}')"> delete </a></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</body>
</html>