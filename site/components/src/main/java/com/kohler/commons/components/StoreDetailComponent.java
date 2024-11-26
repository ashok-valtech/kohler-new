/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.beans.Dealer;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.util.CommonUtil;

/**
 * APAC Implementation for Store locator detail  page, inherits from <code>BaseHstComponent</code>
 * @author sumit.p
 * Date: 26/05/2017
 * @version 1.0
 */
@ParametersInfo(type = EssentialsListComponentInfo.class)
public class StoreDetailComponent extends EssentialsListComponent {
	
	private static final Logger LOG = LoggerFactory.getLogger(StoreDetailComponent.class);

	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
		Map<String, String> requestAttr = null;
		String storeId = null;
		if (request.getQueryString() != null) {
			requestAttr = CommonUtil.generateQueryStringMap(request.getQueryString());
		}
		if (requestAttr != null) {
			storeId = requestAttr.get(Constants.STORE_ID);
		}
		try {
			storeId = URLDecoder.decode(storeId, "UTF-8");
		} catch (UnsupportedEncodingException encodingException) {
			encodingException.printStackTrace();
		}
		final EssentialsListComponentInfo paramInfo = getComponentParametersInfo(request);
		final String path = getScopePath(paramInfo);
		final HippoBean rootBean = getSearchScope(request, path);
		HstQueryResult queryResult = null;
		Dealer currentDealer = null;
		try {

			if (storeId != null) {
				@SuppressWarnings("unchecked")
				HstQuery query = request.getRequestContext().getQueryManager().createQuery(rootBean, Dealer.class);
				Filter filter = query.createFilter();
				filter.addEqualTo("kohler:storeId", storeId);
				query.setFilter(filter);
				queryResult = query.execute();

				if (queryResult.getTotalSize() >= 1) {
					HippoBeanIterator iterator = queryResult.getHippoBeans();
					while (iterator.hasNext()) {
						HippoBean bean = iterator.nextHippoBean();
						if (bean != null && bean instanceof Dealer) {
							currentDealer = (Dealer) bean;

						}
					}
				}
			}
		} catch (IllegalStateException | QueryException e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		request.setAttribute("storeDetail", currentDealer);
	}

}
