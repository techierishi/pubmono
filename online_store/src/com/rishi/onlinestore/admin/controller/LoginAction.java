package com.rishi.onlinestore.admin.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.opensymphony.xwork2.ActionSupport;
import com.rishi.onlinestore.model.User;
import com.rishi.onlinestore.admin.service.LoginService;

public class LoginAction extends ActionSupport {

    private User user;
    private LoginService userService;
    private HttpServletRequest request;
    private HttpSession session;
    private ServletContext application;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String login() throws Exception {

        String ret = "";

        if (null != user) {
            ret = SUCCESS;
        } else {

            LoginService loginService = new LoginService();
            loginService.authenticateUser(user.getEmail(), user.getPassword());
            ret = "profile";
        }

        return ret;

    }

}
