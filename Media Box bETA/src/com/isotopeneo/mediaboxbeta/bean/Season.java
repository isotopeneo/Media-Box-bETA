package com.isotopeneo.mediaboxbeta.bean;

import java.util.List;

public class Season {

	private String number;
	private List<Episode> episodes;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public List<Episode> getEpisodes() {
		return episodes;
	}
	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
}
