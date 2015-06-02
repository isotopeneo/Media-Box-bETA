package com.isotopeneo.mediaboxbeta.contenthandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.isotopeneo.mediaboxbeta.bean.Episode;
import com.isotopeneo.mediaboxbeta.bean.Season;
import com.isotopeneo.mediaboxbeta.bean.TVShow;
import com.isotopeneo.mediaboxbeta.util.LoggerClass;

/*
 * 
{
    "message": "Success",
    "id": "1573",
    "title": "How I Met Your Mother",
    "slug": "how-i-met-your-mother",
    "description": "How I Met Your Mother is a comedy about Ted (Josh Radnor) and how he fell in love. When Ted's best friend, Marshall (Jason Segal, Freaks and Geeks), decides to propose to his long-time girlfriend, Lily (Alyson Hannigan, Buffy the Vampire Slayer), Ted realizes that time may be running out on finding the love of his life. His friend Barney (Neil Patrick Harris, Doogie Howser MD) is the anti-Marshall, a guy who thinks he knows everything about meeting women and isn't afraid to tell you.    Ted first meets Robin (Cobie Smulders, Veritas the Quest), and he believes that she is the woman for him.  Soon he realizes that he may not be right after all.  Bob Saget (Full House) narrates the story with voiceovers as we follow along flashbacks from the future when he is telling his children the story of how he met their mother.",
    "created": "2011-10-04 06:21:00",
    "image": "75760-34.jpg",
    "cominsoon": "0",
    "year": "2005",
    "imdb": "tt0460649",
    "director": "",
    "pg_rating": "",
    "duration": "30",
    "country": "USA",
    "airsat": "08:00 pm",
    "status": "Returning Series",
    "channel": "CBS",
    "geners": [
        {
            "gen_id": "13",
            "gen_name": "Comedy",
            "gen_slug": "comedy"
        }
    ],
    "seasons": {
        "09": [
            {
                "e_id": "53952",
                "e_title": "Last Forever (2)",
                "e_title_slug": "last-forever-2-",
                "showid": "1573",
                "season": "09",
                "episode": "24",
                "star_rating": "10",
                "star_rating_users": "1",
                "links": 57,
                "airsat": "08:30 pm"
            },
            {
                "e_id": "53947",
                "e_title": "Last Forever (1)",
                "e_title_slug": "last-forever-1-",
                "showid": "1573",
                "season": "09",
                "episode": "23",
                "star_rating": "10",
                "star_rating_users": "1",
                "links": 123,
                "airsat": "08:00 pm"
            },
            {
                "e_id": "53618",
                "e_title": "The End of the Aisle",
                "e_title_slug": "the-end-of-the-aisle",
                "showid": "1573",
                "season": "09",
                "episode": "22",
                "star_rating": "10",
                "star_rating_users": "1",
                "links": 126,
                "airsat": "08:00 pm"
            },
            {
                "e_id": "53171",
                "e_title": "Gary Blauman",
                "e_title_slug": "gary-blauman",
                "showid": "1573",
                "season": "09",
                "episode": "21",
                "star_rating": "10",
                "star_rating_users": "1",
                "links": 128,
                "airsat": "08:00 pm"
            },
 */
public class TVShowInfoContentHandler {

	private static final String IMAGE = "image";
	private static final String TITLE = "title";
	private static final String YEAR = "year";
	private static final String DESCRIPTION = "description";
	private static final String IMAGE_ASSETS_FOLDER_URL = "http://static.iwatchonline.se/assets/images/covers/";
	private static final String IMDB = "imdb";
	private static final String SEASONS = "seasons";
	private static final String EPISODE_ID = "e_id";
	private static final String EPISODE_TITLE = "e_title";
	private static final String EPISODE_NUMBER = "episode";
	private JSONObject allSeasons;
	private List<Season> seasonsArrayList = new ArrayList<Season>();
	
	public TVShow parseContent(String result) {
		LoggerClass.log("Search result json is " + result);
		if (!(result.equals(""))) {
			try {
				JSONObject jsonData = new JSONObject(result);
				TVShow tvShow = new TVShow();
				tvShow.setName(jsonData.getString(TITLE));
				tvShow.setYear(jsonData.getString(YEAR));
				if (jsonData.getString(IMAGE).contains("http://") || jsonData.getString(IMAGE).contains("https://")) {
					tvShow.setPoster(jsonData.getString(IMAGE));
				} else {
					tvShow.setPoster(IMAGE_ASSETS_FOLDER_URL + jsonData.getString(IMAGE));
				}
				tvShow.setDescription(jsonData.getString(DESCRIPTION));
				tvShow.setImdbID(jsonData.getString(IMDB));
				
				allSeasons = jsonData.getJSONObject(SEASONS);
				fetchSeasonsAndEpisodes();
				tvShow.setSeasons(seasonsArrayList);
				return tvShow;
			} catch (Exception e) {
				LoggerClass.log(e.getMessage());
			}
		}
		return null;
	}
	
	public boolean fetchSeasonEpisodes(String seasonNumber) {
		LoggerClass.log("fetch for season " + seasonNumber);
		try {
			Season seasonObject = new Season();
			seasonObject.setNumber(seasonNumber);
			JSONArray season = allSeasons.getJSONArray(seasonNumber);
			List<Episode> allEpisodes = new ArrayList<Episode>();
			for (int i = 0; i < season.length(); i++) {
				Episode episode = new Episode();
				episode.setId(LoggerClass.printReturn(((JSONObject)season.get(i)).getString(EPISODE_ID)));
				episode.setTitle(LoggerClass.printReturn(((JSONObject)season.get(i)).getString(EPISODE_TITLE)));
				episode.setEpisodeNumber(LoggerClass.printReturn(((JSONObject)season.get(i)).getString(EPISODE_NUMBER)));
				allEpisodes.add(episode);
			}
			Collections.reverse(allEpisodes);
			seasonObject.setEpisodes(allEpisodes);
			seasonsArrayList.add(seasonObject);
		} catch (JSONException e) {
			LoggerClass.log(e.getMessage());
			return false;
		} catch (Exception e) {
			LoggerClass.log(e.getMessage());
			return false;
		}
		return true;
	}
	
	public void fetchSeasonsAndEpisodes() {
		for (int i = 1; i < 100; i++) {
			DecimalFormat df = new DecimalFormat("00");
			if (fetchSeasonEpisodes(df.format(i))){
				continue;
			} else {
				break;
			}
		}
	}
}
