/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.jackrabbit.commons.JcrUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;
import org.onehippo.cms7.essentials.components.paging.DefaultPagination;
import org.onehippo.cms7.essentials.components.paging.Pageable;
import org.onehippo.taxonomy.api.Category;
import org.onehippo.taxonomy.api.TaxonomyNodeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.CategoryBanner;
import com.kohler.beans.PromoBanner;
import com.kohler.commons.components.componentsInfo.EssentialsProductListComponentInfo;
import com.kohler.commons.constants.Constants;

/**
 * APAC Implementation for Product category listing page, inherits from <code>EssentialsListComponent</code>
 * @author dhanwan.r
 * Date: 05/05/2017
 * @version 1.0
 */
@ParametersInfo(type = EssentialsProductListComponentInfo.class)
public class EssentialsProductListComponent extends EssentialsListComponent {

	private static Logger LOG = LoggerFactory.getLogger(EssentialsListComponent.class);
	
	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();

	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		request.setAttribute("isCompare", "true");
		final EssentialsProductListComponentInfo paramInfo = getComponentParametersInfo(request);
		final String path = getScopePath(paramInfo);
		final HippoBean scope = getSearchScope(request, path);

		final HstRequestContext context = request.getRequestContext();
		Object currentSwatchObject = context.getAttribute(Constants.CURRENT_SWATCH);
		if(currentSwatchObject != null){
			if (!Strings.isNullOrEmpty(currentSwatchObject.toString())) {
				request.setAttribute(Constants.CURRENT_SWATCH, currentSwatchObject.toString());
			}
		}
		Object currentCategoryObject = context.getAttribute(Constants.REQUEST_ATTR_CURRENTCATEGORY);
		request.setAttribute(Constants.REQUEST_ATTR_PAGESIZE, paramInfo.getPageSize() - 1);
		if (currentCategoryObject != null) {
			String currentURL = request.getRequestURL().toString();
			Category currentCategory = (Category) currentCategoryObject;
			String categoryKey = currentCategory.getKey();
			if(currentURL.endsWith(categoryKey + "/") || currentURL.endsWith(categoryKey + "?") || currentURL.endsWith(categoryKey)){
				Object promoBannerFlagObject = context.getAttribute(Constants.REQUEST_ATTR_PROMOBANNERFLAG);
				if ((promoBannerFlagObject != null) && (Boolean) promoBannerFlagObject) {
					List<PromoBanner> promoBanners = getAllPromoBanners((Category) currentCategoryObject, request);
					if(promoBanners != null && !promoBanners.isEmpty()){
						request.setAttribute(Constants.REQUEST_ATTR_PAGESIZE, paramInfo.getPageSize());
						request.setAttribute(Constants.REQUEST_ATTR_PROMOBANNERS, promoBanners);
					}
				}
			}
			List<CategoryBanner> categoryBanners = getAllCategoryBanners((Category) currentCategoryObject, request);
			context.setAttribute(Constants.REQUEST_ATTR_CATEGORYBANNERS, categoryBanners);
			request.setAttribute(Constants.REQUEST_ATTR_CURRENTCATEGORY, currentCategoryObject);
		}
		if (scope == null) {
			handleInvalidScope(request, response);
			return;
		}

