/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;

/**
 * 
 * @author dhanwan.r
 *
 */
public interface EssentialsSearchListComponentInfo extends EssentialsListComponentInfo{
	@Parameter(name = "ignoreSpecialCharacters", required = true, displayName = "Ignore Special Characters")
	 String getIgnoreSpecialCharacters();
}
