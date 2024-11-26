/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.util.SearchInputParsingUtils;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.paging.DefaultPagination;
import org.onehippo.cms7.essentials.components.paging.Pageable;
import org.onehippo.taxonomy.api.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.commons.components.componentsInfo.EssentialsSearchListComponentInfo;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.util.CommonUtil;

/**
 * APAC Implementation for Search, inherits from <code>EssentialsListComponent</code>
 * @author dhanwan.r
 * Date: 06/19/2017
 * @version 1.0
 */
@ParametersInfo(type = EssentialsSearchListComponentInfo.class)
public class EssentialsSearchListComponent extends EssentialsListComponent {

	private static Logger LOG = LoggerFactory.getLogger(EssentialsListComponent.class);
	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	
	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		request.setAttribute("isCompare", "false");
		final HstRequestContext context = request.getRequestContext();
		final EssentialsSearchListComponentInfo paramInfo = getComponentParametersInfo(request);
		final String path = getScopePath(paramInfo);
		final HippoBean scope = getSearchScope(request, path);

		Object currentSwatchObject = context.getAttribute(Constants.CURRENT_SWATCH);
		if(currentSwatchObject != null){
			if (!Strings.isNullOrEmpty(currentSwatchObject.toString())) {
				request.setAttribute(Constants.CURRENT_SWATCH, currentSwatchObject.toString());
			}
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
		populateRequest(request, paramInfo, pageable);
	}

