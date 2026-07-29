package com.pavitra.util;
import java.util.UUID;

public class StringUtil {
	
	public static String getRandomStr(){
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

}
