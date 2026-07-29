<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Direct Messaging</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

	<div class="wrapper">
		<div class="container">
			<div class="left">
				<div class="top">
					<input type="text" /> <a href="javascript:;" class="search"></a>
				</div>
				<ul class="people">
					
				</ul>
			</div>
			<div class="right">
				<div class="top">
					<span>To: <span class="name">__</span></span>
				</div>
				<div class="chat" data-chat="person1">
					<div class="conversation-start">
						<span>__</span>
					</div>
					
				</div>
				<div class="write">
					<a href="javascript:;" class="write-link attach"></a> <input
						type="hidden" size="15" id="to" value="" /> <input type="text" id="msg"
						placeholder="Message" /> <a href="avascript:void(0);"
						class="write-link smiley"></a> <a href="javascript:void(0);"
						onclick="send();" class="write-link send"></a>
				</div>
			</div>
		</div>
	</div>

	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>


	<script src="${pageContext.request.contextPath}/js/script.js"></script>

	<script>
		$(document).ready(function() {
			connect('<%=request.getParameter("connect")%>');

			$.ajax({
				url : "${pageContext.request.contextPath}/user/online_users",
				success : function(result) {
					$.each(result, function(i, item) {
						console.log(item);
						$('.people').append('<li class="person" data-chat="person1">'+
						'<img src="${pageContext.request.contextPath}/images/user_icon.png"alt="" /> '+
						'<span class="name">'+item.username+'</span> '+
						'<span class="time">__</span> <span class="preview">'+item.email+'</span></li>')
					});
				}
			});

		});
	</script>
	<script src="${pageContext.request.contextPath}/js/index.js"></script>
	

</body>
</html>
