/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "kohler:carouselOverlayCompoundDocument")
@Node(jcrType = "kohler:carouselOverlayCompoundDocument")
public class CarouselOverlayCompoundDocument extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:itemNo")
    public String getItemNo() {
        return getSingleProperty("kohler:itemNo");
    }

    @HippoEssentialsGenerated(internalName = "kohler:sku")
    public String getSku() {
        return getSingleProperty("kohler:sku");
    }

    @HippoEssentialsGenerated(internalName = "kohler:xCoordinate")
    public Double getXCoordinate() {
        return getSingleProperty("kohler:xCoordinate");
    }

    @HippoEssentialsGenerated(internalName = "kohler:yCoordinate")
    public Double getYCoordinate() {
        return getSingleProperty("kohler:yCoordinate");
    }
}
