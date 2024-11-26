package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

@HippoEssentialsGenerated(internalName = "kohler:PromoBanner")
@Node(jcrType = "kohler:PromoBanner")
public class PromoBanner extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageURL")
    public String getImageURL() {
        return getSingleProperty("kohler:imageURL");
    }

    @HippoEssentialsGenerated(internalName = "kohler:navigationURL")
    public String getNavigationURL() {
        return getSingleProperty("kohler:navigationURL");
    }

    @HippoEssentialsGenerated(internalName = "hippotaxonomy:keys")
    public String[] getKeys() {
        return getMultipleProperty("hippotaxonomy:keys");
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
