package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "kohler:mapDocument")
@Node(jcrType = "kohler:mapDocument")
public class MapDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:logoUrl")
    public String getLogoUrl() {
        return getSingleProperty("kohler:logoUrl");
    }

    @HippoEssentialsGenerated(internalName = "kohler:mapUrl")
    public String getMapUrl() {
        return getSingleProperty("kohler:mapUrl");
    }

    @HippoEssentialsGenerated(internalName = "kohler:address")
    public HippoHtml getAddress() {
        return getHippoHtml("kohler:address");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:latitude")
    public String getLatitude() {
        return getSingleProperty("kohler:latitude");
    }

    @HippoEssentialsGenerated(internalName = "kohler:longitude")
    public String getLongitude() {
        return getSingleProperty("kohler:longitude");
    }
}
