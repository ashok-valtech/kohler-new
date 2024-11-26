/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsMenuComponentInfo;

public interface MenuNavigationComponentInfo extends EssentialsMenuComponentInfo{
	 @Parameter(name = "submenu", required = true)
	 String getSubSiteMenu();
	 
	 @Parameter(name = "childMenuItem", required = true)
	 String getChildMenuItem();
	 
}
