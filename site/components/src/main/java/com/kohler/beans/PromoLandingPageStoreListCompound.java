package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "kohler:promoLandingPageStoreListCompound")
@Node(jcrType = "kohler:promoLandingPageStoreListCompound")
public class PromoLandingPageStoreListCompound extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:address")
    public HippoHtml getAddress() {
        return getHippoHtml("kohler:address");
    }
}
