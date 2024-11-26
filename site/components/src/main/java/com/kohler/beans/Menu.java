/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;

@HippoEssentialsGenerated(internalName = "kohler:menu")
@Node(jcrType = "kohler:menu")
public class Menu extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:menuText")
    public String getMenuText() {
        return getSingleProperty("kohler:menuText");
    }

    @HippoEssentialsGenerated(internalName = "kohler:menuLink")
    public String getMenuLink() {
        return getSingleProperty("kohler:menuLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:image")
    public HippoGalleryImageSet getImage() {
        return getLinkedBean("kohler:image", HippoGalleryImageSet.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:iconCode")
    public String getIconCode() {
        return getSingleProperty("kohler:iconCode");
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
