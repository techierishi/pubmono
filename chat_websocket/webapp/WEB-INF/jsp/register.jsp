<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Register</title>

<link rel='stylesheet'
	href='http://codepen.io/assets/libs/fullpage/jquery-ui.css'>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_style.css" media="screen"
	type="text/css" />

</head>

<body>

	<div class="login-card">
		<h1>Log-in</h1>
		<br>
		<form method="post" action="${pageContext.request.contextPath}/user/register">
			<input type="text" name="user" placeholder="Username"> <input
				type="text" name="email" placeholder="Email"> <input
				type="password" name="pass" placeholder="Password"> <input
				type="password" name="pass2" placeholder="Repeat Password">
			<input type="file" name="profile_pic" value="Profile Pic"> <input
				type="submit" name="login" class="login login-submit"
				value="register">
		</form>

		<div class="login-help">
			<a href="${pageContext.request.contextPath}/user/login">Login</a>
		</div>
	</div>

	<script
		src='http://codepen.io/assets/libs/fullpage/jquery_and_jqueryui.js'></script>

</body>

</html>