package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

@HippoEssentialsGenerated(internalName = "kohler:walkInBathFbStaticImageSection")
@Node(jcrType = "kohler:walkInBathFbStaticImageSection")
public class WalkInBathFbStaticImageSection extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:imagePath")
    public String getImagePath() {
        return getSingleProperty("kohler:imagePath");
    }
}
