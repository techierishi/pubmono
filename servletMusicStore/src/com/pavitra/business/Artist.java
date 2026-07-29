package com.pavitra.business;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long artistId;

	@Column(columnDefinition = "TEXT")
	private String artistName;
	@Column(columnDefinition = "TEXT")
	private String artistDescr;
	
	public Long getArtistId() {
		return artistId;
	}
	public void setArtistId(Long artistId) {
		this.artistId = artistId;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getArtistDescr() {
		return artistDescr;
	}
	public void setArtistDescr(String artistDescr) {
		this.artistDescr = artistDescr;
	}

	
	
}
