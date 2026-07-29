<%@ taglib uri="../tlds/tlds.tld" prefix="e"%>

<jsp:include page="includes/header.jsp" />

</div>
</div>
<!---->
<!-- reg-form -->
<div class="reg-form">
	<div class="container">
		<div class="box">
			<form method="post"
				action="${pageContext.request.contextPath}/customer/cart/confirm_order">
				<h1>Checkout</h1>

				<div class="content">
					<div class="row">
						<div class="col-sm-12">
							<div class="box payment-method">

								<h3>Address Details</h3>

								<p>Name : ${customer.name}</p>
								<p>Address : ${customer.address}</p>
								<p>City : ${customer.city}</p>
								<p>State : ${customer.state}</p>
								<p>Zip : ${customer.zip}</p>

								<div class="box-footer text-center"></div>
							</div>
						</div>
						<h3>Choose Payment method</h3>

						<div class="col-sm-12">
							<div class="box payment-method">

								<h4>Payment gateway</h4>

								<p>VISA and Mastercard only.</p>
								<div class="row">
									<div class="col-sm-6 col-md-3">
										<div class="form-group">
											<label for="country">Card</label> <select
												class="form-control" id="card_type" name="card_type">
												<option value="visa">Visa</option>
												<option value="Master Card">Master Card</option>
												</select>
										</div>
									</div>

									<div class="col-sm-6 col-md-3">
										<div class="form-group">
											<label for="phone">Card Number</label> <input
												class="form-control" id="card_number" name="card_number"
												type="text">
										</div>
									</div>
									<div class="col-sm-6 col-md-3">
										<div class="form-group">
											<label for="email">CVV</label> <input class="form-control"
												id="card_cvv" name="card_cvv" type="text">
										</div>
									</div>

								</div>
								<div class="box-footer text-center">

									<input name="payment" value="payment2" type="radio">
								</div>
							</div>
						</div>

						<div class="col-sm-12">
							<div class="box payment-method">

								<h4>Cash on delivery</h4>

								<p>You pay when you get it.</p>

								<div class="box-footer text-center">

									<input name="payment" value="payment3" type="radio">
								</div>
							</div>
						</div>
					</div>
					<!-- /.row -->

				</div>


				<div class="box-footer">
					<div class="pull-left">
						<a href="${pageContext.request.contextPath}/customer/cart/list"
							class="btn btn-default"><i class="fa fa-chevron-left"></i>Back
							to basket</a>
					</div>
					<div class="pull-right">
						<button type="submit" class="btn btn-primary">
							Confirm order<i class="fa fa-chevron-right"></i>
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<jsp:include page="includes/footer.jsp" />
