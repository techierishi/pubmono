package com.pavitra.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Const {
	public static final String BAKEND="/WEB-INF/backend/"; 
	public static final String SITE="/WEB-INF/site/"; 
	public static final String UPLOADS="/uploads/"; 

	public static boolean isLin(HttpServletRequest request){

		boolean isLoggedIn = false;
		HttpSession hsession = request.getSession();
		if (hsession != null) {
			if (hsession.getAttribute("customer") != null) {
				isLoggedIn = true;
			}
		}
		
		return isLoggedIn;
	}

}
