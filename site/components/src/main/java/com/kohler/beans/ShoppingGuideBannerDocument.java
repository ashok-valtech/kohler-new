package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "kohler:ShoppingGuideBannerDocument")
@Node(jcrType = "kohler:ShoppingGuideBannerDocument")
public class ShoppingGuideBannerDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imagePath")
    public String getImagePath() {
        return getSingleProperty("kohler:imagePath");
    }

    @HippoEssentialsGenerated(internalName = "kohler:CTAButtonText")
    public String getCTAButtonText() {
        return getSingleProperty("kohler:CTAButtonText");
    }

    @HippoEssentialsGenerated(internalName = "kohler:CTALink")
    public String getCTALink() {
        return getSingleProperty("kohler:CTALink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:textColor")
    public String getTextColor() {
        return getSingleProperty("kohler:textColor");
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
