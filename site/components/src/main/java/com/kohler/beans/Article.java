/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "kohler:Article")
@Node(jcrType = "kohler:Article")
public class Article extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:link")
    public String getLink() {
        return getSingleProperty("kohler:link");
    }

    @HippoEssentialsGenerated(internalName = "kohler:videoLink")
    public String getVideoLink() {
        return getSingleProperty("kohler:videoLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:linkLable")
    public String getLinkLable() {
        return getSingleProperty("kohler:linkLable");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageLink")
    public String getImageLink() {
        return getSingleProperty("kohler:imageLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageCaption")
    public String getImageCaption() {
        return getSingleProperty("kohler:imageCaption");
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
