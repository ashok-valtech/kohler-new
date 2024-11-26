/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "kohler:cCrossSelling")
@Node(jcrType = "kohler:cCrossSelling")
public class CCrossSelling extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:key")
    public String getKey() {
        return getSingleProperty("kohler:key");
    }

    @HippoEssentialsGenerated(internalName = "kohler:values")
    public Long[] getValues() {
        return getMultipleProperty("kohler:values");
    }
}
