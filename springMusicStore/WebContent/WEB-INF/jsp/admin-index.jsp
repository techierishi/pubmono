<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin-left-menu.jsp"%>
<!--Start the content panel-->
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<H1 class="page-header">song list</h1>

	<!--<div class="row placeholders">
<div class="col-xs-6 col-sm-3 placeholder">
<img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
<h4>Label</h4>
<span class="text-muted">Something else</span>
</div>
<div class="col-xs-6 col-sm-3 placeholder">
<img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
<h4>Label</h4>
<span class="text-muted">Something else</span>
</div>
<div class="col-xs-6 col-sm-3 placeholder">
<img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
<h4>Label</h4>
<span class="text-muted">Something else</span>
</div>
<div class="col-xs-6 col-sm-3 placeholder">
<img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
<h4>Label</h4>
<span class="text-muted">Something else</span>
</div>
</div>-->

	<A class="sub-header btn btn-primary btn-lg"
		href="/musicstore/admin/addsong"> Add songs </a>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<Th>number</th>
					<Th>song name</th>
					<Th>singer</th>
					<Th>price</th>
					<Th>release time</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${!empty albumlist }">
					<c:forEach items="${albumlist }" var="album">
						<tr id="${album.id}">
							<td>${album.id}</td>
							<td>${album.title}</td>
							<td>${album.artist}</td>
							<td>${album.price}</td>
							<td>${album.dateReleased}</td>
							<td>
								<div class="btn-group" role="group" aria-label="...">
									<Aid ="${album.id}"name="${album.title}" type="button"
										class="btnbtn-defaultalbum-del"> delete </a> <A
										type="button" class="btn btn-default"
										href="/musicstore/admin/editalbum/${album.id}"> Edit </a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
</div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.12.4.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function() {
		function dele(i, name) {
			If(confirm("delete" + name + "? "))
			{
				$.get("/musicstore/admin/dodelete", {
					"albumid" : i
				}, function(data, status) {
					if (status == "success") {
						$("#" + i).remove();
					}
					;
				});
			}
		}
		;

		$(".album-del").on("click", function(e) {
			var del = $(e.target);
			dele(del.attr("id"), del.attr("name"));
		});

	});
</script>
</body>
</html>