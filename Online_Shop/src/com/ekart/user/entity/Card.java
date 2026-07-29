package com.ekart.user.entity;

public class Card {

	private int cid;
	private int uid;
	private String card_type;
	private String cart_number;
	private String cart_cvv;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getCart_number() {
		return cart_number;
	}
	public void setCart_number(String cart_number) {
		this.cart_number = cart_number;
	}
	public String getCart_cvv() {
		return cart_cvv;
	}
	public void setCart_cvv(String cart_cvv) {
		this.cart_cvv = cart_cvv;
	}
	
	
}
