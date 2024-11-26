package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "kohler:shoppingGuideCompoundDocument")
@Node(jcrType = "kohler:shoppingGuideCompoundDocument")
public class ShoppingGuideCompoundDocument extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:imageURL")
    public String getImageURL() {
        return getSingleProperty("kohler:imageURL");
    }

    @HippoEssentialsGenerated(internalName = "kohler:navigationLink")
    public String getNavigationLink() {
        return getSingleProperty("kohler:navigationLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public String getDescription() {
        return getSingleProperty("kohler:description");
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
