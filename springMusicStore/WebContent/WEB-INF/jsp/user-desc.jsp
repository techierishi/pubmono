<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="user-header.jsp"%>
<div role="main">
	<div class="container ">
		<div class="row">
			<div class="col-md-9">
				<h2 class=" nav-right">
					<span style="font-size: 30px" class="label label-default">${user.username}</span>
				</h2>
				<div class="row"
					style="border-top: 1px solid #000; margin-top: 50px; padding-top: 10px"
					role="userinfo">
					<div class="row header">
						<div class="col-md-10">
							<h3>Personal information</h3>
						</div>
						<div class="col-md-2">
							<A class="btn btn-default btn-lg"
								href="/musicstore/user/userinfomodify/${name}"> modify
								information </a>
						</div>
					</div>
					<div class="table-responsive">
						<div class="consignee">
							<table class="table table-striped">
								<tr>
									<Td>alias:</td>
									<td>${user.nickname}</td>
								</tr>
								<tr>
									<Td>e-mail:</td>
									<td>${user.email}</td>
								</tr>
								<tr>
									<Td>address:</td>
									<td>${user.address}</td>
								</tr>
								<tr>
									<Td>mobile phone:</td>
									<td>${user.phone}</td>
								</tr>

							</table>
						</div>
					</div>
				</div>
				<div class="row "
					style="border-top: 1px solid #000; margin-top: 50px; padding-top: 10px"
					role="orderlist">
					<h3>Order history:</h3>
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<Th>order number</th>
									<Th>user name</th>
									<Th>order date</th>
									<Th>method of payment</th>
									<Th>the number of</th>
									<Th>total</th>
									<Th>notes</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${!empty userorderlist }">
									<c:forEach items="${userorderlist }" var="userorder">
										<c:if test="${userorder.isPay == 1}">
											<tr id="${userorder.id}">
												<td>${userorder.id}</td>
												<td>${userorder.user.username}</td>
												<td>${userorder.getOrderDate()}</td>
												<td><c:choose>
														<c:when test="${userorder.payMethod == 0}">
Online payment
</c:when>
														<c:when test="${userorder.payMethod == 1}">
Payment in cash
</c:when>
													</c:choose></td>
												<td>${userorder.totalquantity}</td>
												<td>${userorder.totalpayment}</td>
												<td>${userorder.remark}</td>
												<td>
													<div class="btn-group" role="group" aria-label="...">
														<A
															href="/musicstore/user/getconsigneedesc/${userorder.id}"
															type="button" class="btn btn-default"> order details
														</a>
													</div>
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				<div class="row "
					style="border-top: 1px solid #000; margin-top: 50px; padding-top: 10px"
					role="consignee">
					<div class="row header">
						<div class="col-md-10">
							<h3>Address list</h3>
						</div>
						<div class="col-md-2">
							<A class="btn btn-default btn-lg"
								href="/musicstore/user/addconsignee"> Add address </a>
						</div>
					</div>
					<div role="consignee-desc" class="table-responsive">
						<div class="consignee">
							<table class="table table-striped">
								<tr>
									<Td>user name:</td>
									<Td>recipient:</td>
									<Td>shipping address:</td>
									<Td>mobile phone:</td>
									<Td>contact phone:</td>
								</tr>
								<c:if test="${!empty consigneelist }">
									<c:forEach items="${consigneelist }" var="consignee">
										<tr>
											<td>${consignee.username}</td>
											<td>${consignee.consignee}</td>
											<td>${consignee.deliverAddress}</td>
											<td>${consignee.phone}</td>
											<td>${consignee.tel}</td>
										</tr>
									</c:forEach>
								</c:if>
							</table>
						</div>
					</div>
				</div>
				<div class="row"
					style="border-top: 1px solid #000; margin-top: 50px; padding-top: 10px">
					<h3>Shopping cart</h3>
					<div class="col-md-12 car-list-title">
						<Div class="col-md-4">product information</div>
						<Div class="col-md-2">unit price (Yuan)</div>
						<Div class="col-md-2">number</div>
						<Div class="col-md-2">amount (Yuan)</div>
						<Div class="col-md-2">operational</div>
					</div>
					<c:if test="${!empty viewmodel }">
						<c:forEach items="${viewmodel }" var="vm">
							<div class="col-md-12 car-list-post">
								<div class="col-md-4">
									<div class="row">
										<div class="col-md-6">
											<a href="/musicstore/album/albumdesc/${vm.getAlbumId() }"
												class="thumbnail"> <img
												src="<%=request.getContextPath()%>/img/${vm.getAlbumId() }.jpg" />
											</a>
										</div>
										<div class="col-md-6">
											<div class="row">
												<div class="col-md-12">
													<a href="/musicstore/album/albumdesc/${vm.getAlbumId() }"><h4>${vm.getAlbumName() }</h4></a>
												</div>
												<div class="col-md-12">
													<a href="#"><h5 role="artist">${vm.getArtist() }</h5></a>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-2 ">
									<p>${vm.getPrice() }</p>
								</div>
								<div class="col-md-2">
									<p>${vm.getQuantity() }</p>
								</div>
								<div class="col-md-2">
									<p>${vm.getUnitPrice()}</p>
								</div>
								<Div class="col-md-2">
									<p>
										<a href="/musicstore/order/deleteorder/${vm.getId ()}">
											delete </a>
									</p>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<div class="col-md-12 car-list-counter">
						<div class="col-md-4">
							<h2>Total</h2>
						</div>
						<div class="col-md-2 ">
							<p></p>
						</div>
						<div class="col-md-2">
							<p>${totalquantity }</p>
						</div>
						<div class="col-md-2">
							<p>${totalPrice}</p>
						</div>
						<Div class="col-md-2">
							<p>
								<a
									href="/musicstore/order/OrderConsignee/${totalquantity}/${totalPrice}/${name}">
									settlement </a>
							</p>
						</div>
					</div>
				</div>

			</div>
			<!-- col-md-9 -->
			<%@ include file="user-aside.jsp"%>
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