	/**
	 * Gets list of search products 
	 * @param request
	 * @param paramInfo
	 * @param scope
	 * @return pageable 
	 * @throws IllegalStateException
	 * @throws QueryException
	 */
	@SuppressWarnings("unchecked")
	protected <T extends EssentialsSearchListComponentInfo> Pageable<HippoBean> doFacetedSearch(final HstRequest request,
			final T paramInfo, final HippoBean scope) {
		
		final HstRequestContext context = request.getRequestContext();
				
		Object totalResultsObject = context.getAttribute(Constants.REQUEST_ATTR_TOTALRESULTS);
		Long totalResultsSize = null;
		if(totalResultsObject != null){
			totalResultsSize = (Long) totalResultsObject;
			request.setAttribute(Constants.REQUEST_ATTR_TOTALRESULTS, totalResultsSize);
		}
		Object totalProductCountObject = context.getAttribute(Constants.REQUEST_ATTR_TOTALPRODUCTCOUNT);
		Long totalProductCount = null;
		if(totalProductCountObject != null){
			totalProductCount = (Long) totalProductCountObject;
			request.setAttribute(Constants.REQUEST_ATTR_TOTALPRODUCTCOUNT, totalProductCount);
		}
		Long totalSpecCount = null;
		Object totalSpecCountObject = context.getAttribute(Constants.REQUEST_ATTR_TOTALSPECCOUNT);
		if(totalSpecCountObject != null){
			totalSpecCount = (Long) totalSpecCountObject;
			request.setAttribute(Constants.REQUEST_ATTR_TOTALSPECCOUNT, totalSpecCount);
		}
		
		Long totalHelpCount = null;
		Object totalHelpCountObject = context.getAttribute(Constants.REQUEST_ATTR_TOTALHELPCOUNT);
		if(totalHelpCountObject != null){
			totalHelpCount = (Long) totalHelpCountObject;
			request.setAttribute(Constants.REQUEST_ATTR_TOTALHELPCOUNT, totalHelpCount);
		}
		Long totalArticleVideoSize = null;
		Object totalArticleVideoSizeObject = context.getAttribute(Constants.REQUEST_ATTR_TOTALARTICLEVIDEOSIZE);
		if(totalArticleVideoSizeObject != null){
			totalArticleVideoSize = (Long) totalArticleVideoSizeObject;
			request.setAttribute(Constants.REQUEST_ATTR_TOTALARTICLEVIDEOSIZE, totalArticleVideoSize);
		}
		
    	Long totalPages = null;
		Object totalPagesObject = context.getAttribute(Constants.REQUEST_ATTR_TOTALPAGES);
		if(totalPagesObject != null){
			totalPages = (Long) totalPagesObject;
			request.setAttribute(Constants.REQUEST_ATTR_TOTALPAGES, totalPages);
		}
		Object categoryObject = context.getAttribute(Constants.REQUEST_ATTR_CURRENTCATEGORY);
		if (categoryObject != null) {
			Category category = (Category) categoryObject;
			request.setAttribute(Constants.REQUEST_ATTR_CURRENTCATEGORY, category);
		}
		
		Long currentPage = null;
		try{
		Object currentPageObject = context.getAttribute(Constants.REQUEST_ATTR_TOTALCURRENTPAGE);
		if(currentPageObject != null){
			currentPage = Long.valueOf(currentPageObject.toString());
			request.setAttribute(Constants.REQUEST_ATTR_TOTALCURRENTPAGE, currentPage);
		}
		}catch(Exception e)
		{
	    LOG.error(e.getMessage()+ " : " +e);
	    e.printStackTrace();
		}
		Pageable<HippoBean> pageable = DefaultPagination.emptyCollection();
		try {
			Map<String, String> queryStringKeyValueMap=null;
			String searchTerm = (String)context.getAttribute(Constants.QUERY_SEARCH_TERM);
			if (!Strings.isNullOrEmpty(request.getQueryString()) && !Strings.isNullOrEmpty(searchTerm)) {
				queryStringKeyValueMap = CommonUtil.generateQueryStringMap(request.getQueryString());
				String searchTermQuery = searchTerm;
				searchTermQuery=SearchInputParsingUtils.parse(searchTermQuery, false);
				if(searchTermQuery.contains("%")){
					if(searchTermQuery.endsWith("%")){
						searchTermQuery=searchTermQuery.replace("%", "");
						searchTerm=searchTermQuery;
					}else
					{
						searchTermQuery=searchTermQuery.replace("%", " ");
						searchTerm=searchTermQuery;	
					}
				}
				request.setAttribute(Constants.QUERY_SEARCH_TERM, searchTerm);
				if(searchTermQuery.trim().isEmpty()){
					request.setAttribute(Constants.REQUEST_ATTR_TOTALRESULTS, totalResultsSize);
					return null;
				} 
				if(searchTermQuery.trim().isEmpty()){
					searchTerm = (String)context.getAttribute(Constants.QUERY_SEARCH_TERM);
					request.setAttribute(Constants.QUERY_SEARCH_TERM, searchTerm);
					context.setAttribute(Constants.QUERY_SEARCH_TERM, searchTerm);
					request.setAttribute(Constants.REQUEST_ATTR_TOTALRESULTS, totalResultsSize);
					return null;
				}
				String type = (String)context.getAttribute(Constants.TYPE);
				if(Strings.isNullOrEmpty(type)){
					type=Constants.PRODUCT;
				}
				request.setAttribute(Constants.TYPE, type);

				if(type.equals(Constants.PRODUCT)||type.equals(Constants.SPEC)) {
					Object resultSetObject = context.getAttribute(Constants.REQUEST_ATTR_RESULT_SET);
					if (resultSetObject != null) {
						final HippoResultSetBean resultSet = (HippoResultSetBean) resultSetObject;
						if (resultSet != null) {
							final HippoDocumentIterator<HippoBean> iterator = resultSet.getDocumentIterator(HippoBean.class);
							long productCount = resultSet.getCount();
							request.setAttribute(Constants.REQUEST_ATTR_TOTALPRODUCTCOUNT, productCount);
							pageable = getPageableFactory().createPageable(iterator, resultSet.getCount().intValue(),
									paramInfo.getPageSize(), currentPage.intValue());
						}
					}
				} else if(type.equals(Constants.ARTICLE) || type.equals(Constants.VIDEO) || type.equals(Constants.PRESSRELEASE))   {
					Long totalArticleCount = null;
					Object totalArticleCountObject = context.getAttribute(Constants.REQUEST_ATTR_TOTALARTICLECOUNT);
					if(totalArticleCountObject != null){
						totalArticleCount = (Long) totalArticleCountObject;
						request.setAttribute(Constants.REQUEST_ATTR_TOTALARTICLECOUNT, totalArticleCount);
					}
					Long totalVideoCount = null;
					Object totalVideoCountObject = context.getAttribute(Constants.REQUEST_ATTR_TOTALVIDEOCOUNT);
					if(totalVideoCountObject != null){
						totalVideoCount = (Long) totalVideoCountObject;
						request.setAttribute(Constants.REQUEST_ATTR_TOTALVIDEOCOUNT, totalVideoCount);
					}
					Long totalPressReleasesCount = null;
					Object totalPressReleasesCountObject = context.getAttribute(Constants.REQUEST_ATTR_TOTALPRESSRELEASESCOUNT);
					if(totalVideoCountObject != null){
						totalPressReleasesCount = (Long) totalPressReleasesCountObject;
						request.setAttribute(Constants.REQUEST_ATTR_TOTALPRESSRELEASESCOUNT, totalPressReleasesCount);
					}
					//for Article
				    if (type.equals(Constants.ARTICLE)) {
						List<HippoBean> resultArticleList = null;
						Object resultArticleListObject = context.getAttribute(Constants.RESULT_ARTICLE_LIST);
						if(resultArticleListObject != null){
							resultArticleList = (LinkedList<HippoBean>)resultArticleListObject;
							request.setAttribute(Constants.RESULT_ARTICLE_LIST, resultArticleList);
						}
				    }
				    else if (type.equals(Constants.VIDEO)) {
				    	//Video
						List<HippoBean> resultVideoList = null;
						Object resultVideoListObject = context.getAttribute(Constants.RESULT_VIDEO_LIST);
						if(resultVideoListObject != null){
							resultVideoList = (LinkedList<HippoBean>)resultVideoListObject;
							request.setAttribute(Constants.RESULT_VIDEO_LIST, resultVideoList);
						}
				    }else if (type.equals(Constants.PRESSRELEASE)) {
				    	//PRESSRELEASES
						List<HippoBean> resultPressreleasesList = null;
						Object resultPressreleasesListObject = context.getAttribute(Constants.RESULT_PRESSRELEASE_LIST);
						if(resultPressreleasesListObject != null){
							resultPressreleasesList = (LinkedList<HippoBean>)resultPressreleasesListObject;
							request.setAttribute(Constants.RESULT_PRESSRELEASE_LIST, resultPressreleasesList);
						}
				    }
				}  else if(type.equals(Constants.HELP))   {
					List<HippoBean> resultHelpList = null;
					Object resultHelpListObject = context.getAttribute(Constants.RESULT_HELP_LIST);
					if(resultHelpListObject != null){
						resultHelpList = (LinkedList<HippoBean>)resultHelpListObject;
						request.setAttribute(Constants.RESULT_HELP_LIST, resultHelpList);
					}
				} 
				if (searchTerm.contains("&")) {
					request.setAttribute(Constants.QUERY_SEARCH_TERM, searchTerm.replaceAll("&", " ").trim());
				} else {
					request.setAttribute(Constants.QUERY_SEARCH_TERM, searchTerm);
				}       
			}
			if(queryStringKeyValueMap!=null){
				request.setAttribute(Constants.SORT_DIRECTION, queryStringKeyValueMap.get(Constants.SORT_DIRECTION));
				request.setAttribute(Constants.SORT_FIELD, queryStringKeyValueMap.get(Constants.SORT_FIELD));
			}	
		} catch (IllegalStateException e) {
			LOG.error("Illegal State " + e.getMessage());
			e.printStackTrace();
		} 
		return pageable;
	}

}
