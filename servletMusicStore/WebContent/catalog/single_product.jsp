<%-- 
    Document   : single_product
    Created on : 08-Aug-2015, 16:34:37
    Author     : Rishikesh Jha
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/header.jsp" />

<div id="all">

	<div id="content">
		<div class="container">
			<div class="col-md-3">
				<!-- *** MENUS AND FILTERS ***
 _________________________________________________________ -->
				<div class="panel panel-default sidebar-menu">
					<div class="panel-body">
						<ul class="nav nav-pills nav-stacked category-menu">
							<li></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-9">

				<div class="row" id="productMain">
					<div class="col-sm-6">
						<div id="mainImage">
							<img
								src="${pageContext.request.contextPath}/siteres/img/detailbig3.jpg"
								alt="" class="img-responsive">
						</div>

						<div class="ribbon sale">
							<div class="theribbon">SALE</div>
							<div class="ribbon-background"></div>
						</div>
						<!-- /.ribbon -->

						<div class="ribbon new">
							<div class="theribbon">NEW</div>
							<div class="ribbon-background"></div>
						</div>
						<!-- /.ribbon -->

					</div>
					<div class="col-sm-6">
						<div class="box jAudio_detail_box">



							<div class='jAudio jAudio_detail jAudio-${product.productId}'>

								<audio></audio>

								<div class='jAudio--ui'>

									<div class='jAudio--thumb'></div>

									<div class='jAudio--status-bar'>

										<div class='jAudio--details'></div>
										<div class='jAudio--volume-bar'></div>

										<div class='jAudio--progress-bar'>
											<div class='jAudio--progress-bar-wrapper'>
												<div class='jAudio--progress-bar-played'>
													<span class='jAudio--progress-bar-pointer'></span>
												</div>
											</div>
										</div>

										<div class='jAudio--time'>
											<span class='jAudio--time-elapsed'>00:00</span> <span
												class='jAudio--time-total'>00:00</span>
										</div>

									</div>

								</div>


								<div class='jAudio--playlist'></div>

								<div class='jAudio--controls'>
									<ul>
										<li><button class='jAudio--control jAudio--control-prev'
												data-action='prev'>
												<span></span>
											</button></li>
										<li><button class='jAudio--control jAudio--control-play'
												data-action='play'>
												<span></span>
											</button></li>
										<li><button class='jAudio--control jAudio--control-next'
												data-action='next'>
												<span></span>
											</button></li>
									</ul>
								</div>

								<div class="row">
									<a class="details_a"
										href="${pageContext.request.contextPath}/catalog/product/${product.productId}">View
										detail</a>
								</div>
							</div>

							<script type="text/javascript">
								$(document)
										.ready(
												function() {

													var t = {
														playlist : [ {
															file : "${pageContext.request.contextPath}/<c:url value='uploads/${product.songMp3}' />",
															thumb : "${pageContext.request.contextPath}/<c:url value='uploads/${product.songImg}' />",
															trackName : "${product.albumInformation}",
															trackArtist : "${product.songArtist}",
															trackAlbum : "${product.songGenre}",
														} ]
													}

													$(
															".jAudio-${product.productId}")
															.jAudio(t);

												});
							</script>


							<p class="price">$${product.price}</p>

							<p class="text-center buttons">
								<form method="post" action="<c:url value='/order/addItem'/>"
								class="float-left">
								<input type="hidden" name="productCode"
									value="<c:out value='${product.code}' />" />
            <input type="submit" class="btn btn-primary"
									value="Add to Cart" />
         </form>
								<!-- <a href="basket.html" class="btn btn-primary"><i
									class="fa fa-shopping-cart"></i> Add to cart</a> <a
									href="basket.html" class="btn btn-default"><i
									class="fa fa-heart"></i> Add to wishlist</a> -->
							</p>
							


						</div>

						<div class="row" id="thumbs">
							<div class="col-xs-4">
								<a
									href="${pageContext.request.contextPath}/siteres/img/detailbig1.jpg"
									class="thumb"> <img
									src="${pageContext.request.contextPath}/siteres/img/detailsquare.jpg"
									alt="" class="img-responsive">
								</a>
							</div>
							<div class="col-xs-4">
								<a
									href="${pageContext.request.contextPath}/siteres/img/detailbig2.jpg"
									class="thumb"> <img
									src="${pageContext.request.contextPath}/siteres/img/detailsquare2.jpg"
									alt="" class="img-responsive">
								</a>
							</div>
							<div class="col-xs-4">
								<a
									href="${pageContext.request.contextPath}/siteres/img/detailbig3.jpg"
									class="thumb active"> <img
									src="${pageContext.request.contextPath}/siteres/img/detailsquare3.jpg"
									alt="" class="img-responsive">
								</a>
							</div>
						</div>
					</div>

				</div>








			</div>

		</div>
	</div>
</div>












<%-- <div class="container push_down">
   <div class="row">
      <div class="col-sm-8">
         <h1>${product.artistName}</h1>
         <h3>${product.albumName}</h3>
         <p class="larger_p">${product.albumInformation}</p>
      </div>
      <div class="col-sm-4">
         <img class="img-thumbnail" src="<c:url value='${product.imageUrl}' />" alt="Cover Image" />
      </div>
   </div>
   <div class="row push_down">
      <div class="col-sm-8">
      </div>
      <div class="col-sm-4">
         <p class="price">${product.priceCurrencyFormat}</p>
         <form method="post" action="<c:url value='/order/addItem'/>" class="float-left">
            <input type="hidden" name="productCode" value="<c:out value='${product.code}' />" />
            <input type="submit" class="btn btn-primary" value="Add to Cart" />
         </form>
         <a href="<c:url value='/catalog/product/${product.code}/listen' />" class="btn btn-default">Listen to Samples</a>
      </div>
   </div>
</div> --%>

<jsp:include page="/footer.jsp" />