package com.isotopeneo.mediaboxbeta.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.net.ParseException;

import com.isotopeneo.mediaboxbeta.util.Constants;
import com.isotopeneo.mediaboxbeta.util.LoggerClass;

public class JSONPostRequestExecutor {

	private String responseText;
	private final String url = "http://www.iwatchonline.to/api.json";
	private final static String IWO_API_KEY = "IWO-API-KEY";
	private final static String TYPE = "type";
	private final static String SHOW = "show";
	private final static String IWO_ID = "iwo_id";
	private final static String TV_RAGE_ID = "tvrage_id";
	private final static String EPISODE = "episode";
	private final static String SEASON_NUM = "season_num";
	private final static String EPISODE_NUM = "episode_num";
	
	public String makeRequest(String imdbID) {
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppostreq = new HttpPost(url);
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			LoggerClass.log("imdbID is " + imdbID);
			pairs.add(new BasicNameValuePair("IWO-API-KEY", "be993610b4e56af3264b4dfeb710cf320ac71902"));
			pairs.add(new BasicNameValuePair("type", "movie"));
			pairs.add(new BasicNameValuePair("iwo_id", ""));
			pairs.add(new BasicNameValuePair("imdb_id", imdbID));
			httppostreq.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = httpclient.execute(httppostreq);
			responseText = EntityUtils.toString(response.getEntity());
			LoggerClass.log("**********************************");
			LoggerClass.log(responseText);
			LoggerClass.log("**********************************");
		} catch (UnsupportedEncodingException e) {
			LoggerClass.log(e.getMessage());
		} catch (ClientProtocolException e) {
			LoggerClass.log(e.getMessage());
		} catch (IOException e) {
			LoggerClass.log(e.getMessage());
		} catch (ParseException e) {
			LoggerClass.log(e.getMessage());
		}
		return responseText;
	}
	
	public String configureTVShowInfoRequestAndMakeRequest(String tvRageID) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		LoggerClass.log("tvRage id is " + tvRageID);
		pairs.add(new BasicNameValuePair(IWO_API_KEY, Constants.KEY));
		pairs.add(new BasicNameValuePair(TYPE, SHOW));
		pairs.add(new BasicNameValuePair(IWO_ID, ""));
		pairs.add(new BasicNameValuePair(TV_RAGE_ID, tvRageID));
		return makeTheRequest(pairs);
	}
	
	public String configureEpisodeLinksRequestAndMakeRequest(String tvRageID, String season, String episode) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		LoggerClass.log("tvRage id is " + tvRageID);
		LoggerClass.log("season is " + season);
		LoggerClass.log("episode is " + episode);
		pairs.add(new BasicNameValuePair(IWO_API_KEY, Constants.KEY));
		pairs.add(new BasicNameValuePair(TYPE, EPISODE));
		pairs.add(new BasicNameValuePair(IWO_ID, ""));
		pairs.add(new BasicNameValuePair(TV_RAGE_ID, tvRageID));
		pairs.add(new BasicNameValuePair(SEASON_NUM, season));
		pairs.add(new BasicNameValuePair(EPISODE_NUM, episode));
		return makeTheRequest(pairs);
	}
	
	public String makeTheRequest(List<NameValuePair> pairs) {
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppostreq = new HttpPost(url);
			
			httppostreq.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = httpclient.execute(httppostreq);
			responseText = EntityUtils.toString(response.getEntity());
			LoggerClass.log("**********************************");
			LoggerClass.log(responseText);
			LoggerClass.log("**********************************");
		} catch (UnsupportedEncodingException e) {
			LoggerClass.log(e.getMessage());
		} catch (ClientProtocolException e) {
			LoggerClass.log(e.getMessage());
		} catch (IOException e) {
			LoggerClass.log(e.getMessage());
		} catch (ParseException e) {
			LoggerClass.log(e.getMessage());
		} catch (Exception e) {
			LoggerClass.log(e.getMessage());
		}
		return responseText;
	}
}
