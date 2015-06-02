package com.isotopeneo.mediaboxbeta.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.isotopeneo.mediaboxbeta.bean.Episode;
import com.isotopeneo.mediaboxbeta.contenthandler.EpisodeLinksContentHandler;
import com.isotopeneo.mediaboxbeta.request.JSONPostRequestExecutor;

public class EpisodeInfoLoader extends AsyncTaskLoader<Episode> {

	private String tvRageID;
	private String season;
	private String episode;
	
	@Override
	public Episode loadInBackground() {
		return new EpisodeLinksContentHandler().parseContent(new JSONPostRequestExecutor().configureEpisodeLinksRequestAndMakeRequest(tvRageID, season, episode));
	}

	public EpisodeInfoLoader(Context context, String tvRageID, String season, String episode) {
		super(context);
		this.tvRageID = tvRageID;
		this.season = season;
		this.episode = episode;
	}

}
