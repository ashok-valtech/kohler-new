package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.forge.seo.support.SEOHelperComponentParamsInfo;

/**
 * Custom SEO Helper Param info interface
 * @author dhanwan.r
 * Date: 10/07/2019
 * @version 1.0
 */

@FieldGroupList({
    @FieldGroup(
    		titleKey = "group.titlecustomsettings",
            value = {
                    "noFollow",
                    "noIndex",
                    "canonicalUrl"
            }
    )
})
public interface CustomSEOHelperComponentParamsInfo extends SEOHelperComponentParamsInfo {

	@Parameter(name = "noFollow", defaultValue = "false", required = false, displayName="No FOLLOW")
    Boolean getNoFollow();
	
	@Parameter(name = "noIndex", defaultValue = "false", required = false, displayName="No INDEX")
    Boolean getNoIndex();	
	
	@Parameter(name = "canonicalUrl", required = false, displayName="Canonical URL")
    String getCanonicalUrl();
	
	@Parameter(name = "beanClassName", required = false, displayName="Bean Class")
    String getBeanClass();
	
	@Parameter(name = "siteMapItem", required = false, displayName="Site Map Item")
    String getSiteMapItem();
	
	@Parameter(name = "seoPageName", required = false, displayName="Seo Display Name")
    String getSeoPageName();
}
