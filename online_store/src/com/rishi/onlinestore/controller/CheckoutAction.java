package com.rishi.onlinestore.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class CheckoutAction extends ActionSupport {

 

    private HttpServletRequest request;

    private HttpSession session;

    private ServletContext application;

   

    public String checkout1() throws Exception {
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
    
    public String checkout2() throws Exception {
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
    
    
    public String checkout3() throws Exception {
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
    
    public String checkout4() throws Exception {
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
