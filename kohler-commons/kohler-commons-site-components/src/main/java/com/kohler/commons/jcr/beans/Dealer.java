/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.jcr.beans;

import java.util.Calendar;

import org.jcrom.annotations.JcrName;
import org.jcrom.annotations.JcrNode;
import org.jcrom.annotations.JcrPath;
import org.jcrom.annotations.JcrProperty;

import com.kohler.commons.constants.Constants;

@JcrNode(nodeType = Constants.NS_DEALER)
public class Dealer {
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

	@JcrProperty(name = Constants.NS_STOREID)
	private String storeId;

	@JcrProperty(name = Constants.NS_STORENAME)
	private String storeName;

	@JcrProperty(name = Constants.NS_PRIMARY_CONTACT_NAME)
	private String primaryContactName;

	@JcrProperty(name = Constants.NS_ADDRESSLINE_ONE)
	private String addressLineOne;

	@JcrProperty(name = Constants.NS_ADDRESSLINE_TWO)
	private String addressLineTwo;

	@JcrProperty(name = Constants.NS_ADDRESSLINE_THREE)
	private String addressLineThree;

	@JcrProperty(name = Constants.NS_CITY)
	private String city;

	@JcrProperty(name = Constants.NS_STATE)
	private String dealerState;

	@JcrProperty(name = Constants.NS_ZIPCODE)
	private String zipCode;

	@JcrProperty(name = Constants.NS_COUNTRY)
	private String country;

	@JcrProperty(name = Constants.NS_PHONE)
	private String[] phone;

	@JcrProperty(name = Constants.NS_MOBILE)
	private String mobile;

	@JcrProperty(name = Constants.NS_GENERAL_EMAIL)
	private String generalEmail;

	@JcrProperty(name = Constants.NS_PRIMARY_CONTACT_EMAIL)
	private String primaryContactEmail;

	@JcrProperty(name = Constants.NS_WEBSITE)
	private String website;

	@JcrProperty(name = Constants.NS_DEALERTYPE)
	private String dealerType;

	@JcrProperty(name = Constants.NS_DESCRIPTION)
	private String description;

	@JcrProperty(name = Constants.NS_LATITUDE)
	private String latitude;

	@JcrProperty(name = Constants.NS_LONGITUDE)
	private String longitude;

	@JcrProperty(name = Constants.NS_FACEBOOK_LINK)
	private String facebookLink;

	@JcrProperty(name = Constants.NS_TWITTER_LINK)
	private String twitterLink;

	@JcrProperty(name = Constants.NS_OPENING_HOURS)
	private String[] openingHours;

	@JcrProperty(name = Constants.NS_LINE_LINK)
	private String lineLink;

	@JcrProperty(name = Constants.NS_INSTAGRAM_LINK)
	private String instagramLink;

	@JcrProperty(name = Constants.NS_STORE_LOGO)
	private String storeLogo;

	@JcrProperty(name = Constants.NS_FAX)
	private String fax;

	@JcrProperty(name = Constants.NS_DETAIL_VIEW)
	private Boolean detailView;

	@JcrProperty(name = Constants.NS_SHOWROOM_TYPE)
	private String showroomType;
	
	@JcrProperty(name = Constants.NS_DEALER_HASH)
	private Long dealerHash;

	public Long getDealerHash() {
		return dealerHash;
	}

	public void setDealerHash(Long dealerHash) {
		this.dealerHash = dealerHash;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateSummary() {
		return stateSummary;
	}

	public void setStateSummary(String stateSummary) {
		this.stateSummary = stateSummary;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Calendar getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Calendar lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTranslationId() {
		return translationId;
	}

	public void setTranslationId(String translationId) {
		this.translationId = translationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getAddressLineOne() {
		return addressLineOne;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	public String getAddressLineThree() {
		return addressLineThree;
	}

	public void setAddressLineThree(String addressLineThree) {
		this.addressLineThree = addressLineThree;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDealerState() {
		return dealerState;
	}

	public void setDealerState(String dealerState) {
		this.dealerState = dealerState;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String[] getPhone() {
		return phone;
	}

	public void setPhone(String[] phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getFacebookLink() {
		return facebookLink;
	}

	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}

	public String getTwitterLink() {
		return twitterLink;
	}

	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}

	public String[] getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String[] openingHours) {
		this.openingHours = openingHours;
	}

	public String getLineLink() {
		return lineLink;
	}

	public void setLineLink(String lineLink) {
		this.lineLink = lineLink;
	}

	public String getInstagramLink() {
		return instagramLink;
	}

	public void setInstagramLink(String instagramLink) {
		this.instagramLink = instagramLink;
	}

	public String getPrimaryContactName() {
		return primaryContactName;
	}

	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}

	public String getGeneralEmail() {
		return generalEmail;
	}

	public void setGeneralEmail(String generalEmail) {
		this.generalEmail = generalEmail;
	}

	public String getPrimaryContactEmail() {
		return primaryContactEmail;
	}

	public void setPrimaryContactEmail(String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}

	public String getStoreLogo() {
		return storeLogo;
	}

	public void setStoreLogo(String storeLogo) {
		this.storeLogo = storeLogo;
	}

	public Boolean getDetailView() {
		return detailView;
	}

	public void setDetailView(Boolean detailView) {
		this.detailView = detailView;
	}

	public String getShowroomType() {
		return showroomType;
	}

	public void setShowroomType(String showroomType) {
		this.showroomType = showroomType;
	}
}
