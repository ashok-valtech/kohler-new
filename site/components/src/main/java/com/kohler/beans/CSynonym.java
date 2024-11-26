package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "kohler:cSynonym")
@Node(jcrType = "kohler:cSynonym")
public class CSynonym extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:key")
    public String getKey() {
        return getSingleProperty("kohler:key");
    }

    @HippoEssentialsGenerated(internalName = "kohler:value")
    public String[] getValue() {
        return getMultipleProperty("kohler:value");
    }
}
