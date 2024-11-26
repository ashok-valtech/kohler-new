/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.List;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;
import org.onehippo.cms7.essentials.components.paging.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.beans.MediaContact;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.PressReleaseServiceImpl;

/**
 * APAC Implementation for Press release list page, inherits from <code>EssentialsListComponent</code>
 * @author girijanandan.p
 * Date:02/06/2017
 * @version 1.0
 */
@ParametersInfo(type = EssentialsListComponentInfo.class)
public class PressReleaseListComponent extends EssentialsListComponent {

	private static final Logger LOG = LoggerFactory.getLogger(PressReleaseListComponent.class);

	PressReleaseServiceImpl pressReleaseService = new PressReleaseServiceImpl();
	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		final EssentialsListComponentInfo paramInfo = getComponentParametersInfo(request);
		final String path = getScopePath(paramInfo);
		LOG.debug("path: {}", path);

		final HippoBean scope = getSearchScope(request, path);
		if (scope != null) {
			LOG.debug("scope: {}", scope.getPath());
		}

		if (scope == null) {
			handleInvalidScope(request, response);
			return;
		}

		final Pageable<? extends HippoBean> pageable;
		if (scope instanceof HippoFacetNavigationBean) {
			pageable = doFacetedSearch(request, paramInfo, scope);
		} else {
			pageable = doSearch(request, paramInfo, scope);
		}
		if (pageable != null) {
			LOG.debug("pageable items: {}", pageable.getTotal());
			Long totalProjects = pageable.getTotal();
			request.setAttribute(Constants.REQUEST_ATTR_TOTALPROJECTS, totalProjects);
		}

		populateRequest(request, paramInfo, pageable);
		List<MediaContact> mediaContacts = pressReleaseService.getMediaContacts(request);
		request.setAttribute(Constants.REQUEST_ATTR_MEDIACONTACTS, mediaContacts);
		request.setAttribute(Constants.REQUEST_ATTR_PAGESIZE, paramInfo.getPageSize());
	}
}