		final Pageable<? extends HippoBean> pageable;
		if (scope instanceof HippoFacetNavigationBean) {
			pageable = doFacetedSearch(request, paramInfo, scope);
		} else {
			pageable = doFacetedSearch(request, paramInfo, scope);
		}
		populateRequest(request, paramInfo, pageable);
	}

	/**
	 * Gets list of products 
	 * @param request
	 * @param paramInfo
	 * @param scope
	 * @return pageable 
	 * @throws IllegalStateException
	 * @throws QueryException
	 */
	protected <T extends EssentialsListComponentInfo> Pageable<HippoBean> doFacetedSearch(final HstRequest request,
			final T paramInfo, final HippoBean scope) {
		final HstRequestContext context = request.getRequestContext();
		
		Pageable<HippoBean> pageable = DefaultPagination.emptyCollection();
		try {
				Object resultSetObject = context.getAttribute(Constants.REQUEST_ATTR_RESULT_SET);
				if (resultSetObject != null) {
					final HippoResultSetBean resultSet = (HippoResultSetBean) resultSetObject;
					Integer pageSize = paramInfo.getPageSize();
					if(request.getAttribute(Constants.REQUEST_ATTR_PAGESIZE) != null)
					{
						pageSize = Integer.parseInt(request.getAttribute(Constants.REQUEST_ATTR_PAGESIZE).toString());
					}
					final HippoDocumentIterator<HippoBean> iterator = resultSet.getDocumentIterator(HippoBean.class);
					pageable = getPageableFactory().createPageable(iterator, resultSet.getCount().intValue(),
							pageSize, getCurrentPage(request));
					request.setAttribute(Constants.REQUEST_ATTR_TOTALPRODUCTCOUNT, resultSet.getCount().intValue());
					request.setAttribute(Constants.REQUEST_ATTR_CURRENT_PAGE, getCurrentPage(request));
				}else{
					request.setAttribute(Constants.REQUEST_ATTR_TOTALPRODUCTCOUNT, new Integer(0));
					request.setAttribute(Constants.REQUEST_ATTR_CURRENT_PAGE, getCurrentPage(request));
				}
		} catch (IllegalStateException e) {
			LOG.error("Illegal State " + e.getMessage());
			e.printStackTrace();
		} 
		return pageable;
	}

	/**
	 * Gets list of Promo Banner
	 * @param category
	 * @param request
	 * @return promoBannerList
	 */
	public List<PromoBanner> getAllPromoBanners(Category category, HstRequest request) {
		String bannerFolderName = Constants.PROMOBANNER_FOLDER;
		List<PromoBanner> promoBannerList = new ArrayList<PromoBanner>();
		try {
			HstRequestContext requestContext = request.getRequestContext();
			String path = requestContext.getSiteContentBasePath();
			Session session = requestContext.getSession();
			Node rootBean = JcrUtils.getNodeIfExists("/" + path + "/" + bannerFolderName, session);
			if (rootBean != null) {
				@SuppressWarnings("unchecked")
				HstQuery query = requestContext.getQueryManager().createQuery(rootBean, PromoBanner.class);
				Filter filter = query.createFilter();
				filter.addEqualTo(TaxonomyNodeTypes.HIPPOTAXONOMY_KEYS, category.getKey());
				query.setFilter(filter);
				HstQueryResult queryResult = query.execute();
				HippoBeanIterator beanItr = queryResult.getHippoBeans();
				while (beanItr.hasNext()) {
					PromoBanner promoBanner = (PromoBanner) beanItr.next();
					promoBannerList.add(promoBanner);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return promoBannerList;
	}
	
	/**
	 * Gets list of category Banner
	 * @param category
	 * @param request
	 * @return promoBannerList
	 */
	public List<CategoryBanner> getAllCategoryBanners(Category category, HstRequest request) {
		String categoryBannerFolderName = Constants.CATEGORYBANNER_FOLDER;
		List<CategoryBanner> categoryBannerList = new ArrayList<CategoryBanner>();
		try {
			HstRequestContext requestContext = request.getRequestContext();
			String path = requestContext.getSiteContentBasePath();
			Session session = requestContext.getSession();
			Node rootBean = JcrUtils.getNodeIfExists("/" + path + "/" + categoryBannerFolderName, session);
			if (rootBean != null) {
				@SuppressWarnings("unchecked")
				HstQuery query = requestContext.getQueryManager().createQuery(rootBean, CategoryBanner.class);
				Filter filter = query.createFilter();
				filter.addEqualTo(TaxonomyNodeTypes.HIPPOTAXONOMY_KEYS, category.getKey());
				query.setFilter(filter);
				String fieldNameAttributeForSort = Constants.NS_DISPLAY_ORDER;
				query.addOrderByAscending(fieldNameAttributeForSort);
				HstQueryResult queryResult = query.execute();
				HippoBeanIterator beanItr = queryResult.getHippoBeans();
				while (beanItr.hasNext()) {
					CategoryBanner categoryBanner = (CategoryBanner) beanItr.next();
					categoryBannerList.add(categoryBanner);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return categoryBannerList;
	}
}
