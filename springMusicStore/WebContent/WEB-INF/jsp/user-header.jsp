<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ZH-cn">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<Title>duck music (duck.com)</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>
	<header class="main-header ">
		<div role="header-img-roller">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1>Duck music-upcoming</h1>
					</div>
					<div class="col-md-12">
						<A href="#" class="btn btn-default btn-doc"> Eason Chan </a> <A
							href="#" class="btn btn-default btn-doc"> Eason Chan </a> <A
							href="#" class="btn btn-default btn-doc"> Eason Chan </a> <A
							href="#" class="btn btn-default btn-doc"> Eason Chan </a> <A
							href="#" class="btn btn-default btn-doc"> Eason Chan </a>
					</div>
				</div>
			</div>
		</div>
		<nav class="navbar navbar-default" data-spy="affix-top"
			data-offset-top="0">
			<div class="container ">
				<!--Mobile display-->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#header-collapse"
						aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<!--Icon-->
					<A class="navbar-brand" href="/musicstore/album/getalbumlist">
						duck music </a>
				</div>
				<!--Menu-->
				<div class="collapse navbar-collapse" id="header-collapse">
					<ul class="nav navbar-nav">
						<Li><a href="#"> found </a></li>
						<Li><a href="/musicstore/order/gotocar?id=${userid}">
								shopping cart </a></li>
						<Li><a href="/musicstore/user/getuserdesc/${name}">
								personal information </a></li>
					</ul>
					<div class="navbar-form navbar-right btn-group">
						<c:choose>
							<c:when test="${!empty name}">
								<a href="/musicstore/user/getuserdesc/${userid}"
									class="btn btn-default">${name }</a>
								<A href="/musicstore/user/dologout" class="btn btn-default">
									cancellation </a>
							</c:when>
							<c:otherwise>
								<A href="/musicstore/user/login" class="btn btn-default">
									logged </a>
								<A href="/musicstore/user/register" class="btn btn-default">
									register </a>
							</c:otherwise>
						</c:choose>
					</div>
					<form class="navbar-form navbar-right" role="search"
						action="/musicstore/album/search " method="get">
						<div class="input-group">
							<Input name="key" type="text" class="form-control"
								style="height: 33.5px" placeholder="search"> </input> <span
								class="input-group-btn">
								<Button type="submit" class="btn btn-default">Search</button>
							</span>
						</div>
					</form>
				</div>
				<!--navbar-collapse-->
			</div>
			<!--container-->
		</nav>
	</header>