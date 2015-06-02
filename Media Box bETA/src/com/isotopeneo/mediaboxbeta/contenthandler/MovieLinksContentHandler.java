package com.isotopeneo.mediaboxbeta.contenthandler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.isotopeneo.mediaboxbeta.bean.Movie;
import com.isotopeneo.mediaboxbeta.bean.MovieLinks;
import com.isotopeneo.mediaboxbeta.util.LoggerClass;

/*
 * 
 * 
 * {
id: "43235"
title: "Wild at Heart"
slug: "wild-at-heart"
description: "Lula's psychopathic mother goes crazy at the thought of Lula being with Sailor, who just got free from jail. Ignoring Sailor's probation, they set out for California. However their mother hires a killer to hunt down Sailor. Unaware of this, the two enjoy their journey and themselves being together... until they witness a young woman dying after a car accident - a bad omen."
created: "2014-01-08 00:38:08"
status: ""
image: "MV5BMjEwMTQxNzA2M15BMl5BanBnXkFtZTcwMTU3MjcyMQ@@._V1_SX300.jpg"
imdb: "tt0100935"
year: "1990"
cominsoon: "0"
director: "David Lynch"
pg_rating: "NC-17"
duration: "125 min"
country: "USA"

 * {
            "link_id": "2308594",
            "odate": "2015-01-21 10:34:43",
            "date": "1 day",
            "link": "http://www.iwatchonline.to/play/2308594",
            "active": "1",
            "language": "English",
            "link_type": "1",
            "lt_title": "DVD"
        },
 */
public class MovieLinksContentHandler {

	private static final String LINKS = "links";
	private static final String LINK = "link";
	private static final String DATE = "date";
	private static final String IMAGE = "image";
	private static final String TITLE = "title";
	private static final String YEAR = "year";
	private static final String DESCRIPTION = "description";
	private static final String LT_TITLE = "lt_title";
	private static final String IMAGE_ASSETS_FOLDER_URL = "http://static.iwatchonline.se/assets/images/covers/";
	
	public Movie parseContent(String movieLinks) {
		LoggerClass.log("Search result json is " + movieLinks);
		if (!(movieLinks.equals(""))) {
			try {
				JSONObject movieLinksJson = new JSONObject(movieLinks);
				Movie returnValueMovie = new Movie();
				returnValueMovie.setTitle(movieLinksJson.getString(TITLE));
				returnValueMovie.setYear(movieLinksJson.getString(YEAR));
				if (movieLinksJson.getString(IMAGE).contains("http://") || movieLinksJson.getString(IMAGE).contains("https://")) {
					returnValueMovie.setPoster(movieLinksJson.getString(IMAGE));
				} else {
					returnValueMovie.setPoster(IMAGE_ASSETS_FOLDER_URL + movieLinksJson.getString(IMAGE));
				}
				returnValueMovie.setDescription(movieLinksJson.getString(DESCRIPTION));
				
				JSONArray allLinks = movieLinksJson.getJSONArray(LINKS);
				if (null != allLinks && allLinks.length() > 0) {
					List<MovieLinks> links = new ArrayList<MovieLinks>();
					for (int i = 0; i < allLinks.length(); i++) {
						JSONObject linkJSON = (JSONObject) allLinks.get(i);
						MovieLinks singleLink = new MovieLinks();
						singleLink.setLink(linkJSON.getString(LINK));
						singleLink.setAge(linkJSON.getString(DATE));
						singleLink.setLt_title(linkJSON.getString(LT_TITLE));
						links.add(singleLink);
					}
					returnValueMovie.setLinks(links);
					return returnValueMovie;
				}
				
			} catch (Exception e) {
				LoggerClass.log(e.getMessage());
			}
		}
		return null;
	}
}
