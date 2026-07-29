/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekart.admin.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ekart.admin.dao.AdminDAO;
import com.ekart.admin.dao.impl.AdminDAOImpl;
import com.ekart.admin.entity.Admin;
import com.ekart.util.Const;

/**
 *
 * @author Rishikesh
 */
@WebServlet(urlPatterns = "/admin/*")
public class AdminLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (null != pathInfo && pathInfo.equalsIgnoreCase("/login")) {
			req.getRequestDispatcher(Const.BAKEND + "login.jsp").forward(req, resp);
		} else if (null != pathInfo && pathInfo.equalsIgnoreCase("/logout")) {
			HttpSession session = req.getSession(true);
			session.invalidate();
			resp.sendRedirect(req.getContextPath());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AdminDAO AdminDAO = new AdminDAOImpl();
		Admin admin = null;
		try {
			String pathInfo = req.getPathInfo();
			if (null != pathInfo && pathInfo.equalsIgnoreCase("/login")) {
				admin = AdminDAO.authenticate(req.getParameter("email"), req.getParameter("password"));
				if (admin != null) {
					HttpSession session = req.getSession(true);
					session.setAttribute("adminLogin", true);
					session.setAttribute("admin", admin);
					resp.sendRedirect(req.getContextPath() + "/backend/category/list");
				} else {
					resp.sendRedirect(req.getContextPath() + "/admin/login?error=loginError");
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(AdminLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(AdminLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
