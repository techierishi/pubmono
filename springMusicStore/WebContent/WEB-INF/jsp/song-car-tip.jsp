<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="user-header.jsp"%>
<div role="main">
	<div class="container ">
		<div class="row">
			<div class="col-md-9">
				<h2>
					Product "${AlbumTitle}" has been added to the shopping cart <a
						href="/musicstore/album/getalbumlist"> continue shopping </a> or
					<a href="/musicstore/order/gotocar?name=${name}"> go to
						checkout </a>
				</h2>
			</div>
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