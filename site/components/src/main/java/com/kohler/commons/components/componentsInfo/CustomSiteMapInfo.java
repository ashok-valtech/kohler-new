/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.Parameter;

import com.kohler.commons.constants.Constants;

/**
 * @author Sumit.Pallakhe
 *
 */
public interface CustomSiteMapInfo {
	@Parameter(name = Constants.SITE_MENU_NAME, required = true)
	String getSiteMenuName();
	
	@Parameter(name = Constants.SITE_MENU_PARENT_NAME, required = true)
	String getSiteMenuParentName();
	
	@Parameter(name = Constants.ROOT_PATH_NAME, required = true)
	String getContentRootPath();
}
