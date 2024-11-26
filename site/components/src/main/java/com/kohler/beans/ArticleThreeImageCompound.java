package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "kohler:articleThreeImageCompound")
@Node(jcrType = "kohler:articleThreeImageCompound")
public class ArticleThreeImageCompound extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:imagePath")
    public String getImagePath() {
        return getSingleProperty("kohler:imagePath");
    }

    @HippoEssentialsGenerated(internalName = "kohler:navigationLink")
    public String getNavigationLink() {
        return getSingleProperty("kohler:navigationLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageDescription")
    public HippoHtml getImageDescription() {
        return getHippoHtml("kohler:imageDescription");
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
