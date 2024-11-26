/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.hippoecm.hst.content.beans.Node;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "kohler:homedocument")
@Node(jcrType = "kohler:homedocument")
public class Homedocument extends BaseDocument {
	@HippoEssentialsGenerated(internalName = "kohler:title")
	public String getTitle() {
		return getSingleProperty("kohler:title");
	}

	@HippoEssentialsGenerated(internalName = "kohler:introduction")
	public String getIntroduction() {
		return getSingleProperty("kohler:introduction");
	}

	@HippoEssentialsGenerated(internalName = "kohler:hstUrl")
	public String getHstUrl() {
		return getSingleProperty("kohler:hstUrl");
	}

	@HippoEssentialsGenerated(internalName = "kohler:imagePath")
	public String getImagePath() {
		return getSingleProperty("kohler:imagePath");
	}

	@HippoEssentialsGenerated(internalName = "kohler:displayOrder")
	public Long getDisplayOrder() {
		return getSingleProperty("kohler:displayOrder");
	}

	@HippoEssentialsGenerated(internalName = "kohler:imageAlt")
	public String getImageAlt() {
		return getSingleProperty("kohler:imageAlt");
	}

	@HippoEssentialsGenerated(internalName = "kohler:imageTitle")
	public String getImageTitle() {
		return getSingleProperty("kohler:imageTitle");
	}
}
