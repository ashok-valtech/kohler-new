package com.kohler.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

import com.google.common.base.Strings;

@HippoEssentialsGenerated(internalName = "seosupport:seo")
@Node(jcrType = "seosupport:seo")
public class SeoSupport extends HippoCompound{

	private String seoTitle;
	private String seoDescription;
	private String canonicalUrl;
	private Boolean noIndex;
	private Boolean noFollow;

	public SeoSupport () {

	}

	public SeoSupport (String seoTitle, String seoDescription, String canonicalUrl, Boolean noIndex, Boolean noFollow) {
		this.seoTitle = seoTitle;
		this.seoDescription = seoDescription;
		this.canonicalUrl = canonicalUrl;
		this.noFollow = noFollow;
		this.noIndex = noIndex;
	}

	@HippoEssentialsGenerated(internalName = "seosupport:seotitle")
    public String getSeoTitle() {
		return Strings.isNullOrEmpty(seoTitle)?getSingleProperty("seosupport:seotitle"): seoTitle;
    }

    @HippoEssentialsGenerated(internalName = "seosupport:seodescription")
    public String getSeoDescription() {
        return Strings.isNullOrEmpty(seoDescription)?getSingleProperty("seosupport:seodescription"): seoDescription;
    }

    @HippoEssentialsGenerated(internalName = "seosupport:noIndex")
    public Boolean getNoIndex() {
    	return noIndex == null?getSingleProperty("seosupport:noIndex"):noIndex;
    }

    @HippoEssentialsGenerated(internalName = "seosupport:noFollow")
    public Boolean getNoFollow() {
        return noFollow == null?getSingleProperty("seosupport:noFollow"):noFollow;
    }

    @HippoEssentialsGenerated(internalName = "seosupport:canonicalUrl")
    public String getCanonicalUrl() {
        return Strings.isNullOrEmpty(canonicalUrl)?getSingleProperty("seosupport:canonicalUrl"): canonicalUrl;
    }

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public void setCanonicalUrl(String canonicalUrl) {
		this.canonicalUrl = canonicalUrl;
	}

	public void setNoIndex(Boolean noIndex) {
		this.noIndex = noIndex;
	}

	public void setNoFollow(Boolean noFollow) {
		this.noFollow = noFollow;
	}
}
