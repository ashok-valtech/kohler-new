package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;

public interface GlobalProjectAjaxComponentInfo extends EssentialsListComponentInfo{

	@Parameter(name = "facetPath", required = true)
	String getFacetPath();
	
	@Parameter(name = "maxCountryProjects", required = true)
	Integer getMaxCountryProjects();
}
