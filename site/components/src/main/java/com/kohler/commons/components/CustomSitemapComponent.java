/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.sitemenu.HstSiteMenus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.commons.components.componentsInfo.CustomSiteMapInfo;


/**
 * @author Sumit.Pallakhe
 *
 * class provides implementation for getting category sitemap and static sitemap xml
 */
@ParametersInfo(type = CustomSiteMapInfo.class)
public class CustomSitemapComponent extends BaseHstComponent{
	
		private static final Logger LOG = LoggerFactory.getLogger(CustomSitemapComponent.class);
	
		public static final int DEFAULT_DEPTH = 5;
	    public void doBeforeRender(HstRequest request, HstResponse response) {
	    	
	    	final CustomSiteMapInfo paramInfo = getComponentParametersInfo(request);
	    	HstRequestContext requestContext = request.getRequestContext();
	    	HippoBean root = requestContext.getSiteContentBaseBean();
			String[] siteMenuName = paramInfo.getSiteMenuName().split(",");
			String siteMenuParentName = paramInfo.getSiteMenuParentName();
	        String depthStr = getComponentParameter("depth");
	        int maxDepth = DEFAULT_DEPTH;
	        if (depthStr != null) {
	            maxDepth = Integer.valueOf(depthStr);
	        }
	        LOG.info("Sitemap exporting started..!!!");
	        HstSiteMenus siteMenus = request.getRequestContext().getHstSiteMenus();
	        CustomSitemapGenerator customeSitemapGenerator = new CustomSitemapGenerator(request.getRequestContext());
	        String allUrls = customeSitemapGenerator.createSitemap(siteMenus, maxDepth, siteMenuParentName, siteMenuName,request,root);
	        LOG.info("Sitemap exporting ends..!!!");
	        request.setAttribute("sitemap", allUrls);
	    }
}
