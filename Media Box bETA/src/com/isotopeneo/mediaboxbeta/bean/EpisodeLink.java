package com.isotopeneo.mediaboxbeta.bean;

public class EpisodeLink {

	/*
	 * {
link_id: "1718275"
odate: "2014-09-15 16:45:01"
date: 1410813901
link: "http://www.iwatchonline.to/play/1718275"
active: "1"
language: "English"
link_type: "4"
lt_title: "HDTV"
}
	 */
	
	private String link;
	private String language;
	private String linkTitle;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}
}
