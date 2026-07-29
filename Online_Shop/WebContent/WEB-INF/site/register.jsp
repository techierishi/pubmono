<%@ taglib uri="../tlds/tlds.tld" prefix="e"%>

<jsp:include page="includes/header.jsp" />

</div>
</div>
<!---->
<!-- reg-form -->
<div class="reg-form">
	<div class="container">
		<div class="reg">
			<h3>Register Now</h3>
			<p>Welcome, please enter the following details to continue.</p>
			<p>
				If you have previously registered with us, <a href="#">click
					here</a>
			</p>
			<e:form isToken="true" css_class=""
				action="${pageContext.request.contextPath}/user/register" id=""
				method="post">
				<ul>
					<li class="text-info">First Name:</li>
					<li><input name="fname" type="text" value=""></li>
				</ul>
				<ul>
					<li class="text-info">Last Name:</li>
					<li><input name="lname" type="text" value=""></li>
				</ul>
				<ul>
					<li class="text-info">Email:</li>
					<li><input name="email" type="text" value=""></li>
				</ul>
				<ul>
					<li class="text-info">Password:</li>
					<li><input name="password" type="password" value=""></li>
				</ul>
				<ul>
					<li class="text-info">Re-enter Password:</li>
					<li><input name="password2" type="password" value=""></li>
				</ul>
				<ul>
					<li class="text-info">Mobile Number:</li>
					<li><input name="phone" type="text" value=""></li>
				</ul>
				<ul>
					<li class="text-info">Address :</li>
					<li><input name="address" type="text" value=""></li>
				</ul>
				<ul>
					<li class="text-info">State:</li>
					<li><input name="state" type="text" value=""></li>
				</ul>
				<ul>
					<li class="text-info">City:</li>
					<li><input name="city" type="text" value=""></li>
				</ul>
				<ul>
					<li class="text-info">Zip:</li>
					<li><input name="zip" type="text" value=""></li>
				</ul>
				<input type="submit" value="Register Now">
				<p class="click">
					By clicking this button, you are agree to my <a href="#">Policy
						Terms and Conditions.</a>
				</p>
			</e:form>
		</div>
	</div>
</div>
<jsp:include page="includes/footer.jsp" />
