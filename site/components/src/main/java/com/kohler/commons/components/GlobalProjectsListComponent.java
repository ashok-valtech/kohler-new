/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.util.ContentBeanUtils;
import org.hippoecm.hst.util.PathUtils;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.taxonomy.api.TaxonomyNodeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.beans.GlobalProject;
import com.kohler.commons.components.componentsInfo.GlobalProjectComponentInfo;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.taxonomies.TaxonomyService;
import com.kohler.commons.util.CommonUtil;

/**
 * APAC Implementation for Global projects listing page, inherits from
 * <code>EssentialsListComponent</code>
 * 
 * @author Girijanadan.p Date: 14/06/2017
 * @version 1.0
 */
@ParametersInfo(type = GlobalProjectComponentInfo.class)
public class GlobalProjectsListComponent extends EssentialsListComponent {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalProjectsListComponent.class);
	TaxonomyService taxonomyService = new TaxonomyService();

	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		super.doBeforeRender(request, response);
		HstRequestContext hstRequestContext = request.getRequestContext();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		final GlobalProjectComponentInfo paramInfo = getComponentParametersInfo(request);
		Integer maxCountryProject = paramInfo.getMaxCountryProjects();
		String facetPath = paramInfo.getFacetPath();
		HippoBean rootBean = hstRequestContext.getContentBean();
		List<String> availableFilterList = new ArrayList<>();
		HippoFacetNavigationBean  hippoFacetNavigationBean = getFacetNavigationBean(hstRequestContext, rootBean, facetPath);
		Map<String, String> countryMap = taxonomyService.getGlobalProjectFirstLevelCategory(language + "_" + country);
		Map<String, HippoFolderBean> availableCountriesWithTotalProjectsMap = new LinkedHashMap<>();
		Map<String, List<GlobalProject>> hippoBeanMap = new LinkedHashMap<>();
		final String globalProjectDocId = cleanupSearchQuery(getAnyParameter(request,Constants.TAXONOMY_KEY));
		if (rootBean == null) {
			final String path = getScopePath(paramInfo);
			if (path != null) {
				rootBean = getSearchScope(request, path);
			}
		}
		
		if (hippoFacetNavigationBean != null) {
			Iterator<HippoFolderBean> itr = hippoFacetNavigationBean.getFolders().iterator();
			while (itr.hasNext()) {
				HippoFolderBean folder = itr.next();
				if (folder.getName().equals(Constants.COUNTRY_FACET_NODE_NAME)) {
					if (!folder.getFolders().isEmpty()) {
						for (HippoFolderBean countryFolder : folder.getFolders()) {
							availableCountriesWithTotalProjectsMap.put(countryFolder.getName(), countryFolder);
							List<GlobalProject> uberGlobalProductList = getUberGlobalProjects(facetPath, countryFolder, hstRequestContext, rootBean, maxCountryProject);
							hippoBeanMap.put(countryFolder.getName(), uberGlobalProductList);
						}
					}
				} else if (folder.getName().equals(Constants.FILTER_FACET_NODE_NAME) && !folder.getFolders().isEmpty()) {
					for (HippoFolderBean facetFolder : folder.getFolders()) {
						availableFilterList.add(facetFolder.getName());
					}
				}
			}
		  
		}
		if(globalProjectDocId!=null && !globalProjectDocId.isEmpty()){
			getCountryBasedProjects(globalProjectDocId,request);
		    availableFilterList = getAvailableFilters(hstRequestContext, rootBean, facetPath + "/"+ Constants.COUNTRY_FACET_NODE_NAME + "/" + globalProjectDocId);
		    request.setAttribute(Constants.GLOBAL_PROJECT_CURRENT_FILTERS, availableFilterList);
		}else{
			request.setAttribute(Constants.GLOBAL_PROJECT_CURRENT_FILTERS, availableFilterList);
		}
		request.setAttribute(Constants.REQUEST_ATTR_ALL_COUNTRIES, countryMap);
		request.setAttribute(Constants.REQUEST_ATTR_COUNTRIES, availableCountriesWithTotalProjectsMap);
		request.setAttribute(Constants.REQUEST_ATTR_CATEGORYBASEDPROJECTS, hippoBeanMap);
		
		request.setAttribute(Constants.GLOBAL_PROJECT_MAX_COUNTRY_PROJECT, maxCountryProject);
	}

	
	/**
	 * 
	 * @param facetPath
	 * @param countryFolder
	 * @param hstRequestContext
	 * @param rootBean
	 * @param maxCountryProject
	 * @return Three global project from the each country
	 */
	public List<GlobalProject> getUberGlobalProjects (String facetPath, HippoFolderBean countryFolder, HstRequestContext hstRequestContext, HippoBean rootBean,Integer maxCountryProject) {
		List<GlobalProject> globalProductList = new LinkedList<>();
		try {
			String relPath = PathUtils.normalizePath(facetPath);
			String countryRelPath = countryFolder.getPath();
			countryRelPath = countryRelPath.substring(countryRelPath.indexOf(relPath));
			@SuppressWarnings("unchecked")
			HstQuery query = hstRequestContext.getQueryManager().createQuery(rootBean, GlobalProject.class);
			String queryAsString = "xpath(" + query.getQueryAsString(true) + ")";
			HippoFacetNavigationBean countryFacetNavigationBean  =  ContentBeanUtils.getFacetNavigationBean(countryRelPath, queryAsString);
			if(countryFacetNavigationBean != null){
				HippoResultSetBean resultSet = countryFacetNavigationBean.getResultSet();
				if (resultSet.getCount() > 0) {
					HippoDocumentIterator<HippoBean> iterator = resultSet.getDocumentIterator(HippoBean.class);
					Integer counter = 0;
					while (iterator.hasNext() && counter < maxCountryProject) {
						GlobalProject project = (GlobalProject) iterator.next();
						globalProductList.add(project);
    					counter++;
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return globalProductList;
	}

	/**
	 * Returning HippoFacetNavigation bean
	 * @param hstRequestContext
	 * @param rootBean
	 * @param facetPath
	 * @return HippoFacetNavigation
	 */
	private HippoFacetNavigationBean getFacetNavigationBean (HstRequestContext hstRequestContext, HippoBean rootBean,
			String facetPath) {
		HippoFacetNavigationBean hippoFacetNavigationBean = null;
		try {
			@SuppressWarnings("unchecked")
			HstQuery query = hstRequestContext.getQueryManager().createQuery(rootBean, GlobalProject.class);
			String relPath = PathUtils.normalizePath(facetPath);
			String queryAsString = "xpath(" + query.getQueryAsString(true) + ")";
			hippoFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(relPath, queryAsString);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return hippoFacetNavigationBean;
	}
	private void getCountryBasedProjects(String categoryKey, HstRequest request) {
		HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();
		List<GlobalProject> globalProjectList = new LinkedList<>();
		try {
			if (rootBean != null) {
				@SuppressWarnings("unchecked")
				HstQuery query = request.getRequestContext().getQueryManager().createQuery(rootBean, GlobalProject.class);
				Filter filter = query.createFilter();
				filter.addEqualTo(TaxonomyNodeTypes.HIPPOTAXONOMY_KEYS, categoryKey);
				query.setFilter(filter);
				String fieldNameAttributeForSort = Constants.NS_DISPLAY_ORDER;
				query.addOrderByAscending(fieldNameAttributeForSort);
				HstQueryResult queryResult = query.execute();
				HippoBeanIterator beanItr = queryResult.getHippoBeans();
				while (beanItr.hasNext()){
				GlobalProject projects = (GlobalProject) beanItr.next();
				globalProjectList.add(projects);
				}
			}
			
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute(Constants.SELECTED_COUNTRY_BEAN, globalProjectList);
		request.setAttribute(Constants.SELECTED_COUNTRY_TAXONOMYKEY, categoryKey);
		
	}
	 /**
	  * 
	  * @param hippoFacetNavigationBean
	  * @return available filters 
	  */
	 public List <String> getAvailableFilters (HstRequestContext hstRequestContext, HippoBean rootBean, String facetPath) {
		 List <String> availableFilterList = new LinkedList<>();
		 try {
			 HippoFacetNavigationBean hippoFacetNavigationBean = getFacetNavigationBean(hstRequestContext, rootBean, facetPath);
			 if (hippoFacetNavigationBean != null && !hippoFacetNavigationBean.getFolders().isEmpty()) {
				 Iterator<HippoFolderBean> itr = hippoFacetNavigationBean.getFolders().iterator();
				 while (itr.hasNext()) {
					 HippoFolderBean folder = itr.next();
					 if (folder.getName().equals(Constants.FILTER_FACET_NODE_NAME) && !folder.getFolders().isEmpty()) {
						 for (HippoFolderBean facetFolder : folder.getFolders()) {
							 availableFilterList.add(facetFolder.getName());
						 }
					 }
				 }
			 }
		 } catch (Exception ex) {
			 LOG.error(ex.getMessage(), ex);
		 }	 
		 return availableFilterList;
	 }
	 

}
