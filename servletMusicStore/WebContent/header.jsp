<%-- 
    Document   : header.jsp
    Created on : 05-Aug-2015, 15:53:22
    Author     : Rishikesh Jha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>


<title>Online Music Store</title>

<meta name="keywords" content="">

<link
	href='http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100'
	rel='stylesheet' type='text/css'>

<!-- styles -->
<link
	href="${pageContext.request.contextPath}/siteres/css/font-awesome.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/siteres/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/siteres/css/animate.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/siteres/css/owl.carousel.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/siteres/css/owl.theme.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/siteres/css/player.css"
	rel="stylesheet">

<!-- theme stylesheet -->
<link
	href="${pageContext.request.contextPath}/siteres/css/style.default.css"
	rel="stylesheet" id="theme-stylesheet">

<!-- your stylesheet with modifications -->
<link href="${pageContext.request.contextPath}/siteres/css/custom.css"
	rel="stylesheet">

<script
	src="${pageContext.request.contextPath}/siteres/js/jquery-1.11.0.min.js"></script>
<script
	src="${pageContext.request.contextPath}/siteres/js/jaudio.min.js"></script>

<script
	src="${pageContext.request.contextPath}/siteres/js/respond.min.js"></script>

<link rel="shortcut icon" href="favicon.png">



</head>

<body>

	<!-- *** TOPBAR ***
 _________________________________________________________ -->
	<div id="top">
		<div class="container">
			<div class="col-md-6 offer" data-animate="fadeInDown">
				<a href="${pageContext.request.contextPath}"
					class="btn btn-success btn-sm" data-animate-hover="shake">Online
					Music Store </a>
			</div>
			<div class="col-md-6" data-animate="fadeInDown">
				<ul class="menu">
					<li><a href="#" data-toggle="modal" data-target="#login-modal">Login</a>
					</li>
					<li><a href="#">Register</a></li>
				</ul>
			</div>
		</div>
		<div class="modal fade" id="login-modal" tabindex="-1" role="dialog"
			aria-labelledby="Login" aria-hidden="true">
			<div class="modal-dialog modal-sm">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="Login">Customer login</h4>
					</div>
					<div class="modal-body">
						<form action="#" method="post">
							<div class="form-group">
								<input type="text" class="form-control" id="email-modal"
									placeholder="email">
							</div>
							<div class="form-group">
								<input type="password" class="form-control" id="password-modal"
									placeholder="password">
							</div>

							<p class="text-center">
								<button class="btn btn-primary">
									<i class="fa fa-sign-in"></i> Log in
								</button>
							</p>

						</form>

						<p class="text-center text-muted">Not registered yet?</p>
						<p class="text-center text-muted">
							<a href="#"><strong>Register now</strong></a>! It is easy and
							done in 1&nbsp;minute and gives you access to special discounts
							and much more!
						</p>

					</div>
				</div>
			</div>
		</div>

	</div>

	<!-- *** TOP BAR END *** -->

	<!-- *** NAVBAR ***
 _________________________________________________________ -->

	<div class="navbar navbar-default yamm" role="navigation" id="navbar">
		<div class="container">
			<div class="navbar-header">

				<a class="navbar-brand home"
					href="${pageContext.request.contextPath}"
					data-animate-hover="bounce"> <span class="sr-only">Music
						Store</span>
				</a>
				<div class="navbar-buttons">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#navigation">
						<span class="sr-only">Toggle navigation</span> <i
							class="fa fa-align-justify"></i>
					</button>
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#search">
						<span class="sr-only">Toggle search</span> <i class="fa fa-search"></i>
					</button>
					<a class="btn btn-default navbar-toggle" href="#"> <i
						class="fa fa-shopping-cart"></i> <span class="hidden-xs">
							items in cart</span>
					</a>
				</div>
			</div>
			<!--/.navbar-header -->

			<div class="navbar-collapse collapse" id="navigation">

				<ul class="nav navbar-nav navbar-left">
					<li id="home"><a href="<c:url value='/' />">Home</a></li>
					<li id="catalog"><a href="<c:url value='/catalog' />">Catalog</a></li>
					<li id="subscribe"><a href="<c:url value='/subscribe' />">Subscribe</a></li>
					<li id="contact"><a href="<c:url value='/contact' />">Contact</a></li>
				</ul>

			</div>
			<!--/.nav-collapse -->

			<div class="navbar-buttons">

				<div class="navbar-collapse collapse right" id="basket-overview">
					<a href="#" class="btn btn-primary navbar-btn"><i
						class="fa fa-shopping-cart"></i><span class="hidden-sm">
							items in cart</span></a>
				</div>
				<!--/.nav-collapse -->

				<div class="navbar-collapse collapse right" id="search-not-mobile">
					<button type="button" class="btn navbar-btn btn-primary"
						data-toggle="collapse" data-target="#search">
						<span class="sr-only">Toggle search</span> <i class="fa fa-search"></i>
					</button>
				</div>

			</div>

			<div class="collapse clearfix" id="search">

				<form class="navbar-form" role="search">
					<div class="input-group">
						<input type="text" class="form-control" placeholder="Search">
						<span class="input-group-btn">

							<button type="submit" class="btn btn-primary">
								<i class="fa fa-search"></i>
							</button>

						</span>
					</div>
				</form>

			</div>
			<!--/.nav-collapse -->

		</div>
		<!-- /.container -->
	</div>
	<!-- /#navbar -->

	<!-- *** NAVBAR END *** -->