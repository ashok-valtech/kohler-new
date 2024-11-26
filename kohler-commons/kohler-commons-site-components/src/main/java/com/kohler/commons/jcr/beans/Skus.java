package com.kohler.commons.jcr.beans;

import java.util.Map;

import org.jcrom.annotations.JcrName;
import org.jcrom.annotations.JcrNode;
import org.jcrom.annotations.JcrPath;
import org.jcrom.annotations.JcrProperty;

import com.kohler.commons.constants.Constants;

/**
 * 
 * @author harish.p
 *
 */
@JcrNode(nodeType = Constants.NS_CSKU)

public class Skus {

	@JcrPath
	private String path;

	@JcrName
	private String nodeName;

	@JcrProperty(name = Constants.NS_SKU)
	private String sku;

	@JcrProperty(name = Constants.NS_ID)
	private Integer id;

	// @JcrChildNode(name="kohler:skuAttributes")
	private Map<String, Object> skuAttributes;



	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}



	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}



	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}



	/**
	 * @param nodeName
	 *            the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}



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
