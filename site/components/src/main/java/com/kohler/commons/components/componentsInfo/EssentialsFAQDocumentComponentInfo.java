/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsSortable;

/**
 *
 * @author dhanwan.r
 *
 */
public interface EssentialsFAQDocumentComponentInfo extends EssentialsSortable{

	 @Parameter(name = "pageSize", required = true, defaultValue = "10")
	    int getPageSize();

	 @Parameter(name = "path", required = false)
	 String getPath();

}
