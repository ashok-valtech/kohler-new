/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "kohler:literature")
@Node(jcrType = "kohler:literature")
public class Literature extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:introduction")
    public String getIntroduction() {
        return getSingleProperty("kohler:introduction");
    }

    @HippoEssentialsGenerated(internalName = "kohler:relatedFile")
    public HippoBean getRelatedFile() {
        return getLinkedBean("kohler:relatedFile", HippoBean.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:displayOrder")
    public Long getDisplayOrder() {
        return getSingleProperty("kohler:displayOrder");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageUrl")
    public String getImageUrl() {
        return getSingleProperty("kohler:imageUrl");
    }

    @HippoEssentialsGenerated(internalName = "kohler:pdfUrl")
    public String getPdfUrl() {
        return getSingleProperty("kohler:pdfUrl");
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
