<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="user-header.jsp"%>
<div role="main">
	<div class="container ">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-5">
				<div class="">
					<form class="form-info-modify"
						action="/musicstore/user/douserinfomodify/${name }" method="post">
						<H2 class="form-signin-heading">modifying user information</h2>
						<input name="id" type="hidden" id="inputid" class="form-control"
							value="${user. id}" required autofocus> <Label
							for="inputEmail" class=""> e-mail </label> <input name="email"
							type="email" id="inputEmail" class="form-control"
							value="${user. email}" required autofocus> <Label
							for="inputUsername" class=""> user name </label> <input
							name="username" type="text" id="inputUsername"
							class="form-control" value="${user. username}" required autofocus>

						<Label for="inputPassword" class=""> password </label> <input
							name="password" type="password" id="inputPassword"
							class="form-control" value="${user. password}" required>

						<Label for="inputAddress" class=""> address </label> <input
							name="address" type="text" id="inputAddress" class="form-control"
							value="${user. address}" required> <Label
							for="inputPhone" class=""> phone </label> <Input name="phone"
							type="phone" id="inputPhone" class="form-control"
							value="${user. phone}" phone "required> <Label
							for="inputName" class=""> alias </label> <Input name="nickname"
							type="text" id="inputName" class="form-control"
							value="${user. nickname}" alias "required> <input
							name="id" type="hidden" id="" value="0" />

						<Button class="btn btn-lg btn-primary btn-block" type="submit">
							Save</button>
					</form>
				</div>
			</div>
			<div class="col-md-2"></div>
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