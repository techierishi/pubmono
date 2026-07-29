<%-- 
    Document   : index
    Created on : 15-Aug-2015, 15:16:28
    Author     : Rishikesh Jha
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/header.jsp" />

<div class="container">
	<div class="page-header centre-align">
		<h1>Subscribe to our newsletter!</h1>
	</div>
	<p class="larger_p centre-align">If you would like to receive
		announcements about new releases and special offers, please fill the
		form below.</p>
	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
			<c:if test="${message != null}">
				<div class="alert alert-info" role="alert">
					<p>${message}</p>
				</div>
			</c:if>
			<form class=""
				action="${pageContext.request.contextPath}/admin/add_product_file"
				id="" method="post" enctype="multipart/form-data">
				<div class="col-md-6">




					<div class="form-group">
						<label>Song Name</label> <input type="text" name="product_name"
							class="form-control">
					</div>

					<div class="form-group">
						<label>Song Price</label> <input type="text" name="product_price"
							class="form-control">
					</div>

					<div class="form-group">
						<label>Album Information</label> <input type="text"
							name="product_album" class="form-control">
					</div>

					<div class="form-group">
						<label>Description</label> <input type="text" name="product_descr"
							class="form-control">
					</div>
					<div class="form-group">
						<label>Code</label> <input type="text" name="product_code"
							class="form-control">
					</div>
					
					<div class="form-group">
						<label>Genre</label> <input type="text" name="product_genre"
							class="form-control">
					</div>

					<div class="form-group">
						<label>Artist</label> <input type="text" name="product_artist"
							class="form-control">
					</div>

					<div class="form-group">
						<label>Product Images</label> <input id="imagefileupload"
							type="file" name="pimage">


					</div>
					<div class="form-group">
						<label>Product Mp3</label> <input id="mp3fileupload" type="file"
							name="pmp3">


					</div>



					<button type="submit" class="btn btn-primary">Submit
						Button</button>
					<button type="reset" class="btn btn-default">Reset Button</button>


				</div>

			</form>
		</div>
	</div>
</div>

<jsp:include page="/footer.jsp" />

