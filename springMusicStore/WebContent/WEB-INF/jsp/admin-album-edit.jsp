<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin-left-menu.jsp"%>
<!--Start the content panel-->
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<H1 class="page-header">Edit song</h1>
	<form action="/musicstore/admin/doeditsong" method="post"
		enctype="multipart/form-data">
		<div class="row">
			<div class="col-md-3">
				<fieldset>
					<input name="id" type="hidden" class="form-control" id="id"
						value="${album.id }">
					<div class="form-group">
						<Label for="title"> song </label> <input name="title" type="text"
							class="form-control" id="title" value="${album.title }">
					</div>
					<div class="form-group">
						<Label for="artist"> singer </label> <input name="artist"
							type="text" class="form-control" id="artist"
							value="${album.artist }">
					</div>
					<div class="form-group">
						<Label for="dateReleased"> dates </label> <input
							name="dateReleased" type="text" class="form-control"
							id="dateReleased" value="${album.dateReleased }">
					</div>
					<div class="form-group">
						<Label for="price"> price </label> <input name="price"
							type="text" class="form-control" id="price"
							value="${album.price }">
					</div>
					<div class="form-group">
						<Label for="genreid" class="sr-only"> song types </label> <Input
							name="genreid" type="hidden" class="form-control" id="genreid"
							placeholder="song types" value="2">
					</div>
					<div class="form-group">
						<Label for="genreid" class=""> song types </label> <select
							class="form-control" name="genreid">
							<c:if test="${!empty genrelist }">
								<c:forEach items="${genrelist }" var="genre">
									<c:choose>
										<c:when test="${genre.id == album.genreid }">
											<option value="${genre.id }" selected>${genre.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${genre.id }">${genre.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:if>
						</select>
					</div>
					<div class="form-group">
						<Label for="title" class="sr-only"> song </label> <input
							name="picPath" type="hidden" value="${album.picPath }" />
					</div>
					<Button type="submit" class="btn btn-default">Save</button>
				</fieldset>
			</div>
			<div class="col-md-3">
				<fieldset>
					<div class="form-group">
						<Label for="album-cover"> upload cover </label>
						<div class="input-upload">
							<input name="file" type="file" id="album-cover">
							<P class="help-block">Allow uploaded files in JPG,PNG format
							</p>
						</div>
					</div>
					<div class="">
						<a href="#" class="thumbnail"> <img src="${album.picPath }"
							alt="">
						</a>
					</div>
					<!--<div class="form-group">
<Label for= "album-song" > upload song files </label>
<div class="input-upload">
<input type="file" id="album-song">
<P class= "help-block" > allows to upload mp3 format files </p>
</div>
</div> -->
				</fieldset>
			</div>
			<div class="col-md-5 ">
				<Label for="desc"> details </label>
				<textarea name="description" class="form-control" id="desc"
					rows="20">
${album.description}
</textarea>
			</div>
		</div>

	</form>
</div>
</div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>