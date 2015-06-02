package com.isotopeneo.mediaboxbeta.contenthandler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.isotopeneo.mediaboxbeta.application.IWatchOnlineApplication;
import com.isotopeneo.mediaboxbeta.bean.Movie;
import com.isotopeneo.mediaboxbeta.request.JSONPostRequestExecutor;
import com.isotopeneo.mediaboxbeta.util.LoggerClass;

public class SearchResultsContentHandler {

	private static final String STATUS = "status";
	private static final String DATA = "data";
	private static final String SUCCESS = "success";
	private static final String IMDB_ID = "imdbId";
	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String POSTER = "poster";
	private static final String YEAR = "year";
	private static final String RATING = "rating";
	private IWatchOnlineApplication application;
	private static final String IMDB_URL = "imdbUrl";
	private static final String CAST = "cast";
	private static final String DIRECTORS = "directors";
	private static final String GENRES = "genres";

	public SearchResultsContentHandler(Context context) {
		application = (IWatchOnlineApplication) context;
	}

	public Movie parseContent(String jsonSearchResults) {
		LoggerClass.log("Search result json is " + jsonSearchResults);
		if (!(jsonSearchResults.equals(""))) {
			try {
				JSONObject searchResults = new JSONObject(jsonSearchResults);
				String status = searchResults.getString(STATUS);
				if (null != status && status.equals(SUCCESS)) {
					JSONObject data = searchResults.getJSONObject(DATA);
					if (null != data) {
						Movie movie = new Movie();
						movie.setImdbID(data.getString(IMDB_ID));
						if (data.getString(IMDB_ID).contains("tt")) {
							/*
							movie.setLinks(new MovieLinksContentHandler()
									.parseContent(new JSONPostRequestExecutor()
											.makeRequest(data
													.getString(IMDB_ID))));
													*/
						}
						movie.setTitle(data.getString(TITLE));
						movie.setYear(data.getString(YEAR));
						movie.setRating(data.getString(RATING));
						movie.setDescription(data.getString(DESCRIPTION)
								.replace("+", " "));
						movie.setImdbURL("http://www.imdb.com"
								+ data.getString(IMDB_URL));
						movie.setPoster(data.getString(POSTER));
						// cast
						JSONArray castData = data.getJSONArray(CAST);
						if (null != castData && castData.length() > 0) {
							String[] cast = new String[castData.length()];
							for (int i = 0; i < castData.length(); i++) {
								cast[i] = castData.get(i).toString();
							}
							movie.setCast(cast);
						}
						// directors
						JSONArray directorsData = data.getJSONArray(DIRECTORS);
						if (null != directorsData && directorsData.length() > 0) {
							String[] directors = new String[directorsData
									.length()];
							for (int i = 0; i < directorsData.length(); i++) {
								directors[i] = directorsData.get(i).toString();
							}
							movie.setDirectors(directors);
						}
						// genres
						JSONArray genresData = data.getJSONArray(GENRES);
						if (null != genresData && genresData.length() > 0) {
							String[] genres = new String[genresData.length()];
							for (int i = 0; i < genresData.length(); i++) {
								genres[i] = genresData.get(i).toString();
							}
							movie.setGenres(genres);
						}
						return movie;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/*
	 * { Search: [10] 0: { Title: "Harry Potter and the Deathly Hallows: Part 2"
	 * Year: "2011" imdbID: "tt1201607" Type: "movie" }- 1: { Title:
	 * "Harry Potter and the Sorcerer's Stone" Year: "2001" imdbID: "tt0241527"
	 * Type: "movie" }
	 */
	public void parseContent1(String jsonSearchResults) {
		LoggerClass.log("Search result json is " + jsonSearchResults);
		if (!(jsonSearchResults.equals(""))) {
			try {
				JSONObject searchResults = new JSONObject(jsonSearchResults);
				JSONArray search = searchResults.getJSONArray("Search");
				if (null != search && search.length() > 0) {
					List<Movie> moviesList = new ArrayList<Movie>();
					for (int i = 0; i < search.length(); i++) {
						Movie movie = new Movie();
						movie.setTitle(((JSONObject)search.get(i)).getString("Title"));
						movie.setImdbID(((JSONObject)search.get(i)).getString("imdbID"));
						movie.setYear(((JSONObject)search.get(i)).getString("Year"));
						moviesList.add(movie);
					}
					application.setMovies(moviesList);
				}
			} catch (JSONException e) {
				LoggerClass.log(e.getMessage());
			} catch (Exception e) {
				LoggerClass.log(e.getMessage());
			}

		}
	}
}

/*
 * { "status": "success", "code": 0, "data": { "imdbEndPoint":
 * "http://www.imdb.com", "imdbId": "tt0100935", "title": "Wild at Heart",
 * "year": "1990", "rating": "7.3", "description":
 * "Young lovers Sailor and Lula run from the variety of weirdos that Lula's mom has hired to kill Sailor."
 * , "cast": [ "Nicolas Cage", "Laura Dern", "Willem Dafoe", "J.E. Freeman",
 * "Crispin Glover", "Diane Ladd", "Calvin Lockhart", "Isabella Rossellini",
 * "Harry Dean Stanton", "Grace Zabriskie", "Sherilyn Fenn", "Marvin Kaplan",
 * "William Morgan Sheppard", "David Patrick Kelly", "Freddie Jones" ],
 * "imdbUrl": "http://www.imdb.com/title/tt0100935/?ref_=fn_tt_tt_1", "country":
 * "USA", "releaseDate": "1990-08-17", "directors": [ "David Lynch" ],
 * "writers": [ "Barry Gifford", "David Lynch" ], "genres": [ "Comedy", "Crime",
 * "Romance" ], "poster":
 * "http://imdb.wemakesites.net/api/1.0/img/?url=http://ia.media-imdb.com/images/M/MV5BMjEwMTQxNzA2M15BMl5BanBnXkFtZTcwMTU3MjcyMQ@@._V1_SY317_CR10,0,214,317_.jpg"
 * , "runningTime": "125 min" } }
 */
