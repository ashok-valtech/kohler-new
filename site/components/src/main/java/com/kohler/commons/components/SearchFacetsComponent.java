/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoDocumentBean;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.site.HstServices;
import org.hippoecm.hst.util.ContentBeanUtils;
import org.hippoecm.hst.util.SearchInputParsingUtils;
import org.hippoecm.repository.api.NodeNameCodec;
import org.onehippo.cms7.essentials.components.EssentialsFacetsComponent;
import org.onehippo.taxonomy.api.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;
import com.kohler.beans.ArticleBannerDocument;
import com.kohler.beans.CSKU;
import com.kohler.beans.CSynonym;
import com.kohler.beans.FaqItem;
import com.kohler.beans.Pressreleases;
import com.kohler.beans.Product;
import com.kohler.beans.Synonym;
import com.kohler.commons.components.componentsInfo.SearchFacetsComponentInfo;
import com.kohler.commons.components.search.SuggestionService;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.ProductDetailService;
import com.kohler.commons.service.SwatchColorNameFinderService;
import com.kohler.commons.taxonomies.TaxonomyService;
import com.kohler.commons.util.CommonUtil;

/**
 * APAC Implementation for Searching facets, inherits from <code>EssentialsFacetsComponent</code>
 * @author dhanwan.r
 * Date:19/06/2017
 * @version 1.0
 */
@ParametersInfo(type = SearchFacetsComponentInfo.class)
public class SearchFacetsComponent extends EssentialsFacetsComponent{
	
	private static Logger log = LoggerFactory.getLogger(SearchFacetsComponent.class);
	
	TaxonomyService taxonomyService = new TaxonomyService();
	
	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();

