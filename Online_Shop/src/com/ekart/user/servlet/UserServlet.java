package com.ekart.user.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ekart.user.dao.CustomerDao;
import com.ekart.user.dao.impl.CustomerDaoImpl;
import com.ekart.user.entity.Customer;
import com.ekart.util.Const;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pathInfo = request.getPathInfo();
		if (null != pathInfo && pathInfo.equalsIgnoreCase("/register")) {
			try {

				request.getRequestDispatcher(Const.SITE + "register.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (null != pathInfo && pathInfo.equalsIgnoreCase("/login")) {
			try {

				request.getRequestDispatcher(Const.SITE + "login.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (null != pathInfo && pathInfo.equalsIgnoreCase("/logout")) {
			try {

				HttpSession session = request.getSession();
				session.invalidate();
				
				response.sendRedirect(getServletContext().getContextPath()+"?msg=Successfully logged out..");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			CustomerDao customerDao = new CustomerDaoImpl();
			HttpSession session = request.getSession(true);
			String pathInfo = request.getPathInfo();
			if (null != pathInfo && pathInfo.equalsIgnoreCase("/register")) {

				Customer cust = new Customer();

				cust.setName(request.getParameter("fname") + " " + request.getParameter("lname"));
				cust.setEmail(request.getParameter("email"));
				cust.setPhone(request.getParameter("phone"));
				cust.setPassword(request.getParameter("password"));
				cust.setAddress(request.getParameter("address"));
				cust.setState(request.getParameter("state"));
				cust.setCity(request.getParameter("city"));
				cust.setZip(request.getParameter("zip"));

				int ret = customerDao.insert(cust);
				response.sendRedirect(request.getContextPath() + "/user/register?msg=Registration Successfull");
				
			} else if (null != pathInfo && pathInfo.equalsIgnoreCase("/login")) {
				String username = request.getParameter("name");
				String password = request.getParameter("password");
				Customer cObj = customerDao.login(username, password);
				session.setAttribute("customer", cObj);
				response.sendRedirect(request.getContextPath() + "/customer/cart/list?msg=Login Successfull");

			}
		} catch (Exception ex) {
		}
	}

}
