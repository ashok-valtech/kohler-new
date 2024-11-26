/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.beans;

import java.util.List;

import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;

import com.kohler.beans.Menu;

public class MenuNavigation extends HippoDocument {
	
	List<MenuNavigation> children;
	Menu menu;
	HstSiteMenuItem siteMenuItem;
	public List<MenuNavigation> getChildren() {
		return children;
	}
	public void setChildren(List<MenuNavigation> children) {
		this.children = children;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public HstSiteMenuItem getSiteMenuItem() {
		return siteMenuItem;
	}
	public void setSiteMenuItem(HstSiteMenuItem siteMenuItem) {
		this.siteMenuItem = siteMenuItem;
	}

}
