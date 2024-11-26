/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.beans;

public class CarouselOverlayCompoundView {
	private String itemNo;
	private String sku;
	private Double xcord;
	private Double ycord;
	private String IMG_ISO;
	private String description;
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIMG_ISO() {
		return IMG_ISO;
	}
	public void setIMG_ISO(String iMG_ISO) {
		IMG_ISO = iMG_ISO;
	}
	public Double getXcord() {
		return xcord;
	}
	public void setXcord(Double xcord) {
		this.xcord = xcord;
	}
	public Double getYcord() {
		return ycord;
	}
	public void setYcord(Double ycord) {
		this.ycord = ycord;
	}
}
