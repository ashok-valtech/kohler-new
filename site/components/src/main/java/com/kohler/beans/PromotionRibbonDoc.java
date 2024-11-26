package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.List;
import com.kohler.beans.PromotionRibbon;

@HippoEssentialsGenerated(internalName = "kohler:promotionRibbonDoc")
@Node(jcrType = "kohler:promotionRibbonDoc")
public class PromotionRibbonDoc extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:kohler_promotionribbon")
    public List<PromotionRibbon> getKohler_promotionribbon() {
        return getChildBeansByName("kohler:kohler_promotionribbon",
                PromotionRibbon.class);
    }
}
