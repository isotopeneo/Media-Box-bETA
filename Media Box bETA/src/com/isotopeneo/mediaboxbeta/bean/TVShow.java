package com.isotopeneo.mediaboxbeta.bean;

import java.util.List;

public class TVShow {
	
	private String id;
	private String name;
	private String startedYear;
	private String endedYear;
	private String year;
	private String poster;
	private String description;
	private String imdbID;
	private List<Season> seasons;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartedYear() {
		return startedYear;
	}
	public void setStartedYear(String startedYear) {
		this.startedYear = startedYear;
	}
	public String getEndedYear() {
		return endedYear;
	}
	public void setEndedYear(String endedYear) {
		this.endedYear = endedYear;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImdbID() {
		return imdbID;
	}
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	public List<Season> getSeasons() {
		return seasons;
	}
	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}

}
