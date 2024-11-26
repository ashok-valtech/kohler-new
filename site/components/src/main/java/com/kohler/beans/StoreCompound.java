package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "kohler:storeCompound")
@Node(jcrType = "kohler:storeCompound")
public class StoreCompound extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:storeNames")
    public String getStoreNames() {
        return getSingleProperty("kohler:storeNames");
    }
}