	private ProductDetailService productService = new ProductDetailService();
	

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
		String suggestionService = resourceBundle.getPropertyValue(bundleApacLabel, Constants.SUGGESTION_SERVICE);
		String searchType = null;
		String searchTerm = "";
		Long productCount = Long.valueOf(0);
		Long specProductcount = Long.valueOf(0);
		Long totalResultsSize = Long.valueOf(0);
		Long totalArticleVideoSize=Long.valueOf(0);
		Long totalHelpSize=Long.valueOf(0);
		Boolean hideFilters = true;
		Map<String, String> queryStringKeyValueMap=null;
		if (!Strings.isNullOrEmpty(request.getQueryString())) {
			queryStringKeyValueMap = CommonUtil.generateQueryStringMap(request.getQueryString());
			if(queryStringKeyValueMap.containsKey(Constants.TYPE)){
				searchType = queryStringKeyValueMap.get(Constants.TYPE);
			}
			if(queryStringKeyValueMap.containsKey(Constants.QUERY_SEARCH_TERM)){
			    searchTerm = request.getRequestContext().getServletRequest().getParameter(Constants.QUERY_SEARCH_TERM);
			}
			try{
				if(Strings.isNullOrEmpty(searchTerm)){
					searchTerm="";
				}
				if(Strings.isNullOrEmpty(searchType)){
					searchType = Constants.PRODUCT;
				}
				final SearchFacetsComponentInfo paramInfo = getComponentParametersInfo(request);
				String regex = paramInfo.getIgnoreSpecialCharacters();
				searchTerm=searchTerm.replaceAll(regex, " ").trim();
				if(searchTerm.toLowerCase().startsWith("k-")) {
					searchTerm=searchTerm.substring(2);
				}
				String searchTermQuery = searchTerm;
				 List<String> synonmys=null;
				try{
			    synonmys =getSynonmys(searchTermQuery,request);
				}catch(Exception e)
				{
					System.out.println("Exception occurs in searchtermQuery2");
					
				}
				String productURL = "";
				Boolean isMultiCategory = false;
				if(searchType.equals(Constants.PRODUCT)){
					Product productBean = productService.getProductByItemNoOrSkuId(request, searchTermQuery);				        
					if(productBean != null){
						StringBuilder productURLBuilder = new StringBuilder ();
						if (productBean.getKeys().length > 1) {
							isMultiCategory = true;
						}
						if(context.getResolvedMount().getMount().getParent() != null){
							productURLBuilder.append("/" + context.getResolvedMount().getMount().getName());
						}
						productURLBuilder.append (ProductDetailService.SITEMAP + productBean.getItemNo());
						for(CSKU sku: productBean.getSkus()){
							if(searchTermQuery.equalsIgnoreCase(sku.getSku())){
								productURLBuilder.append ("?skuid=" + sku.getSku()) ;
								productURL = productURLBuilder.toString();
								break;
							}
						}
					}
				}

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
				context.setAttribute(Constants.QUERY_SEARCH_TERM, searchTerm);
				if(searchTermQuery.trim().isEmpty()){
					searchTerm = request.getRequestContext().getServletRequest().getParameter(Constants.QUERY_SEARCH_TERM);
					request.setAttribute(Constants.QUERY_SEARCH_TERM, searchTerm);
					context.setAttribute(Constants.QUERY_SEARCH_TERM, searchTerm);
					request.setAttribute(Constants.REQUEST_ATTR_TOTALRESULTS, totalResultsSize);
					return;
				}
				Map <String, Category> categoryBeanMap = new LinkedHashMap <>();
				HippoFolderBean categoryFolderBean = null;
				HippoFolderBean categoryLeafNode = null;
				Map<String, HippoFolderBean> hippoFolderBeanMap = new LinkedHashMap<>();
				HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();
				@SuppressWarnings("unchecked")
				HstQuery productHstQuery = request.getRequestContext().getQueryManager().createQuery(rootBean, Product.class);
				@SuppressWarnings("unchecked")
				HstQuery productHstQuery2 = request.getRequestContext().getQueryManager().createQuery(rootBean, Product.class);

				setQueryOrder (queryStringKeyValueMap, productHstQuery);
				setQueryOrder (queryStringKeyValueMap, productHstQuery2);

				Filter productFilter = productHstQuery.createFilter();
				if (!Strings.isNullOrEmpty(searchTermQuery)){
					String parsedSearchTermQuery = SearchInputParsingUtils.parse(searchTermQuery, true);
					if (!StringUtils.isEmpty(parsedSearchTermQuery)) {
						Filter filter = productHstQuery.createFilter();
						Filter f = productHstQuery.createFilter();
						f.addContains(".", parsedSearchTermQuery);	
						filter.addOrFilter(f);
						
						if (synonmys!=null && !synonmys.isEmpty()){
						for(String searchTermQuery2: synonmys){
						String parsedSearchTermQuery1 = SearchInputParsingUtils.parse(searchTermQuery2, true);
						Filter f1 = productHstQuery.createFilter();
						f1.addContains(".", parsedSearchTermQuery1);	
						filter.addOrFilter(f1);
						}
						}	
						
						Filter f2 = productHstQuery.createFilter();
						f2.addLike(Constants.NS_ITEM_NO, parsedSearchTermQuery + "%");
						filter.addOrFilter(f2);
						productFilter.addAndFilter(filter);
					}
				}
				
		/*		if (!Strings.isNullOrEmpty(searchTermQuery2)){
					String parsedSearchTermQuery1 = SearchInputParsingUtils.parse(searchTermQuery2, true);
					if (!StringUtils.isEmpty(parsedSearchTermQuery1)) {
						Filter filter = productHstQuery.createFilter();
						Filter f = productHstQuery.createFilter();
						f.addContains(".", parsedSearchTermQuery1);	
						filter.addOrFilter(f);
						Filter f2 = productHstQuery.createFilter();
						f2.addLike(Constants.NS_ITEM_NO, parsedSearchTermQuery1 + "%");
						filter.addOrFilter(f2);
						productFilter.addAndFilter(filter);
					}
				}*/
				
			
				Filter discontinuedFilter = productHstQuery.createFilter();
				String fieldRestriction = Constants.NS_DISCONTINUED;
				discontinuedFilter.addNotEqualTo(fieldRestriction, true);
				productFilter.addAndFilter(discontinuedFilter);

				HippoFacetNavigationBean hippoFacetNavigationBeanWithoutFilter = this.defineSliderMinMax(request, productHstQuery2, productFilter);
				Filter rangeFilter = productHstQuery.createFilter();
				Boolean rangeFilterFlag = false;

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

				if(rangeFilterFlag){
					productFilter.addAndFilter(rangeFilter);
				}  
				productHstQuery.setFilter(productFilter);
				Boolean defaultFacetFlag = true;
				HippoFacetNavigationBean hippoFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(productHstQuery);
				//Applying filters and no products available  
				if(hippoFacetNavigationBean == null){
					hippoFacetNavigationBean = hippoFacetNavigationBeanWithoutFilter;
					defaultFacetFlag = false;
				}else{
					HippoResultSetBean resultSet = hippoFacetNavigationBean.getResultSet();
					if(resultSet.getCount() <= 0 && rangeFilterFlag){
						hippoFacetNavigationBean = hippoFacetNavigationBeanWithoutFilter;
					}
				}
				hippoFacetNavigationBean=addExtendedProperties(hippoFacetNavigationBean);
				List<String> swatchList = new ArrayList<> ();
				if(hippoFacetNavigationBean != null){
					HippoResultSetBean resultSet = hippoFacetNavigationBean.getResultSet();
					if (resultSet != null) {
						totalResultsSize = resultSet.getCount();
						productCount = resultSet.getCount();
					}	
					if (searchType.equals(Constants.PRODUCT)){
						if (resultSet != null && defaultFacetFlag) {
							context.setAttribute(Constants.REQUEST_ATTR_RESULT_SET, resultSet);
						}
						if(productCount == 1 && Strings.isNullOrEmpty(productURL)){
							HippoDocumentBean documentBean = resultSet.getDocuments().get(0);
							Product productBean = (Product) documentBean;
							if(context.getResolvedMount().getMount().getParent() != null){
								productURL = "/" + context.getResolvedMount().getMount().getName();
							}
							productURL = productURL + ProductDetailService.SITEMAP + productBean.getItemNo();
							if (productBean.getKeys().length > 1) {
								isMultiCategory = true;
							}
						}
						List<HippoFolderBean> lstItr = hippoFacetNavigationBean.getFolders();
						String folderName = null;
						for (HippoFolderBean folderBean: lstItr) {
							if(folderBean.getName().equalsIgnoreCase(categoriesFilterLabel)){
								Iterator<HippoFolderBean> subFolderItr = folderBean.getFolders().iterator();
								while(subFolderItr.hasNext()){
									HippoFolderBean subFolder = subFolderItr.next();
									if(subFolder.isLeaf()){
										categoryLeafNode = subFolder;
										folderName = categoryLeafNode.getName();
										break;
									}
									if(!Strings.isNullOrEmpty(subFolder.getName())){
										Category category = taxonomyService.getCategoryFromKey(Constants.TAXONOMY_NODE_NAME, subFolder.getName());
										if(category != null){
											categoryBeanMap.put(category.getKey(), category);
										}
										else
										{
											log.error(String.format("Getting Category NUll for folderName '%s'", subFolder.getName()));   
										}
									}
								}
								if(Strings.isNullOrEmpty(folderName)){
									categoryFolderBean = folderBean;
								}
							}else if(folderBean.getName().equalsIgnoreCase(colorFilterLabel)){
								Iterator<HippoFolderBean> subFolderItr = folderBean.getFolders().iterator();
								while(subFolderItr.hasNext()){
									HippoFolderBean subFolder = subFolderItr.next();
									swatchList.add(subFolder.getName());
									hippoFolderBeanMap.put(folderBean.getName(), folderBean);
									if(subFolder.isLeaf()){
										context.setAttribute(Constants.CURRENT_SWATCH,  subFolder.getName());
										if (hideFilters) {
											hideFilters = false;
										}
									}
									if (hideFilters && folderBean.getFolders().size() > 1) {
										hideFilters = false;
									}
								}
							}else{
								hippoFolderBeanMap.put(folderBean.getName(),folderBean);
								Iterator<HippoFolderBean> subFolderItr = folderBean.getFolders().iterator();
								while (subFolderItr.hasNext()) {
									HippoFolderBean subFolder = subFolderItr.next();
									if (hideFilters && subFolder.isLeaf()) {
										hideFilters = false;
									}
								}
								if (hideFilters && folderBean.getFolders().size() > 1) {
									hideFilters = false;
								}
							}
						}
						request.setAttribute(Constants.REQUEST_ATTR_HIDE_ALL_FILTERS, hideFilters);

						if (!swatchList.isEmpty ()) {
							request.setAttribute (Constants.REQUEST_ATTR_SWATCHMAP, getSwatchColorName(swatchList, language, country));
						}
						String defaultCategoryURL = "";
						if(!Strings.isNullOrEmpty(folderName)){
							Category category = taxonomyService.getCategoryFromKey(Constants.TAXONOMY_NODE_NAME, folderName);
							request.setAttribute(Constants.REQUEST_ATTR_CURRENTCATEGORY, category);
							context.setAttribute(Constants.REQUEST_ATTR_CURRENTCATEGORY, category);
							if (isMultiCategory) {
								defaultCategoryURL = "defaultCategory=" + folderName ;
							}
						}
						if (!Strings.isNullOrEmpty(productURL)) {
							this.sendProductDetails (productURL, defaultCategoryURL, response); 
							return;
						}
						request.setAttribute(Constants.REQUEST_ATTR_LEAFNODE, categoryLeafNode);
						request.setAttribute(Constants.REQUEST_ATTR_NAVBEANMAP,hippoFolderBeanMap);
						request.setAttribute(Constants.REQUEST_ATTR_CATEGORYFOLDERBEAN,categoryFolderBean);
					}    
				}
				//For Spec
				@SuppressWarnings("unchecked")
				HstQuery hstQuery = request.getRequestContext().getQueryManager().createQuery(rootBean, Product.class);
				Filter specFilter = hstQuery.createFilter();
				if (!Strings.isNullOrEmpty(searchTermQuery)){
					String parsedSearchTermQuery = SearchInputParsingUtils.parse(searchTermQuery, true);
					if (!StringUtils.isEmpty(parsedSearchTermQuery)) {
						Filter orFilter = hstQuery.createFilter();
						Filter f = hstQuery.createFilter();
						f.addContains(".", parsedSearchTermQuery);		
						orFilter.addOrFilter(f);

						if (synonmys!=null && !synonmys.isEmpty()){
							for(String searchTermQuery2: synonmys){
						if (!Strings.isNullOrEmpty(searchTermQuery2)){
							String parsedSearchTermQuery1 = SearchInputParsingUtils.parse(searchTermQuery2, true);
							Filter f1 = hstQuery.createFilter();
							f1.addContains(".", parsedSearchTermQuery1);	
							orFilter.addOrFilter(f1);
							}	
						}
					 }	
						Filter f2 = hstQuery.createFilter();
						f2.addLike(Constants.NS_ITEM_NO, parsedSearchTermQuery + "%");
						orFilter.addOrFilter(f2);

						specFilter.addAndFilter(orFilter);
					}
					List<String> attrs = CommonUtil.getSpecAttributeList();
					Filter orFilters = hstQuery.createFilter();
					for (String attr : attrs)
					{
						Filter subquery = hstQuery.createFilter();
						subquery.addEqualTo(String.format("%s:attributes/%s:key", Constants.NAMESPACE, Constants.NAMESPACE), attr);
						orFilters.addOrFilter(subquery);
					}
					specFilter.addAndFilter(orFilters);
					if(rangeFilterFlag){
						specFilter.addAndFilter(rangeFilter);
					} 
				}
				hstQuery.setFilter(specFilter);
				setQueryOrder (queryStringKeyValueMap, hstQuery);
				final HippoFacetNavigationBean hippoSpecFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(hstQuery);
				if(hippoSpecFacetNavigationBean != null){
					final HippoResultSetBean resultSet = hippoSpecFacetNavigationBean.getResultSet();
					if (resultSet != null) {
						specProductcount = resultSet.getCount();
						if ( ((productCount == 0) &&  searchType.equals(Constants.PRODUCT)) || searchType.equals(Constants.SPEC)) {
							searchType = Constants.SPEC;
							context.setAttribute(Constants.REQUEST_ATTR_RESULT_SET, resultSet);
						}
					}
					totalResultsSize = totalResultsSize + specProductcount;
					if(searchType.equals(Constants.SPEC)){
						List<HippoFolderBean> lstItr = hippoSpecFacetNavigationBean.getFolders();
						String folderName = null;
						for (HippoFolderBean folderBean: lstItr) {
							if(folderBean.getName().equalsIgnoreCase(Constants.CATEGORIES)){
								Iterator<HippoFolderBean> subFolderItr = folderBean.getFolders().iterator();
								while(subFolderItr.hasNext()){
									HippoFolderBean subFolder = subFolderItr.next();
									if(subFolder.isLeaf()){
										categoryLeafNode = subFolder;
										folderName = categoryLeafNode.getName();
										break;
									}
									if(!Strings.isNullOrEmpty(subFolder.getName())){
										Category category = taxonomyService.getCategoryFromKey(Constants.TAXONOMY_NODE_NAME, subFolder.getName());
										if(category != null){
											categoryBeanMap.put(category.getKey(), category);
										}
									}
								}
								if(Strings.isNullOrEmpty(folderName)){
									categoryFolderBean = folderBean;
								}
							}
						}     
						if(!Strings.isNullOrEmpty(folderName)){
							Category category = taxonomyService.getCategoryFromKey(Constants.TAXONOMY_NODE_NAME, folderName);
							request.setAttribute(Constants.REQUEST_ATTR_CURRENTCATEGORY, category);
							context.setAttribute(Constants.REQUEST_ATTR_CURRENTCATEGORY, category);
						}
						request.setAttribute(Constants.REQUEST_ATTR_LEAFNODE, categoryLeafNode);
						request.setAttribute(Constants.REQUEST_ATTR_CATEGORYFOLDERBEAN,categoryFolderBean);
					}
				}

				//For Articles and Videos size
				totalArticleVideoSize=doArticlesVideoSearch(request, context, searchTermQuery,synonmys);

				//for Help
				totalHelpSize=doHelpSearch(searchType, request,context, searchTermQuery, FaqItem.class,synonmys);

				//spell checking if total results Zero
				totalResultsSize=totalResultsSize+totalArticleVideoSize+totalHelpSize;
				if(totalResultsSize == null || totalResultsSize <= 0){
					SuggestionService suggestionService1 = HstServices.getComponentManager().getComponent(suggestionService);
					if(suggestionService1 != null){
						List<String> spellcheckresults =  suggestionService1.getSpellCheck(searchTermQuery);
						if(spellcheckresults != null && !spellcheckresults.isEmpty()){
							request.setAttribute(Constants.REQUEST_ATTR_DIDYOUMEAN, spellcheckresults.get(0).replace("&", " "));
						}
					} 
				}
				request.setAttribute(Constants.REQUEST_ATTR_CATEGORYBEANMAP, categoryBeanMap);
				super.doBeforeRender(request, response);
			} catch(HstComponentException ex){
				log.error(ex.getMessage(), ex);
				CommonUtil.pageNotFound(response, Constants.PAGE_NOT_FOUND_URL);
			} catch(Exception ex) {
				log.error(ex.getMessage(), ex);
			}
			int currentPage = getCurrentPage(context);
			if(searchType.equals(Constants.PRODUCT)){
				setPaginationAttributes(request,currentPage, productCount);
			}else if(searchType.equals(Constants.SPEC)){
				setPaginationAttributes(request,currentPage, specProductcount);
			}
			request.setAttribute(Constants.REQUEST_ATTR_TOTALRESULTS, totalResultsSize);
			request.setAttribute(Constants.REQUEST_ATTR_TOTALPRODUCTCOUNT, productCount);
			request.setAttribute(Constants.REQUEST_ATTR_TOTALSPECCOUNT, specProductcount);
			request.setAttribute(Constants.REQUEST_ATTR_TOTALARTICLEVIDEOSIZE, totalArticleVideoSize);
			request.setAttribute(Constants.REQUEST_ATTR_TOTALHELPCOUNT, totalHelpSize);
			if(queryStringKeyValueMap!=null){
				request.setAttribute(Constants.SORT_DIRECTION, queryStringKeyValueMap.get(Constants.SORT_DIRECTION));
				request.setAttribute(Constants.SORT_FIELD, queryStringKeyValueMap.get(Constants.SORT_FIELD));
			}
			context.setAttribute(Constants.REQUEST_ATTR_TOTALRESULTS, totalResultsSize);
			context.setAttribute(Constants.REQUEST_ATTR_TOTALPRODUCTCOUNT, productCount);
			context.setAttribute(Constants.REQUEST_ATTR_TOTALSPECCOUNT, specProductcount);
			context.setAttribute(Constants.REQUEST_ATTR_TOTALARTICLEVIDEOSIZE, totalArticleVideoSize);
			context.setAttribute(Constants.REQUEST_ATTR_TOTALHELPCOUNT, totalHelpSize);
			
			//Code for default tab populated if product results is Zero.
			if((productCount==0 && searchType.equalsIgnoreCase(Constants.PRODUCT)) || searchType.equalsIgnoreCase(Constants.SPEC))
			{
				searchType=Constants.SPEC;
				if(specProductcount==0)
				{
					searchType=Constants.ARTICLE;
					if(totalArticleVideoSize==0)
					{
						searchType=Constants.HELP;
						if(totalHelpSize==0)
						{
							searchType=Constants.PRODUCT;	
						}
					}
				}
			} 
			if(searchType.equals(Constants.ARTICLE)){
				Long totalArticleCount = Long.valueOf(0);
				Object totalArticleCountObject = request.getAttribute(Constants.REQUEST_ATTR_TOTALARTICLECOUNT);
				if(totalArticleCountObject != null){
					totalArticleCount = (Long) totalArticleCountObject;
				}
				if(totalArticleCount.equals(Long.valueOf(0)))
				{
					Long totalVideoCount = Long.valueOf(0);
					Object totalVideoObject = request.getAttribute(Constants.REQUEST_ATTR_TOTALVIDEOCOUNT);
					if(totalVideoObject != null){
						totalVideoCount = (Long) totalVideoObject;
					}
					if(totalVideoCount.equals(Long.valueOf(0)))
					{
						Long totalPressReleasesCount = Long.valueOf(0);
						Object totalPressReleasesObject = request.getAttribute(Constants.REQUEST_ATTR_TOTALPRESSRELEASESCOUNT);
						if(totalPressReleasesObject != null){
							totalPressReleasesCount = (Long) totalPressReleasesObject;
						}
						if(!totalPressReleasesCount.equals(Long.valueOf(0)))
						{
							searchType=Constants.PRESSRELEASE;
						}
					}else {
						searchType=Constants.VIDEO;
					}	
				}
				else
				{
					searchType=Constants.ARTICLE;
				}
			}
			
			if (searchTerm.contains("&")) {
				request.setAttribute(Constants.QUERY_SEARCH_TERM, searchTerm.replaceAll("&", " ").trim());
			} else {
				request.setAttribute(Constants.QUERY_SEARCH_TERM, searchTerm);
			}
			
			request.setAttribute(Constants.TYPE, searchType);
			context.setAttribute(Constants.TYPE, searchType);
			request.setAttribute(Constants.REQUEST_ATTR_TOTALCURRENTPAGE, currentPage);
		}
		else{
			CommonUtil.redirectHomePage(response);
		}
		
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
	 * Gets list of video attributes
	 * @return attrs
	 */
	private List<String> getVideoAttributeList()
    {
        return  Arrays.asList(Constants.YOUTUBE_ID);
    }
	
