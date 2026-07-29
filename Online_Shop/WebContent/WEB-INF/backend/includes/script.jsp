<script src="${pageContext.request.contextPath}/assets_backend/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/chart.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/chart-data.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/easypiechart.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/bootstrap-datepicker.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/admin.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/jquery.ui.core.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/jquery.ui.widget.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/jquery.ui.progressbar.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/easypiechart-data.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/jquery.iframe-transport.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/jquery.fileupload.js"></script>
	<script src="${pageContext.request.contextPath}/assets_backend/js/fileupload.js"></script>

 
	<script>
		$('#calendar').datepicker({
		});

		!function ($) {
		    $(document).on("click","ul.nav li.parent > a > span.icon", function(){          
		        $(this).find('em:first').toggleClass("glyphicon-minus");      
		    }); 
		    $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
		}(window.jQuery);

		$(window).on('resize', function () {
		  if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
		})
		$(window).on('resize', function () {
		  if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
		})
	</script>	