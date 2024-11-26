package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

@HippoEssentialsGenerated(internalName = "kohler:BizSchema")
@Node(jcrType = "kohler:BizSchema")
public class BizSchema extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:script")
    public String getScript() {
        return getSingleProperty("kohler:script");
    }
}
