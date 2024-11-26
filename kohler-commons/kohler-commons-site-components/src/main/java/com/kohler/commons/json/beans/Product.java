/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.json.beans;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author harish.p
 *
 */
public class Product {

	private String itemNo;

	private String productName;

	private String status;

	private String itemType;

	private Map<String, Object> attributes;

	private Long[] ancestors;

	private List<Skus> skus;

	private Map<String, Long[]> crossSelling;

	private Map<String, String> adCopy;
	
	private Long producthash;
	
	private Boolean starProduct;
	
	private String shape;
	
	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public Boolean getStarProduct() {
		return starProduct;
	}

	public void setStarProduct(Boolean starProduct) {
		this.starProduct = starProduct;
	}


	public Long getProducthash() {
		return producthash;
	}



	public void setProducthash(Long producthash) {
		this.producthash = producthash;
	}



	/**
	 * @return the itemNo
	 */
	public String getItemNo() {
		return itemNo;
	}



	/**
	 * @param itemNo
	 *            the itemNo to set
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}



	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}



	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}



	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}



	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}



	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}



	/**
	 * @param itemType
	 *            the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}



	/**
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}



	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}



	/**
	 * @return the ancestors
	 */
	public Long[] getAncestors() {
		return ancestors;
	}



	/**
	 * @param ancestors
	 *            the ancestors to set
	 */
	public void setAncestors(Long[] ancestors) {
		this.ancestors = ancestors;
	}



	/**
	 * @return the skus
	 */
	public List<Skus> getSkus() {
		return skus;
	}



	/**
	 * @param skus
	 *            the skus to set
	 */
	public void setSkus(List<Skus> skus) {
		this.skus = skus;
	}



	/**
	 * @return the crossSelling
	 */
	public Map<String, Long[]> getCrossSelling() {
		return crossSelling;
	}



	/**
	 * @param crossSelling
	 *            the crossSelling to set
	 */
	public void setCrossSelling(Map<String, Long[]> crossSelling) {
		this.crossSelling = crossSelling;
	}



	/**
	 * @return the adCopy
	 */
	public Map<String, String> getAdCopy() {
		return adCopy;
	}



	/**
	 * @param adCopy
	 *            the adCopy to set
	 */
	public void setAdCopy(Map<String, String> adCopy) {
		this.adCopy = adCopy;
	}

}
