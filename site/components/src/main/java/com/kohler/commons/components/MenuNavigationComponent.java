/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
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

/**
 * APAC Implementation for Menus, inherits from <code>CommonComponent</code>
 * @author dhanwan.r
 * Date: 05/02/2017
 * @version 1.0
 */
@ParametersInfo(type = MenuNavigationComponentInfo.class)
public class MenuNavigationComponent extends CommonComponent {

	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
		super.doBeforeRender(request, response);

		final MenuNavigationComponentInfo paramInfo = getComponentParametersInfo(request);
		String siteMenu = paramInfo.getSiteMenu();
		String siteSubMenu = paramInfo.getSubSiteMenu();
		if (Strings.isNullOrEmpty(siteMenu)) {
			return;
		}
		List <String> subMenues = new ArrayList<String>();
		if (!Strings.isNullOrEmpty(siteMenu)) {
			subMenues = new ArrayList<String>(Arrays.asList(siteSubMenu.split(",")));
		}
		siteMenu = CharMatcher.whitespace().trimFrom(siteMenu);
		final HstSiteMenu menu = request.getRequestContext().getHstSiteMenus().getSiteMenu(siteMenu);
		if (menu == null) {
			return;
		}
		HstRequestContext requestContext = request.getRequestContext();
		HippoBean root = requestContext.getSiteContentBaseBean();

		for (HstSiteMenuItem siteMenuItem : menu.getSiteMenuItems()) {
			for(String subMenu : subMenues){
				MenuNavigation menuNavigationItem = new MenuNavigation();
				if(siteMenuItem.getName().equalsIgnoreCase(subMenu)){
					Menu menuBean = null;
					try {
						menuBean = root.getBean(siteMenuItem.getHstLink().getPath(), Menu.class);
					} catch (Exception e) {
						//Do nothing
					}
					if (menuBean != null) {
						menuNavigationItem.setMenu(menuBean);
					}
					menuNavigationItem.setSiteMenuItem(siteMenuItem);
					menuNavigationItem.setChildren(getChildren(root,siteMenuItem));
					request.setAttribute(subMenu, menuNavigationItem);
				}
			}
		}
		request.setAttribute(Constants.REQUEST_ATTR_MENU, menu);
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
