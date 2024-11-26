package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "kohler:walkInBathFBDetailsCompound")
@Node(jcrType = "kohler:walkInBathFBDetailsCompound")
public class WalkInBathFBDetailsCompound extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:anchorName")
    public String getAnchorName() {
        return getSingleProperty("kohler:anchorName");
    }

    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imagePath")
    public String getImagePath() {
        return getSingleProperty("kohler:imagePath");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageAlt")
    public String getImageAlt() {
        return getSingleProperty("kohler:imageAlt");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageTitle")
    public String getImageTitle() {
        return getSingleProperty("kohler:imageTitle");
    }

    @HippoEssentialsGenerated(internalName = "kohler:subTitle")
    public String getSubTitle() {
        return getSingleProperty("kohler:subTitle");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageCaption")
    public String getImageCaption() {
        return getSingleProperty("kohler:imageCaption");
    }
}
