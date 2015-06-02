package com.isotopeneo.mediaboxbeta.util;

public class Constants {

	public static final String ENDPOINT_IMDB_URL = "http://imdb.wemakesites.net/api/1.0/get/title/?q=";
	// e.g., http://imdb.wemakesites.net/api/1.0/get/title/?q=Wild+at+Heart
	
	// e.g., http://services.tvrage.com/feeds/search.php?show=buffy
	public static final String TV_RAGE_ENDPOINT_URL = "http://services.tvrage.com/feeds/search.php?show=";
	
	// e.g., http://www.omdbapi.com/?type=movie&r=json&s=harry+potter
	public static final String OMDB_ENDPOINT_URL = "http://www.omdbapi.com/?type=movie&r=json&s=";
	
	public static final String MOVIES = "movies";
	public static final String TV_SHOWS = "tvShows";
	
	public static final String IMDB_ID = "imdbId";
	public static final String TVRAGE_ID = "tvrage_id";
	public static final String EPISODE_INFO = "episode_info";
	
	public static final String IMDB_TITLE_URL = "http://www.imdb.com/title/";
	public static final String KEY = "be993610b4e56af3264b4dfeb710cf320ac71902";
}
