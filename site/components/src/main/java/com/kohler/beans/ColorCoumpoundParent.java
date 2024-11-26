/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import java.util.List;
import com.kohler.beans.ColorCoumpound;

@HippoEssentialsGenerated(internalName = "kohler:colorCoumpoundParent")
@Node(jcrType = "kohler:colorCoumpoundParent")
public class ColorCoumpoundParent extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "kohler:string")
    public String getString() {
        return getSingleProperty("kohler:string");
    }

    @HippoEssentialsGenerated(internalName = "kohler:kohler_colorcoumpound")
    public List<ColorCoumpound> getKohler_colorcoumpound() {
        return getChildBeansByName("kohler:kohler_colorcoumpound",
                ColorCoumpound.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:searchURL")
    public String getSearchURL() {
        return getSingleProperty("kohler:searchURL");
    }
}
