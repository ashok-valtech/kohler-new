/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.Cookie;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.site.HstServices;
import org.hippoecm.hst.util.ContentBeanUtils;
import org.hippoecm.hst.util.PathUtils;
import org.hippoecm.repository.api.NodeNameCodec;
import org.onehippo.cms7.essentials.components.EssentialsFacetsComponent;
import org.onehippo.taxonomy.api.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.SeoCategory;
import com.kohler.commons.components.componentsInfo.ProductFacetsComponentInfo;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.PriceRangeLabelService;
import com.kohler.commons.service.SwatchColorNameFinderService;
import com.kohler.commons.taxonomies.TaxonomyService;
import com.kohler.commons.util.CommonUtil;

/**
 * APAC Implementation for Filter, inherits from <code>EssentialsFacetsComponent</code>
 * @author dhanwan.r
 * Date:05/05/2017
 * @version 1.0
 */
@ParametersInfo(type = ProductFacetsComponentInfo.class)
public class ProductFacetsComponent extends EssentialsFacetsComponent {

	private static Logger log = LoggerFactory.getLogger(ProductFacetsComponent.class);

	TaxonomyService taxonomyService = new TaxonomyService();

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	
	PriceRangeLabelService priceRangeLabelService = new PriceRangeLabelService();
	
	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		final HstRequestContext context = request.getRequestContext();
		String language = CommonUtil.getLanguage(context.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(context.getResolvedMount().getMount());
		Locale locale = new Locale(language, country); 
		ResourceBundle bundleApacLabel = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
		String overAllLength = resourceBundle.getPropertyValue(bundleApacLabel, Constants.OVERALL_LENGTH_SUFFIX);
		String overAllLengthMin = overAllLength + Constants.RANGE_FILTER_MIN_SUFFIX;
		String overAllLengthMax = overAllLength + Constants.RANGE_FILTER_MAX_SUFFIX;
		String overAllHeight = resourceBundle.getPropertyValue(bundleApacLabel, Constants.OVERALL_HEIGHT_SUFFIX);
		String overAllHeightMin = overAllHeight + Constants.RANGE_FILTER_MIN_SUFFIX;
		String overAllHeightMax = overAllHeight + Constants.RANGE_FILTER_MAX_SUFFIX;
		String overAllWidth = resourceBundle.getPropertyValue(bundleApacLabel, Constants.OVERALL_WIDTH_SUFFIX);
		String overAllWidthMin = overAllWidth + Constants.RANGE_FILTER_MIN_SUFFIX;
		String overAllWidthMax = overAllWidth + Constants.RANGE_FILTER_MAX_SUFFIX;
		String categoriesFilterLabel = resourceBundle.getPropertyValue(bundleApacLabel, Constants.CATEGORIES);
		String colorFilterLabel = resourceBundle.getPropertyValue(bundleApacLabel, Constants.COLOR);
		String countryFilterLabel = resourceBundle.getPropertyValue(bundleApacLabel, Constants.FT_COUNTRY);
		final ProductFacetsComponentInfo paramInfo = getComponentParametersInfo(request);
		String facetPath = paramInfo.getFacetPath();
		context.setAttribute(Constants.REQUEST_ATTR_PROMOBANNERFLAG, true);
		if (!Strings.isNullOrEmpty(facetPath)) {
			try{
				final HippoBean bean = context.getContentBean();
				if (bean != null) {
					facetPath = bean.getPath();
					String relFacetPath = PathUtils.normalizePath(Constants.PRODUCTS_FACETS_PATH);
					if (facetPath.contains(relFacetPath)) {
						facetPath = facetPath.substring(facetPath.indexOf(relFacetPath));
					}
				}
				String queryParam = cleanupSearchQuery(getAnyParameter(request, REQUEST_PARAM_QUERY));
				
				HippoFacetNavigationBean hippoFacetNavigationBean = null;
				
				Map<String, String> queryStringKeyValueMap=null;
				Boolean rangeFilterFlag = false;
				Map <String, Category> categoryBeanMap = new LinkedHashMap<>();
				HippoFolderBean categoryFolderBean = null;
				
				HippoBean rootBean = context.getSiteContentBaseBean();
				HippoBean productFolderBean = null;
				if (rootBean != null) {
					final String myPath = PathUtils.normalizePath(Constants.PRODUCTS_PATH);
					productFolderBean = rootBean.getBean(myPath);
					if (productFolderBean == null) {
						productFolderBean = rootBean;
					}
				}
				HstQuery hstQuery = context.getQueryManager().createQuery(productFolderBean);
				if (locale.getCountry().equals(Constants.COUNTRY_CODE_APAC) && locale.getLanguage().equals(Constants.LOCALE_EN)) {
					String reMarketCookieName = resourceBundle.getPropertyValue(bundleApacLabel, Constants.COOKIE_NAME_RE_MARKET);
					Cookie[] cookies = request.getCookies();
					String storedMarket = this.getCookieByName(reMarketCookieName, cookies);
					if (!Strings.isNullOrEmpty(storedMarket) && facetPath.contains(storedMarket)) {
						HippoFacetNavigationBean hippoFacetNavigationBeanValidate = this.getFacetNavigationBean(facetPath, hstQuery);
						if (hippoFacetNavigationBeanValidate == null) {
							facetPath = facetPath.replace(storedMarket, "");
							request.setAttribute("resetMarket", "true");
						}
					} else {
						HippoFacetNavigationBean hippoFacetNavigationBeanValidate = this.getFacetNavigationBean(facetPath + storedMarket, hstQuery);
						if (hippoFacetNavigationBeanValidate == null) {
							request.setAttribute("resetMarket", "true");
						} else {
							facetPath = facetPath + storedMarket;
						} 
					}
					
				}
				Filter productFilter = hstQuery.createFilter();
				
				/*Range Filter - Start*/
				Filter rangeFilter = hstQuery.createFilter();
				if (Strings.isNullOrEmpty(request.getQueryString())) {
					hstQuery.addOrderByDescending(Constants.NS_IS_NEW);
					hstQuery.addOrderByDescending(Constants.NS_STARPRODUCT);
					String fieldNameAttribute = Constants.NS_LIST_PRICE;
					hstQuery.addOrderByDescending(fieldNameAttribute);
				}else{	
					queryStringKeyValueMap = CommonUtil.generateQueryStringMap(request.getQueryString());
					this.setOrder(queryStringKeyValueMap, hstQuery); 
					
					if (queryStringKeyValueMap.containsKey(overAllLengthMin)
							&& queryStringKeyValueMap.containsKey(overAllLengthMax)
							&& this.setRangeFilter(queryStringKeyValueMap, rangeFilter, Constants.NS_OVERALL_LENGTH, overAllLengthMin, overAllLengthMax)) {
						rangeFilterFlag = true;
					}

					if (queryStringKeyValueMap.containsKey(overAllHeightMin)
							&& queryStringKeyValueMap.containsKey(overAllHeightMax) 
							&& this.setRangeFilter(queryStringKeyValueMap, rangeFilter, Constants.NS_OVERALL_HEIGHT, overAllHeightMin, overAllHeightMax)) {
						rangeFilterFlag = true;
					}

					if (queryStringKeyValueMap.containsKey(overAllWidthMin)
							&& queryStringKeyValueMap.containsKey(overAllWidthMax) 
							&& this.setRangeFilter(queryStringKeyValueMap, rangeFilter, Constants.NS_OVERALL_WIDTH, overAllWidthMin, overAllWidthMax)) {
							rangeFilterFlag = true;
					}
				}	
				queryParam = (Strings.isNullOrEmpty(queryParam)) ?  "" : queryParam;
				this.defineSliderMinMax(request, context, facetPath, queryParam);
				
				Filter discontinuedFilter = hstQuery.createFilter();
				String fieldRestriction = Constants.NS_DISCONTINUED;
				discontinuedFilter.addNotEqualTo(fieldRestriction, true);
				if(rangeFilterFlag){
					productFilter.addAndFilter(rangeFilter);
					context.setAttribute(Constants.REQUEST_ATTR_PROMOBANNERFLAG, false);
				}	
				productFilter.addAndFilter(discontinuedFilter);
				hstQuery.setFilter(productFilter);
				hippoFacetNavigationBean = this.getFacetNavigationBean(facetPath, hstQuery);
				Boolean defaultFacetFlag = true;
				//Applying filters and no products available  
				if(hippoFacetNavigationBean == null && rangeFilterFlag){
					HstQuery hstQuery2 = context.getQueryManager()
							.createQuery(productFolderBean);
					Filter discontinuedFilterQuery2 = hstQuery2.createFilter();
					discontinuedFilterQuery2.addNotEqualTo(fieldRestriction, true);
					hstQuery2.setFilter(discontinuedFilterQuery2);
					hippoFacetNavigationBean = this.getFacetNavigationBean(facetPath, hstQuery2);
					defaultFacetFlag = false;
				}
				
				if(hippoFacetNavigationBean != null) { 
					hippoFacetNavigationBean = addExtendedProperties(hippoFacetNavigationBean);
					HippoResultSetBean hippoResultSetBean=hippoFacetNavigationBean.getResultSet();
					if(hippoResultSetBean!=null) {
					Long totalCount = hippoResultSetBean.getCount();
					Boolean hideFilters = true;
					List<String> swatchList = new ArrayList <>();
						List<HippoFolderBean> lstItr = hippoFacetNavigationBean.getFolders();
						String folderName = null;
						HippoFolderBean categoryLeafNode = null;
						Map<String, HippoFolderBean> hippoFolderBeanMap = new LinkedHashMap <>();
						Set<String> filterFromTaxonomy = new HashSet<String>();
						for (HippoFolderBean folderBean : lstItr) {
							if (folderBean.getName().equalsIgnoreCase(categoriesFilterLabel)) {
								categoryFolderBean = folderBean;
								Iterator<HippoFolderBean> subFolderItr = folderBean.getFolders().iterator();
								while (subFolderItr.hasNext()) {
									categoryLeafNode = subFolderItr.next();
									if (categoryLeafNode.isLeaf()) {
										folderName = categoryLeafNode.getName();
										break;
									}
									if(!Strings.isNullOrEmpty(categoryLeafNode.getName())){
										Category category = taxonomyService.getCategoryFromKey(Constants.TAXONOMY_NODE_NAME, categoryLeafNode.getName());
			     			            if(category != null){
			     			            	categoryBeanMap.put(category.getKey(), category);
			     			            }else{
			     			            	if (log.isErrorEnabled()) {
			     			            		log.error(String.format("No Category Found  with name : ''%s", categoryLeafNode.getName()));
			     			            	}
			     			            }
									}
								}
								if(!Strings.isNullOrEmpty(folderName)){
		     			            Category category = taxonomyService.getCategoryFromKey(Constants.TAXONOMY_NODE_NAME, folderName);
		     			            if(category != null){
		     			            	categoryBeanMap.put(category.getKey(), category);
		     			            	taxonomyService.addFilterOrder(category, hippoFolderBeanMap);
		     			            	filterFromTaxonomy = new HashSet<String>(hippoFolderBeanMap.keySet());
		     			            }else{
		     			            	if (log.isErrorEnabled()) {
		     			            		log.error(String.format("No Category Found  with name : ''%s", categoryLeafNode.getName()));
		     			            	}
		     			            }
	     		            	}
							} else if (folderBean.getName().equalsIgnoreCase(colorFilterLabel)) {
								Iterator<HippoFolderBean> subFolderItr = folderBean.getFolders().iterator();
								while (subFolderItr.hasNext()) {
									HippoFolderBean subFolder = subFolderItr.next();
									swatchList.add(subFolder.getName());
									hippoFolderBeanMap.put(folderBean.getName(), folderBean);
									if (subFolder.isLeaf()) {
										context.setAttribute(Constants.CURRENT_SWATCH, subFolder.getName());
										if (hideFilters) {
											hideFilters = false;
										}
									}
									if (hideFilters && folderBean.getFolders().size() > 1) {
										hideFilters = false;
									}
								}
							} else if (folderBean.getName().equalsIgnoreCase(countryFilterLabel)) {
								request.setAttribute(Constants.REQUEST_ATTR_MARKET_LIST, folderBean);
								hippoFolderBeanMap.put(folderBean.getName(), folderBean);
								Iterator<HippoFolderBean> subFolderItr = folderBean.getFolders().iterator();
								while (subFolderItr.hasNext()) {
									HippoFolderBean subFolder = subFolderItr.next();
									if (subFolder.isLeaf()) {
										request.setAttribute(Constants.REQUEST_ATTR_SELECTED_COUNTRY, subFolder);
										if (hideFilters) {
											hideFilters = false;
										}
									}
								}
								if (hideFilters && folderBean.getFolders().size() > 1) {
									hideFilters = false;
								}
							}
							else {
								hippoFolderBeanMap.put(folderBean.getName(), folderBean);
								Iterator<HippoFolderBean> subFolderItr = folderBean.getFolders().iterator();
								while (subFolderItr.hasNext()) {
									HippoFolderBean subFolder = subFolderItr.next();
									if (hideFilters && subFolder.isLeaf()) {
										hideFilters = false;
									}
									if(!Strings.isNullOrEmpty(folderName) && subFolder.isLeaf()){
										priceRangeLabelService.definePriceRangeLabel(request, locale, folderName, subFolder.getName());
									}
								}
								if (hideFilters && folderBean.getFolders().size() > 1) {
									hideFilters = false;
								}
							}
						}
						if (!filterFromTaxonomy.isEmpty()) {
							this.setShowMaxFilter(filterFromTaxonomy, request, hippoFolderBeanMap);
						}
						request.setAttribute(Constants.REQUEST_ATTR_HIDE_ALL_FILTERS, hideFilters);
						
						//For Result Set//
						final HippoResultSetBean resultSet = hippoFacetNavigationBean.getResultSet();
						if (resultSet != null && defaultFacetFlag) {
							context.setAttribute(Constants.REQUEST_ATTR_RESULT_SET, resultSet);
						}	
						//END Result Set//
						
						
						if (!swatchList.isEmpty()) {
							request.setAttribute("swatchMap", getSwatchColorName(swatchList, language, country));
						}
						request.setAttribute("NavBeanMap", hippoFolderBeanMap);
						request.setAttribute(Constants.REQUEST_ATTR_TOTALRESULTS, totalCount);
						if(!Strings.isNullOrEmpty(folderName)){
							Category category = taxonomyService.getCategoryFromKey(Constants.TAXONOMY_NODE_NAME, folderName);
							getseoSupport(request, rootBean, category.getKey(), locale);
							request.setAttribute(Constants.REQUEST_ATTR_CURRENTCATEGORY, category);
							context.setAttribute(Constants.REQUEST_ATTR_CURRENTCATEGORY, category);
							request.setAttribute(Constants.REQUEST_ATTR_PARENTCATEGORY, category.getParent());
							context.setAttribute(Constants.REQUEST_ATTR_PARENTCATEGORY, category.getParent());
							request.setAttribute("leafNode", categoryLeafNode);
						}else{
							request.setAttribute(Constants.REQUEST_ATTR_CATEGORYBEANMAP, categoryBeanMap);
							request.setAttribute(Constants.REQUEST_ATTR_CATEGORYFOLDERBEAN,categoryFolderBean);
						}
				}	
			  }		
			}catch(Exception e)
			{
				log.error(e.getMessage(), e);
				CommonUtil.pageNotFound(response, Constants.PAGE_NOT_FOUND_URL);
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Boolean getseoSupport(HstRequest request, HippoBean rootBean, String categoryKey, Locale locale) {
		HstQuery query;
		try {
			ResourceBundle bundleSeoLabel = resourceBundle.getBundle(Constants.SEO_LABLES, locale);
			String categoryRelativeBasePath = resourceBundle.getPropertyValue(bundleSeoLabel, Constants.CATEGORY_RELATIVE_BASE_PATH);
			HippoBean categoryBaseBean = null;
			categoryBaseBean = rootBean.getBean(categoryRelativeBasePath);
			if (categoryBaseBean == null) {
				categoryBaseBean = rootBean;
			}
			query = request.getRequestContext().getQueryManager().createQuery(categoryBaseBean, SeoCategory.class);
			Filter f1 = query.createFilter();
			f1.addEqualTo(Constants.NS_CATEGORY_KEY, categoryKey);
			query.setFilter(f1);
			HstQueryResult queryResult = query.execute();
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			while(iterator.hasNext()){
				SeoCategory seoCategory = (SeoCategory) iterator.next();
				request.setAttribute(Constants.REQUEST_SEO_CATEGORY, seoCategory);
				break;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return true;
	}
	
	/**
	 * Gets Swatch color name
	 * @param swatchList
	 * @return map 
	 */

	private Map<String, String> getSwatchColorName(List<String> swatchList, String language,String country) {
		Map<String, String> swatchMap = new HashMap<>();
		SwatchColorNameFinderService swatchColorNameFinderService = HstServices.getComponentManager().getComponent(Constants.SWATCH_COLOR_NAME_FINDER_SERVICE + language + country);
		Map<String, String> map = swatchColorNameFinderService.getSwatchColorNameMap();
		for(String swatchItem : swatchList){
			String colorName = map.get(swatchItem);
			if(!Strings.isNullOrEmpty(colorName)){
				swatchMap.put(swatchItem, colorName);
			}
		}
		return swatchMap;
	}

	/**
	 * Used for encoding string
	 * @param facets
	 * @param hstRequestContext
	 * @return facets 
	 * @throws Exception
	 */
	private HippoFacetNavigationBean addExtendedProperties(HippoFacetNavigationBean facets) {
		String encodedFacetValue;
		try {
			if (facets != null){
				for (HippoFolderBean facet : facets.getFolders()) {
					if (!facet.getFolders().isEmpty()) {
						for (HippoFolderBean item : facet.getFolders()) {
							encodedFacetValue = encodePart(item.getDisplayName());
							item.getProperties().put(Constants.ENCODE_NAME, encodedFacetValue);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return facets;
	}

	/**
	 * Gets Encoded filters name
	 * @param part
	 * @return  
	 * @throws UnsupportedEncodingException
	 */
	public static String encodePart(String part) {
		String name = NodeNameCodec.decode(part);
		try {
			return name.indexOf(47) > 0 ? URLEncoder.encode(name.replaceAll("\\/", "_x002f_"), Constants.UTF_8)
					: URLEncoder.encode(name, Constants.UTF_8);
		} catch (UnsupportedEncodingException var3) {
			log.error("Missing utf-8 codec?", var3);
			return "";
		}
	}

	protected void defineSliderMinMax(HstRequest request, HstRequestContext context, String facetPath, String queryParam){
		HippoFacetNavigationBean hippoFacetNavigationBean = super.getFacetNavigationBean(context, facetPath,
				queryParam);
		if (hippoFacetNavigationBean != null) {
			List<HippoFolderBean> lstItr = hippoFacetNavigationBean.getFolders();
			for (HippoFolderBean folderBean : lstItr) {
				if ( (folderBean.getName().equalsIgnoreCase(Constants.LENGTH_FACET_NAME)
						|| folderBean.getName().equalsIgnoreCase(Constants.HEIGHT_FACET_NAME)
						|| folderBean.getName().equalsIgnoreCase(Constants.WIDTH_FACET_NAME)) && !folderBean.getFolders().isEmpty() ) {
						List<HippoFolderBean> subFolder = folderBean.getFolders();
						if (subFolder.get(0).getDisplayName().equalsIgnoreCase("0") && (subFolder.size() > 1)) {
							request.setAttribute(folderBean.getName() + "InitMin", subFolder.get(1).getDisplayName());
						} else {
							request.setAttribute(folderBean.getName() + "InitMin", subFolder.get(0).getDisplayName());
						}
						if (!folderBean.getFolders().isEmpty()) {
							request.setAttribute(folderBean.getName() + "InitMax",
								subFolder.get(folderBean.getFolders().size() - 1).getDisplayName());
						}		
				}
			}
		}
	}
	
	protected Boolean setRangeFilter (Map<String, String> queryStringKeyValueMap, Filter rangeFilter, String betweenString, String stringMin, String stringMax) {
		try {
			rangeFilter.addBetween(betweenString,
					Long.parseLong(queryStringKeyValueMap.get(stringMin)),
					Long.parseLong(queryStringKeyValueMap.get(stringMax)));
			return true;
		} catch (Exception ex) {
			log.error(String.format("'%s' Parsing: '%s'" , betweenString, ex.getMessage()));
			return false;
		} 
	}
	
	protected void setOrder (Map<String, String> queryStringKeyValueMap, HstQuery hstQuery ) {
		if (queryStringKeyValueMap.containsKey(Constants.SORT_FIELD)
				&& queryStringKeyValueMap.containsKey(Constants.SORT_DIRECTION)) {
							if (queryStringKeyValueMap.get(Constants.SORT_FIELD).equals(Constants.NAME)) {
								String fieldNameAttribute = Constants.NS_DESCRIPTION_PRODUCT;
								if (queryStringKeyValueMap.get(Constants.SORT_DIRECTION)
										.equals(Constants.SORT_DIRECTION_ASC)) {
									hstQuery.addOrderByAscending(fieldNameAttribute);
								} else if (queryStringKeyValueMap.get(Constants.SORT_DIRECTION)
										.equals(Constants.SORT_DIRECTION_DESC)) {
									hstQuery.addOrderByDescending(fieldNameAttribute);
								}
							} else {
								String fieldNameAttribute = Constants.NS_LIST_PRICE;
								hstQuery.addOrderByDescending(Constants.NS_IS_NEW);
								hstQuery.addOrderByDescending(Constants.NS_STARPRODUCT);
								hstQuery.addOrderByDescending(fieldNameAttribute);
							}
		}else
		{
			String fieldNameAttribute = Constants.NS_LIST_PRICE;
			hstQuery.addOrderByDescending(Constants.NS_IS_NEW);
			hstQuery.addOrderByDescending(Constants.NS_STARPRODUCT);
			hstQuery.addOrderByDescending(fieldNameAttribute);
		}
	}
	
	protected void setMarketAvailableList (HstRequest request, HstQuery hstQuery, String rootFacetPath, String countryFilterLabel) {
		String queryAsString;
		try {
			queryAsString = "xpath("+hstQuery.getQueryAsString(true)+")";
			HippoFacetNavigationBean hippoFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(rootFacetPath, queryAsString);
			if (hippoFacetNavigationBean != null) {
				List<HippoFolderBean> lstItr = hippoFacetNavigationBean.getFolders();
				for (HippoFolderBean folderBean : lstItr) {
					if (folderBean.getName().equalsIgnoreCase(countryFilterLabel)) {
						request.setAttribute(Constants.REQUEST_ATTR_MARKET_LIST, folderBean);
					}
				}
			}
		} catch (QueryException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	protected String getCookieByName (String cookieName, Cookie[] cookies) {
		String savedLocale = "";
		if (cookies != null) {
			cookieName = cookieName.trim();
			for (int index = 0; index < cookies.length; index++) {
				if (cookieName.equalsIgnoreCase(cookies[index].getName().trim())) {
					savedLocale = cookies[index].getValue();
				}
			}
		}
		return savedLocale;
	}
	
	protected HippoFacetNavigationBean getFacetNavigationBean (String facetPath, HstQuery hstQuery){
		HippoFacetNavigationBean hippoFacetNavigationBean = null;
		try {
			String queryAsString = "xpath("+hstQuery.getQueryAsString(true)+")";
			hippoFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(facetPath, queryAsString);
		} catch (QueryException e) {
			log.error(e.getMessage(), e);
		}
		return hippoFacetNavigationBean;
	}
	
	private void setShowMaxFilter (Set<String> filterFromTaxonomy,HstRequest request, Map<String, HippoFolderBean> hippoFolderBeanMap) {
		Integer size = filterFromTaxonomy.size();
		for(String filter: filterFromTaxonomy) {
			if (hippoFolderBeanMap.get(filter) == null) {
				size--;
			} else if (filter.equalsIgnoreCase(Constants.FTR_ISNEW)) {
				HippoFolderBean hippoFolderBean= hippoFolderBeanMap.get(filter);
				Boolean flag = true;
				for (HippoFolderBean child: hippoFolderBean.getFolders()) {
					if (child.getName().equalsIgnoreCase(Constants.FTR_ISNEW_NODE_TRUE)) {
						flag = false;
					}
				}
				if (flag) {
					size--;	
				}
			} else {
				HippoFolderBean hippoFolderBean= hippoFolderBeanMap.get(filter);
				if (hippoFolderBean.getFolders().size() <= 1) {
					if (hippoFolderBean.getFolders().isEmpty()) {
						size--;	
					} else if (!hippoFolderBean.getFolders().get(0).isLeaf()) {
						size--;
					}
				}
			}
		}
		request.setAttribute(Constants.REQUEST_ATTR_SHOW_MAX_FILTERS, size);
	}
}
