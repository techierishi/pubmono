<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<% String context = request.getContextPath(); %>
<html lang="en">
    <jsp:include page="commons/head.jsp"></jsp:include>
        <body>
        <jsp:include page="commons/topbar.jsp"></jsp:include>
        <jsp:include page="commons/navbar.jsp"></jsp:include>

            <div id="all">

                <div id="content">
                    <div class="container">

                        <div class="col-md-12">

                            <ul class="breadcrumb">
                                <li><a href="#">Home</a>
                                </li>
                                <li>New account / Sign in</li>
                            </ul>

                        </div>

                        <div class="col-md-6">
                            <div class="box">
                                <h1>New account</h1>

                                <p class="lead">Not our registered customer yet?</p>
                                <p>With registration with us new world of fashion, fantastic discounts and much more opens to you! The whole process will not take you more than a minute!</p>
                                <p class="text-muted">If you have any questions, please feel free to <a href="contact.html">contact us</a>, our customer service center is working for you 24/7.</p>

                                <hr>

                                <form action="userregister" method="post">
                                    <div class="form-group">
                                        <label for="name">Name</label>
                                        <input class="form-control" id="name" name="user.name" type="text">
                                    </div>
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input class="form-control" id="email" name="user.email" type="text">
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Password</label>
                                        <input class="form-control" id="password" name="user.password" type="password">
                                    </div>
                                    <div class="text-center">
                                        <button type="submit" class="btn btn-primary"><i class="fa fa-user-md"></i> Register</button>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="box">
                                <h1>Login</h1>

                                <p class="lead">Already our customer?</p>
                                <p class="text-muted">Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies
                                    mi vitae est. Mauris placerat eleifend leo.</p>

                                <hr>

                                <form action="userlogin" method="post">
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input class="form-control" id="email" name="user.email" type="text">
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Password</label>
                                        <input class="form-control" id="password" name="user.password" type="password">
                                    </div>
                                    <div class="text-center">
                                        <button type="submit" class="btn btn-primary"><i class="fa fa-sign-in"></i> Log in</button>
                                    </div>
                                </form>
                            </div>
                        </div>


                    </div>
                    <!-- /.container -->
                </div>
                <!-- /#content -->
            </div>
            <!-- /#all -->
        <jsp:include page="commons/footer.jsp"></jsp:include>
        <jsp:include page="commons/script.jsp"></jsp:include>

            <script>

                var json_errs = <s:property value="%{validation_messages}" escape="false" />;

                //var json_errs = JSON.parse(errs);

                if (json_errs.length > 0) {
                   
                    for (var i = 0; i < json_errs.length; i++) {

                        var a = $.floatingMessage("" + json_errs[i], {
                            position: "top-right",
                            height: 40,
                            show: "blind",
                            hide: "puff",
                            stuffEaseTime: 100,
                            stuffEasing: "easeInExpo",
                            moveEaseTime: 200,
                            moveEasing: "easeOutBounce"
                        });

//                        setTimeout(function () {
//                            a.floatingMessage("destroy");
//                        }, 3000);
                    }
                }


        </script>
    </body>

</html>