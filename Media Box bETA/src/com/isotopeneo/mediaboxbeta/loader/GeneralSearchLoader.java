package com.isotopeneo.mediaboxbeta.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.isotopeneo.mediaboxbeta.application.IWatchOnlineApplication;
import com.isotopeneo.mediaboxbeta.contenthandler.SearchResultsContentHandler;
import com.isotopeneo.mediaboxbeta.contenthandler.TVShowSearchResultsContentHandler;
import com.isotopeneo.mediaboxbeta.request.RequestExecutor;
import com.isotopeneo.mediaboxbeta.request.TVShowRequestExecutor;
import com.isotopeneo.mediaboxbeta.util.Constants;

public class GeneralSearchLoader extends AsyncTaskLoader<String> {

	private RequestExecutor requestExecutor;
	private IWatchOnlineApplication application;
	private SearchResultsContentHandler searchResultsContentHandler;
	private TVShowSearchResultsContentHandler tvShowsearchResultsContentHandler;
	

	@Override
	public String loadInBackground() {
		if (application.isSearchMovie()) {
			requestExecutor = new RequestExecutor(application.computeQueryUrl());
			searchResultsContentHandler = new SearchResultsContentHandler(application);
			searchResultsContentHandler.parseContent1(requestExecutor.makeRequest());
			return Constants.MOVIES;
		} else {
			TVShowRequestExecutor tvShowRequestExecutor = new TVShowRequestExecutor();
			tvShowsearchResultsContentHandler = new TVShowSearchResultsContentHandler(application);
			tvShowsearchResultsContentHandler.parseContent(tvShowRequestExecutor.makeRequest(application.computeQueryUrl()));
			return Constants.TV_SHOWS;
		}
	}

	public GeneralSearchLoader(Context context) {
		super(context);
		this.application = (IWatchOnlineApplication) context;
	}

}
