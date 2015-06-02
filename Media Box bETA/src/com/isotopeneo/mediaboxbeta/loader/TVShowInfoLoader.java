package com.isotopeneo.mediaboxbeta.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.isotopeneo.mediaboxbeta.bean.TVShow;
import com.isotopeneo.mediaboxbeta.contenthandler.TVShowInfoContentHandler;
import com.isotopeneo.mediaboxbeta.request.JSONPostRequestExecutor;

public class TVShowInfoLoader extends AsyncTaskLoader<TVShow> {

	private String tvRageId;
	

	@Override
	public TVShow loadInBackground() {
		return new TVShowInfoContentHandler().parseContent(new JSONPostRequestExecutor().configureTVShowInfoRequestAndMakeRequest(tvRageId));
	}

	public TVShowInfoLoader(Context context, String tvRageId) {
		super(context);
		this.tvRageId = tvRageId;
	}

}