	/**
	 * Gets current page by passing context parameter
	 * @return currentPage
	 * @throws Exception
	 */
	private int getCurrentPage(HstRequestContext context) {
        int currentPage = 1;
        try{
            currentPage = Integer.parseInt(context.getServletRequest().getParameter(Constants.CURRENT_PAGE));
        }catch(Exception e){
            currentPage = 1;
        }
        if(currentPage < 1){
            currentPage = 1;
        }
        return currentPage;
    }
    	
	/**
	 * sets Pagination for attributes
	 * @param request
	 * @param currentPage
	 * @param totalResults 
	 */
	private void setPaginationAttributes(HstRequest request, Integer currentPage, Long totalResults) {
    	final SearchFacetsComponentInfo paramInfo = getComponentParametersInfo(request);
       	Integer searchPageSize = paramInfo.getPageSize();
        if((totalResults % searchPageSize) > 0){
            Long totalPages = (totalResults / searchPageSize) + 1;
            request.setAttribute(Constants.REQUEST_ATTR_TOTALPAGES, totalPages);
            request.getRequestContext().setAttribute(Constants.REQUEST_ATTR_TOTALPAGES, totalPages);
            if(currentPage > totalPages){
                request.setAttribute(Constants.REQUEST_ATTR_TOTALCURRENTPAGE, totalPages);
                request.getRequestContext().setAttribute(Constants.REQUEST_ATTR_TOTALCURRENTPAGE, totalPages);
            }else{
            	request.setAttribute(Constants.REQUEST_ATTR_TOTALCURRENTPAGE, currentPage);
                request.getRequestContext().setAttribute(Constants.REQUEST_ATTR_TOTALCURRENTPAGE, currentPage);
            }
        }
        else{
        	Long totalPages = (totalResults/ searchPageSize);
            request.setAttribute(Constants.REQUEST_ATTR_TOTALPAGES, totalPages);
            request.getRequestContext().setAttribute(Constants.REQUEST_ATTR_TOTALPAGES, totalPages);
            if(currentPage > totalPages){
             request.setAttribute(Constants.REQUEST_ATTR_TOTALCURRENTPAGE, totalPages);
             request.getRequestContext().setAttribute(Constants.REQUEST_ATTR_TOTALCURRENTPAGE, totalPages);
            }else{
            	request.setAttribute(Constants.REQUEST_ATTR_TOTALCURRENTPAGE, currentPage);
                request.getRequestContext().setAttribute(Constants.REQUEST_ATTR_TOTALCURRENTPAGE, currentPage);
            }
        }
    }
	
