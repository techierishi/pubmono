<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="user-header.jsp"%>
<div role="main">
	<diV class="row">
		<div class="col-md-4 col-md-offset-4">
			<form class="form-horizontal"
				action="/musicstore/order/doOrderConsignee" methode="get">


				<div class="form-group">
					<h3>Total: ${totalPrice}</h3>
					<input name="totalpayment" type="hidden" class="form-control"
						value="${totalPrice }">
				</div>


				<div class="form-group">
					<h3>Total: ${totalquantity}</h3>
					<input name="totalquantity" type="hidden" class="form-control"
						value="${totalquantity }">
				</div>


				<div class="radio-inline">
					<label> <input type="radio" name="payMethod" id="linedown"
						value="0" checked> Online payment
					</label>
				</div>


				<div class="radio-inline">
					<label> <input type="radio" name="payMethod" id="lineup"
						value="1"> Payment in cash
					</label>
				</div>


				<div class="form-group">
					<Label for="remark" class="col-sm-2 control-label"> notes
					</label>
					<textarea name="remark" id="remark" class="form-control" rows="13"></textarea>
				</div>

				<c:if test="${!empty consigneelist }">
					<c:forEach items="${consigneelist }" var="consignee">
						<div class="radio">
							<label> <input type="radio" name="consigneeid"
								id="consigneeid" value="${consignee.id }" />
							</label>
							<h3>${consignee.consignee }</h3>
							<p>${consignee.deliverAddress }</p>
						</div>
					</c:forEach>
				</c:if>


				<div class="form-group">
					<label for="totalquantity" class="sr-only col-sm-2 control-label"></label>
					<div class="col-sm-10">
						<input name="totalquantity" type="hidden" class="form-control"
							id="totalquantity" value="${totalquantity }">
					</div>
				</div>


				<Button type="submit" class="btn btn-default">submit order
				</button>


				<div class="form-group">
					<Label for="username" class="sr-only col-sm-2 control-label">
						user name </label>
					<div class="col-sm-10">
						<input name="userid" type="hidden" class="form-control"
							id="userid" value="${userid }">
					</div>
				</div>


			</form>
		</div>
	</div>
</div>
<footer class="main-footer">
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<ul>
					<h2>Latest song list</h2>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
				</ul>
			</div>
			<div class="col-md-4">
				<ul>
					<h2>Latest song list</h2>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
				</ul>
			</div>
			<div class="col-md-4">
				<ul>
					<h2>Latest song list</h2>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
					<li><a href="#" class=""> Playlist name
							<P class="date">March 21, 2015</p>
					</a></li>
				</ul>
			</div>
		</div>
	</div>
	</div>
</footer>
<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>