/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.jcr.beans;

import java.util.Calendar;
import java.util.List;
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
@JcrNode(nodeType = Constants.NS_PRODUCT)
public class Product {

	@JcrPath
	private String path;

	@JcrName
	private String nodeName;

	@JcrProperty(name = Constants.HIPPOSTD_STATE)
	private String state;

	@JcrProperty(name = Constants.HIPPOSTD_STATE_SUMMARY)
	private String stateSummary;

	@JcrProperty(name = Constants.HIPPOSTDPUBWF_CREATED_BY)
	private String createdBy;

	@JcrProperty(name = Constants.HIPPOSTDPUBWF_CREATION_DATE)
	private Calendar creationDate;

	@JcrProperty(name = Constants.HIPPOSTDPUBWF_LAST_MODIFIED_BY)
	private String lastModifiedBy;

	@JcrProperty(name = Constants.HIPPOSTDPUBWF_LAST_MODIFICATION_DATE)
	private Calendar lastModificationDate;

	@JcrProperty(name = Constants.HIPPOTRANSLATION_LOCALE)
	private String locale;

	@JcrProperty(name = Constants.HIPPOTRANSLATION_ID)
	private String translationId;

	@JcrProperty(name = Constants.HIPPO_NAME)
	private String name;

	@JcrProperty(name = Constants.NS_ITEM_NO)
	private String itemNo;

	@JcrProperty(name = Constants.NS_ID)
	private Long productId;

	@JcrProperty(name = Constants.NS_ITEM_TYPE)
	private String itemType;

	@JcrProperty(name = Constants.NS_STATUS)
	private String status;

	private Map<String, Object> attributes;

	@JcrProperty(name = Constants.NS_ANCESTORS)
	private Long[] ancestors;

	private List<Skus> skus;

	private Map<String, Long[]> crossSelling;

	private Map<String, String> adCopy;

	@JcrProperty(name = Constants.HIPPOTAXONOMY_KEYS)
	private String[] taxonomyKeys;

	@JcrProperty(name = Constants.HIPPOSTDPUBWF_PUBLICATION_DATE)
	private Calendar publicationDate;

	@JcrProperty(name = Constants.NS_PRODUCT_HASH)
	private Long producthash;
	
	@JcrProperty(name = Constants.NS_LIST_PRICE)
	private Double listPrice;
	
	@JcrProperty(name = Constants.NS_DISCONTINUED)
	private Boolean discontinued;
	
	@JcrProperty(name = Constants.NS_STARPRODUCT)
	private Boolean starProduct;
	
	@JcrProperty(name = Constants.NS_STYLE)
	private String[] style;
	
	@JcrProperty(name = Constants.NS_FEATURE)
	private String[] feature;
	
	@JcrProperty(name = Constants.NS_SHAPE)
	private String shape;
	
	public String[] getStyle() {
		return style;
	}

	public void setStyle(String[] style) {
		this.style = style;
	}

	public String[] getFeature() {
		return feature;
	}

	public void setFeature(String[] feature) {
		this.feature = feature;
	}

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
	
	public Boolean getDiscontinued() {
		return discontinued;
	}


	public void setDiscontinued(Boolean discontinued) {
		this.discontinued = discontinued;
	}

	public Long getProducthash() {
		return producthash;
	}


	public void setProducthash(Long producthash) {
		this.producthash = producthash;
	}


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
	 * @return the state
	 */
	public String getState() {
		return state;
	}



	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}



	/**
	 * @return the stateSummary
	 */
	public String getStateSummary() {
		return stateSummary;
	}



	/**
	 * @param stateSummary
	 *            the stateSummary to set
	 */
	public void setStateSummary(String stateSummary) {
		this.stateSummary = stateSummary;
	}



	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}



	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	/**
	 * @return the creationDate
	 */
	public Calendar getCreationDate() {
		return creationDate;
	}



	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}



	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}



	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}



	/**
	 * @return the lastModificationDate
	 */
	public Calendar getLastModificationDate() {
		return lastModificationDate;
	}



	/**
	 * @param lastModificationDate
	 *            the lastModificationDate to set
	 */
	public void setLastModificationDate(Calendar lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}



	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}



	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}



	/**
	 * @return the translationId
	 */
	public String getTranslationId() {
		return translationId;
	}



	/**
	 * @param translationId
	 *            the translationId to set
	 */
	public void setTranslationId(String translationId) {
		this.translationId = translationId;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}



	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
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



	/**
	 * @return the taxonomyKeys
	 */
	public String[] getTaxonomyKeys() {
		return taxonomyKeys;
	}



	/**
	 * @param taxonomyKeys
	 *            the taxonomyKeys to set
	 */
	public void setTaxonomyKeys(String[] taxonomyKeys) {
		this.taxonomyKeys = taxonomyKeys;
	}



	/**
	 * @return the publicationDate
	 */
	public Calendar getPublicationDate() {
		return publicationDate;
	}



	/**
	 * @param publicationDate
	 *            the publicationDate to set
	 */
	public void setPublicationDate(Calendar publicationDate) {
		this.publicationDate = publicationDate;
	}

	
	/**
	 * 
	 * @return listPrice
	 */
	public Double getListPrice() {
		return listPrice;
	}

	/**
	 * 
	 * @param listPrice
	 */
	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}
}
