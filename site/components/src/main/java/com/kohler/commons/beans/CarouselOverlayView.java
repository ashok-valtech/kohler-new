/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.beans;

import java.util.ArrayList;
import java.util.List;

public class CarouselOverlayView {
	private String title;
	private String description;
	private String imageURL;
	private String link;
	private String label;
	private String imageAlt;
	private String imageTitle;
	private List<CarouselOverlayCompoundView> carouselOverlayCompoundViews = new ArrayList<CarouselOverlayCompoundView>();
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public List<CarouselOverlayCompoundView> getCarouselOverlayCompoundViews() {
		return carouselOverlayCompoundViews;
	}
	public void setCarouselOverlayCompoundViews(List<CarouselOverlayCompoundView> carouselOverlayCompoundViews) {
		this.carouselOverlayCompoundViews = carouselOverlayCompoundViews;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getImageAlt() {
		return imageAlt;
	}
	public void setImageAlt(String imageAlt) {
		this.imageAlt = imageAlt;
	}
	public String getImageTitle() {
		return imageTitle;
	}
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}
}
