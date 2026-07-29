<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- <link rel="icon" href="http://v3.bootcss.com/favicon.ico">-->


<title>Console</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">

<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/css/dashboard.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/common.css">
<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script
	src="<%=request.getContextPath()%>/js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="">Console</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/musicstore/admin/albumlist">Song list</span></a></li>
					<li><a href="/musicstore/admin/userlist">User list</a></li>
					<li><a href="/musicstore/admin/getconsigneelist">Address
							List</a></li>
					<li><a href="/musicstore/admin/getorderlist">Order List</a></li>
					<li><a href="http://v3.bootcss.com/examples/dashboard/#">Help</a></li>
				</ul>
				<form class="navbar-form navbar-right">
					<input type="text" class="form-control" placeholder="Search..">
				</form>
			</div>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<h2>Song Management</h2>
					<li class="active"><a href="/musicstore/admin/albumlist">The
							song list<span class="sr-only">(current)</span>
					</a></li>
					<li class="active"><a href="/musicstore/admin/genrelist">Song
							genre<span class="sr-only">(current)</span>
					</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<h2>User Management</h2>
					<li><a href="/musicstore/admin/userlist">User List</a></li>
					<li><a href="/musicstore/admin/getconsigneelist">Address
							List</a></li>

				</ul>
				<ul class="nav nav-sidebar">
					<h2>Order Management</h2>
					<li><a href="/musicstore/admin/getdeliveriedorderlist">List
							of shipped orders</a></li>
					<li><a href="/musicstore/admin/getnotdeliveryorderlist">List
							of unfilled orders</a></li>
					<li><a href=""></a></li>
				</ul>
			</div>