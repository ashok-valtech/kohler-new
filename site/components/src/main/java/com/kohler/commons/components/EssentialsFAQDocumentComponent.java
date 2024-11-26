/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.CommonComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.FaqList;
import com.kohler.commons.components.componentsInfo.EssentialsFAQDocumentComponentInfo;
import com.kohler.commons.constants.Constants;

/**
 * APAC Implementation for FAQ , inherits from <code>CommonComponent</code>
 * @author dhanwan.r
 * Date: 06/21/2017
 * @version 1.0
 */

@ParametersInfo(type = EssentialsFAQDocumentComponentInfo.class)
public class EssentialsFAQDocumentComponent extends CommonComponent {
	
	private static final Logger LOG = LoggerFactory.getLogger(EssentialsFAQDocumentComponent.class);
	
	@Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
		final HstRequestContext context = request.getRequestContext();
        final HippoBean bean = context.getContentBean();
        final EssentialsFAQDocumentComponentInfo paramInfo = getComponentParametersInfo(request);
        List<FaqList> faqList = getFaqList(request, paramInfo, response);
        if(bean.getClass().getName().equals(FaqList.class.getName())){
        	request.setAttribute(REQUEST_ATTR_DOCUMENT, bean);
        }
        request.setAttribute("items", faqList);
	}
	
	/**
	 * Gets FAQ list
	 * @param request
	 * @param paramInfo
	 * @return  
	 * @throws IllegalStateException
	 * @throws QueryException
	 */
	@SuppressWarnings("unchecked")
	public List<FaqList> getFaqList(HstRequest request, EssentialsFAQDocumentComponentInfo paramInfo, HstResponse response){
		HippoBean rootBean = null;
		HstQueryResult queryResult = null;
		List<FaqList> faqList = new ArrayList<FaqList>();
		try {
			Integer totalFaqCategory = paramInfo.getPageSize();
			String sortField = paramInfo.getSortField();
			String sortOrder = paramInfo.getSortOrder();
			String path = paramInfo.getPath();
			if(!Strings.isNullOrEmpty(path)){
				rootBean = getScopeBean(path);
			}
			if(rootBean == null){
				rootBean = request.getRequestContext ().getSiteContentBaseBean ();
			}
			HstQuery query = request.getRequestContext().getQueryManager().createQuery(rootBean, FaqList.class);
			if(!Strings.isNullOrEmpty(sortOrder)){
				if(!Strings.isNullOrEmpty(sortField) && sortOrder.equals(Constants.SORT_DIRECTION_ASC)){
					query.addOrderByAscending(sortField );
				}else{
					query.addOrderByDescending(sortField);
				}
			}
			queryResult = query.execute();
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			while(iterator.hasNext() && faqList.size() < totalFaqCategory){
				faqList.add((FaqList) iterator.next());
			}
		} catch (IllegalStateException | QueryException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
			try {
				response.forward(Constants.PAGE_NOT_FOUND_URL);
			} catch (IOException ex) {
				e.printStackTrace();
				LOG.error(ex.getMessage() + ex);
			}
		}
		return faqList;
	}
}
