package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "kohler:landingPagePackageCompund")
@Node(jcrType = "kohler:landingPagePackageCompund")
public class LandingPagePackageCompund extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:imageLink")
    public String getImageLink() {
        return getSingleProperty("kohler:imageLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:navigationLink")
    public String getNavigationLink() {
        return getSingleProperty("kohler:navigationLink");
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
