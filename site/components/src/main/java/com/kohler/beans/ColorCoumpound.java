/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "kohler:colorCoumpound")
@Node(jcrType = "kohler:colorCoumpound")
public class ColorCoumpound extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:smallImage")
    public String getSmallImage() {
        return getSingleProperty("kohler:smallImage");
    }

    @HippoEssentialsGenerated(internalName = "kohler:largeImage")
    public String getLargeImage() {
        return getSingleProperty("kohler:largeImage");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public String getDescription() {
        return getSingleProperty("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:colorCode")
    public String getColorCode() {
        return getSingleProperty("kohler:colorCode");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageLargeAlt")
  	public String getImageLargeAlt() {
  		return getSingleProperty("kohler:imageLargeAlt");
  	}

  	@HippoEssentialsGenerated(internalName = "kohler:imageLargeTitle")
  	public String getImageLargeTitle() {
  		return getSingleProperty("kohler:imageLargeTitle");
  	}

    @HippoEssentialsGenerated(internalName = "kohler:imageSmallAlt")
  	public String getImageSmallAlt() {
  		return getSingleProperty("kohler:imageSmallAlt");
  	}

  	@HippoEssentialsGenerated(internalName = "kohler:imageSmallTitle")
  	public String getImageSmallTitle() {
  		return getSingleProperty("kohler:imageSmallTitle");
  	}
}
