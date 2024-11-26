/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.Parameter;

public interface ProductListComponentInfo {
	@Parameter(name = "category", required = true)
	String getCategory();
	
	 @Parameter(name = "facetPath", required = true, displayName = "Facet path")
	 String getFacetPath();
}
