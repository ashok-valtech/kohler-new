package com.kohler.commons.components;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.CommonComponent;

import com.kohler.commons.components.componentsInfo.HRLineComponentInfo;
import com.kohler.commons.constants.Constants;

/**
 * Component for Horizontal line
 * @author dhanwan.r
 * Date: 03/08/2018
 * @version 1.0
 */
@ParametersInfo(type = HRLineComponentInfo.class)
public class HRLineComponent extends CommonComponent{

	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		super.doBeforeRender(request, response);
		final HRLineComponentInfo paramInfo = getComponentParametersInfo(request);
		request.setAttribute(Constants.REQUEST_ATTR_IS_FULL_SIZE, paramInfo.getFullSize());
	}
}
