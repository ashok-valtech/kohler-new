package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "kohler:CollectionsPromo")
@Node(jcrType = "kohler:CollectionsPromo")
public class CollectionsPromo extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:largeImageURL")
    public String getLargeImageURL() {
        return getSingleProperty("kohler:largeImageURL");
    }

    @HippoEssentialsGenerated(internalName = "kohler:smallImageURL")
    public String[] getSmallImageURL() {
        return getMultipleProperty("kohler:smallImageURL");
    }

    @HippoEssentialsGenerated(internalName = "kohler:navigationURL")
    public String getNavigationURL() {
        return getSingleProperty("kohler:navigationURL");
    }

    @HippoEssentialsGenerated(internalName = "kohler:navigationLabel")
    public String getNavigationLabel() {
        return getSingleProperty("kohler:navigationLabel");
    }

    @HippoEssentialsGenerated(internalName = "kohler:shortDescription")
    public String getShortDescription() {
        return getSingleProperty("kohler:shortDescription");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:displayOrder")
    public Long getDisplayOrder() {
        return getSingleProperty("kohler:displayOrder");
    }

    @HippoEssentialsGenerated(internalName = "kohler:imageLargeAlt")
  	public String getImageLargeAlt() {
  		return getSingleProperty("kohler:imageLargeAlt");
  	}

  	@HippoEssentialsGenerated(internalName = "kohler:imageLargeTitle")
  	public String getImageLargeTitle() {
  		return getSingleProperty("kohler:imageLargeTitle");
  	}

    @HippoEssentialsGenerated(internalName = "kohler:imageSmallAlt")
  	public String getImageSmallAlt() {
  		return getSingleProperty("kohler:imageSmallAlt");
  	}

  	@HippoEssentialsGenerated(internalName = "kohler:imageSmallTitle")
  	public String getImageSmallTitle() {
  		return getSingleProperty("kohler:imageSmallTitle");
  	}

  	 @HippoEssentialsGenerated(internalName = "kohler:imageSmallAlt1")
   	public String getImageSmallAlt1() {
   		return getSingleProperty("kohler:imageSmallAlt1");
   	}

   	@HippoEssentialsGenerated(internalName = "kohler:imageSmallTitle1")
   	public String getImageSmallTitle1() {
   		return getSingleProperty("kohler:imageSmallTitle1");
   	}
}
