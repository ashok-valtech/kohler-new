/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "kohler:commoncontent")
@Node(jcrType = "kohler:commoncontent")
public class Commoncontent extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:content")
    public HippoHtml getContent() {
        return getHippoHtml("kohler:content");
    }

    @HippoEssentialsGenerated(internalName = "kohler:pageTitle")
    public String getPageTitle() {
        return getSingleProperty("kohler:pageTitle");
    }
}
