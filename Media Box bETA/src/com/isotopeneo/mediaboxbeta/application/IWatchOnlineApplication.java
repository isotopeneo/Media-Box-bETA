package com.isotopeneo.mediaboxbeta.application;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.isotopeneo.mediaboxbeta.bean.Movie;
import com.isotopeneo.mediaboxbeta.bean.TVShow;
import com.isotopeneo.mediaboxbeta.util.Constants;
import com.isotopeneo.mediaboxbeta.util.LoggerClass;

public class IWatchOnlineApplication extends Application {

	private ProgressDialog progressDialog;
	private String queryString;
	private boolean isSearchMovie = true;
	private List<TVShow> tvShows;
	private List<Movie> movies;
	
	public boolean isDeviceConnectedToNetwork() {
		ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}
	
	public void showProgressBar(String message, Activity activity) {
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage(message);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}
	
	public void dismissProgressBar() {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
	
	public void showAlertDialog(String msg, Activity parent) {
		new AlertDialog.Builder(parent).setMessage(msg).show();
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	public String computeQueryUrl() {
		String encodedString = queryString;
		try {
			encodedString = URLEncoder.encode(queryString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LoggerClass.log(e.getMessage());
		}
		if (isSearchMovie) {
			return Constants.OMDB_ENDPOINT_URL + encodedString;
		} else {
			return Constants.TV_RAGE_ENDPOINT_URL + encodedString;
		}
	}

	public boolean isSearchMovie() {
		return isSearchMovie;
	}

	public void setSearchMovie(boolean isSearchMovie) {
		this.isSearchMovie = isSearchMovie;
	}

	public List<TVShow> getTvShows() {
		return tvShows;
	}

	public void setTvShows(List<TVShow> tvShows) {
		this.tvShows = tvShows;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
}
