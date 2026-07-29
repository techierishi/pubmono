package com.pavitra.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This Entity represents a single product in the store, which have its code, a
 * description and a price, as well as album information and its titles.
 * 
 * @author Rishikesh Jha
 */
@Entity
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;

	private String code;
	private String description;

	@Column(columnDefinition = "TEXT")
	private String albumInformation;

	@Column(columnDefinition = "TEXT")
	private String csAlbumTitles;

	@Column(columnDefinition = "TEXT")
	private String songImg;

	@Column(columnDefinition = "TEXT")
	private String songMp3;

	@Column(columnDefinition = "TEXT")
	private String songGenre;
	
	@Column(columnDefinition = "TEXT")
	private String songArtist;

	@Column(precision = 8, scale = 2)
	private BigDecimal price;

	public Product() {
	}

	public Long getProductId() {
		return productId;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getPriceCurrencyFormat() {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);
		return currencyFormat.format(price.doubleValue());
	}

	public String getArtistName() {
		int hyphenIndex = description.indexOf("-");
		return description.substring(0, hyphenIndex).trim();
	}

	public String getAlbumName() {
		int hyphenIndex = description.indexOf("-");
		return description.substring(hyphenIndex + 1, description.length()).trim();
	}

	public String getImageUrl() {
		return "/img/" + code + "_cover.jpg";
	}

	public String getAlbumInformation() {
		return albumInformation;
	}

	public String getCsAlbumTitles() {
		return csAlbumTitles;
	}

	/**
	 * @return A list of titles for this albums, from the comma-separated
	 *         csAlbumTitles
	 */
	public List<String> getTitlesList() {
		String[] tokens = csAlbumTitles.split(",");
		return Arrays.asList(tokens);
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setAlbumInformation(String albumInformation) {
		this.albumInformation = albumInformation;
	}

	public void setCsAlbumTitles(String csAlbumTitles) {
		this.csAlbumTitles = csAlbumTitles;
	}

	public String getSongImg() {
		return songImg;
	}

	public void setSongImg(String songImg) {
		this.songImg = songImg;
	}

	public String getSongMp3() {
		return songMp3;
	}

	public void setSongMp3(String songMp3) {
		this.songMp3 = songMp3;
	}

	public String getSongGenre() {
		return songGenre;
	}

	public void setSongGenre(String songGenre) {
		this.songGenre = songGenre;
	}

	public String getSongArtist() {
		return songArtist;
	}

	public void setSongArtist(String songArtist) {
		this.songArtist = songArtist;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", code=" + code + ", description=" + description
				+ ", albumInformation=" + albumInformation + ", csAlbumTitles=" + csAlbumTitles + ", songImg=" + songImg
				+ ", songMp3=" + songMp3 + ", songGenre=" + songGenre + ", songArtist=" + songArtist + ", price="
				+ price + "]";
	}

	
	
}
