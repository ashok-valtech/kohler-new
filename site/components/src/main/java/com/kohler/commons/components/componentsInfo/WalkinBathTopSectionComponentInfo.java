package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsDocumentComponentInfo;

/**
 * 
 * @author krushna.mahunta
 *
 */
public interface WalkinBathTopSectionComponentInfo extends EssentialsDocumentComponentInfo {

	@Parameter(name = "document", required = true)
	@JcrPath(isRelative = true, pickerConfiguration = "cms-pickers/documents", pickerSelectableNodeTypes = {
			"hippo:document" })
	String getDocument();

	@Parameter(name = "hrLine", defaultValue = "false", required = false)
	Boolean getHrLine();

}
