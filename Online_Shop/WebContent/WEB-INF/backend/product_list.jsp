<%@ page import="java.util.List"%>
<%@ page import="com.ekart.user.entity.Product"%>
<!DOCTYPE html>
<html>
<jsp:include page="includes/head.jsp"></jsp:include>
<body>

	<jsp:include page="includes/nav.jsp"></jsp:include>
	<jsp:include page="includes/sidebar.jsp"></jsp:include>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="#"><svg class="glyph stroked home">
							<use xlink:href="#stroked-home"></use></svg></a></li>
				<li class="active">Icons</li>
			</ol>
		</div>
		<!--/.row-->

		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Category</h1>
			</div>
		</div>
		<!--/.row-->


		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">Form Elements</div>
					<div class="panel-body">
						<table class="table table-hover">
							<thead>
								<tr>

									<th>Product Name</th>
									<th>Product Price</th>
									<th>Product Quantity</th>
									<th>Action</th>

								</tr>
								<%
									List<Product> entityList = (List<Product>) request.getAttribute("product_list");
									if (entityList != null && !entityList.isEmpty()) {

										for (Product entity : entityList) {
								%>
								<tr>
									<td><%=entity.getProductName()%></td>
									<td><%=entity.getProductPrice()%></td>
									<td><%=entity.getProductQuantity()%></td>
									<td><button class="btn btn-delete">
											<svg class="glyph stroked cancel">
												<use xlink:href="#stroked-cancel" /></svg>
										</button></td>
								</tr>

								<%
									}
								}
								%>
							</thead>
						</table>
					</div>
				</div>
			</div>
			<!-- /.col-->
		</div>
		<!-- /.row -->

	</div>
	<!--/.main-->


	<jsp:include page="includes/script.jsp"></jsp:include>
</body>
</html>
