package com.kohler.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hippoecm.hst.content.beans.ContentNodeBinder;
import org.hippoecm.hst.content.beans.ContentNodeBindingException;
import org.hippoecm.hst.content.beans.Node;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "kohler:SeoCategory")
@Node(jcrType = "kohler:SeoCategory")
public class SeoCategory extends BaseDocument implements ContentNodeBinder {
    private static final String KOHLER_SEO = "kohler:seo";
    private static final Logger LOGGER = LoggerFactory.getLogger(SeoCategory.class);
    private SeoSupport seo;

	@HippoEssentialsGenerated(internalName = KOHLER_SEO)
    public SeoSupport getSeo() {
        return seo == null ? getBean(KOHLER_SEO) : seo;
    }

    @HippoEssentialsGenerated(internalName = "kohler:categoryKey")
    public String getCategoryKey() {
        return getSingleProperty("kohler:categoryKey");
    }

    @HippoEssentialsGenerated(internalName = "kohler:categoryType")
    public String getCategoryType() {
        return getSingleProperty("kohler:categoryType");
    }

    @HippoEssentialsGenerated(internalName = "kohler:categoryName")
    public String getCategoryName() {
        return getSingleProperty("kohler:categoryName");
    }

    @Override
    public boolean bind(Object content, javax.jcr.Node node)
            throws ContentNodeBindingException {
        try {
            SeoCategory bean = (SeoCategory) content;
            SeoSupport seoSupport = bean.getSeo();
            javax.jcr.Node compound = getNode(node);
            if (compound == null) {
                compound = node.addNode(KOHLER_SEO, "seosupport:seo");
            }
            compound.setProperty("seosupport:seotitle",
                    seoSupport.getSeoTitle());
            compound.setProperty("seosupport:seodescription",
                    seoSupport.getSeoDescription());
            compound.setProperty("seosupport:noIndex", seoSupport.getNoIndex());
            compound.setProperty("seosupport:noFollow",
                    seoSupport.getNoFollow());
            compound.setProperty("seosupport:canonicalUrl",
                    seoSupport.getCanonicalUrl());
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

    public void setSeo(SeoSupport seo) {
        this.seo = seo;
    }
}
