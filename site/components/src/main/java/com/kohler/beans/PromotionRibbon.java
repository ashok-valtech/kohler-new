package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "kohler:promotionRibbon")
@Node(jcrType = "kohler:promotionRibbon")
public class PromotionRibbon extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:ctaLink")
    public String getCtaLink() {
        return getSingleProperty("kohler:ctaLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:deskTopTitle")
    public String getDeskTopTitle() {
        return getSingleProperty("kohler:deskTopTitle");
    }

    @HippoEssentialsGenerated(internalName = "kohler:responsiveTitle")
    public String getResponsiveTitle() {
        return getSingleProperty("kohler:responsiveTitle");
    }

    @HippoEssentialsGenerated(internalName = "kohler:ctaLabel")
    public String getCtaLabel() {
        return getSingleProperty("kohler:ctaLabel");
    }

}
