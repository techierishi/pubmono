<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin-left-menu.jsp"%>
<!--Start the content panel-->
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<H1 class="page-header">order details</h1>
	<div class="row">
		<div class="col-md-7">
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<Th>order number</th>
							<Th>record</th>
							<Th>singer</th>
							<Th>the number of</th>
							<Th>total price</th>
							<Th>unit price</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${!empty orderdesclist }">
							<c:forEach items="${orderdesclist }" var="orderdesc">
								<tr id="${orderdesc.id}">
									<td>${orderdesc.id}</td>
									<td>${orderdesc.getAlbumName()}</td>
									<td>${orderdesc.getArtist()}</td>
									<td>${orderdesc.getQuantity()}</td>
									<td>${orderdesc.getUnitPrice()}</td>
									<td>${orderdesc.getPrice()}</td>
									<td></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		<div class="col-md-5 ">
			<div role="consignee-desc" class="table-responsive">
				<div class="consignee">
					<table class="table table-striped">
						<tr>
							<Td>user name:</td>
							<td>${consignee.username}</td>
						</tr>
						<tr>
							<Td>recipient:</td>
							<td>${consignee.consignee}</td>
						</tr>
						<tr>
							<Td>shipping address:</td>
							<td>${consignee.deliverAddress}</td>
						</tr>
						<tr>
							<Td>mobile phone:</td>
							<td>${consignee.phone}</td>
						</tr>
						<tr>
							<Td>contact phone number</td>
							<td>${consignee.tel}</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.12.4.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
</body>
</html>