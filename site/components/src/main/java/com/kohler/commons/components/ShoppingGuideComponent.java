package com.kohler.commons.components;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsDocumentComponent;

import com.kohler.commons.components.componentsInfo.ShoppingGuideComponentInfo;

/**
 * APAC Implementation for Shopping Guide Page , inherits from <code>EssentialsDocumentComponent</code>
 * @author krushna.mahunta
 * Date: 16/05/2019
 * @version 1.0
 */
@ParametersInfo(type = ShoppingGuideComponentInfo.class)
public class ShoppingGuideComponent extends EssentialsDocumentComponent {
	
	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		final ShoppingGuideComponentInfo paramInfo = getComponentParametersInfo(request);
		final String layout = paramInfo.getLayout();
		final Boolean hrLine = paramInfo.getHrLine();
		request.setAttribute("hrLine", hrLine);
		request.setAttribute("layout", layout);
		super.doBeforeRender(request, response);
	}

}
