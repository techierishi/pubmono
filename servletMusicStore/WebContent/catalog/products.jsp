<%-- 
    Document   : products
    Created on : 07-Aug-2015, 15:37:08
    Author     : Rishikesh Jha
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/header.jsp" />

<!-- *** NAVBAR END *** -->

<div id="all">

	<div id="content">
		<div class="container">
			<div class="col-md-12">

				<c:choose>
					<c:when test="${products == null}">
						<div class="page-header">
							<h3>No products found</h3>
						</div>
						<p>Unfortunately there are no product available for purchase
							right now. Please visit our shop later.</p>
					</c:when>
					<c:otherwise>
						<div class="page-header centre-align">
							<h3>The Music Store Catalog</h3>
						</div>
						<div class="row products">

							<c:forEach var="product" items="${products}">

								<div class='jAudio jAudio-${product.productId}'>

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
										<a class="details_a" href="${pageContext.request.contextPath}/catalog/product/${product.code}">View detail</a>
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



							</c:forEach>
						</div>
			</div>
			</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

<jsp:include page="/footer.jsp" />