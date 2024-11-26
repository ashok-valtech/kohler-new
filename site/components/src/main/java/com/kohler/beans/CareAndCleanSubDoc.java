package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "kohler:careAndCleanSubDoc")
@Node(jcrType = "kohler:careAndCleanSubDoc")
public class CareAndCleanSubDoc extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageUrl")
    public String getImageUrl() {
        return getSingleProperty("kohler:imageUrl");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
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
