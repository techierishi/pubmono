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
                            Product
                            <small>Add Product</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                            <li class="active">Add Product</li>
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
                                        <h3 class="box-title">Quick Example</h3>
                                    </div><!-- /.box-header -->
                                    <!-- form start -->
                                    <form role="form" action="productadd" method="post" enctype="multipart/form-data">
                                        <div class="box-body">
                                            <div class="form-group">
                                                <label for="product_name">Product Name</label>
                                                <input type="text" class="form-control" name="product.product_name" id="product_name" placeholder="Enter Product Name">
                                            </div>
                                            <div class="form-group">
                                                <label for="product_price">Product Price</label>
                                                <input type="text" class="form-control" name="product.product_price" id="product_price" placeholder="Enter Product Price">
                                            </div>
                                            <div class="form-group">
                                                <label for="product_price">Product Quantity</label>
                                                <input type="text" class="form-control" name="product.product_qty" id="product_price" placeholder="Enter Product Quantity">
                                            </div>
                                            <div class="form-group">
                                                <label>Select</label>
                                                <select class="form-control" name="categories" multiple>
                                                <s:iterator value="categoryList">
                                                    <option value="<s:property value="category_id"/>"><s:property value="category_name"/></option>
                                                </s:iterator>

                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="productImage">Product Image</label>
                                            <input type="file" name="productImage" id="exampleInputFile">
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
