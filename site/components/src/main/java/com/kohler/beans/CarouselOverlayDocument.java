/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import java.util.List;
import org.hippoecm.hst.content.beans.Node;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "kohler:carouselOverlayDocument")
@Node(jcrType = "kohler:carouselOverlayDocument")
public class CarouselOverlayDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public String getDescription() {
        return getSingleProperty("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:kohler_carouseloverlaycompounddocument")
    public List<CarouselOverlayCompoundDocument> getKohler_carouseloverlaycompounddocument() {
        return getChildBeansByName(
                "kohler:kohler_carouseloverlaycompounddocument",
                CarouselOverlayCompoundDocument.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:link")
    public String getLink() {
        return getSingleProperty("kohler:link");
    }

    @HippoEssentialsGenerated(internalName = "kohler:label")
    public String getLabel() {
        return getSingleProperty("kohler:label");
    }

    @HippoEssentialsGenerated(internalName = "kohler:image")
    public String getImage() {
        return getSingleProperty("kohler:image");
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
