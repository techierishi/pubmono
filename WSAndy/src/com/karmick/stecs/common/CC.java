package com.karmick.stecs.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CC {

	public static final String GETINCIDENTS = "http://www.stecsonline.com/portal/webservice/get_student_scenario_list.php";
	public static final String SENDINCIDENTS = "http://www.stecsonline.com/portal/webservice/send_notification.php";

	
	public static boolean iNa(Context ctx) {
		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
