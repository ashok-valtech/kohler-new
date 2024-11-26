package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hippoecm.hst.content.beans.ContentNodeBinder;
import org.hippoecm.hst.content.beans.ContentNodeBindingException;
import org.hippoecm.hst.content.beans.Node;
import com.kohler.beans.CAttributes;
import com.kohler.beans.CSKU;
import com.kohler.beans.CCrossSelling;
import java.util.List;
import com.kohler.beans.SeoSupport;

@HippoEssentialsGenerated(internalName = "kohler:Product")
@Node(jcrType = "kohler:Product")
public class Product extends BaseDocument implements ContentNodeBinder{

	private static final String KOHLER_SEO = "kohler:seo";
	private static final Logger LOGGER = LoggerFactory.getLogger (Product.class);

	private SeoSupport seo;

    @HippoEssentialsGenerated(internalName = "kohler:id")
    public Long getId() {
        return getSingleProperty("kohler:id");
    }

    @HippoEssentialsGenerated(internalName = "kohler:itemNo")
    public String getItemNo() {
        return getSingleProperty("kohler:itemNo");
    }

    @HippoEssentialsGenerated(internalName = "kohler:status")
    public String getStatus() {
        return getSingleProperty("kohler:status");
    }

    @HippoEssentialsGenerated(internalName = "kohler:ItemType")
    public String getItemType() {
        return getSingleProperty("kohler:ItemType");
    }

    @HippoEssentialsGenerated(internalName = "kohler:ancestors")
    public Long[] getAncestors() {
        return getMultipleProperty("kohler:ancestors");
    }

