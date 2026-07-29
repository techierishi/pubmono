package com.rishi.onlinestore.controller;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.rishi.onlinestore.lib.DBG;
import com.rishi.onlinestore.model.User;
import com.rishi.onlinestore.service.UserService;
import com.rishi.onlinestore.util.ValidateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserAction extends ActionSupport {

    private Map<String, Object> session;
    private User user = new User();

    private UserService userService;
    private HttpServletRequest request;
    private ServletContext application;
    private String validation_messages;

    public String getValidation_messages() {
        return validation_messages;
    }

    public void setValidation_messages(String validation_messages) {
        this.validation_messages = validation_messages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String login() throws Exception {
        this.request = ServletActionContext.getRequest();

        String ret = "";

        userService = new UserService();
        boolean result = userService.authenticateUser(user.getEmail(), user.getPassword());
        User _user = userService.getUserByUserId(user.getEmail());

        Map session = ActionContext.getContext().getSession();
        session.put("user", _user);
        if (result == true) {
            ret = "profile";
        } else {
            ret = "login";
        }

        return ret;

    }

    public String profile() throws Exception {
        this.request = ServletActionContext.getRequest();

        String ret = "";

        userService = new UserService();
        Map session = ActionContext.getContext().getSession();

        User _user = (User) session.get("user");
        if (_user != null) {
            ret = "profile";
        } else {
            ret = "profile";
        }

        return ret;

    }

    public String register() {
        String ret = "register";

        try {

            if (null != user && null != user.getEmail()) {
                List<String> errors = validation(user);

                DBG.d("ERRORS", "" + errors.toString());

                if (errors.size() <= 0) {
                    userService = new UserService();

                    boolean result = userService.register(user);
                } else {
                    String errs = new Gson().toJson(errors);
                    DBG.d("ERRORS 2", "" + errs);

                    setValidation_messages(errs);
                    DBG.d("ERRORS 3", "" + getValidation_messages());

                }

                ret = "register";

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;

    }

    private List<String> validation(User _user) {
        userService = new UserService();

        List<String> errors = new ArrayList<>();

        if (null != _user) {
            DBG.d("ERRORS", "User not null ! ");

            if (!(null != _user.getEmail() && !_user.getEmail().trim().isEmpty())) {
                errors.add("Email cannot be empty!");
            } 
            
            if (ValidateUtil.isValidEmailAddress(_user.getEmail())) {
                errors.add("Please enter valid email!");

            }
            if (!(null != _user.getPassword() && !_user.getPassword().trim().isEmpty())) {
                errors.add("Password cannot be empty!");
            }
            if (userService.isUserExists(_user)) {
                errors.add("User already exist!");
            }
        } else {
            errors.add("Some error occured !");
        }

        return errors;
    }

}
