<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
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
                            Category
                        </h1>
                        <ol class="breadcrumb">
                            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                            <li class="active">Add Category</li>
                        </ol>
                    </section>

                    <!-- Main content -->
                    <section class="content">
                        <div class="row">
                            <!-- left column -->
                            <div class="col-md-6">
                                <!-- general form elements -->
                                <div class="box box-primary">
                                    <div class="box-header with-border">
                                        <h3 class="box-title">Add Category</h3>
                                    </div><!-- /.box-header -->
                                    <!-- form start -->
                                    <form role="form" method="post" action="categoryadd">
                                        <div class="box-body">
                                            <div class="form-group">
                                                <label for="exampleInputEmail1">Category Name</label>
                                                <input type="text" class="form-control" id="category" name="category.category_name" placeholder="Enter category name">
                                            </div>

                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox"> Active
                                                </label>
                                            </div>
                                        </div><!-- /.box-body -->

                                        <div class="box-footer">
                                            <button type="submit" class="btn btn-primary">Submit</button>
                                        </div>
                                    </form>
                                </div><!-- /.box -->

                            </div><!--/.col (left) -->
                            <!-- right column -->
                            <div class="col-md-6">
                                <div class="box">
                                    <div class="box-header">
                                        <h3 class="box-title">Categories</h3>
                                        <div class="box-tools">
                                            <ul class="pagination pagination-sm no-margin pull-right">
                                                <li><a href="#">«</a></li>
                                                <li><a href="#">1</a></li>
                                                <li><a href="#">2</a></li>
                                                <li><a href="#">3</a></li>
                                                <li><a href="#">»</a></li>
                                            </ul>
                                        </div>
                                    </div><!-- /.box-header -->
                                    <div class="box-body no-padding">
                                        <table class="table">
                                            <tbody>
                                            <s:iterator value="categoryList">
                                                <tr>
                                                    <th style="width: 10px">#</th>
                                                    <th><s:property value="category_name"/></th>
                                                </tr>
                                            </s:iterator>
                                        </tbody></table>
                                </div><!-- /.box-body -->
                            </div><!-- /.box -->
                        </div><!--/.col (right) -->
                    </div>   <!-- /.row -->
                </section><!-- /.content -->

            </div><!-- /.content-wrapper -->

            <jsp:include page="commons/footer.jsp"></jsp:include>


                <!-- Control Sidebar -->
            <jsp:include page="commons/common_sidebar.jsp" ></jsp:include>
                <!-- Add the sidebar's background. This div must be placed
                     immediately after the control sidebar -->
                <div class="control-sidebar-bg"></div>
            </div><!-- ./wrapper -->

        <jsp:include page="commons/script.jsp" ></jsp:include>
    </body>
</html>
