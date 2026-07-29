/**
 * 
 */
package com.ekart.user.entity;

import java.util.List;

/**
 * @author Rishikesh
 *
 */
public class Product {
	private int productId;
	private String productName;
	private String categoryName;
	private String productAvailability;
	private int productQuantity;
	private int productPrice;
	private String productDetails;
	private List<ProductImage> productImages; 
	
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getProductAvailability() {
		return productAvailability;
	}
	public void setProductAvailability(String productAvailability) {
		this.productAvailability = productAvailability;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	
	
	public String getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}
	public List<ProductImage> getProductImages() {
		return productImages;
	}
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}
	
	

}
