/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import java.util.List;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "kohler:faqlist")
@Node(jcrType = "kohler:faqlist")
public class FaqList extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:faqitem")
    public List<FaqItem> getFaqItems() {
        return getLinkedBeans("kohler:faqitem", FaqItem.class);
    }

    /**
     * Additional marker for the template to validate it's working with an FAQ list bean.
     */
    public boolean isFAQ() {
        return true;
    }

    @HippoEssentialsGenerated(internalName = "kohler:displayOrder")
    public Long getDisplayOrder() {
        return getSingleProperty("kohler:displayOrder");
    }
}
