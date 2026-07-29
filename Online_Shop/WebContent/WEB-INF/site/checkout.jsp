<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="includes/header.jsp" />

<!-- check-out -->
<div class="container">
	<div class="check">

		<div class="col-md-9 cart-items">
			<h1>My Shopping Bag </h1>
			<c:forEach items="${cart_list}" var="cart">

				<div class="cart-header2">
					<div class="close2"></div>
					<div class="cart-sec simpleCart_shelfItem">
						<div class="cart-item-info">
							<ul class="qty">
								<li><p>Name : <c:out value="${cart.productName}" /></p></li>
								<li><p>Qty : <c:out value="${cart.quantity}" /></p></li>
							</ul>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="clearfix"></div>
		<div class="col-md-3">

			<a class="order" href="${pageContext.request.contextPath}/customer/cart/place_order">Place Order</a>
		</div>
	</div>
</div>
<!-- //check-out -->

<jsp:include page="includes/footer.jsp" />

