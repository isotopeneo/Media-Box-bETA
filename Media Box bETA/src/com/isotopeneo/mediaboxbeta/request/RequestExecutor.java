package com.isotopeneo.mediaboxbeta.request;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;

import com.isotopeneo.mediaboxbeta.util.LoggerClass;

public class RequestExecutor {

	private String requestURL;

	public RequestExecutor(String requestURL) {
		this.requestURL = requestURL;
	}

	public String makeRequest() {
		LoggerClass.log("makeRequest");
		InputStream inputStream = null;
		StringWriter stringWriter = new StringWriter();
		try {
			URL url = new URL(requestURL);
			inputStream = url.openStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, Charset.forName("UTF-8"));
			char[] buffer = new char[8192];
			// Yes this is an 'Unnecessary buffer' (=twice the memory movement)
			// but Java Streams need an intermediate array
			// to bulk copy bytes& chars
			// Cant glue InputStreamReader directly to a StringWriter unless you
			// want to move 1 char at a time
			int count;
			while ((count = inputStreamReader.read(buffer, 0, buffer.length)) != -1) {
				stringWriter.write(buffer, 0, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception ignored) {

			}
		}
		return stringWriter.toString();
	}

}
