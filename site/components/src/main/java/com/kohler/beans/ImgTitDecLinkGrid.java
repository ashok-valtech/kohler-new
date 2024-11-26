package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

@HippoEssentialsGenerated(internalName = "kohler:imgTitDecLinkGrid")
@Node(jcrType = "kohler:imgTitDecLinkGrid")
public class ImgTitDecLinkGrid extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public String getDescription() {
        return getSingleProperty("kohler:description");
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

    @HippoEssentialsGenerated(internalName = "kohler:navigationTitle")
    public String getNavigationTitle() {
        return getSingleProperty("kohler:navigationTitle");
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
