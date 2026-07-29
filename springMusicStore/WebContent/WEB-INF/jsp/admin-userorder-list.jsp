<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin-left-menu.jsp"%>
<!--Start the content panel-->
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<H1 class="page-header">orders list</h1>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<Th>order number</th>
					<Th>user name</th>
					<Th>order date</th>
					<Th>method of payment</th>
					<Th>the number of</th>
					<Th>total</th>
					<Th>notes</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${!empty userorderlist }">
					<c:forEach items="${userorderlist }" var="userorder">
						<c:if test="${userorder.isPay == 1}">
							<tr id="${userorder.id}">
								<td>${userorder.id}</td>
								<td>${userorder.user.username}</td>
								<td>${userorder.getOrderDate()}</td>
								<td><c:choose>
										<c:when test="${userorder.payMethod == 0}">
Online payment
</c:when>
										<c:when test="${userorder.payMethod == 1}">
Payment in cash
</c:when>
									</c:choose></td>
								<td>${userorder.totalquantity}</td>
								<td>${userorder.totalpayment}</td>
								<td>${userorder.remark}</td>
								<td>
									<div class="btn-group" role="group" aria-label="...">
										<A href="/musicstore/admin/getconsigneedesc/${userorder.id}"
											type="button" class="btn btn-default"> order details </a>
										<c:if test="${userorder.getIsDeliveried() == 0 }">
											<A href="/musicstore/admin/dodelivery/${userorder.id}"
												type="button" class="btn btn-default"> shipped </a>
										</c:if>
									</div>
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
</div>
</div>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.12.4.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
</body>
</html>