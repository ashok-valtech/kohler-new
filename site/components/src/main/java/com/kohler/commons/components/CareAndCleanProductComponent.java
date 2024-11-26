/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.io.IOException;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;
import org.onehippo.cms7.essentials.components.paging.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.commons.constants.Constants;

/**
 * APAC Implementation for CareAndCleanProduct, inherits from <code>EssentialsListComponent</code>
 * @author Girijanandan.p
 * Date: 05/11/2017
 * @version 1.0
 */
@ParametersInfo(type = EssentialsListComponentInfo.class)
public class CareAndCleanProductComponent extends EssentialsListComponent{
	
	private static final Logger LOG = LoggerFactory.getLogger (CareAndCleanProductComponent.class);
	
	@Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
		super.doBeforeRender(request, response);
		HippoBean document = request.getRequestContext().getContentBean();
		if(document.isHippoFolderBean()){
			if (request.getAttribute(REQUEST_ATTR_PAGEABLE) != null) {
				@SuppressWarnings("unchecked")
				Pageable<? extends HippoBean> pageable = (Pageable<? extends HippoBean>) request.getAttribute(REQUEST_ATTR_PAGEABLE);
				if (!pageable.getItems().isEmpty()) {
					document = pageable.getItems().get(0);
				}
			}
			if(!document.isHippoFolderBean()){
				try {
					response.sendRedirect(request.getRequestContext().getHstLinkCreator().create(document, request.getRequestContext()).toUrlForm(request.getRequestContext(), true));
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);;
				}
			}
		}
		request.setAttribute(Constants.REQUEST_ATTR_DOCUMENT, document);
	}
}

