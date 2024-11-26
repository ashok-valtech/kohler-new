/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;
import org.onehippo.cms7.essentials.components.CommonComponent;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.kohler.beans.Menu;
import com.kohler.commons.beans.MenuNavigation;
import com.kohler.commons.components.componentsInfo.MenuNavigationComponentInfo;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.util.CommonUtil;

/**
 * APAC Implementation for Site map menus, inherits from <code>CommonComponent</code>
 * @author krushna.mahunta
 * Date: 02/08/2017
 * @version 1.0
 */
@ParametersInfo(type = MenuNavigationComponentInfo.class)
public class SiteMapMenuNavigationComponent extends CommonComponent {

	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) {
		try {
			final MenuNavigationComponentInfo paramInfo = getComponentParametersInfo(request);
			String siteMenu = paramInfo.getSiteMenu();
			String siteSubMenu = paramInfo.getSubSiteMenu();
			String[] childMenuItems = paramInfo.getChildMenuItem().split(",");

			List<String> subMenues = new ArrayList<String>();
			if (!Strings.isNullOrEmpty(siteSubMenu)) {
				subMenues = new ArrayList<String>(Arrays.asList(siteSubMenu.split(",")));
			}

			HstRequestContext requestContext = request.getRequestContext();
			HippoBean root = requestContext.getSiteContentBaseBean();

			siteMenu = CharMatcher.whitespace().trimFrom(siteMenu);
			final HstSiteMenu menu = request.getRequestContext().getHstSiteMenus().getSiteMenu(siteMenu);
			if (menu == null) {
				return;
			}
			Set<MenuNavigation> menuNavigationSet = new LinkedHashSet<MenuNavigation>();
			for (HstSiteMenuItem siteMenuItem : menu.getSiteMenuItems()) {
				for (String subMenu : subMenues) {
					if (siteMenuItem.getName().equalsIgnoreCase(subMenu)) {
						for (HstSiteMenuItem childMenuItem : siteMenuItem.getChildMenuItems()) {
							for (String childMenuItemString : childMenuItems) {
								String childMenuItemName = childMenuItem.getName();
								if (childMenuItemName != null) {
									if (childMenuItemName.equalsIgnoreCase(childMenuItemString)) {
										MenuNavigation menuNavigationItem = new MenuNavigation();
										Menu menuBean = null;
										try {
											menuBean = root.getBean(childMenuItem.getHstLink().getPath(), Menu.class);
										} catch (Exception e) {
											//DO nothing
										}
										if (menuBean != null) {
											menuNavigationItem.setMenu(menuBean);
										}
										menuNavigationItem.setSiteMenuItem(childMenuItem);
										menuNavigationItem.setChildren(getChildren(root, childMenuItem));
										menuNavigationSet.add(menuNavigationItem);
									}
								}
							}
						}
					}
				}
			}
			request.setAttribute(Constants.REQUEST_ATTR_SITEMAPMENU, menuNavigationSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			CommonUtil.pageNotFound(response, Constants.PAGE_NOT_FOUND_URL);
		}
	}

	/**
	 * Gets list of Menus
	 * @param root
	 * @param siteMenuItemParent
	 * @return menuNavigationItems
	 * @throws Exception
	 */
	private List<MenuNavigation> getChildren(HippoBean root, HstSiteMenuItem siteMenuItemParent) {
		List<MenuNavigation> menuNavigationItems = new LinkedList<MenuNavigation>();
		for (HstSiteMenuItem siteMenuItem : siteMenuItemParent.getChildMenuItems()) {
			Menu menuBean = null;
			MenuNavigation menuNavigationItem = new MenuNavigation();
			try {
				menuBean = root.getBean(siteMenuItem.getHstLink().getPath(), Menu.class);
			} catch (Exception e) {
				//Do nothing
			}
			if (menuBean != null) {
				menuNavigationItem.setMenu(menuBean);
			}
			menuNavigationItem.setSiteMenuItem(siteMenuItem);
			menuNavigationItem.setChildren(getChildren(root, siteMenuItem));
			menuNavigationItems.add(menuNavigationItem);
		}
		return menuNavigationItems;
	}
}
