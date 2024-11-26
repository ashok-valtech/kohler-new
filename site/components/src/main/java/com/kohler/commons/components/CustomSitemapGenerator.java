/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;
import org.hippoecm.hst.core.sitemenu.HstSiteMenus;
import org.onehippo.forge.sitemap.components.model.ChangeFrequency;
import org.onehippo.forge.sitemap.components.model.Url;
import org.onehippo.forge.sitemap.components.model.Urlset;
import org.onehippo.forge.sitemap.generator.SitemapGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.Menu;
import com.kohler.commons.constants.Constants;

/**
 * @author Sumit.Pallakhe
 *
 *         class provides implementation for getting sitemenu and creating
 *         sitemap
 */
public class CustomSitemapGenerator extends SitemapGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(CustomSitemapGenerator.class);

	public CustomSitemapGenerator(HstRequestContext requestContext) {
		super(requestContext);
	}

	/**
	 * @param sitemenus
	 * @param urls
	 * @param maxDepth
	 * @param parentName
	 * @param siteMenuName
	 * 
	 *            accept parentSiteMenu and siteMenuName and add all menu item
	 *            urls in Urlset
	 */
	protected void addSitemapItems(final HstSiteMenus sitemenus, final Urlset urls, final int maxDepth,
			String parentName, String siteMenuName, HstRequest request, HippoBean root, Mount mount) {
		Calendar now = new GregorianCalendar(TimeZone.getTimeZone(Constants.TIME_ZONE));
		HstSiteMenu sitemenu = sitemenus.getSiteMenu(Constants.SITE_MENU);
		for (HstSiteMenuItem item : sitemenu.getSiteMenuItems()) {
			if (item.getName().equals(parentName)) {
				for (HstSiteMenuItem childItem : item.getChildMenuItems()) {
					if (childItem.getName().equalsIgnoreCase(siteMenuName)) {
						addMenuItem(childItem, urls, 1, maxDepth, now, request, root, mount);
					}
				}
			}
		}
	}

	/**
	 * @param siteMenus
	 * @param maxDepth
	 * @param parentName
	 * @param siteMenuName
	 * @return
	 * 
	 * 		accept parentSiteMenu and siteMenuNames and returns all sitemap
	 *         url as a string
	 */
	public String createSitemap(HstSiteMenus siteMenus, int maxDepth, String parentName, String[] siteMenuName,
			HstRequest request, HippoBean root) {
		Mount mount = request.getRequestContext().getResolvedMount().getMount();
		Urlset urls = new Urlset();
		LOG.info("In createsitemap..!!");
		for (int i = 0; i < siteMenuName.length; i++) {
			addSitemapItems(siteMenus, urls, maxDepth, parentName, siteMenuName[i], request, root, mount);
		}
		return toString(urls);
	}

	protected void addMenuItem(final HstSiteMenuItem item, final Urlset urlSet, final int depth, final int maxDepth,
			final Calendar defaultLastModifedDate, HstRequest request, HippoBean root, Mount mount) {
		Url url = new Url();
		if (item.getHstLink() != null) {
			url.setChangeFrequency(ChangeFrequency.DAILY);
			url.setLastmod(defaultLastModifedDate);
			String menuLink = getMenuLink(item.getHstLink().getPath(), request, root);
			if (Strings.isNullOrEmpty(menuLink)) {
				url.setLoc(item.getHstLink().toUrlForm(request.getRequestContext(), true));
			} else {
				url.setLoc(request.getRequestContext().getHstLinkCreator().create(menuLink.trim(), mount).toUrlForm(request.getRequestContext(), true));
			}
			url.setPriority(new BigDecimal("1.0"));
			urlSet.getUrls().add(url);
		}

		if (depth < maxDepth) {
			for (HstSiteMenuItem childItem : item.getChildMenuItems()) {
				addMenuItem(childItem, urlSet, depth + 1, maxDepth, defaultLastModifedDate, request, root, mount );
			}
		}
	}

	private String getMenuLink(String location, HstRequest request, HippoBean root) {
		String new_location = null;
		try {
			Menu menuBean = null;
			try {
				menuBean = root.getBean(location, Menu.class);;
			} catch (Exception e) {
				//Do nothing
			}
			if (menuBean != null)  {
				new_location = menuBean.getMenuLink();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return new_location;
	}
}
