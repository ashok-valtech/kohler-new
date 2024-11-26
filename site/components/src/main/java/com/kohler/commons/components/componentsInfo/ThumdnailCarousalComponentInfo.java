package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;
import org.onehippo.cms7.essentials.components.info.EssentialsSortable;

/**
 * 
 * @author Krushna.mahunta
 *
 */
public interface ThumdnailCarousalComponentInfo extends EssentialsListComponentInfo, EssentialsSortable {
	
	@Parameter(name = "path", required = false)
    @JcrPath(
            isRelative = true,
            pickerConfiguration = "cms-pickers/documents",
            pickerSelectableNodeTypes = {"hippostd:folder"}
    )
    String getPath();

    @Parameter(name = "documentTypes", required = false)
    String getDocumentTypes();

}
