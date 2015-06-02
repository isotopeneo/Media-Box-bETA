package com.isotopeneo.mediaboxbeta.bean;

import java.util.List;

/**
 * 
 * @author Jayesh
 */
public class Movie {

	private String imdbID;
	private String title;
	private String year;
	private String rating;
	private String description;
	private String[] cast;
	private String imdbURL;
	private String[] directors;
	private String[] genres;
	private String poster;
	private List<MovieLinks> links;
	
	public String getImdbID() {
		return imdbID;
	}
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getCast() {
		return cast;
	}
	public void setCast(String[] cast) {
		this.cast = cast;
	}
	public String getImdbURL() {
		return imdbURL;
	}
	public void setImdbURL(String imdbURL) {
		this.imdbURL = imdbURL;
	}
	public String[] getDirectors() {
		return directors;
	}
	public void setDirectors(String[] directors) {
		this.directors = directors;
	}
	public String[] getGenres() {
		return genres;
	}
	public void setGenres(String[] genres) {
		this.genres = genres;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public List<MovieLinks> getLinks() {
		return links;
	}
	public void setLinks(List<MovieLinks> links) {
		this.links = links;
	}

}
