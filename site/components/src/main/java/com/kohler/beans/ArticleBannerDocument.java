/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import java.util.List;

import org.hippoecm.hst.content.beans.ContentNodeBinder;
import org.hippoecm.hst.content.beans.ContentNodeBindingException;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@HippoEssentialsGenerated(internalName = "kohler:articleBannerDocument")
@Node(jcrType = "kohler:articleBannerDocument")
public class ArticleBannerDocument extends BaseDocument implements ContentNodeBinder{

	private static final Logger LOGGER = LoggerFactory.getLogger (ArticleBannerDocument.class);

	private String seoTitle;
	private String metaDescription;
	private String canonicalUrl;
	private Boolean noIndex;
	private Boolean noFollow;

	@HippoEssentialsGenerated(internalName = "kohler:imageAlt")
	public String getImageAlt() {
		return getSingleProperty("kohler:imageAlt");
	}

	@HippoEssentialsGenerated(internalName = "kohler:imageTitle")
	public String getImageTitle() {
		return getSingleProperty("kohler:imageTitle");
	}

    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:link")
    public String getLink() {
        return getSingleProperty("kohler:link");
    }

    @HippoEssentialsGenerated(internalName = "kohler:linkLable")
    public String getLinkLable() {
        return getSingleProperty("kohler:linkLable");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageLink")
    public String getImageLink() {
        return getSingleProperty("kohler:imageLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:videoUrl")
    public String getVideoUrl() {
        return getSingleProperty("kohler:videoUrl");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:titleHeader")
    public String getTitleHeader() {
        return getSingleProperty("kohler:titleHeader");
    }

    @HippoEssentialsGenerated(internalName = "kohler:relatedDocument")
    public List<HippoBean> getRelatedDocument() {
        return getLinkedBeans("kohler:relatedDocument", HippoBean.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:seoTitle")
    public String getSeoTitle() {
        return getSingleProperty("kohler:seoTitle");
    }

    @HippoEssentialsGenerated(internalName = "kohler:metaDescription")
    public String getMetaDescription() {
        return getSingleProperty("kohler:metaDescription");
    }

    @HippoEssentialsGenerated(internalName = "kohler:noIndex")
    public Boolean getNoIndex() {
        return getSingleProperty("kohler:noIndex");
    }

    @HippoEssentialsGenerated(internalName = "kohler:noFollow")
    public Boolean getNoFollow() {
        return getSingleProperty("kohler:noFollow");
    }

    @HippoEssentialsGenerated(internalName = "kohler:canonicalUrl")
    public String getCanonicalUrl() {
        return getSingleProperty("kohler:canonicalUrl");

    }

    @HippoEssentialsGenerated(internalName = "kohler:thumbnailUrl")
    public String getThumbnailUrl() {
        return getSingleProperty("kohler:thumbnailUrl");
    }

    @HippoEssentialsGenerated(internalName = "kohler:keywords")
    public String getKeywords() {
        return getSingleProperty("kohler:keywords");
    }

    @HippoEssentialsGenerated(internalName = "kohler:textColor")
    public String getTextColor() {
        return getSingleProperty("kohler:textColor");
    }

    @HippoEssentialsGenerated(internalName = "kohler:backgroundColor")
    public String getBackgroundColor() {
        return getSingleProperty("kohler:backgroundColor");
    }

    @HippoEssentialsGenerated(internalName = "kohler:titleColor")
    public String getTitleColor() {
        return getSingleProperty("kohler:titleColor");
    }

    public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
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

    public String getBeanSeoTitle() {
        return seoTitle;
    }

    public String getBeanMetaDescription() {
        return metaDescription;
    }

    public Boolean getBeanNoIndex() {
        return noIndex;
    }

    public Boolean getBeanNoFollow() {
        return noFollow;
    }

    public String getBeanCanonicalUrl() {
        return canonicalUrl;

    }

    @Override
	public boolean bind(Object content, javax.jcr.Node node) throws ContentNodeBindingException {
		try {
			ArticleBannerDocument bean =  (ArticleBannerDocument) content;
			node.setProperty("kohler:seoTitle", bean.getBeanSeoTitle());
			node.setProperty("kohler:metaDescription", bean.getBeanMetaDescription());
			node.setProperty("kohler:noIndex", bean.getBeanNoIndex());
			node.setProperty("kohler:noFollow", bean.getBeanNoFollow());
			node.setProperty("kohler:canonicalUrl", bean.getBeanCanonicalUrl());
        } catch (Exception e) {
        	if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
            throw new ContentNodeBindingException(e);
        }
        return true;
	}
}
