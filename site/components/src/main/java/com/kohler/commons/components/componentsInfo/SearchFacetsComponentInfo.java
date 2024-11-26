/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsFacetsComponentInfo;
/**
 * 
 * @author dhanwan.r
 *
 */
public interface SearchFacetsComponentInfo extends EssentialsFacetsComponentInfo{
	@Parameter(name = "ignoreSpecialCharacters", required = true, displayName = "Ignore Special Characters")
	 String getIgnoreSpecialCharacters();
	
	@Parameter(name = "pageSize", required = true, displayName = "Page Size")
	 int getPageSize();
}
