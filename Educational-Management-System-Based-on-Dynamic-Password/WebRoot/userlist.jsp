<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'userlist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
	<div  align="center">
		<form method="post" action="User!listexample">
			用户名：<input type="text" name="account"> 用户类型：<select
				name="type">
				<option value="-1">不限</option>
				<option value="1">学生</option>
				<option value="2">教师</option>
				<option value="3">管理员</option>
			</select> <input type="submit" value="查询" />
		</form>
	</div>

	<table border="1" align="center">
		<tr>
			<th>编号</th>
			<th>账号</th>
			<th>密码</th>
			<th>动态密码序列号</th>
		</tr>
		 <s:iterator value="users">
		<tr>
			<td><s:property value="userid"/></td>
			<td><s:property value="account"/></td>
			<td><s:property value="password"/></td>
			<td><s:property value="ekey"/></td>
		</tr>
		</s:iterator>
	</table>

  </body>
</html>
