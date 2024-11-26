package com.kohler.commons.dto;

/**
 * Data Transfer class to update SEO Plugin 
 * @author dhanwan.r
 * Date: 10/07/2019
 * @version 1.0
 */
public class SeoRequest {
	
	private String canonicalUrl;
	private Boolean noIndex;
	private Boolean noFollow;
	private String pageDocument;
	private String seoTitle;
	private String seoDesc;
	private String relativePath;
	private String className;
	private String siteMapItem;
	private String currentPage;
	private String query;
	private String isProduct;
	
	public String getIsProduct() {
		return isProduct;
	}
	public void setIsProduct(String isProduct) {
		this.isProduct = isProduct;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getCanonicalUrl() {
		return canonicalUrl;
	}
	public void setCanonicalUrl(String canonicalUrl) {
		this.canonicalUrl = canonicalUrl;
	}
	public Boolean getNoIndex() {
		return noIndex;
	}
	public void setNoIndex(Boolean noIndex) {
		this.noIndex = noIndex;
	}
	public Boolean getNoFollow() {
		return noFollow;
	}
	public void setNoFollow(Boolean noFollow) {
		this.noFollow = noFollow;
	}
	public String getPageDocument() {
		return pageDocument;
	}
	public void setPageDocument(String pageDocument) {
		this.pageDocument = pageDocument;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoDesc() {
		return seoDesc;
	}
	public void setSeoDesc(String seoDesc) {
		this.seoDesc = seoDesc;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSiteMapItem() {
		return siteMapItem;
	}
	public void setSiteMapItem(String siteMapItem) {
		this.siteMapItem = siteMapItem;
	}
	
}
