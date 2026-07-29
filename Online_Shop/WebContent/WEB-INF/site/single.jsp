<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="includes/header.jsp" />

<!-- single-page -->
<div class="single">
	<div class="container">
		<div class="single-page">
			<div class="flexslider details-lft-inf">
				<ul class="slides">
					<c:forEach items="${product.productImages}" var="productimg">
						<li
							data-thumb="${pageContext.request.contextPath}/uploads/<c:out value="${productimg.imageName}" />"><img
							src="${pageContext.request.contextPath}/uploads/<c:out value="${productimg.imageName}" />" /></li>
					</c:forEach>

				</ul>
			</div>
			<!-- FlexSlider -->
			<script defer
				src="${pageContext.request.contextPath}/assets_site/js/jquery.flexslider.js"></script>
			<link rel="stylesheet"
				href="${pageContext.request.contextPath}/assets_site/css/flexslider.css"
				type="text/css" media="screen" />

			<script>
				// Can also be used with $(document).ready()
				$(window).load(function() {
					$('.flexslider').flexslider({
						animation : "slide",
						controlNav : "thumbnails"
					});
				});
			</script>
			<div class="details-left-info">
				<h3 id="pname">
					<c:out value="${product.productName}" />
				</h3>
				<h4>Pellentesque pretium</h4>
				<div class="simpleCart_shelfItem">
					<p>
						<span class="item_price qwe">$ <c:out
								value="${product.productPrice}" />
						</span>
					</p>
					<div class="col-rad">
						<div class="col-rad-left">
							<select class="arrival">
								<option>Color</option>
								<option>Green</option>
								<option>Violet</option>
								<option>Pink</option>
							</select>
						</div>
						<div class="col-rad-right">
							<section>
								<div class="row">
									<div class="colr ert">
										<label class="radio"><input type="radio" name="radio"
											checked=""><i></i>14Kt</label>
									</div>
									<div class="colr">
										<label class="radio"><input type="radio" name="radio"><i></i>10Kt</label>
									</div>
								</div>
							</section>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="col-rad tyy">
						<div class="col-rad-left">
							<p>Diamond</p>
						</div>
						<div class="col-rad-right">
							<section>
								<div class="row">
									<div class="colr ert">
										<label class="radio"><input type="radio" name="radio1"
											checked=""><i></i>SI GH</label>
									</div>
									<div class="colr">
										<label class="radio"><input type="radio" name="radio1"><i></i>VS
											GH</label>
									</div>
									<div class="colr">
										<label class="radio"><input type="radio" name="radio1"><i></i>VVS
											EF</label>
									</div>
								</div>
							</section>
						</div>
						<div class="clearfix"></div>
					</div>
					<p class="qty">Qty ::</p>
					<input min="1" type="number" id="quantity" name="quantity"
						value="1" class="form-control input-small">
					<div class="single-but item_add">
						<input type="hidden" name="pid" id="pid"
							value="<c:out value="${product.productId}" />" />
						<button id="add_to_cart">Add to Cart</button>
					</div>
				</div>
				<div class="flower-type">
					<p>
						Model ::<a href="#">Gem Stone</a>
					</p>
					<p>
						Carrets ::<a href="#">22</a>
					</p>
				</div>
				<p class="desc">Proin vestibulum scelerisque tempus. Phasellus
					at fermentum erat. Pellentesque mattis velit eget elit condimentum
					gravida. Donec vehicula mollis velit, a eleifend est hendrerit
					quis. Etiam pulvinar at ex eget cursus. Etiam luctus orci ut tortor
					rhoncus, sit amet faucibus lorem finibus. Nulla id ornare arcu. or
					lowest node and working upwards) are as follows:</p>
			</div>
			<div class="clearfix"></div>
		</div>

		
	</div>
</div>
<!-- single -->

<jsp:include page="includes/footer.jsp" />

<script type="text/javascript">
	$("#add_to_cart").click(function() {
		var pId = $("#pid").val();
		var quantity = $("#quantity").val();
		var pname = $("#pname").html();
		
		var saveData = $.ajax({
			type : 'GET',
			url : "${pageContext.request.contextPath}/customer/cart/add?pid=" + pId+"&quantity="+quantity+"&pname="+pname,
			dataType : "text",
			success : function(resultData) {
				console.log(resultData);
				$("#simpleCart_quantity").html(resultData);
			}
		});
		saveData.error(function() {
			alert("Something went wrong");
		});

	});
</script>
