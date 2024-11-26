/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.SitemapService;

/**
 * @author Sumit.Pallakhe
 *
 *	class provides implementation for getting siteindex sitemap xml
 */
public class SiteIndexSitemap extends BaseHstComponent {

	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
		List<String> allSitemapUrls = new ArrayList<String>();
		allSitemapUrls.add(Constants.BRAND_SITEMAP_PATH);
		allSitemapUrls.add(Constants.PRODUCT_SITEMAP_PATH);
		allSitemapUrls.add(Constants.CATEGORY_SITEMAP_PATH);
		allSitemapUrls.add(Constants.STATIC_SITEMAP_PATH);
		allSitemapUrls.add(Constants.CONTENT_SITEMAP_PATH);
		SitemapService sitemapService = new SitemapService();
		request.setAttribute("sitemap", sitemapService.createXML(allSitemapUrls, request));
	}

}
