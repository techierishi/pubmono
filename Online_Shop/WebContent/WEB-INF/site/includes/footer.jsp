<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.ekart.util.Const" %>
<!-- footer -->
<div class="footer">
	<div class="container">
		<div class="col-md-3 footer-grid">
			<h6>About us</h6>
			<p>Suspendisse sed accumsan risus. Curabitur rhoncus, elit vel
				tincidunt elementum, nunc urna tristique nisi, in interdum libero
				magna tristique ante. adipiscing varius. Vestibulum dolor lorem.</p>
		</div>
		<div class="col-md-3 footer-grid">
			<h6>Information</h6>
			<ul>
				<li><a href="#">About Us</a></li>
				<li><a href="#">Delivery Information</a></li>
				<li><a href="#">Privacy Policy</a></li>
				<li><a href="#">Terms &amp; Conditions</a></li>
			</ul>
		</div>
		<div class="col-md-3 footer-grid">
			<h6>My Account</h6>
			<ul>
				<li><a href="#">My Account</a></li>
				<li><a href="#">Order History</a></li>
				<li><a href="#">Wish List</a></li>
				<li><a href="#">Newsletter</a></li>
			</ul>
		</div>
		<div class="col-md-3 footer-grid">
			<h6>Extras</h6>
			<ul>
				<li><a href="#">Brands</a></li>
				<li><a href="#">Gift Vouchers</a></li>
				<li><a href="#">Affiliates</a></li>
				<li><a href="#">Specials</a></li>
			</ul>
		</div>
		<div class="clearfix"></div>
	</div>
</div>

<script>
	
<%if (Const.isLin(request)) {%>
	$(document).ready(function() {

		getCartQty();
	});
<%}%>
	var getCartQty = function() {
		var saveData = $.ajax({
			type : 'GET',
			url : "${pageContext.request.contextPath}/customer/cart/count",
			dataType : "text",
			success : function(resultData) {
				console.log(resultData);
				$("#simpleCart_quantity").html(resultData);
			}
		});
		saveData.error(function() {
			console.log("Something went wrong");
		});
	}
</script>

<script
	src="${pageContext.request.contextPath}/assets_site/js/sweetalert.min.js"></script>

<script type="text/javascript">
	
<%if (request.getParameter("msg") != null && !((String) request.getParameter("msg")).isEmpty()) {%>
	swal("Message!", "<%=request.getParameter("msg")%>", "success")
<%}%>
	
</script>


<!-- //footer -->
</body>
</html>