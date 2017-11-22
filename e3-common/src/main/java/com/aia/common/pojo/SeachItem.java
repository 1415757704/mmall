package com.aia.common.pojo;

import java.io.Serializable;

public class SeachItem implements Serializable{

	private String id;
	private String title;
	private String sellPoint;
	private Long price;
	private String image;
	private String categoryName;
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
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return "SeachItem [id=" + id + ", title=" + title + ", sellPoint=" + sellPoint + ", price=" + price + ", image="
				+ image + ", categoryName=" + categoryName + "]";
	}
	
	
}
