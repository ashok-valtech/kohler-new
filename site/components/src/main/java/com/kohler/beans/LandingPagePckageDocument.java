package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import com.kohler.beans.LandingPagePackageCompund;
import java.util.List;

@HippoEssentialsGenerated(internalName = "kohler:landingPagePckageDocument")
@Node(jcrType = "kohler:landingPagePckageDocument")
public class LandingPagePckageDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:promotionLink")
    public String getPromotionLink() {
        return getSingleProperty("kohler:promotionLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:title")
    public HippoHtml getTitle() {
        return getHippoHtml("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:landingPagePackageCompund")
    public List<LandingPagePackageCompund> getLandingPagePackageCompund() {
        return getChildBeansByName("kohler:landingPagePackageCompund",
                LandingPagePackageCompund.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:promotionButton")
    public String getPromotionButton() {
        return getSingleProperty("kohler:promotionButton");
    }
}
