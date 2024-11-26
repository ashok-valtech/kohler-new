package com.kohler.commons.components;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsDocumentComponent;

import com.kohler.commons.components.componentsInfo.WalkinBathTopSectionComponentInfo;

/**
 * APAC Implementation for Walkin-Bath Page , inherits from <code>EssentialsDocumentComponent</code>
 * @author krushna.mahunta Date: 23/01/2020
 * @version 1.0
 */
@ParametersInfo(type = WalkinBathTopSectionComponentInfo.class)
public class WalkinBathTopSectionComponent extends EssentialsDocumentComponent {

	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		final WalkinBathTopSectionComponentInfo paramInfo = getComponentParametersInfo(request);
		final Boolean hrLine = paramInfo.getHrLine();
		request.setAttribute("hrLine", hrLine);
		super.doBeforeRender(request, response);
	}

}
