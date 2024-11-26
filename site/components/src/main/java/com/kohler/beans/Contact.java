/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

@HippoEssentialsGenerated(internalName = "kohler:Contact")
@Node(jcrType = "kohler:Contact")
public class Contact extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:streeMain")
    public String getStreeMain() {
        return getSingleProperty("kohler:streeMain");
    }

    @HippoEssentialsGenerated(internalName = "kohler:streetSub")
    public String getStreetSub() {
        return getSingleProperty("kohler:streetSub");
    }

    @HippoEssentialsGenerated(internalName = "kohler:phoneNumber")
    public String getPhoneNumber() {
        return getSingleProperty("kohler:phoneNumber");
    }

    @HippoEssentialsGenerated(internalName = "kohler:fax")
    public String getFax() {
        return getSingleProperty("kohler:fax");
    }

    @HippoEssentialsGenerated(internalName = "kohler:externalLink")
    public String getExternalLink() {
        return getSingleProperty("kohler:externalLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:email")
    public String getEmail() {
        return getSingleProperty("kohler:email");
    }

    @HippoEssentialsGenerated(internalName = "kohler:phoneLabel")
    public String getPhoneLabel() {
        return getSingleProperty("kohler:phoneLabel");
    }

    @HippoEssentialsGenerated(internalName = "kohler:displayOrder")
    public Long getDisplayOrder() {
        return getSingleProperty("kohler:displayOrder");
    }
}
