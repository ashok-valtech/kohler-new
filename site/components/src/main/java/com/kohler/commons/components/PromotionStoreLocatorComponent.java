package com.kohler.commons.components;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.CommonComponent;

import com.kohler.commons.components.componentsInfo.PromotionStoreLocatorComponentInfo;

/**
 * Component for Horizontal line
 * 
 * @author Krushna.Mahunta Date: 21/05/2020
 * @version 1.0
 */
@ParametersInfo(type = PromotionStoreLocatorComponentInfo.class)
public class PromotionStoreLocatorComponent extends CommonComponent {

	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		super.doBeforeRender(request, response);
		final PromotionStoreLocatorComponentInfo paramInfo = getComponentParametersInfo(request);
		request.setAttribute("storeComponent", paramInfo.getStoreCompomemt());
	}
}
