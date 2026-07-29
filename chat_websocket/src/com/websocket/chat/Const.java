package com.websocket.chat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Const {
	public static final String SITE="/WEB-INF/jsp/"; 
	public static final String UPLOADS="/uploads/"; 

	public static boolean isLin(HttpServletRequest request){

		boolean isLoggedIn = false;
		HttpSession hsession = request.getSession();
		if (hsession != null) {
			if (hsession.getAttribute("user") != null) {
				isLoggedIn = true;
			}
		}
		
		return isLoggedIn;
	}

}
