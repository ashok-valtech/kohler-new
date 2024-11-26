package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

@HippoEssentialsGenerated(internalName = "kohler:walkInBathVideoDocument")
@Node(jcrType = "kohler:walkInBathVideoDocument")
public class WalkInBathVideoDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:videoPath")
    public String getVideoPath() {
        return getSingleProperty("kohler:videoPath");
    }
}
