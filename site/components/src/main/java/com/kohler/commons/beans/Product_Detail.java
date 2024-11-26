/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.beans;

import java.util.List;
import java.util.Map;
import com.kohler.beans.SeoSupport;

public class Product_Detail {

	private String itemNo;
	private String defaultSKU;
	private String shortDescription;
	private String description;
	private String imgIso;
	private String glamImg;
	private Boolean isNew;
	private String youtubeId;
	private String logosToDisplay;
	private AdCopy_Detail adCopy;
	private Technical_Detail techDetail;
	private Cad_Detail cadDetail;
	private List<SKU_Detail> skus;
	private CrossSelling_Detail crossSellingDetail;
	private String pdpArticleURL;
	private List<String> installationDownloads;
	private List<String> collection;
	private List<String> installationType;
	private List<String> brandName;
	private Map<String,String> imgMap;
	private String material;
	private String discontinued;
	private String[] feature;
	private String shape;
	private SeoSupport seo;

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

	public String[] keys;
	
	public String[] getKeys() {
        return this.keys;
    }
	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public List<String> getInstallationDownloads() {
		return installationDownloads;
	}

	public void setInstallationDownloads(List<String> installationDownloads) {
		this.installationDownloads = installationDownloads;
	}

	public List<String> getCollection() {
		return collection;
	}

	public void setCollection(List<String> collection) {
		this.collection = collection;
	}

	public String getDefaultSKU() {
		return defaultSKU;
	}

	public void setDefaultSKU(String defaultSKU) {
		this.defaultSKU = defaultSKU;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgIso() {
		return imgIso;
	}

	public void setImgIso(String imgIso) {
		this.imgIso = imgIso;
	}

	public List<String> getInstallationType() {
		return installationType;
	}

	public void setInstallationType(List<String> installationType) {
		this.installationType = installationType;
	}

	public List<String> getBrandName() {
		return brandName;
	}

	public void setBrandName(List<String> brandName) {
		this.brandName = brandName;
	}

	public List<SKU_Detail> getSkus() {
		return skus;
	}

	public void setSkus(List<SKU_Detail> skus) {
		this.skus = skus;
	}

	public AdCopy_Detail getAdCopy() {
		return adCopy;
	}

	public void setAdCopy(AdCopy_Detail adCopy) {
		this.adCopy = adCopy;
	}

	public Technical_Detail getTechDetail() {
		return techDetail;
	}

	public void setTechDetail(Technical_Detail techDetail) {
		this.techDetail = techDetail;
	}

	public String getGlamImg() {
		return glamImg;
	}

	public void setGlamImg(String glamImg) {
		this.glamImg = glamImg;
	}

	public String getYoutubeId() {
		return youtubeId;
	}

	public void setYoutubeId(String youtubeId) {
		this.youtubeId = youtubeId;
	}

	public String getLogosToDisplay() {
		return logosToDisplay;
	}

	public void setLogosToDisplay(String logosToDisplay) {
		this.logosToDisplay = logosToDisplay;
	}

	public Cad_Detail getCadDetail() {
		return cadDetail;
	}

	public void setCadDetail(Cad_Detail cadDetail) {
		this.cadDetail = cadDetail;
	}

	public CrossSelling_Detail getCrossSellingDetail() {
		return crossSellingDetail;
	}

	public void setCrossSellingDetail(CrossSelling_Detail crossSellingDetail) {
		this.crossSellingDetail = crossSellingDetail;
	}

	public Map<String, String> getImgMap() {
		return imgMap;
	}

	public void setImgMap(Map<String, String> imgMap) {
		this.imgMap = imgMap;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public String getPdpArticleURL() {
		return pdpArticleURL;
	}

	public void setPdpArticleURL(String pdpArticleURL) {
		this.pdpArticleURL = pdpArticleURL;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public SeoSupport getSeo() {
		return this.seo;
	}

	public void setSeo(SeoSupport seo) {
		this.seo = seo;
	}
}
