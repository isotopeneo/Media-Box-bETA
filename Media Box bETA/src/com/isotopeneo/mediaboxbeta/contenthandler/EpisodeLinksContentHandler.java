package com.isotopeneo.mediaboxbeta.contenthandler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.isotopeneo.mediaboxbeta.bean.Episode;
import com.isotopeneo.mediaboxbeta.bean.EpisodeLink;
import com.isotopeneo.mediaboxbeta.util.LoggerClass;

public class EpisodeLinksContentHandler {

	private static final String LINKS = "links";
	private static final String LINK = "link";
	private static final String TITLE = "e_title";
	private static final String LANGUAGE = "language";
	private static final String SYNOPSIS = "synopsis";
	private static final String LT_TITLE = "lt_title";
	
	public Episode parseContent(String result) {
		LoggerClass.log("Search result json is " + result);
		if (!(result.equals(""))) {
			try {
				JSONObject jsonResult = new JSONObject(result);
				Episode returnValueEpisode = new Episode();
				returnValueEpisode.setTitle(jsonResult.getString(TITLE));
				returnValueEpisode.setSynopsis(jsonResult.getString(SYNOPSIS));
				
				JSONArray allLinks = jsonResult.getJSONArray(LINKS);
				if (null != allLinks && allLinks.length() > 0) {
					List<EpisodeLink> links = new ArrayList<EpisodeLink>();
					for (int i = 0; i < allLinks.length(); i++) {
						JSONObject linkJSON = (JSONObject) allLinks.get(i);
						EpisodeLink singleLink = new EpisodeLink();
						singleLink.setLink(linkJSON.getString(LINK));
						singleLink.setLanguage(linkJSON.getString(LANGUAGE));
						singleLink.setLinkTitle(linkJSON.getString(LT_TITLE));
						links.add(singleLink);
					}
					returnValueEpisode.setLinks(links);
					return returnValueEpisode;
				}
				
			} catch (Exception e) {
				LoggerClass.log(e.getMessage());
			}
		}
		return null;
	}
}



/*

{
"message": "Success",
"id": "44953",
"e_id": "55865",
"title": "The Strain",
"slug": "the-strain",
"description": "Drama based on Guillermo del Toro and Chuck Hogan's horror trilogy, about a vampiric virus that has infected New York and the battle of mammoth proportions that follows. (Source: FX)",
"created": "2014-04-26 19:52:11",
"image": "276564-3.jpg",
"cominsoon": "0",
"year": "2014",
"imdb": "tt2654620",
"episode": "01",
"director": "",
"pg_rating": "",
"duration": "60",
"synopsis": "When a plane lands in New York City with everyone on board dead, Dr. Ephraim Goodweather, head of the CDC’s Canary Project, and his team are called upon to investigate. Harlem pawnbroker Abraham Setrakian races to the airport, convinced that what looks at first like a mysterious viral outbreak might be the beginning of something infinitely more sinister.",
"country": "USA",
"e_title": "Pilot",
"dated": "2014-07-13",
"e_title_slug": "pilot",
"season": "01",
"airsat": "10:00 pm",
"actors": [
    {
        "actorid": null,
        "act_name": null,
        "act_slug": null
    }
],
"geners": [
    {
        "gen_id": "5",
        "gen_name": "Thriller",
        "gen_slug": "thriller"
    },
    {
        "gen_id": "21",
        "gen_name": "Horror/Supernatural",
        "gen_slug": "horror-supernatural"
    },
    {
        "gen_id": "4",
        "gen_name": "Family",
        "gen_slug": "family"
    },
    {
        "gen_id": "2",
        "gen_name": "Drama",
        "gen_slug": "drama"
    },
    {
        "gen_id": "1",
        "gen_name": "Action",
        "gen_slug": "action"
    }
],
"links": [
    {
        "link_id": "2058723",
        "odate": "2014-11-28 18:22:26",
        "date": 1417216946,
        "link": "http://www.iwatchonline.to/play/2058723",
        "active": "1",
        "language": "English",
        "link_type": "4",
        "lt_title": "HDTV"
    },
*/