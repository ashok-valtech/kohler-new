/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "kohler:bannerdocument")
@Node(jcrType = "kohler:bannerdocument")
public class Banner extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:content")
    public HippoHtml getContent() {
        return getHippoHtml("kohler:content");
    }

    @HippoEssentialsGenerated(internalName = "kohler:textColor")
    public String getTextColor() {
        return getSingleProperty("kohler:textColor");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imagePath")
    public String getImagePath() {
        return getSingleProperty("kohler:imagePath");
    }

    @HippoEssentialsGenerated(internalName = "kohler:navigationPath")
    public String getNavigationPath() {
        return getSingleProperty("kohler:navigationPath");
    }

    @HippoEssentialsGenerated(internalName = "kohler:buttonText")
    public String getButtonText() {
        return getSingleProperty("kohler:buttonText");
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
