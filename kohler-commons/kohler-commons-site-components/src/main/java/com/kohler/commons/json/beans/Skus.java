/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.json.beans;

import java.util.Map;

/**
 * 
 * @author harish.p
 *
 */
public class Skus {

	private String sku;

	private Integer id;

	private Map<String, Object> skuAttributes;



	/**
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}



	/**
	 * @param sku
	 *            the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}



	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}



	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}



	/**
	 * @return the skuAttributes
	 */
	public Map<String, Object> getSkuAttributes() {
		return skuAttributes;
	}



	/**
	 * @param skuAttributes
	 *            the skuAttributes to set
	 */
	public void setSkuAttributes(Map<String, Object> skuAttributes) {
		this.skuAttributes = skuAttributes;
	}

}
