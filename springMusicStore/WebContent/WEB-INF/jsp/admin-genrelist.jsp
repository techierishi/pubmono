<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin-left-menu.jsp"%>
<!--Start the content panel-->
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<H1 class="page-header">added song types</h1>
	<form action="/musicstore/admin/doaddsong" method="post"
		enctype="multipart/form-data">
		<div class="row">
			<div class="col-md-3" id="genrelist">
				<c:if test="${!empty genrelist }">
					<c:forEach items="${genrelist}" var="genre">
						<div class="input-group" id="genre">
							<!-- <span class="input-group-btn">
<a class="btn btn-default" type="button">${genre.id }</a>
</span> -->
							<input type="text" class="form-control" value="${genre.name} "
								disabled> <span class="input-group-btn">
								<Button class="btn btn-default" type="button">Edit</button>
							</span>
						</div>
						<!-- /input-group -->
					</c:forEach>
				</c:if>
				<form method="get" action="/musicstore/admin/addgenre" id="genre">
					<div class="input-group " id="addgenre">
						<Input type="text" class="form-control"
							placeholder="song type name" id="genrename"> <span
							class="input-group-btn">
							<Button class="btn btn-default" type="button" id="button">
								added</button>
						</span>

					</div>
					<!-- /input-group -->
					<Label for="desc"> details </label>
					<textarea name="description" class="form-control" id="desc"
						rows="20" id="genredesc"></textarea>
				</form>
			</div>
			<!-- /.col-md-4 -->
		</div>
	</form>
</div>
</div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="/musicstore/js/jquery-1.12.4.min.js"></script>
<script src="/musicstore/js/bootstrap.min.js"></script>
<script>
	$("#button")
			.on(
					"click",
					function() {
						var html1 = "<div class='input-group'><input type='text' class='form-control' value='";
Var html2 = "' disabled><span class='input-group-btn'><button class='btn btn-default' type='button'> Edit </button> </span> </div> <!--/input-group-->";

						$.get("/musicstore/admin/addgenre", {
							"name" : $("#genrename").val(),
							"description" : $("#genredesc").val()
						}, function(data) {
							$("#addgenre").before(
									html1 + $("#genrename").val() + html2);
							$("#genrename").val("");
						});
					});
</script>

</body>
</html>