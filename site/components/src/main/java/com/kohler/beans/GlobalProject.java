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

@HippoEssentialsGenerated(internalName = "kohler:globalProject")
@Node(jcrType = "kohler:globalProject")
public class GlobalProject extends BaseDocument implements ContentNodeBinder{

	private static final String KOHLER_SEO = "kohler:seo";

	private static final Logger LOGGER = LoggerFactory.getLogger (GlobalProject.class);

	private SeoSupport seo;

    @HippoEssentialsGenerated(internalName = "kohler:imageUrl")
    public String getImageUrl() {
        return getSingleProperty("kohler:imageUrl");
    }

    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "hippotaxonomy:keys")
    public String[] getKeys() {
        return getMultipleProperty("hippotaxonomy:keys");
    }

    @HippoEssentialsGenerated(internalName = "kohler:place")
    public String getPlace() {
        return getSingleProperty("kohler:place");
    }

    @HippoEssentialsGenerated(internalName = "kohler:content")
    public HippoHtml getContent() {
        return getHippoHtml("kohler:content");
    }

    @HippoEssentialsGenerated(internalName = "kohler:relatedProduct")
    public String[] getRelatedProduct() {
        return getMultipleProperty("kohler:relatedProduct");
    }

    @HippoEssentialsGenerated(internalName = "kohler:bannerLink")
    public List<HippoBean> getBannerLink() {
        return getLinkedBeans("kohler:bannerLink", HippoBean.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:displayOrder")
    public Long getDisplayOrder() {
        return getSingleProperty("kohler:displayOrder");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageAlt")
  	public String getImageAlt() {
  		return getSingleProperty("kohler:imageAlt");
  	}

  	@HippoEssentialsGenerated(internalName = "kohler:imageTitle")
  	public String getImageTitle() {
  		return getSingleProperty("kohler:imageTitle");
  	}

    @HippoEssentialsGenerated(internalName = "kohler:filters")
    public String getFilters() {
        return getSingleProperty("kohler:filters");
    }

    @HippoEssentialsGenerated(internalName = KOHLER_SEO)
    public SeoSupport getSeo() {
        return seo == null?getBean(KOHLER_SEO):seo;
    }

    @HippoEssentialsGenerated(internalName = "kohler:keywords")
    public String getKeywords() {
        return getSingleProperty("kohler:keywords");
    }

	public void setSeo(SeoSupport seo) {
		this.seo = seo;
	}

	@Override
	public boolean bind(Object content, javax.jcr.Node node) throws ContentNodeBindingException {
		try {
			GlobalProject bean =  (GlobalProject) content;
			SeoSupport seoSupport = bean.getSeo();
			javax.jcr.Node compound = getNode(node);
			if (compound == null) {
				 compound = node.addNode(KOHLER_SEO, "seosupport:seo");
			}
    		compound.setProperty("seosupport:seotitle", seoSupport.getSeoTitle());
    		compound.setProperty("seosupport:seodescription", seoSupport.getSeoDescription());
    		compound.setProperty("seosupport:noIndex", seoSupport.getNoIndex());
    		compound.setProperty("seosupport:noFollow", seoSupport.getNoFollow());
    		compound.setProperty("seosupport:canonicalUrl", seoSupport.getCanonicalUrl());
        } catch (Exception e) {
        	if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
            throw new ContentNodeBindingException(e);
        }
        return true;
	}

    protected javax.jcr.Node getNode(javax.jcr.Node node) {
    	try {
    		return node.getNode(KOHLER_SEO);
    	} catch (Exception ex) {
    		return null;
    	}
    }
}
