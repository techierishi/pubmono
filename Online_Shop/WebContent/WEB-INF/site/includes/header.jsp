<%-- 
    Document   : index
    Created on : Jul 19, 2015, 12:06:51 PM
    Author     : Rishikesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.ekart.util.Const"%>


<!DOCTYPE html>
<html>
<head>
<title>Pendent Store a Ecommerce Online Shopping Category Flat
	Bootstarp Resposive Website Template | About :: w3layouts</title>
<!-- for-mobile-apps -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Pendent Store Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript">
	
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
		function hideURLbar(){ window.scrollTo(0,1); } 

</script>
<!-- //for-mobile-apps -->
<link
	href="${pageContext.request.contextPath}/assets_site/css/bootstrap.css"
	rel="stylesheet" type="text/css" media="all" />
<link
	href="${pageContext.request.contextPath}/assets_site/css/style.css"
	rel="stylesheet" type="text/css" media="all" />

<link
	href="${pageContext.request.contextPath}/assets_site/css/sweetalert.css"
	rel="stylesheet">
<!-- js -->
<script
	src="${pageContext.request.contextPath}/assets_site/js/jquery-1.11.1.min.js"></script>
<!-- //js -->
<!-- start-smoth-scrolling -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets_site/js/move-top.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets_site/js/easing.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function($) {
		$(".scroll").click(function(event) {
			event.preventDefault();
			$('html,body').animate({
				scrollTop : $(this.hash).offset().top
			}, 1000);
		});
	});
</script>
<!-- start-smoth-scrolling -->
<!-- start menu -->
<link
	href="${pageContext.request.contextPath}/assets_site/css/megamenu.css"
	rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets_site/js/megamenu.js"></script>
<script>
	$(document).ready(function() {
		$(".megamenu").megamenu();
	});
</script>
<script
	src="${pageContext.request.contextPath}/assets_site/js/menu_jquery.js"></script>
<script
	src="${pageContext.request.contextPath}/assets_site/js/simpleCart.min.js">
	
</script>
<link href='http://fonts.googleapis.com/css?family=Monda:400,700'
	rel='stylesheet' type='text/css'>
</head>

<body>
	<!-- header -->

	<!-- //header -->
	<!-- top-header -->
	<div class="top_bg">
		<div class="container">
			<div class="header_top-sec">
				<div class="top_right">
					<ul>
						<li><a href="site/contact.jsp">Contact</a></li>|
						<li><a href="site/login.jsp">Track Order</a></li>
					</ul>
				</div>
				<div class="top_left">
					<ul>
						<li class="top_link">Email:<a href="mailto:info@example.com">info@example.com</a></li>|

						<%
							boolean isLoggedIn = Const.isLin(request);
						%>

						<%
							if (isLoggedIn) {
						%>
						<li class="top_link"><a
							href="${pageContext.request.contextPath}/user/logout">Logout</a></li>
						<%
							} else {
						%>
						<li class="top_link"><a
							href="${pageContext.request.contextPath}/user/login">Login</a></li>
						<%
							}
						%>
					</ul>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<!-- top-header -->
	<!-- logo-cart -->
	<div class="header_top">
		<div class="container">
			<div class="logo">
				<a href="${pageContext.request.contextPath}">Pendent Store</a>
			</div>
			<div class="header_right">
				<div class="cart box_1">
					<a href="${pageContext.request.contextPath}/customer/cart/list">
						<h3>
							<div class="total">
								(<span id="simpleCart_quantity" class="simpleCart_quantity"></span>
								items)
							</div>
							<img
								src="${pageContext.request.contextPath}/assets_site/images/cart1.png"
								alt="" />
						</h3>
					</a>
					<p>
						<a href="javascript:;" class="simpleCart_empty">Empty Cart</a>
					</p>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<!-- //logo-cart -->
	<!------>
	<div class="mega_nav">
		<div class="container">
			<div class="menu_sec">
				<!-- start header menu -->
				<ul class="megamenu skyblue">
					<li class="active grid"><a class="color1"
						href="${pageContext.request.contextPath}">Home</a></li>
					<li class="grid"><a class="color1"
						href="${pageContext.request.contextPath}/product/list">Products</a>

					</li>
					<li><a class="color1"
						href="${pageContext.request.contextPath}/customer/cart/list">Cart</a>
					</li>
					<li><a class="color1" href="#">Contact</a></li>
					<li><a class="color1" href="#">About Us</a></li>

				</ul>
				<div class="search">
					<form>
						<input type="text" value="" placeholder="Search..."> <input
							type="submit" value="">
					</form>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<!---->