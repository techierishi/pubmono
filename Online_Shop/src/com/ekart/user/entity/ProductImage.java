package com.ekart.user.entity;

public class ProductImage {

	private String imageId;
	private String pid;
	private String imageName;
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	@Override
	public String toString() {
		return "ProductImage [imageId=" + imageId + ", pid=" + pid + ", imageName=" + imageName + "]";
	}
	
	
}
