package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.linking.HstLinkCreator;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.util.ContentBeanUtils;
import org.hippoecm.hst.util.PathUtils;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.GlobalProject;
import com.kohler.commons.components.componentsInfo.GlobalProjectAjaxComponentInfo;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.taxonomies.TaxonomyService;
import com.kohler.commons.util.CommonUtil;

import net.sf.json.JSONObject;

/**
 * APAC Implementation for Global projects listing page, inherits from
 * <code>EssentialsListComponent</code>
 * 
 * @author Girijanadan.p Date: 14/06/2017
 * @version 1.0
 */

@ParametersInfo(type = GlobalProjectAjaxComponentInfo.class)
public class GlobalProjectAjaxComponent extends EssentialsListComponent {
	
	private static final Logger LOG = LoggerFactory.getLogger(GlobalProjectAjaxComponent.class);
	TaxonomyService taxonomyService = new TaxonomyService();
	
	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		String filterData = this.getPublicRequestParameter(request, Constants.GLOBAL_PROJECT_SELECTED_FILTERS);
		String currentCountryKey = this.getPublicRequestParameter(request, Constants.GP_AJAX_TAXONOMY_VALUE);
		HstRequestContext hstRequestContext = request.getRequestContext();
		final GlobalProjectAjaxComponentInfo paramInfo = getComponentParametersInfo(request);
		Integer maxCountryProject = paramInfo.getMaxCountryProjects();
		String facetPath = paramInfo.getFacetPath();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		JSONObject result = new JSONObject();
		HippoBean rootBean = hstRequestContext.getContentBean();
		if (rootBean == null) {
			final String path = getScopePath(paramInfo);
			if (path != null) {
				rootBean = getSearchScope(request, path);
			}
		}
		try {
			List<String> selectedFilters =  new ArrayList<> ();
			Map<String, String> countryMap = taxonomyService.getGlobalProjectFirstLevelCategory( language  + "_" + country);
			if (!Strings.isNullOrEmpty(filterData)) {
				selectedFilters =  Arrays.asList(filterData.split(","));
			}
			HippoFacetNavigationBean  hippoFacetNavigationBean = getFacetNavigationBean(hstRequestContext, rootBean, facetPath, selectedFilters);
			Map<String,String> newCountryMap = getAvailableCountries(hippoFacetNavigationBean, countryMap);
			List <String> availableCountryFilterList = null;
			if (Strings.isNullOrEmpty(currentCountryKey)) {
				result = globalProjectsWithoutCountry(request, hstRequestContext, rootBean, facetPath, maxCountryProject, selectedFilters);
				result.put(Constants.ALL_COUNTRIES_IN_PROJECTS, newCountryMap);
				availableCountryFilterList = getAvailableFilters(hstRequestContext, rootBean, facetPath);
			} else {
				result = globalProjectsWithCountry(request, hstRequestContext, rootBean, facetPath, maxCountryProject, currentCountryKey, selectedFilters);
				result.put(Constants.TAXONOMY_FOR_COUNTRY, countryMap.get(currentCountryKey));
				result.put(Constants.GP_NEW_COUNTRY_MAP, newCountryMap);
				availableCountryFilterList = getAvailableFilters(hstRequestContext, rootBean, facetPath + "/"+ Constants.COUNTRY_FACET_NODE_NAME + "/" + currentCountryKey);
			}
			result.put(Constants.AVAILABLE_COUNTRY_FILTER_LIST, availableCountryFilterList);
		} catch (Exception e) {
			result.put(Constants.SUCCESS, false);
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				response.getWriter().write(result.toString());
				response.setContentType(Constants.CONTENT_TYPE_APPLICATION_JSON);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
	}
	 
