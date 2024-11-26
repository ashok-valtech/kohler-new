package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.Parameter;

/**
 * 
 * @author dhanwan.r
 *
 */
public interface HRLineComponentInfo {

	@Parameter(name = "fullSize", required = true)
    Boolean getFullSize();
}
