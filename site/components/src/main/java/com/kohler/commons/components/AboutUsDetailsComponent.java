/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.io.IOException;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.cms7.essentials.components.utils.SiteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kohler.beans.ContentDocument;
import com.kohler.commons.constants.Constants;

/**
 * APAC Implementation for AboutUS detail page, inherits from <code>BaseHstComponent</code>
 * @author dhanwan.r
 * Date: 06/13/2017
 * @version 1.0
 */
public class AboutUsDetailsComponent extends BaseHstComponent {
	
	private static final Logger LOG = LoggerFactory.getLogger (AboutUsDetailsComponent.class);
	
	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
		final String documentId = SiteUtils.getAnyParameter(Constants.ID, request, this);
		try {
			ContentDocument documentBean = getBean(documentId, request);
			request.setAttribute(Constants.REQUEST_ATTR_DOCUMENT, documentBean);
		} catch (IllegalStateException | QueryException e1) {
			e1.printStackTrace();
			try {
				response.forward(Constants.PAGE_NOT_FOUND_URL);
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error(e.getMessage() + e);
			}
		}
	}
	
	/**
	 * Gets ContentDocument using UUID
	 * @param UUID
	 * @param request
	 * @return ContentDocument 
	 * @throws IllegalStateException
	 * @throws QueryException
	 */
	public ContentDocument getBean(String UUID, HstRequest request) throws IllegalStateException, QueryException {
		HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();
		ContentDocument documentBean = null;
			if (rootBean != null) {
				@SuppressWarnings("unchecked")
				HstQuery query = request.getRequestContext().getQueryManager().createQuery(rootBean,
						ContentDocument.class);
				Filter filter = query.createFilter();
				filter.addEqualTo(Constants.JCR_UUID, UUID);
				query.setFilter(filter);
				HstQueryResult queryResult = query.execute();
				HippoBeanIterator resultDocumentBeans = queryResult.getHippoBeans();
				while (resultDocumentBeans.hasNext()) {
					documentBean = (ContentDocument) resultDocumentBeans.nextHippoBean();
					if (documentBean != null) {
						break;
					}
				}
			}
		return documentBean;
	}
}
