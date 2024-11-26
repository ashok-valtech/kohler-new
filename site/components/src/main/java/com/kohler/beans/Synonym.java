package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.List;
import com.kohler.beans.CSynonym;

@HippoEssentialsGenerated(internalName = "kohler:Synonym")
@Node(jcrType = "kohler:Synonym")
public class Synonym extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:Synonym")
    public List<CSynonym> getSynonym() {
        return getChildBeansByName("kohler:Synonym", CSynonym.class);
    }
}
