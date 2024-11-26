package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.DropDownList;
import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsDocumentComponentInfo;

/**
 *
 * @author krushna.mahunta
 *
 */
public interface ShoppingGuideComponentInfo extends EssentialsDocumentComponentInfo{

	String TWO_COLUMN_LAYOUT = "Two Column";
	String THREE_COLUMN_LAYOUT = "Three Column";

	@Parameter(name = "layout", required = false, defaultValue = "Two Column", displayName = "Layout")
	@DropDownList(value = { TWO_COLUMN_LAYOUT, THREE_COLUMN_LAYOUT })
	String getLayout();

	@Parameter(name = "document", required = true)
    @JcrPath(
            isRelative = true,
            pickerConfiguration = "cms-pickers/documents",
            pickerSelectableNodeTypes = {"hippo:document"}
    )
    String getDocument();

	@Parameter(name = "hrLine", defaultValue = "false", required = false)
    Boolean getHrLine();

}
