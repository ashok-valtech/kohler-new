/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.beans;

import java.util.LinkedHashMap;
import java.util.Map;

public class Technical_Detail {
	private String overAllHeight;
	private String overAllLength;
	private String overAllWidth;
	private String gfLineArt;
	private Map<String, String> installationPdf = new LinkedHashMap<String, String>();
	private String[] installationWithoutSpPdf;
	private String[] specPDF;
	public String[] getInstallationWithoutSpPdf() {
		return installationWithoutSpPdf;
	}
	public void setInstallationWithoutSpPdf(String[] installationWithoutSpPdf) {
		this.installationWithoutSpPdf = installationWithoutSpPdf;
	}
	
	public String getOverAllHeight() {
		return overAllHeight;
	}
	public void setOverAllHeight(String overAllHeight) {
		this.overAllHeight = overAllHeight;
	}
	public String getOverAllLength() {
		return overAllLength;
	}
	public void setOverAllLength(String overAllLength) {
		this.overAllLength = overAllLength;
	}
	public String getOverAllWidth() {
		return overAllWidth;
	}
	public void setOverAllWidth(String overAllWidth) {
		this.overAllWidth = overAllWidth;
	}
	public String getGfLineArt() {
		return gfLineArt;
	}
	public void setGfLineArt(String gfLineArt) {
		this.gfLineArt = gfLineArt;
	}
	public Map<String, String> getInstallationPdf() {
		return installationPdf;
	}
	public void setInstallationPdf(Map<String, String> installationPdf) {
		this.installationPdf = installationPdf;
	}
	public String[] getSpecPDF() {
		return specPDF;
	}
	public void setSpecPDF(String[] specPDF) {
		this.specPDF = specPDF;
	}
}
