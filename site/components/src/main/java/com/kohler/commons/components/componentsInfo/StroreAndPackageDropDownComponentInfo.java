package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;

public interface StroreAndPackageDropDownComponentInfo extends EssentialsListComponentInfo {

	@Parameter(name = "formCompomemt", required = true)
	Boolean getFormCompomemt();
}
