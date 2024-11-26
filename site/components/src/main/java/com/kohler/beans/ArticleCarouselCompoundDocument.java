package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "kohler:articleCarouselCompoundDocument")
@Node(jcrType = "kohler:articleCarouselCompoundDocument")
public class ArticleCarouselCompoundDocument extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:imageURL")
    public String getImageURL() {
        return getSingleProperty("kohler:imageURL");
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
