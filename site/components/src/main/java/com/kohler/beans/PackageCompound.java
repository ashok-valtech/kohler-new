package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "kohler:packageCompound")
@Node(jcrType = "kohler:packageCompound")
public class PackageCompound extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:packageNames")
    public String getPackageNames() {
        return getSingleProperty("kohler:packageNames");
    }
}