	 private List<JSONObject> constructJSONForListPage (HstRequest request, List<GlobalProject> resultDocumentBeans) { 
		 List<JSONObject> jsonObjectList = new ArrayList<>();
		 HstLinkCreator linkCreator = request.getRequestContext().getHstLinkCreator();
		 for(GlobalProject globalproject : resultDocumentBeans ) {
			 if (globalproject != null) {
				 JSONObject globalprojectJsonObject = new JSONObject();
				 globalprojectJsonObject.put(Constants.GLOBAL_PROJECT_TITLE, globalproject.getTitle());
				 globalprojectJsonObject.put(Constants.GLOBAL_PROJECT_PLACE, globalproject.getPlace());
				 HstLink link = linkCreator.create(globalproject, request.getRequestContext());
				 String beanHstURL = link.toUrlForm(request.getRequestContext(), false);
				 globalprojectJsonObject.put(Constants.REQUEST_ATTR_HSTLINK, beanHstURL);
				 globalprojectJsonObject.put(Constants.GLOBAL_PROJECT_IMAGE, globalproject.getImageUrl());
				 globalprojectJsonObject.put(Constants.GLOBAL_PROJECT_IMAGE_TITLE, globalproject.getImageTitle());
				 if (Strings.isNullOrEmpty(globalproject.getImageAlt())) {
					 globalprojectJsonObject.put(Constants.GLOBAL_PROJECT_IMAGE_ALT, globalproject.getTitle());
				 } else {
					 globalprojectJsonObject.put(Constants.GLOBAL_PROJECT_IMAGE_ALT, globalproject.getImageAlt());
				 }
				 jsonObjectList.add(globalprojectJsonObject);
			 }
		 }
		 return jsonObjectList;
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

	 /**
	  * Returning HippoFacetNavigation bean
	  * @param hstRequestContext
	  * @param rootBean
	  * @param facetPath
	  * @return HippoFacetNavigation
	  */
	 private HippoFacetNavigationBean getFacetNavigationBean (HstRequestContext hstRequestContext, HippoBean rootBean,
			 String facetPath, List<String> selectedFilters) {
		 HippoFacetNavigationBean hippoFacetNavigationBean = null;
		 try {
			 @SuppressWarnings("unchecked")
			 HstQuery query = hstRequestContext.getQueryManager().createQuery(rootBean, GlobalProject.class);
			 String relPath = PathUtils.normalizePath(facetPath);
			 if (!selectedFilters.isEmpty()) {
				 Filter filtersFilter = query.createFilter();
				 for (String filterString : selectedFilters) {
					 Filter orFiltersFilter = query.createFilter();
					 String fieldRestriction = Constants.NS_FILTERS;
					 orFiltersFilter.addEqualTo(fieldRestriction, filterString);
					 filtersFilter.addOrFilter(orFiltersFilter);
				 }
				 query.setFilter(filtersFilter);
			 }
			 String queryAsString = "xpath(" + query.getQueryAsString(true) + ")";
			 hippoFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(relPath, queryAsString);
		 } catch (Exception e) {
			 LOG.error(e.getMessage(), e);
		 }
		 return hippoFacetNavigationBean;
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
	 public UberListClass getUberGlobalProjects (Boolean isUber, String facetPath, HippoFolderBean countryFolder, HstRequestContext hstRequestContext, HippoBean rootBean,Integer maxCountryProject, List<String> selectedFilters) {
		 UberListClass uberListView = null;
		 try {
			 List<GlobalProject> globalProductList = new LinkedList<>();
			 String relPath = PathUtils.normalizePath(facetPath);
			 if (isUber) {
				 String countryRelPath = countryFolder.getPath();
				 relPath = countryRelPath.substring(countryRelPath.indexOf(relPath));
			 } 
			 @SuppressWarnings("unchecked")
			 HstQuery query = hstRequestContext.getQueryManager().createQuery(rootBean, GlobalProject.class);
			 String fieldNameAttributeForSort = Constants.NS_DISPLAY_ORDER;
			 query.addOrderByAscending(fieldNameAttributeForSort);
			 if (!selectedFilters.isEmpty()) {
				 Filter filtersFilter = query.createFilter();
				 for (String filterString : selectedFilters) {
					 Filter orFiltersFilter = query.createFilter();
					 String fieldRestriction = Constants.NS_FILTERS;
					 orFiltersFilter.addEqualTo(fieldRestriction, filterString);
					 filtersFilter.addOrFilter(orFiltersFilter);
				 }
				 query.setFilter(filtersFilter);
			 }
			 String queryAsString = "xpath(" + query.getQueryAsString(true) + ")";
			 HippoFacetNavigationBean countryFacetNavigationBean  =  ContentBeanUtils.getFacetNavigationBean(relPath, queryAsString);
			 if(countryFacetNavigationBean != null){
				 HippoResultSetBean resultSet = countryFacetNavigationBean.getResultSet();
				 if (resultSet != null && resultSet.getCount() > 0) {
					 HippoDocumentIterator<HippoBean> iterator = resultSet.getDocumentIterator(HippoBean.class);
					 //If Uber page will return only limited projects
					 if (isUber) {
						 Integer counter = 0;
						 while (iterator.hasNext() && counter < maxCountryProject) {
							 GlobalProject project = (GlobalProject) iterator.next();
							 globalProductList.add(project);
							 counter++;
						 }
					 } else {
						 while (iterator.hasNext()) {
							 GlobalProject project = (GlobalProject) iterator.next();
							 globalProductList.add(project);
						 }
					 }
					 uberListView = new UberListClass();
					 uberListView.setFlag(false);
					 uberListView.setUberGlobalProjects(globalProductList);
					 if (resultSet.getCount() > maxCountryProject)  {
						 uberListView.setFlag(true);
					 } 
				 }
			 }
		 } catch (Exception e) {
			 LOG.error(e.getMessage(), e);
		 }
		 return uberListView;
	 }
	 
	 /**
	  * 
	  * @param request
	  * @param hstRequestContext
	  * @param rootBean
	  * @param facetPath
	  * @param maxCountryProject
	  * @param countryMap
	  * @param selectedFilters
	  * @return
	  */
	 private JSONObject globalProjectsWithoutCountry (HstRequest request, HstRequestContext hstRequestContext, HippoBean rootBean, String facetPath, Integer maxCountryProject, List<String> selectedFilters) {
		 Map<String,List<JSONObject>> listPageResultMap = new LinkedHashMap<>();
		 JSONObject resultJson = new JSONObject();
		 Map<String,String> seeAllMap = new HashMap<>();
		 HippoFacetNavigationBean  hippoFacetNavigationBean = getFacetNavigationBean(hstRequestContext, rootBean, facetPath);
		 if (hippoFacetNavigationBean != null) {
			 Iterator<HippoFolderBean> itr = hippoFacetNavigationBean.getFolders().iterator();
			 while (itr.hasNext()) {
				 HippoFolderBean folder = itr.next();
				 if (folder.getName().equals(Constants.COUNTRY_FACET_NODE_NAME) && !folder.getFolders().isEmpty()) {
					 for (HippoFolderBean countryFolder : folder.getFolders()) {
						 UberListClass uberListView = getUberGlobalProjects(true, facetPath, countryFolder, hstRequestContext, rootBean, maxCountryProject, selectedFilters);
						 if (uberListView != null && uberListView.getUberGlobalProjects() != null && !uberListView.getUberGlobalProjects().isEmpty()) {
							 List<JSONObject> jsonObjectList = constructJSONForListPage(request, uberListView.getUberGlobalProjects());
							 if(!jsonObjectList.isEmpty()){
								 if(uberListView.getFlag()){
									 seeAllMap.put(countryFolder.getName(), "seeAll");
								 }
								 listPageResultMap.put(countryFolder.getName(), jsonObjectList);
							 }
						 }
					 }
				 } 
			 }
		 }
		 resultJson.put(Constants.GLOBAL_PROJECT_LIST_JSON, listPageResultMap);
		 resultJson.put(Constants.SEE_ALL_LINK, seeAllMap);
		 resultJson.put(Constants.SUCCESS, true);
		 return resultJson;
	 }
	 
	 /**
	  * 
	  * @param request
	  * @param hstRequestContext
	  * @param rootBean
	  * @param facetPath
	  * @param maxCountryProject
	  * @param countryMap
	  * @param selectedFilters
	  * @return
	  */
	 private JSONObject globalProjectsWithCountry (HstRequest request, HstRequestContext hstRequestContext, HippoBean rootBean, String facetPath, Integer maxCountryProject, String currentCountryKey, List<String> selectedFilters) {
		 JSONObject resultJson = new JSONObject();
		 List<JSONObject> jsonObjectList = null;
		 UberListClass uberListView = getUberGlobalProjects(false, facetPath + "/"+ Constants.COUNTRY_FACET_NODE_NAME + "/" + currentCountryKey, null, hstRequestContext, rootBean, maxCountryProject, selectedFilters);
		 if (uberListView != null && uberListView.getUberGlobalProjects() != null && !uberListView.getUberGlobalProjects().isEmpty()) {
			 jsonObjectList = constructJSONForListPage(request, uberListView.getUberGlobalProjects());
			 resultJson.put(Constants.TOTAL_GLOBAL_PROJECT_SIZE, uberListView.getUberGlobalProjects().size());
		 }
		 resultJson.put(Constants.GLOBAL_PROJECT_JSON, jsonObjectList);
		 resultJson.put(Constants.SUCCESS, true);
		 return resultJson;
	 }
	 
	 /**
	  * 
	  * @param hstRequestContext
	  * @param rootBean
	  * @param facetPath
	  * @param countryMap
	  * @param selectedFilters
	  * @return available countries based on selected filters 
	  */
	 public Map<String,String> getAvailableCountries (HippoFacetNavigationBean  hippoFacetNavigationBean, Map<String, String> countryMap) {
		 Map<String,String> availableCountryMap = new LinkedHashMap<>();
		 try {
			 if (hippoFacetNavigationBean != null) {
				 Iterator<HippoFolderBean> itr = hippoFacetNavigationBean.getFolders().iterator();
				 while (itr.hasNext()) {
					 HippoFolderBean folder = itr.next();
					 if (folder.getName().equals(Constants.COUNTRY_FACET_NODE_NAME) && !folder.getFolders().isEmpty()) {
						 for (HippoFolderBean countryFolder : folder.getFolders()) {
							 availableCountryMap.put(countryFolder.getName(), countryMap.get(countryFolder.getName()));
						 }
					 }
				 }
			 }	 
		 } catch (Exception ex) {
			 LOG.error(ex.getMessage(), ex);
		 }
		 return availableCountryMap;
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

/**
 * 
 * @author dhanwan.r
 * View been for presenting uber global projects
 * @version 1.0
 */
class UberListClass {
	
	private Boolean flag;
	
	List<GlobalProject> uberGlobalProjects;

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public List<GlobalProject> getUberGlobalProjects() {
		return uberGlobalProjects;
	}

	public void setUberGlobalProjects(List<GlobalProject> uberGlobalProjects) {
		this.uberGlobalProjects = uberGlobalProjects;
	}
}