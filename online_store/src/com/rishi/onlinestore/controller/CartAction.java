package com.rishi.onlinestore.controller;

import com.opensymphony.xwork2.ActionContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.rishi.onlinestore.model.Cart;
import com.rishi.onlinestore.model.User;
import com.rishi.onlinestore.service.CartService;
import java.util.List;
import java.util.Map;

public class CartAction extends ActionSupport {

    Cart cart;
    private HttpServletRequest request;

    private HttpSession session;

    private ServletContext application;

    public String showcart() throws Exception {
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

    public String addtocart() throws Exception {
        this.request = ServletActionContext.getRequest();
        this.session = this.request.getSession();

        String ret = "";

        if (null!=cart) {
            Map session = ActionContext.getContext().getSession();
            User _user = (User) session.get("user");
            
            cart.setUser_id(_user.getId());
            
            CartService cartService = new CartService();
            cartService.addToCart(cart);
            
            
            List<Cart> cartList = cartService.getCartByUserId(_user.getId()+"");
                    
            ret = SUCCESS;
        } else {
            ret = SUCCESS;
        }

        return ret;

    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

}
