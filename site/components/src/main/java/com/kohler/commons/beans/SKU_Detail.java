/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.beans;

import java.util.LinkedHashMap;
import java.util.Map;

public class SKU_Detail {
	private String skuId;
	private String colorFinishName;
	private String imgSwatch;
	private String imgItemIso;
	private String discontinued;
	private String productPrice;
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	private Map<String,String[]> availableInCountries = new LinkedHashMap<String, String[]>();
	Map<String,String> retailerImageMap= new LinkedHashMap<>();
	Map<String,String> retailerUrlMap= new LinkedHashMap<>();
	
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getColorFinishName() {
		return colorFinishName;
	}
	public void setColorFinishName(String colorFinishName) {
		this.colorFinishName = colorFinishName;
	}
	public String getImgSwatch() {
		return imgSwatch;
	}
	public void setImgSwatch(String imgSwatch) {
		this.imgSwatch = imgSwatch;
	}
	public String getImgItemIso() {
		return imgItemIso;
	}
	public void setImgItemIso(String imgItemIso) {
		this.imgItemIso = imgItemIso;
	}
	public Map<String, String[]> getAvailableInCountries() {
		return availableInCountries;
	}
	public void setAvailableInCountries(Map<String, String[]> availableInCountries) {
		this.availableInCountries = availableInCountries;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	public Map<String, String> getRetailerImageMap() {
		return retailerImageMap;
	}
	public void setRetailerImageMap(Map<String, String> retailerImageMap) {
		this.retailerImageMap = retailerImageMap;
	}
	public Map<String, String> getRetailerUrlMap() {
		return retailerUrlMap;
	}
	public void setRetailerUrlMap(Map<String, String> retailerUrlMap) {
		this.retailerUrlMap = retailerUrlMap;
	}
}
