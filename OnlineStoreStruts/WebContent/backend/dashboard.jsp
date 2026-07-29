<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="commons/head.jsp"></jsp:include>

        <body class="hold-transition skin-blue sidebar-mini">
            <div class="wrapper">

            <jsp:include page="commons/header.jsp"></jsp:include>

                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="commons/sidebar.jsp"></jsp:include>
                <!-- Content Wrapper. Contains page content -->
                <div class="content-wrapper">
                    <!-- Content Header (Page header) -->
                    <section class="content-header">
                        <h1>
                            Dashboard
                            <small>Control panel</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                            <li class="active">Dashboard</li>
                        </ol>
                    </section>

                    <!-- Main content -->
                    <section class="content">

                    </section><!-- /.content -->
                </div><!-- /.content-wrapper -->

            <jsp:include page="commons/footer.jsp"></jsp:include>


                <!-- Control Sidebar -->
            <jsp:include page="commons/common_sidebar.jsp" ></jsp:include>
                <!-- Add the sidebar's background. This div must be placed
                     immediately after the control sidebar -->
                <div class="control-sidebar-bg"></div>
            </div><!-- ./wrapper -->

        <jsp:include page="commons/script.js" ></jsp:include>
    </body>
</html>
