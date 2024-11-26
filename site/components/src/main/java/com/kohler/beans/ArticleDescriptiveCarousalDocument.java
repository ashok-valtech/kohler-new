package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "kohler:articleDescriptiveCarousalDocument")
@Node(jcrType = "kohler:articleDescriptiveCarousalDocument")
public class ArticleDescriptiveCarousalDocument extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:carousalImageURL")
    public String getCarousalImageURL() {
        return getSingleProperty("kohler:carousalImageURL");
    }

    @HippoEssentialsGenerated(internalName = "kohler:carousalImageAlt")
    public String getCarousalImageAlt() {
        return getSingleProperty("kohler:carousalImageAlt");
    }

    @HippoEssentialsGenerated(internalName = "kohler:carousalImageTitle")
    public String getCarousalImageTitle() {
        return getSingleProperty("kohler:carousalImageTitle");
    }

    @HippoEssentialsGenerated(internalName = "kohler:learnMoreLabel")
    public String getLearnMoreLabel() {
        return getSingleProperty("kohler:learnMoreLabel");
    }

    @HippoEssentialsGenerated(internalName = "kohler:learnMoreLink")
    public String getLearnMoreLink() {
        return getSingleProperty("kohler:learnMoreLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:viewAllLabel")
    public String getViewAllLabel() {
        return getSingleProperty("kohler:viewAllLabel");
    }

    @HippoEssentialsGenerated(internalName = "kohler:viewAllLink")
    public String getViewAllLink() {
        return getSingleProperty("kohler:viewAllLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:logoImageURL1")
    public String getLogoImageURL1() {
        return getSingleProperty("kohler:logoImageURL1");
    }

    @HippoEssentialsGenerated(internalName = "kohler:logoImageURL1Alt")
    public String getLogoImageURL1Alt() {
        return getSingleProperty("kohler:logoImageURL1Alt");
    }
    @HippoEssentialsGenerated(internalName = "kohler:logoImageURL1Title")
    public String getLogoImageURL1Title() {
        return getSingleProperty("kohler:logoImageURL1Title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:logoImageURL2")
    public String getLogoImageURL2() {
        return getSingleProperty("kohler:logoImageURL2");
    }

    @HippoEssentialsGenerated(internalName = "kohler:logoImageURL2Alt")
    public String getLogoImageURL2Alt() {
        return getSingleProperty("kohler:logoImageURL2Alt");
    }
    @HippoEssentialsGenerated(internalName = "kohler:logoImageURL2Title")
    public String getLogoImageURL2Title() {
        return getSingleProperty("kohler:logoImageURL2Title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:navigationLink")
    public String getNavigationLink() {
        return getSingleProperty("kohler:navigationLink");
    }
}
