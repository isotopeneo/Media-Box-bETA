package com.isotopeneo.mediaboxbeta.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.isotopeneo.mediaboxbeta.util.LoggerClass;

public class TVShowRequestExecutor {
	
	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	public InputStream makeRequest(String queryURL) {
		try {
			LoggerClass.log(queryURL);
			URL url = new URL(queryURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			InputStream is = conn.getInputStream();
			if (null == is){
				LoggerClass.log("null the first time, so trying again");
				return conn.getInputStream();
			} else {
				return is;
			}
			
		} catch (IOException e) {
			
		}
		return null;
	}
}
