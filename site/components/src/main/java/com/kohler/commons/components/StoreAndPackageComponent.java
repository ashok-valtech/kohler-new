package com.kohler.commons.components;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.util.PathUtils;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;

import com.google.common.base.Strings;
import com.kohler.commons.constants.Constants;

@ParametersInfo(type = EssentialsListComponentInfo.class)
public class StoreAndPackageComponent extends EssentialsListComponent {

	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		super.doBeforeRender(request, response);
		HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();
		if (rootBean != null) {
			final String storeAndPackageBeanPath = PathUtils.normalizePath(Constants.STORE_AND_PACKAGE_LIST_DOCUMENT_PATH);
			if (!Strings.isNullOrEmpty(storeAndPackageBeanPath)) {
				HippoBean storeAndPackageBean = getScopeBean(storeAndPackageBeanPath);
				request.setAttribute("storeAndPackageBean", storeAndPackageBean);
			}
		}

	}
}
