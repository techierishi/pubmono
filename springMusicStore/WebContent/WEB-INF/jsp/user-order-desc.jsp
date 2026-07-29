<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="user-header.jsp"%>
<div role="main">
	<div class="container ">
		<div class="row">
			<div class="col-md-9">
				<H1 class="page-header">order details</h1>
				<div role="consignee" class="row">
					<div class="col-md-12 ">
						<div role="consignee-desc" class="table-responsive">
							<table class="table table-striped">
								<tr>
									<Td>user name:</td>
									<td>${consignee.username}</td>
								</tr>
								<tr>
									<Td>recipient:</td>
									<td>${consignee.consignee}</td>
								</tr>
								<tr>
									<Td>shipping address:</td>
									<td>${consignee.deliverAddress}</td>
								</tr>
								<tr>
									<Td>mobile phone:</td>
									<td>${consignee.phone}</td>
								</tr>
								<tr>
									<Td>contact phone number</td>
									<td>${consignee.tel}</td>
								</tr>
							</table>
						</div>
						<!--consignee-desc -->
					</div>
				</div>
				<div role="orderdetail" class="row">
					<div class="col-md-12">
						<div class="table-responsive">
							<table class="table table-striped">
								<thead>
									<tr>
										<Th>order number</th>
										<Th>record</th>
										<Th>singer</th>
										<Th>the number of</th>
										<Th>total price</th>
										<Th>unit price</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty orderdesclist }">
										<c:forEach items="${orderdesclist }" var="orderdesc">
											<tr id="${orderdesc.id}">
												<td>${orderdesc.id}</td>
												<td>${orderdesc.getAlbumName()}</td>
												<td>${orderdesc.getArtist()}</td>
												<td>${orderdesc.getQuantity()}</td>
												<td>${orderdesc.getUnitPrice()}</td>
												<td>${orderdesc.getPrice()}</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>

			</div>
			<!-- col-md-9 -->
			<%@ include file="user-aside.jsp"%>
		</div>
	</div>
</div>
<footer class="main-footer">
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<ul>
					<h2>Latest song list</h2>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
				</ul>
			</div>
			<div class="col-md-4">
				<ul>
					<h2>Latest song list</h2>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
				</ul>
			</div>
			<div class="col-md-4">
				<ul>
					<h2>Latest song list</h2>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
				</ul>
			</div>
		</div>
	</div>
</footer>
<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>