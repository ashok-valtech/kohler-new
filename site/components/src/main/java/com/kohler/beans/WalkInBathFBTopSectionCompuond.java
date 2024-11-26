package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "kohler:walkInBathFBTopSectionCompuond")
@Node(jcrType = "kohler:walkInBathFBTopSectionCompuond")
public class WalkInBathFBTopSectionCompuond extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:imagePath")
    public String getImagePath() {
        return getSingleProperty("kohler:imagePath");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageAlt")
    public String getImageAlt() {
        return getSingleProperty("kohler:imageAlt");
    }

    @HippoEssentialsGenerated(internalName = "kohler:anchorName")
    public String getAnchorName() {
        return getSingleProperty("kohler:anchorName");
    }

    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageTitle")
    public String getImageTitle() {
        return getSingleProperty("kohler:imageTitle");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageOrder")
    public String getImageOrder() {
        return getSingleProperty("kohler:imageOrder");
    }
}
