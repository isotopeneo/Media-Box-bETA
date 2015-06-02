package com.isotopeneo.mediaboxbeta.util;

import android.util.Log;

public class LoggerClass {
	
	private static final String TAG = "com.isotopeneo.iwatchonlinebeta";
	
	public static void log(String msg) {
		if (null != msg) {
			//Log.d(TAG, msg);
		}
	}
	
	public static String printReturn(String msg) {
		if (null != msg) {
			//Log.d(TAG, msg);
			return msg;
		} else {
			return "";
		}
	}
}
