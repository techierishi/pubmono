<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ZH-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<Title>user login | (duck.com)</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>
	<div class="login">
		<div class="container">
			<form class="form-signin" action="/musicstore/user/dologin">
				<H2 class="form-signin-heading">logged</h2>
				<Label for="inputname" class="sr-only"> user name </label> <Input
					name="username" type="text" id="inputUsername" class="form-control"
					placeholder="user name" required autofocus> <Label
					for="inputPassword" class="sr-only"> password </label> <Input
					name="password" type="password" id="inputPassword"
					class="form-control" placeholder="password" required>
				<div class="checkbox">
					<label> <Input type="checkbox" value="remember-me">
						Remember me
					</label>
				</div>
				<Button class="btn btn-lg btn-primary btn-block" type="submit">
					logged in</button>
			</form>
		</div>
		<!-- /container -->
	</div>
	<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>
<body>

</body>
</html>