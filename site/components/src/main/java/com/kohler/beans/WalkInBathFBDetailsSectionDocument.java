package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.List;
import com.kohler.beans.WalkInBathFBDetailsCompound;

@HippoEssentialsGenerated(internalName = "kohler:WalkInBathFBDetailsSectionDocument")
@Node(jcrType = "kohler:WalkInBathFBDetailsSectionDocument")
public class WalkInBathFBDetailsSectionDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:kohler_walkinbathfbdetailscompound")
    public List<WalkInBathFBDetailsCompound> getKohler_walkinbathfbdetailscompound() {
        return getChildBeansByName("kohler:kohler_walkinbathfbdetailscompound",
                WalkInBathFBDetailsCompound.class);
    }
}
