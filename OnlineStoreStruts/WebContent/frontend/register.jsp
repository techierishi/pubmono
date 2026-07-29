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

                        <form action="<%=context %>/from/user_register"  method="post">
                            
                            <table    border="1">
                                
                                <tr><td>用户名：<td> <input type="text" name="name"></td></tr>
                                <tr><td>密码：<td> <input type="password" name="password1"></td></tr>
                                <tr><td>确定密码：<td> <input type="password" name="password2"></td></tr>
                                <tr><td>地址：<td> <input type="text" name="address"></td></tr>
                                <tr><td>邮编：<td> <input type="text" name="postCode"></td></tr>
                                <tr><td>E-Mail：<td> <input type="text" name="email"></td></tr>
                                <tr><td>家庭电话号码：<td> <input type="text" name="homePhone"></td></tr>
                                <tr><td>手机号码：<td> <input type="text" name="cellPhone"></td></tr>
                                <tr><td>办公室号码：<td> <input type="text" name="officePhone"></td></tr>
                                <tr>
                                    <td>验证码：<td> <input type="text" name="rand" size="4">
                                        
                                        &nbsp;
                                        
                                        <img  src="<c:url value="/from/user_code"/>"/>
                                        
                                    </td>
                                    
                                </tr>
                                <tr>
                                    
                                    <td colspan="2" align="center">
                                        
                                        <input type="submit" value="注册">
                                        
                                        <input type="reset" value="重置">
                                        
                                    </td>
                                    
                                </table>
                                
                                
                                
                                
                            </form>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="box">
                            <s:form action="addUser">
                            <s:textfield name="name" label="User Name" />
                            <s:password name="password" label="Password" />
                            <s:radio name="gender" label="Gender" list="{'Male','Female'}" />
                            <s:select name="country" list="{'India','USA','UK'}" headerKey=""
                            headerValue="Country" label="Select a country" />
                            <s:textarea name="aboutYou" label="About You" />
                            <s:checkbox name="mailingList"
                            label="Would you like to join our mailing list?" />
                            <s:submit />
                        </s:form>

                        <s:if test="userList.size() > 0">
                        <div class="content">
                            <table class="userTable" cellpadding="5px">
                                <tr class="even">
                                    <th>Name</th>
                                    <th>Gender</th>
                                    <th>Country</th>
                                    <th>About You</th>
                                    <th>Mailing List</th>
                                </tr>
                                <s:iterator value="userList" status="userStatus">
                                <tr
                                class="<s:if test="#userStatus.odd == true ">odd</s:if><s:else>even</s:else>">
                                <td><s:property value="email" /></td>
                                <td><s:property value="gender" /></td>
                                <td><s:property value="country" /></td>
                                <td><s:property value="aboutYou" /></td>
                                <td><s:property value="mailingList" /></td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </s:if>
        </div>
    </div>


</div>
<!-- /.container -->
</div>
<!-- /#content -->
</div>
<!-- /#all -->
<jsp:include page="commons/footer.jsp"></jsp:include>
</body>

</html>