package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import com.google.common.base.Strings;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "kohler:FilterCompound")
@Node(jcrType = "kohler:FilterCompound")
public class FilterCompound extends HippoCompound {

    private String defaultVal;
    private String alternate;

    @HippoEssentialsGenerated(internalName = "kohler:defaultVal")
    public String getDefaultVal() {
        return Strings.isNullOrEmpty(defaultVal)?getSingleProperty("kohler:defaultVal"): defaultVal;
    }

    @HippoEssentialsGenerated(internalName = "kohler:alternate")
    public String getAlternate() {
        return Strings.isNullOrEmpty(alternate) ? getSingleProperty("kohler:alternate")
                : alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }
}
