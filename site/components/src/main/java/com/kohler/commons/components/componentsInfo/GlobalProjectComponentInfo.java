/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;

public interface GlobalProjectComponentInfo extends EssentialsListComponentInfo{
	@Parameter(name = "maxCountryProjects", required = true)
	Integer getMaxCountryProjects();
	
	@Parameter(name = "facetPath", required = true)
	String getFacetPath();
}
