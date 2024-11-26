package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.List;
import com.kohler.beans.WalkInBathFBTopSectionCompuond;

@HippoEssentialsGenerated(internalName = "kohler:walkInBathFBTopSectionDocument")
@Node(jcrType = "kohler:walkInBathFBTopSectionDocument")
public class WalkInBathFBTopSectionDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:kohler_walkinbathfbtopsectioncompuond")
    public List<WalkInBathFBTopSectionCompuond> getKohler_walkinbathfbtopsectioncompuond() {
        return getChildBeansByName(
                "kohler:kohler_walkinbathfbtopsectioncompuond",
                WalkInBathFBTopSectionCompuond.class);
    }
}