	/**
	 * Used for encoding string
	 * @param facets
	 * @param hstRequestContext
	 * @param totalResults 
	 */
	private HippoFacetNavigationBean addExtendedProperties(HippoFacetNavigationBean facets) {
		String encodedFacetValue;
		try {
			if (facets != null)
				for (HippoFolderBean facet : facets.getFolders()) {
					if (!facet.getFolders().isEmpty()) {
						for (HippoFolderBean item : facet.getFolders()) {
							encodedFacetValue = encodePart(item.getDisplayName());
							item.getProperties().put(Constants.ENCODE_NAME, encodedFacetValue);
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
	
	/**
	 * Gets Article videos
	 * @param request
	 * @param context
	 * @param  query
	 * @return  
	 */
	public Long doArticlesVideoSearch(HstRequest request,final HstRequestContext context, String query,List<String> synonmys)
	{
		long articleVideoTotalResultsSize = 0;
		Long articleResults = Long.valueOf(0);
		Long videoResults = Long.valueOf(0);
		Long pressReleaseResults =  Long.valueOf(0);
		HstQueryResult resultVideos = doDocumentVideoSearch(request,context, query, Product.class,synonmys);
		if (resultVideos != null) {
			videoResults = Long.valueOf(resultVideos.getTotalSize());
			List<HippoBean> resultVideoList = new LinkedList<>();
			HippoBeanIterator videoIterator = resultVideos.getHippoBeans();
			while (videoIterator.hasNext()) {
				HippoBean videoBean=videoIterator.next();
				resultVideoList.add(videoBean);
			}	
			context.setAttribute(Constants.RESULT_VIDEO_LIST, resultVideoList);
			articleVideoTotalResultsSize = resultVideos.getTotalSize(); 
		}
		HstQueryResult resultPressRelease = doDocumentArticleSearch(request,context, query, Pressreleases.class,synonmys);
		if (resultPressRelease != null) {
			pressReleaseResults = Long.valueOf(resultPressRelease.getTotalSize());
			List<HippoBean> resultPressReleaseList = new LinkedList<>();
			HippoBeanIterator prIterator = resultPressRelease.getHippoBeans();
			while (prIterator.hasNext()) {
				resultPressReleaseList.add(prIterator.next());
			}
			context.setAttribute(Constants.RESULT_PRESSRELEASE_LIST, resultPressReleaseList);
			articleVideoTotalResultsSize = articleVideoTotalResultsSize + resultPressRelease.getTotalSize();
		}
		HstQueryResult resultArticle = doDocumentArticleSearch(request,context, query, ArticleBannerDocument.class,synonmys);
		if (resultArticle != null) {
			articleResults = Long.valueOf(resultArticle.getTotalSize());
			List<HippoBean> resultArticleList = new LinkedList<>();
			HippoBeanIterator prIterator = resultArticle.getHippoBeans();
			while (prIterator.hasNext()) {
				resultArticleList.add(prIterator.next());
			}
			context.setAttribute(Constants.RESULT_ARTICLE_LIST, resultArticleList);
			articleVideoTotalResultsSize = articleVideoTotalResultsSize + resultArticle.getTotalSize();
		}
		request.setAttribute(Constants.REQUEST_ATTR_TOTALARTICLECOUNT, articleResults);
		request.setAttribute(Constants.REQUEST_ATTR_TOTALVIDEOCOUNT, videoResults);
		request.setAttribute(Constants.REQUEST_ATTR_TOTALPRESSRELEASESCOUNT, pressReleaseResults);
		//Set Context Parameters
		context.setAttribute(Constants.REQUEST_ATTR_TOTALARTICLECOUNT, articleResults);
		context.setAttribute(Constants.REQUEST_ATTR_TOTALVIDEOCOUNT, videoResults);
		context.setAttribute(Constants.REQUEST_ATTR_TOTALPRESSRELEASESCOUNT, pressReleaseResults);
		return Long.valueOf(articleVideoTotalResultsSize);
	}
	
	/**
	 * Gets Article Documents
	 * @param request
	 * @param context
	 * @param  searchTerm
	 * @throws QueryException
	 * @return  
	 */
	private HstQueryResult doDocumentArticleSearch(HstRequest request,final HstRequestContext context, String searchTerm, Class<? extends HippoBean> type,List<String> synonmys) {
        HippoBean scope = context.getSiteContentBaseBean();
        try {
        	Map<String, String> queryStringKeyValueMap = CommonUtil.generateQueryStringMap(request.getQueryString());
        	HstQuery hstQuery = context.getQueryManager().createQuery(scope, type, true);
        	Filter filter = hstQuery.createFilter();
            Filter f = hstQuery.createFilter();
            f.addContains(".", searchTerm);
            filter.addOrFilter(f);
            if (synonmys!=null && !synonmys.isEmpty()){
				for(String searchTermQuery2: synonmys){
				String parsedSearchTermQuery1 = SearchInputParsingUtils.parse(searchTermQuery2, true);
				Filter f1 = hstQuery.createFilter();
				f1.addContains(".", parsedSearchTermQuery1);	
				filter.addOrFilter(f1);
				}
				} 
            hstQuery.setFilter(filter);
            String fieldNameAttribute = Constants.NS_PRESSRELEASES_TITLE;
          	if (queryStringKeyValueMap.containsKey(Constants.SORT_FIELD) && queryStringKeyValueMap.get(Constants.SORT_FIELD).equals(Constants.NAME)) {
				if (queryStringKeyValueMap.containsKey(Constants.SORT_DIRECTION)){
					if(queryStringKeyValueMap.get(Constants.SORT_DIRECTION).equals(Constants.SORT_DIRECTION_ASC)) {
						hstQuery.addOrderByAscending(fieldNameAttribute);
					}else if(queryStringKeyValueMap.get(Constants.SORT_DIRECTION).equals(Constants.SORT_DIRECTION_DESC)) {
					hstQuery.addOrderByDescending(fieldNameAttribute);
					}
				}
			} else {
				hstQuery.addOrderByAscending(fieldNameAttribute);
			}
            return hstQuery.execute();
        }  catch (QueryException e) {
        	log.error(String.format(errorRetrivingDocuments, type.toString(),  searchTerm), e);
        }
        return null;
    }
	
	/**
	 * Gets FAQ 
	 * @param request
	 * @param context
	 * @param  searchTerm
	 * @throws QueryException
	 * @return  
	 */
	private Long doHelpSearch(String searchType, HstRequest request,final HstRequestContext context, String searchTerm, Class<? extends HippoBean> type,List<String> synonmys) {
        HippoBean scope = context.getSiteContentBaseBean();
        List<FaqItem> resultHelpList = new LinkedList<>();
        Long totalHelpResults =  Long.valueOf(0);
        try {
        	Map<String, String> queryStringKeyValueMap = CommonUtil.generateQueryStringMap(request.getQueryString());
        	HstQuery hstQuery = context.getQueryManager().createQuery(scope, type, true);
        	Filter filter = hstQuery.createFilter();
            Filter f = hstQuery.createFilter();
            f.addContains(".", searchTerm);
			filter.addOrFilter(f);
			if (synonmys!=null && !synonmys.isEmpty()){
				for(String searchTermQuery2: synonmys){
				String parsedSearchTermQuery1 = SearchInputParsingUtils.parse(searchTermQuery2, true);
				Filter f1 = hstQuery.createFilter();
				f1.addContains(".", parsedSearchTermQuery1);	
				filter.addOrFilter(f1);
				}
			}	
            hstQuery.setFilter(filter);
        	String fieldNameAttribute = Constants.NS_FAQ_QUESTION;
        	if (queryStringKeyValueMap.containsKey(Constants.SORT_FIELD) && queryStringKeyValueMap.get(Constants.SORT_FIELD).equals(Constants.NAME)) {
				if (queryStringKeyValueMap.containsKey(Constants.SORT_DIRECTION)){
					if(queryStringKeyValueMap.get(Constants.SORT_DIRECTION).equals(Constants.SORT_DIRECTION_ASC)) {
						hstQuery.addOrderByAscending(fieldNameAttribute);
					}else if(queryStringKeyValueMap.get(Constants.SORT_DIRECTION).equals(Constants.SORT_DIRECTION_DESC)) {
						hstQuery.addOrderByDescending(fieldNameAttribute);
					}
				}
			} else {
				hstQuery.addOrderByAscending(fieldNameAttribute);
			}
        	HstQueryResult helpResults = hstQuery.execute();
        	if (helpResults != null) {
        		totalHelpResults = Long.valueOf(helpResults.getSize());
        		if (!Strings.isNullOrEmpty(searchType) && searchType.equalsIgnoreCase(Constants.HELP)) {
        			HippoBeanIterator helpIterator = helpResults.getHippoBeans();
        			while (helpIterator.hasNext()){
        				FaqItem faqItem=(FaqItem)helpIterator.next();
        				if(!resultHelpList.contains(faqItem)) {
        					resultHelpList.add(faqItem);
        				}
        			}
        			//Set Context Parameters
        			context.setAttribute(Constants.RESULT_HELP_LIST, resultHelpList);
        		}
        	}
            return totalHelpResults;
        }  catch (QueryException e) {
        	log.error(String.format(errorRetrivingDocuments, type.toString(),  searchTerm), e);
        }
        return null;
    }
	
	/**
	 * Gets Document videos
	 * @param request
	 * @param context
	 * @param  searchTerm
	 * @throws QueryException
	 * @return  
	 */
	private HstQueryResult doDocumentVideoSearch(HstRequest request,final HstRequestContext context, String searchTerm, Class<? extends HippoBean> type,List<String> synonmys) {
        HippoBean scope = context.getSiteContentBaseBean();
        try {      
        	
        	Map<String, String> queryStringKeyValueMap = CommonUtil.generateQueryStringMap(request.getQueryString());	
        	HstQuery hstQuery = context.getQueryManager().createQuery(scope, type, true);
				Filter filter = hstQuery.createFilter();
	        	if (!Strings.isNullOrEmpty(searchTerm)){
					Filter f = hstQuery.createFilter();
					String parsedSearchTermQuery = SearchInputParsingUtils.parse(searchTerm, true);
		            if (!StringUtils.isEmpty(parsedSearchTermQuery)) {
		            	f.addContains(".", parsedSearchTermQuery);	
		            }
		            filter.addAndFilter(f);
		            List<String> attrs = getVideoAttributeList();
	                Filter orFilters = hstQuery.createFilter();
	                for (String attr : attrs)
	                {
	                    Filter subquery = hstQuery.createFilter();
	                    subquery.addEqualTo(String.format("%s:attributes/%s:key", Constants.NAMESPACE, Constants.NAMESPACE), attr);
	                    orFilters.addOrFilter(subquery);
	                }
	                filter.addAndFilter(orFilters);
				}
	         	 hstQuery.setFilter(filter);
	         	if (queryStringKeyValueMap.containsKey(Constants.SORT_FIELD) && queryStringKeyValueMap.get(Constants.SORT_FIELD).equals(Constants.NAME)) {
					String fieldNameAttribute = Constants.NS_DESCRIPTION_PRODUCT;
					if (queryStringKeyValueMap.containsKey(Constants.SORT_DIRECTION)){
						if(queryStringKeyValueMap.get(Constants.SORT_DIRECTION).equals(Constants.SORT_DIRECTION_ASC)) {
							hstQuery.addOrderByAscending(fieldNameAttribute);
						}else if(queryStringKeyValueMap.get(Constants.SORT_DIRECTION).equals(Constants.SORT_DIRECTION_DESC)) {
						hstQuery.addOrderByDescending(fieldNameAttribute);
						}
						request.setAttribute(Constants.SORT_DIRECTION, queryStringKeyValueMap.get(Constants.SORT_DIRECTION));
					}
					request.setAttribute(Constants.SORT_FIELD, queryStringKeyValueMap.get(Constants.SORT_FIELD));
				} else {
					String fieldNameAttribute = Constants.NS_ITEM_NO;
					hstQuery.addOrderByAscending(fieldNameAttribute);
				}
	             return hstQuery.execute();
        }  catch (QueryException e) {
        	log.error(String.format(errorRetrivingDocuments, type.toString(),  searchTerm), e);
        }
        return null;
    }
	
	protected HippoFacetNavigationBean defineSliderMinMax(HstRequest request, HstQuery productHstQuery, Filter productFilter){
		productHstQuery.setFilter(productFilter);
		HippoFacetNavigationBean hippoFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(productHstQuery);
		if (hippoFacetNavigationBean != null) {
			List<HippoFolderBean> lstItr = hippoFacetNavigationBean.getFolders();
			for (HippoFolderBean folderBean : lstItr) {
				if ( (!folderBean.getFolders().isEmpty()) && (folderBean.getName().equalsIgnoreCase(Constants.LENGTH_FACET_NAME)
						|| folderBean.getName().equalsIgnoreCase(Constants.HEIGHT_FACET_NAME)
						|| folderBean.getName().equalsIgnoreCase(Constants.WIDTH_FACET_NAME))) {
					List<HippoFolderBean> subFolder = folderBean.getFolders();
					request.setAttribute(folderBean.getName() +"InitMin", subFolder.get(0).getDisplayName());
					request.setAttribute(folderBean.getName()+"InitMax", subFolder.get(folderBean.getFolders().size()-1).getDisplayName());
				}
			}
		}
		return hippoFacetNavigationBean;
	}
	
	protected void setQueryOrder (Map<String, String> queryStringKeyValueMap, HstQuery productHstQuery) {
		if (queryStringKeyValueMap.containsKey(Constants.SORT_FIELD) && queryStringKeyValueMap.get(Constants.SORT_FIELD).equals(Constants.NAME)) {
			String fieldNameAttribute = Constants.NS_DESCRIPTION_PRODUCT;
			if (queryStringKeyValueMap.containsKey(Constants.SORT_DIRECTION)){
				if(queryStringKeyValueMap.get(Constants.SORT_DIRECTION).equals(Constants.SORT_DIRECTION_ASC)) {
					productHstQuery.addOrderByAscending(fieldNameAttribute);
				}else if(queryStringKeyValueMap.get(Constants.SORT_DIRECTION).equals(Constants.SORT_DIRECTION_DESC)) {
					productHstQuery.addOrderByDescending(fieldNameAttribute);
				}
			}
		} else {
			String fieldNameAttribute = Constants.NS_LIST_PRICE;
			productHstQuery.addOrderByDescending(Constants.NS_IS_NEW);
			productHstQuery.addOrderByDescending(Constants.NS_STARPRODUCT);
			productHstQuery.addOrderByDescending(fieldNameAttribute);
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

	protected void sendProductDetails(String productURL, String defaultCategoryURL, HstResponse response) {
		try {
			if (!Strings.isNullOrEmpty(defaultCategoryURL)) {
				if (productURL.contains("?")) {
					defaultCategoryURL = "&" + defaultCategoryURL;
				}else {
					defaultCategoryURL = "?" + defaultCategoryURL;
				}
			}
			response.sendRedirect(productURL + defaultCategoryURL);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private static String errorRetrivingDocuments = "Error Retrieving '%s' Documents for Search Page for search-term : '%s'"; 

	
	/**
	 * Gets Synonmys for Search Term
	 * @param  searchTerm
	 * @throws QueryException
	 * @return List
	 */
	private List<String> getSynonmys(String searchTerm,HstRequest request) {
	 List<String>  values = new ArrayList<String>();
		try{
		HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();
		@SuppressWarnings("unchecked")
		HstQuery hstQuery = request.getRequestContext().getQueryManager().createQuery(rootBean, Synonym.class);
		Filter filter = hstQuery.createFilter();
		filter.addContains(".", searchTerm);
		hstQuery.setFilter(filter);
		HstQueryResult result = hstQuery.execute();
		HippoBeanIterator iterator = result.getHippoBeans();
		while (iterator.hasNext()) {
            Synonym bean = (Synonym) iterator.next();
            List <CSynonym> cSynonyms = bean.getSynonym();
             for (CSynonym cs : cSynonyms) {
            	 if(cs.getKey().toString().toLowerCase().equals(searchTerm.toLowerCase())){
                     values = Arrays.asList(cs.getValue());
                     break;
            	 }    
            } 
		  }
		 }
		catch(QueryException e){
			log.error("QueryException in getting Synonmys");
		}
		catch(Exception e){
			log.error("Exception in getting Synonmys");
		}
		return values;
	} 

}
