/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@HippoEssentialsGenerated(internalName = "kohler:protooldocument")
@Node(jcrType = "kohler:protooldocument")
public class Protooldocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:link")
    public String getLink() {
        return getSingleProperty("kohler:link");
    }

    @HippoEssentialsGenerated(internalName = "kohler:image")
    public HippoGalleryImageSet getImage() {
        return getLinkedBean("kohler:image", HippoGalleryImageSet.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imagepath")
    public String getImagepath() {
        return getSingleProperty("kohler:imagepath");
    }

    @HippoEssentialsGenerated(internalName = "kohler:relatedDocument")
    public HippoBean getRelatedDocument() {
        return getLinkedBean("kohler:relatedDocument", HippoBean.class);
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
