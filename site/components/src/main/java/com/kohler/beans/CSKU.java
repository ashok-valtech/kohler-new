/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import java.util.List;
import com.kohler.beans.CAttributes;

@HippoEssentialsGenerated(internalName = "kohler:cSKU")
@Node(jcrType = "kohler:cSKU")
public class CSKU extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:sku")
    public String getSku() {
        return getSingleProperty("kohler:sku");
    }

    @HippoEssentialsGenerated(internalName = "kohler:id")
    public Long getId() {
        return getSingleProperty("kohler:id");
    }

    @HippoEssentialsGenerated(internalName = "kohler:attributes")
    public List<CAttributes> getAttributes() {
        return getChildBeansByName("kohler:attributes", CAttributes.class);
    }
}
