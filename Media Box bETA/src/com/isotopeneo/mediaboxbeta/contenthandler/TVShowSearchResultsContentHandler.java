package com.isotopeneo.mediaboxbeta.contenthandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

import com.isotopeneo.mediaboxbeta.application.IWatchOnlineApplication;
import com.isotopeneo.mediaboxbeta.bean.TVShow;
import com.isotopeneo.mediaboxbeta.util.LoggerClass;

public class TVShowSearchResultsContentHandler {

	private IWatchOnlineApplication application;
	private static final String ns = null;
	private static final String RESULTS = "Results";
	private static final String SHOW = "show";
	private static final String SHOWID = "showid";
	private static final String NAME = "name";
	private List<TVShow> shows = new ArrayList<TVShow>();
	private static final String STARTED = "started";
	private static final String ENDED = "ended";

	public TVShowSearchResultsContentHandler(Context context) {
		application = (IWatchOnlineApplication) context;
	}

	public void parseContent(InputStream in) {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			parser.require(XmlPullParser.START_TAG, ns, RESULTS);
			while (parser.next() != XmlPullParser.END_TAG) {
				String name = parser.getName();
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				if (name.equals(SHOW)) {
					readShowDetails(parser);
				} else {
					skip(parser);
				}
			}
		} catch (XmlPullParserException e) {
			LoggerClass.log(e.getMessage());
		} catch (Exception e) {
			LoggerClass.log(e.getMessage());
		}
		application.setTvShows(shows);
	}

	private void readShowDetails(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		parser.require(XmlPullParser.START_TAG, ns, SHOW);
		TVShow tvShow = new TVShow();
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals(SHOWID)) {
				parser.require(XmlPullParser.START_TAG, ns, SHOWID);
				tvShow.setId(readText(parser));
				parser.require(XmlPullParser.END_TAG, ns, SHOWID);
			} else if (name.equals(NAME)) {
				parser.require(XmlPullParser.START_TAG, ns, NAME);
				tvShow.setName(readText(parser));
				parser.require(XmlPullParser.END_TAG, ns, NAME);
			} else if (name.equals(STARTED)) {
				parser.require(XmlPullParser.START_TAG, ns, STARTED);
				tvShow.setStartedYear((readText(parser)));
				parser.require(XmlPullParser.END_TAG, ns, STARTED);
			} else if (name.equals(ENDED)) {
				parser.require(XmlPullParser.START_TAG, ns, ENDED);
				tvShow.setEndedYear(readText(parser));
				parser.require(XmlPullParser.END_TAG, ns, ENDED);
			} else {
				skip(parser);
			}
		}
		shows.add(tvShow);
	}

	// For the tags title and summary, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			LoggerClass.log(result);
			parser.nextTag();
		}
		return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
}

/*
 * 
 * 
 * 
 * 
 * <?xml version="1.0" encoding="UTF-8" ?>
 * 
 * <Results> <show> <showid>2930</showid> <name>Buffy the Vampire Slayer</name>
 * <link>http://www.tvrage.com/Buffy_The_Vampire_Slayer</link>
 * <country>US</country> <started>1997</started> <ended>2003</ended>
 * <seasons>7</seasons> <status>Ended</status>
 * <classification>Scripted</classification>
 * <genres><genre>Action</genre><genre>
 * Adventure</genre><genre>Comedy</genre><genre
 * >Drama</genre><genre>Horror/Supernatural
 * </genre><genre>Mystery</genre><genre>Sci-Fi</genre></genres> </show> <show>
 * <showid>31192</showid> <name>Buffy the Vampire Slayer - Season Eight: Motion
 * comics</name>
 * <link>http://www.tvrage.com/buffy-the-vampire-slayer-season-eight-mo</link>
 * <country>US</country> <started>2010</started> <ended>2010</ended>
 * <seasons>1</seasons> <status>Canceled/Ended</status>
 * <classification>Animation</classification> <genres><genre>Animation
 * General</genre
 * ><genre>Action</genre><genre>Adventure</genre><genre>Comedy</genre
 * ><genre>Drama
 * </genre><genre>Horror/Supernatural</genre><genre>Sci-Fi</genre></genres>
 * </show> <show> <showid>2931</showid> <name>Buffy the Animated Series</name>
 * <link>http://www.tvrage.com/Buffy_the_Animated_Series</link>
 * <country>US</country> <started>2002</started> <ended>0</ended>
 * <seasons>1</seasons> <status>Pilot Rejected</status>
 * <classification>Animation</classification> <genres><genre>Animation
 * General</genre
 * ><genre>Action</genre><genre>Adventure</genre><genre>Horror/Supernatural
 * </genre></genres> </show> </Results>
 */

