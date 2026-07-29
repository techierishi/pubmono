<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="user-header.jsp"%>
<div role="main">
	<div class="container ">
		<div class="row">
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
										class="thumbnail"> <img src="${vm.picPath }" />
									</a>
								</div>
								<div class="col-md-6">
									<div class="row">
										<div class="col-md-12">
											<a href="/musicstore/album/albumdesc/${vm.getAlbumId() }"><h1>${vm.getAlbumName() }</h1></a>
										</div>
										<div class="col-md-12">
											<a href="#"><h2 role="artist">${vm.getArtist() }</h2></a>
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
	</div>
</footer>
<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>