    @HippoEssentialsGenerated(internalName = "kohler:adcopy")
    public List<CAttributes> getAdcopy() {
        return getChildBeansByName("kohler:adcopy", CAttributes.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:attributes")
    public List<CAttributes> getAttributes() {
        return getChildBeansByName("kohler:attributes", CAttributes.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:skus")
    public List<CSKU> getSkus() {
        return getChildBeansByName("kohler:skus", CSKU.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:crossselling")
    public List<CCrossSelling> getCrossselling() {
        return getChildBeansByName("kohler:crossselling", CCrossSelling.class);
    }

    @HippoEssentialsGenerated(internalName = "hippotaxonomy:keys")
    public String[] getKeys() {
        return getMultipleProperty("hippotaxonomy:keys");
    }

    @HippoEssentialsGenerated(internalName = "kohler:isNew")
    public Boolean getIsNew() {
        return getSingleProperty("kohler:isNew");
    }

    @HippoEssentialsGenerated(internalName = "kohler:installationType")
    public String getInstallationType() {
        return getSingleProperty("kohler:installationType");
    }

    @HippoEssentialsGenerated(internalName = "kohler:country")
    public String[] getCountry() {
        return getMultipleProperty("kohler:country");
    }

    @HippoEssentialsGenerated(internalName = "kohler:color")
    public String[] getColor() {
        return getMultipleProperty("kohler:color");
    }

    @HippoEssentialsGenerated(internalName = "kohler:handleType")
    public String getHandleType() {
        return getSingleProperty("kohler:handleType");
    }

    @HippoEssentialsGenerated(internalName = "kohler:waterSaving")
    public String getWaterSaving() {
        return getSingleProperty("kohler:waterSaving");
    }

    @HippoEssentialsGenerated(internalName = "kohler:descriptionProduct")
    public String getDescriptionProduct() {
        return getSingleProperty("kohler:descriptionProduct");
    }

    @HippoEssentialsGenerated(internalName = "kohler:priceRange")
    public String[] getPriceRange() {
        return getMultipleProperty("kohler:priceRange");
    }
    
    @HippoEssentialsGenerated(internalName = "kohler:priceSegment")
    public String[] getPriceSegment() {
        return getMultipleProperty("kohler:priceSegment");
    }

    @HippoEssentialsGenerated(internalName = "kohler:producthash")
    public String getProducthash() {
        return getSingleProperty("kohler:producthash");
    }

    @HippoEssentialsGenerated(internalName = "kohler:productFamily")
    public String getProductFamily() {
        return getSingleProperty("kohler:productFamily");
    }

    @HippoEssentialsGenerated(internalName = "kohler:Liters_Per_Flush")
    public String getLiters_Per_Flush() {
        return getSingleProperty("kohler:Liters_Per_Flush");
    }

    @HippoEssentialsGenerated(internalName = "kohler:minimumRoughIn")
    public String getMinimumRoughIn() {
        return getSingleProperty("kohler:minimumRoughIn");
    }

    @HippoEssentialsGenerated(internalName = "kohler:ProductType")
    public String[] getProductType() {
        return getMultipleProperty("kohler:ProductType");
    }

    @HippoEssentialsGenerated(internalName = "kohler:toiletType")
    public String getToiletType() {
        return getSingleProperty("kohler:toiletType");
    }

    @HippoEssentialsGenerated(internalName = "kohler:bowlShape")
    public String getBowlShape() {
        return getSingleProperty("kohler:bowlShape");
    }

    @HippoEssentialsGenerated(internalName = "kohler:Trapway_Type")
    public String getTrapway_Type() {
        return getSingleProperty("kohler:Trapway_Type");
    }

    @HippoEssentialsGenerated(internalName = "kohler:listPrice")
    public Double getListPrice() {
        return getSingleProperty("kohler:listPrice");
    }

    @HippoEssentialsGenerated(internalName = "kohler:material")
    public String getMaterial() {
        return getSingleProperty("kohler:material");
    }

    @HippoEssentialsGenerated(internalName = "kohler:overallHeight")
    public Long getOverallHeight() {
        return getSingleProperty("kohler:overallHeight");
    }

    @HippoEssentialsGenerated(internalName = "kohler:overallLength")
    public Long getOverallLength() {
        return getSingleProperty("kohler:overallLength");
    }

    @HippoEssentialsGenerated(internalName = "kohler:overallWidth")
    public Long getOverallWidth() {
        return getSingleProperty("kohler:overallWidth");
    }

    @HippoEssentialsGenerated(internalName = "kohler:discontinued")
    public Boolean getDiscontinued() {
        return getSingleProperty("kohler:discontinued");
    }

    @HippoEssentialsGenerated(internalName = "kohler:numberOfHandles")
    public String[] getNumberOfHandles() {
        return getMultipleProperty("kohler:numberOfHandles");
    }

    @HippoEssentialsGenerated(internalName = "kohler:lightingType")
    public String getLightingType() {
        return getSingleProperty("kohler:lightingType");
    }

    @HippoEssentialsGenerated(internalName = "kohler:mirrorDefogger")
    public String getMirrorDefogger() {
        return getSingleProperty("kohler:mirrorDefogger");
    }

    @HippoEssentialsGenerated(internalName = "kohler:flushingTechnology")
    public String getFlushingTechnology() {
        return getSingleProperty("kohler:flushingTechnology");
    }

    @HippoEssentialsGenerated(internalName = "kohler:bowlStyle")
    public String getBowlStyle() {
        return getSingleProperty("kohler:bowlStyle");
    }

    @HippoEssentialsGenerated(internalName = "kohler:style")
    public String[] getStyle() {
        return getMultipleProperty("kohler:style");
    }

    @HippoEssentialsGenerated(internalName = "kohler:lengthRange")
    public String getLengthRange() {
        return getSingleProperty("kohler:lengthRange");
    }

    @HippoEssentialsGenerated(internalName = "kohler:glassThicknessmm")
    public String getGlassThicknessmm() {
        return getSingleProperty("kohler:glassThicknessmm");
    }

    @HippoEssentialsGenerated(internalName = "kohler:starProduct")
    public Boolean getstarProduct() {
        return getSingleProperty("kohler:starProduct");
    }

    @HippoEssentialsGenerated(internalName = "kohler:feature")
    public String[] getFeature() {
        return getMultipleProperty("kohler:feature");
    }

    @HippoEssentialsGenerated(internalName = "kohler:shape")
    public String getShape() {
        return getSingleProperty("kohler:shape");
    }


    @HippoEssentialsGenerated(internalName = KOHLER_SEO)
    public SeoSupport getSeo() {
    	return seo == null?getBean(KOHLER_SEO):seo;
    }

    public void setSeo(SeoSupport seo) {
		this.seo = seo;
	}

    @Override
	public boolean bind(Object content, javax.jcr.Node node) throws ContentNodeBindingException {
		try {
			Product bean =  (Product) content;
			SeoSupport seoSupport = bean.getSeo();
			javax.jcr.Node compound = getNode(node);
			if (compound == null) {
				 compound = node.addNode(KOHLER_SEO, "seosupport:seo");
			}
    		compound.setProperty("seosupport:seotitle", seoSupport.getSeoTitle());
    		compound.setProperty("seosupport:seodescription", seoSupport.getSeoDescription());
    		compound.setProperty("seosupport:noIndex", seoSupport.getNoIndex());
    		compound.setProperty("seosupport:noFollow", seoSupport.getNoFollow());
    		compound.setProperty("seosupport:canonicalUrl", seoSupport.getCanonicalUrl());
        } catch (Exception e) {
        	if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
            throw new ContentNodeBindingException(e);
        }
        return true;
	}

    protected javax.jcr.Node getNode(javax.jcr.Node node) {
    	try {
    		return node.getNode(KOHLER_SEO);
    	} catch (Exception ex) {
    		return null;
    	}
    }
}
