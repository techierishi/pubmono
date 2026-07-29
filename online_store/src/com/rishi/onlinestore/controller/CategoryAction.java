package com.rishi.onlinestore.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class CategoryAction extends ActionSupport {

 

    private HttpServletRequest request;

    private HttpSession session;

    private ServletContext application;

   

    public String category() throws Exception {
        this.request = ServletActionContext.getRequest();
        this.session = this.request.getSession();

        String ret = "";

        
        if (true) {
            //request.getSession().setAttribute("user", "");
            ret = SUCCESS;
        } else {
            ret = ERROR;
        }

        return ret;

    }

}
