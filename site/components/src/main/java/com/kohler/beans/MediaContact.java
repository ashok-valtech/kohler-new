/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

@HippoEssentialsGenerated(internalName = "kohler:mediaContact")
@Node(jcrType = "kohler:mediaContact")
public class MediaContact extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:name")
    public String getName() {
        return getSingleProperty("kohler:name");
    }

    @HippoEssentialsGenerated(internalName = "kohler:designation")
    public String getDesignation() {
        return getSingleProperty("kohler:designation");
    }

    @HippoEssentialsGenerated(internalName = "kohler:email")
    public String getEmail() {
        return getSingleProperty("kohler:email");
    }

    @HippoEssentialsGenerated(internalName = "kohler:mobile")
    public String getMobile() {
        return getSingleProperty("kohler:mobile");
    }
}
