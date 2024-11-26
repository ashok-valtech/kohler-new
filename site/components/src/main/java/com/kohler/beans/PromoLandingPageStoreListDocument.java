package com.kohler.beans;

import java.util.List;

import org.hippoecm.hst.content.beans.Node;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "kohler:promoLandingPageStoreListDocument")
@Node(jcrType = "kohler:promoLandingPageStoreListDocument")
public class PromoLandingPageStoreListDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:kohler_promolandingpagestorelistcompound")
    public List<PromoLandingPageStoreListCompound> getKohler_promolandingpagestorelistcompound() {
        return getChildBeansByName(
                "kohler:kohler_promolandingpagestorelistcompound",
                PromoLandingPageStoreListCompound.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:imagePath")
    public String getImagePath() {
        return getSingleProperty("kohler:imagePath");
    }

    @HippoEssentialsGenerated(internalName = "kohler:displayorder")
    public Long getDisplayorder() {
        return getSingleProperty("kohler:displayorder");
    }
}
