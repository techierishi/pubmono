<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="includes/header.jsp" />

<!-- products -->
<div class="products">
	<div class="container">
		<div class="products-grids">
			<div class="col-md-8 products-grid-left">
				<div class="products-grid-lft">
					<c:forEach items="${product_list}" var="product">

						<div class="products-grd">
							<div class="p-one simpleCart_shelfItem prd">
								<a href="${pageContext.request.contextPath}/product/detail?pid=<c:out value="${product.productId}" />"> <img src="${pageContext.request.contextPath}/uploads/<c:out value="${product.productImages[0].imageName}" />" alt=""
									class="img-responsive" />
									<div class="mask">
										<span>Quick View</span>
									</div>
								</a>
								<h4>
									<c:out value="${product.productName}" />
								</h4>
								<p>
									<a class="item_add" href="#"><i></i> <span
										class=" item_price valsa"> <c:out
												value="${product.productPrice}" />
									</span></a>
								</p>
								
							</div>
						</div>
					</c:forEach>


					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- //products -->

	<jsp:include page="includes/footer.jsp" />