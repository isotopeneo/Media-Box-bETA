package com.isotopeneo.mediaboxbeta.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.isotopeneo.mediaboxbeta.bean.Movie;
import com.isotopeneo.mediaboxbeta.contenthandler.MovieLinksContentHandler;
import com.isotopeneo.mediaboxbeta.request.JSONPostRequestExecutor;

public class MovieInfoLoader extends AsyncTaskLoader<Movie> {

	private String imdbId;
	

	@Override
	public Movie loadInBackground() {
		return new MovieLinksContentHandler().parseContent(new JSONPostRequestExecutor().makeRequest(imdbId));
	}

	public MovieInfoLoader(Context context, String imdbId) {
		super(context);
		this.imdbId = imdbId;
	}

}
