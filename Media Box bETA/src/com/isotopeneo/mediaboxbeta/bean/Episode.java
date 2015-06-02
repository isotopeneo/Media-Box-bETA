package com.isotopeneo.mediaboxbeta.bean;

import java.util.List;

public class Episode {

	private String id;
	private String title;
	private String episodeNumber;
	private String synopsis;
	private List<EpisodeLink> links;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEpisodeNumber() {
		return episodeNumber;
	}
	public void setEpisodeNumber(String episodeNumber) {
		this.episodeNumber = episodeNumber;
	}
	public List<EpisodeLink> getLinks() {
		return links;
	}
	public void setLinks(List<EpisodeLink> links) {
		this.links = links;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
}
