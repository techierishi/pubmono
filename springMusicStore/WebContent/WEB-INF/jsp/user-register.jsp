<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ZH-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<Title>user registration | (duck.com)</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>
	<div class="login">
		<div class="container">
			<form class="form-signin" action="/musicstore/user/doregister"
				method="post">
				<H2 class="form-signin-heading">register</h2>

				<Label for="inputEmail" class="sr-only"> e-mail </label> <Input
					name="email" type="email" id="inputEmail" class="form-control"
					placeholder="email" required autofocus> <Label
					for="inputUsername" class="sr-only"> user name </label> <Input
					name="username" type="text" id="inputUsername" class="form-control"
					placeholder="user name" required autofocus> <Label
					for="inputPassword" class="sr-only"> password </label> <Input
					name="password" type="password" id="inputPassword"
					class="form-control" placeholder="password" required> <Label
					for="inputAddress" class="sr-only"> address </label> <Input
					name="address" type="text" id="inputAddress" class="form-control"
					placeholder="address" required> <Label for="inputPhone"
					class="sr-only"> phone </label> <Input name="phone" type="phone"
					id="inputPhone" class="form-control" placeholder="phone" required>

				<Label for="inputName" class="sr-only"> alias </label> <Input
					name="nickname" type="text" id="inputName" class="form-control"
					placeholder="alias" required> <input name="id"
					type="hidden" id="" value="0" />

				<!-- <input name="userroleid" type="hidden" id="" value="1">
<input name="isinuse" type="hidden" id="" value="1">

<Label for= "confirmPassword" class= "sr-only" > Verify password </label>
<Input type = "password" id = "confirmPassword" class= "form-control" placeholder= "confirm password" required>-->

				<Button class="btn btn-lg btn-primary btn-block" type="submit">
					register</button>
			</form>
		</div>
		<!-- /container -->
	</div>
	<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>