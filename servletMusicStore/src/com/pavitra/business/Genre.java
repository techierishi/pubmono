package com.pavitra.business;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long genreId;

	@Column(columnDefinition = "TEXT")
	private String genreName;
	@Column(columnDefinition = "TEXT")
	private String genreDescr;

	public Long getGenreId() {
		return genreId;
	}

	public void setGenreId(Long genreId) {
		this.genreId = genreId;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public String getGenreDescr() {
		return genreDescr;
	}

	public void setGenreDescr(String genreDescr) {
		this.genreDescr = genreDescr;
	}

}
