package com.websocket.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserServlet() {
		super();
	}

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
		} else if (null != pathInfo && pathInfo.equalsIgnoreCase("/home")) {
			try {
				request.getRequestDispatcher(Const.SITE + "home.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (null != pathInfo && pathInfo.equalsIgnoreCase("/online_users")) {
			try {
				
				List<User> _userList= DBUtil.onlineUsers(null);
				Gson gson = new Gson();
				String jsonUsers = gson.toJson(_userList);
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print(jsonUsers);
				out.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);
			String pathInfo = request.getPathInfo();
			if (null != pathInfo && pathInfo.equalsIgnoreCase("/register")) {

				User cust = new User();

				cust.setUsername(request.getParameter("user"));
				cust.setEmail(request.getParameter("email"));
				cust.setPassword(request.getParameter("pass"));

				DBUtil.insertUser(cust);
				response.sendRedirect(request.getContextPath() + "/user/register?msg=Registration Successfull");

			} else if (null != pathInfo && pathInfo.equalsIgnoreCase("/login")) {

				User cust = new User();

				cust.setUsername(request.getParameter("user"));
				cust.setPassword(request.getParameter("pass"));
				cust = DBUtil.loginUser(cust);

				session.setAttribute("user", cust);
				if (null != cust)
					response.sendRedirect(request.getContextPath() + "/user/home?connect=" + cust.getUsername());
				else
					response.sendRedirect(request.getContextPath() + "/user/login?msg=Login Failed");

			}
		} catch (Exception ex) {
		}
	}

}
