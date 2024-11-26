/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

@HippoEssentialsGenerated(internalName = "kohler:Dealer")
@Node(jcrType = "kohler:Dealer")
public class Dealer extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:nameOfStore")
    public String getNameOfStore() {
        return getSingleProperty("kohler:nameOfStore");
    }

    @HippoEssentialsGenerated(internalName = "kohler:addressLine1")
    public String getAddressLine1() {
        return getSingleProperty("kohler:addressLine1");
    }

    @HippoEssentialsGenerated(internalName = "kohler:addressLine2")
    public String getAddressLine2() {
        return getSingleProperty("kohler:addressLine2");
    }

    @HippoEssentialsGenerated(internalName = "kohler:addressLine3")
    public String getAddressLine3() {
        return getSingleProperty("kohler:addressLine3");
    }

    @HippoEssentialsGenerated(internalName = "kohler:city")
    public String getCity() {
        return getSingleProperty("kohler:city");
    }

    @HippoEssentialsGenerated(internalName = "kohler:state")
    public String getState() {
        return getSingleProperty("kohler:state");
    }

    @HippoEssentialsGenerated(internalName = "kohler:zip")
    public String getZip() {
        return getSingleProperty("kohler:zip");
    }

    @HippoEssentialsGenerated(internalName = "kohler:country")
    public String getCountry() {
        return getSingleProperty("kohler:country");
    }

    @HippoEssentialsGenerated(internalName = "kohler:mobilePhone")
    public String getMobilePhone() {
        return getSingleProperty("kohler:mobilePhone");
    }

    @HippoEssentialsGenerated(internalName = "kohler:website")
    public String getWebsite() {
        return getSingleProperty("kohler:website");
    }

    @HippoEssentialsGenerated(internalName = "kohler:dealerType")
    public String getDealerType() {
        return getSingleProperty("kohler:dealerType");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public String getDescription() {
        return getSingleProperty("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:storeId")
    public String getStoreId() {
        return getSingleProperty("kohler:storeId");
    }

    @HippoEssentialsGenerated(internalName = "kohler:latitude")
    public String getLatitude() {
        return getSingleProperty("kohler:latitude");
    }

    @HippoEssentialsGenerated(internalName = "kohler:longitude")
    public String getLongitude() {
        return getSingleProperty("kohler:longitude");
    }

    @HippoEssentialsGenerated(internalName = "kohler:facebookLink")
    public String getFacebookLink() {
        return getSingleProperty("kohler:facebookLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:twitterLink")
    public String getTwitterLink() {
        return getSingleProperty("kohler:twitterLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:openingHours")
    public String[] getOpeningHours() {
        return getMultipleProperty("kohler:openingHours");
    }

    @HippoEssentialsGenerated(internalName = "kohler:instagramLink")
    public String getInstagramLink() {
        return getSingleProperty("kohler:instagramLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:lineLink")
    public String getLineLink() {
        return getSingleProperty("kohler:lineLink");
    }

    @HippoEssentialsGenerated(internalName = "kohler:primaryContactName")
    public String getPrimaryContactName() {
        return getSingleProperty("kohler:primaryContactName");
    }

    @HippoEssentialsGenerated(internalName = "kohler:primaryContactEmail")
    public String getPrimaryContactEmail() {
        return getSingleProperty("kohler:primaryContactEmail");
    }

    @HippoEssentialsGenerated(internalName = "kohler:generalEmail")
    public String getGeneralEmail() {
        return getSingleProperty("kohler:generalEmail");
    }

    @HippoEssentialsGenerated(internalName = "kohler:storeLogo")
    public String getStoreLogo() {
        return getSingleProperty("kohler:storeLogo");
    }

    @HippoEssentialsGenerated(internalName = "kohler:phone")
    public String[] getPhone() {
        return getMultipleProperty("kohler:phone");
    }

    @HippoEssentialsGenerated(internalName = "kohler:fax")
    public String getFax() {
        return getSingleProperty("kohler:fax");
    }

    @HippoEssentialsGenerated(internalName = "kohler:detailView")
    public Boolean getDetailView() {
        return getSingleProperty("kohler:detailView");
    }

    @HippoEssentialsGenerated(internalName = "kohler:showroomType")
    public String getShowroomType() {
        return getSingleProperty("kohler:showroomType");
    }

    @HippoEssentialsGenerated(internalName = "kohler:dealerhash")
    public Long getDealerhash() {
        return getSingleProperty("kohler:dealerhash");
    }
}
