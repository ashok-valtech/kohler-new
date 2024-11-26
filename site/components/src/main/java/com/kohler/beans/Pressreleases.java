/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import java.util.Calendar;

import org.hippoecm.hst.content.beans.ContentNodeBinder;
import org.hippoecm.hst.content.beans.ContentNodeBindingException;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@HippoEssentialsGenerated(internalName = "kohler:pressreleases")
@Node(jcrType = "kohler:pressreleases")
public class Pressreleases extends BaseDocument implements ContentNodeBinder{

	private static final String KOHLER_SEO = "kohler:seo";

	private static final Logger LOGGER = LoggerFactory.getLogger (Pressreleases.class);

	private SeoSupport seo;

    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public String getDescription() {
        return getSingleProperty("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:text")
    public HippoHtml getText() {
        return getHippoHtml("kohler:text");
    }

    @HippoEssentialsGenerated(internalName = "kohler:date")
    public Calendar getDate() {
        return getSingleProperty("kohler:date");
    }

    @HippoEssentialsGenerated(internalName = "kohler:subtitle")
    public String getSubtitle() {
        return getSingleProperty("kohler:subtitle");
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
			Pressreleases bean =  (Pressreleases) content;
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
