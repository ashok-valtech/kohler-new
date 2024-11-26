/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.util.ContentBeanUtils;
import org.hippoecm.hst.util.PathUtils;
import org.onehippo.cms7.essentials.components.CommonComponent;
import org.onehippo.cms7.essentials.components.paging.DefaultPagination;
import org.onehippo.cms7.essentials.components.paging.Pageable;
import org.onehippo.taxonomy.api.Category;
import org.onehippo.taxonomy.api.TaxonomyNodeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.CAttributes;
import com.kohler.beans.CSKU;
import com.kohler.beans.Product;
import com.kohler.commons.components.componentsInfo.ProductListComponentInfo;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.taxonomies.TaxonomyService;
import com.kohler.commons.util.CommonUtil;

/**
 * APAC Implementation for Uber category product page, inherits from <code>CommonComponent</code>
 * @author dhanwan.r
 * Date:03/05/2017
 * @version 1.0
 */
@ParametersInfo(type = ProductListComponentInfo.class)
public class ProductListComponent extends CommonComponent{
	
	private static Logger LOG = LoggerFactory.getLogger(ProductListComponent.class);
	
	TaxonomyService taxonomyService = new TaxonomyService();
	
	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	
	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
		super.doBeforeRender(request, response);
		final HstRequestContext context = request.getRequestContext();
		String language = CommonUtil.getLanguage(context.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(context.getResolvedMount().getMount());
		Locale locale = new Locale(language, country); 
		ResourceBundle bundleApacLabel = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
		String categoriesFilterLabel = resourceBundle.getPropertyValue(bundleApacLabel, Constants.CATEGORIES);
		final ProductListComponentInfo paramInfo = getComponentParametersInfo(request);
		String categoryKey = paramInfo.getCategory();
	
		try{
			HippoBean rootBean = context.getSiteContentBaseBean();
			HippoBean productFolderBean = null;
			if (rootBean != null) {
				final String myPath = PathUtils.normalizePath(Constants.PRODUCTS_PATH);
				productFolderBean = rootBean.getBean(myPath);
				if (productFolderBean == null) {
					productFolderBean = rootBean;
				}
			}				
			HstQuery productHstQuery = context.getQueryManager().createQuery(productFolderBean);
			Filter discontinuedFilter = productHstQuery.createFilter();
	    	String fieldRestriction = Constants.NS_DISCONTINUED;
	    	discontinuedFilter.addNotEqualTo(fieldRestriction, true);
	    	productHstQuery.setFilter(discontinuedFilter);
	    	String fieldNameAttribute = Constants.HIPPOSTDPUBWF_PUBLICATION_DATE;
	    	productHstQuery.addOrderByDescending(fieldNameAttribute);
	    	String queryAsString = "xpath("+productHstQuery.getQueryAsString(true)+")";
	    	HippoFacetNavigationBean hippoFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(productHstQuery);
	        Map<String, HippoFolderBean> hippoFolderBeanMap = new HashMap<String,HippoFolderBean>();
	        Category currentCategory = taxonomyService.getCategoryFromKey(Constants.TAXONOMY_NODE_NAME, categoryKey);
	        Map<String, Category> subCategoryMap = getSubCategories(categoryKey, request);
			Map<String, List<Product>> productList = new LinkedHashMap<>();
			ResolvedSiteMapItem resolvedSiteMapItem = context.getResolvedSiteMapItem();
			String relPath = PathUtils.normalizePath(resolvedSiteMapItem.getRelativeContentPath());
	        if(hippoFacetNavigationBean != null){
				Iterator<HippoFolderBean> itr = hippoFacetNavigationBean.getFolders().iterator();
		        while(itr.hasNext()) {
		        	HippoFolderBean facet= itr.next();
		        	if(facet.getName().equalsIgnoreCase(categoriesFilterLabel)){
		        		for(HippoFolderBean folderBean: facet.getFolders()){
		        			if(subCategoryMap.containsKey(folderBean.getName())){
		        				hippoFolderBeanMap.put(folderBean.getName(),folderBean);
		        				try {
		        					String categoryRelPath = folderBean.getPath();
									categoryRelPath = categoryRelPath.substring(categoryRelPath.indexOf(relPath));
		        					HippoFacetNavigationBean categoryFacetNavigationBean  =  ContentBeanUtils.getFacetNavigationBean(categoryRelPath, queryAsString);
			        				if(categoryFacetNavigationBean != null){
			        					HippoResultSetBean resultSet = categoryFacetNavigationBean.getResultSet();
				        				if (resultSet.getCount() > 0) {
				        					HippoDocumentIterator<HippoBean> iterator = resultSet.getDocumentIterator(HippoBean.class);
				        					Pageable<HippoBean> pageable = DefaultPagination.emptyCollection();
				    						pageable = getPageableFactory().createPageable(iterator, resultSet.getCount().intValue(),
				    								3, 1);
				        					List<Product> categoryProductList = new LinkedList<Product>();
				        					for(HippoBean bean: pageable.getItems()){
				        						Product product = (Product) bean;
				        						categoryProductList.add(product);
				        					}
					        				productList.put(folderBean.getName(), categoryProductList);
				        				}
			        				}
		        				}catch (Exception ex) {
		        					LOG.error(ex.getMessage());
		        				}
		        			}
		        		}
		        	} 
		        }
	        }
	        request.setAttribute(Constants.REQUEST_ATTR_CATEGORIES,subCategoryMap);
	        request.setAttribute(Constants.REQUEST_ATTR_FACET,hippoFolderBeanMap);
			request.setAttribute(Constants.REQUEST_ATTR_PRODUCTS, productList);
			request.setAttribute(Constants.REQUEST_ATTR_CURRENTCATEGORY, currentCategory);
		}catch(HstComponentException e)
		{
			LOG.error(e.getMessage());
			CommonUtil.pageNotFound(response, Constants.PAGE_NOT_FOUND_URL);
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
			CommonUtil.pageNotFound(response, Constants.PAGE_NOT_FOUND_URL);
		}	
	}
	
	/**
	 * Gets sub category of category
	 * @param categoryKey
	 * @param request
	 * @return subCategoryMap 
	 */
	protected Map<String, Category> getSubCategories(String categoryKey, HstRequest request) {
		Map<String, Category> subCategoryMap = new LinkedHashMap<String, Category>();
		Category category = taxonomyService.getCategoryFromKey(Constants.TAXONOMY_NODE_NAME, categoryKey);
		ListIterator<? extends Category> subCategoryItr = taxonomyService.getListIterator(category);
		while (subCategoryItr.hasNext()) {
			Category subCategory = subCategoryItr.next();
			String subCatkey = subCategory.getKey();
			subCategoryMap.put(subCatkey, subCategory);
		}
		return subCategoryMap;
	}
	
	/**
	 * Gets product of sub category
	 * @param subCategoryMap
	 * @param request
	 * @return productMap 
	 */
	protected Map<String, List<Product>> getSubCategoryProducts( Map<String, Category> subCategoryMap, HstRequest request) {
		Map<String, List<Product>> productMap = new LinkedHashMap<>();
		for(Map.Entry<String, Category> entry: subCategoryMap.entrySet()){
			String subCategorykey = entry.getValue().getKey();
			List<Product> products = getProducts(subCategorykey, request);
			if(products.size() > 0){
				productMap.put(subCategorykey, products);
			}	
		}
		return productMap;
	}
	
	/**
	 * Gets list of products
	 * @param childCategoryKey
	 * @param request
	 * @return productList 
	 * @throws Exception
	 */
	public List<Product> getProducts(String childCategoryKey, HstRequest request) {

		HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();
		int count = 0;
		List<Product> productList = new LinkedList<Product>();
		try {
			if (rootBean != null) {
				@SuppressWarnings("unchecked")
				HstQuery query = request.getRequestContext().getQueryManager().createQuery(rootBean, Product.class);
				Filter filter = query.createFilter();
				filter.addEqualTo(TaxonomyNodeTypes.HIPPOTAXONOMY_KEYS, childCategoryKey);
				query.setFilter(filter);
				//default Ordering
				String fieldNameAttribute = Constants.NS_LIST_PRICE;
				query.addOrderByDescending(fieldNameAttribute);
				HstQueryResult queryResult = query.execute();
				HippoBeanIterator beanItr = queryResult.getHippoBeans();
				while (beanItr.hasNext() && count < 3) {
					Product product = (Product) beanItr.next();
					if(!this.productDiscontinued(product.getAttributes(), product.getSkus())){
						productList.add(product);
						count++;
					}	
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return productList;
	}
	
	/**
	 * Gets Product count
	 * @param childCategoryKey
	 * @param request
	 * @return product based on taxonomy key
	 * @throws Exception
	 */
	public static long getProdCount(String childCategoryKey, HstRequest request) {
		long count = 0;
		HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();

		try {
			if (rootBean != null) {
				@SuppressWarnings("unchecked")
				HstQuery query = request.getRequestContext().getQueryManager().createQuery(rootBean, Product.class);
				Filter filter = query.createFilter();
				filter.addEqualTo(TaxonomyNodeTypes.HIPPOTAXONOMY_KEYS, childCategoryKey);
				query.setFilter(filter);
				HstQueryResult queryResult = query.execute();
				count = queryResult.getHippoBeans().getSize();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * Gets Facet Navigation bean
	 * @param context
	 * @param path
	 * @param query
	 * @return facNavBean
	 */
	 protected HippoFacetNavigationBean getFacetNavigationBean(final HstRequestContext context, String path, String query) {
	        if (Strings.isNullOrEmpty(path)) {
	            return null;
	        }
	        ResolvedSiteMapItem resolvedSiteMapItem = context.getResolvedSiteMapItem();
	        String resolvedContentPath = PathUtils.normalizePath(resolvedSiteMapItem.getRelativeContentPath());
	        String parsedQuery = cleanupSearchQuery(query);
	        HippoFacetNavigationBean facNavBean;
	        if (!StringUtils.isBlank(resolvedContentPath)
	                && !resolvedContentPath.startsWith("/")
	                && context.getSiteContentBaseBean().getBean(resolvedContentPath, HippoFacetNavigationBean.class) != null) {
	            facNavBean = ContentBeanUtils.getFacetNavigationBean(resolvedContentPath, parsedQuery);
	        } else {
	            facNavBean = ContentBeanUtils.getFacetNavigationBean(path, parsedQuery);
	        }
	        return facNavBean;
	    }
	 
	 /**
		 * 
		 * @param product
		 * @param skus
		 * @return Booleans true or false. If product's default sku contains discontinued date field it will return true or false. 
		 */
		public Boolean productDiscontinued (List<CAttributes> attributes, List<CSKU> skus){
			Boolean discontinuedFlag = false;
			String[] defaultSkuId = null;
			String skuId [] = null;
			for(CAttributes attribute : attributes){
				if(attribute.getKey().equalsIgnoreCase(Constants.DEFAULT_SKU)){
					defaultSkuId = attribute.getValue();
					break;
				}
			}
			if(defaultSkuId != null){
				if (defaultSkuId.length > 0) {
					for (CSKU sku : skus) {
						if(sku.getSku().equalsIgnoreCase(defaultSkuId[0])){
							for(CAttributes skuAttribute : sku.getAttributes()){
								if(skuAttribute.getKey().equals(Constants.DISCONTINUED_DATE)){
									skuId = skuAttribute.getValue();
									if(skuId != null && skuId.length > 0){
										discontinuedFlag = true;
										break;
									}
								}
							}
						}
					}
				}
			}
			return discontinuedFlag;
		}
		
		public List<String> getAncestorProducts(String childCategoryKey, HstRequestContext requestContext) {
			HippoBean rootBean = requestContext.getSiteContentBaseBean();
			List<com.kohler.beans.Product> productList = new LinkedList<com.kohler.beans.Product>();
			List<String> ancestorProductIds = new LinkedList<String>();
			try {
				if (rootBean != null) {
					@SuppressWarnings("unchecked")
					HstQuery query = requestContext.getQueryManager().createQuery(rootBean, com.kohler.beans.Product.class);
					Filter filter = query.createFilter();
					filter.addEqualTo(TaxonomyNodeTypes.HIPPOTAXONOMY_KEYS, childCategoryKey);
					query.setFilter(filter);
					//default Ordering
					String fieldNameAttribute = Constants.NS_LIST_PRICE;
					query.addOrderByDescending(fieldNameAttribute);
					HstQueryResult queryResult = query.execute();
					HippoBeanIterator beanItr = queryResult.getHippoBeans();
					while (beanItr.hasNext()) {
						com.kohler.beans.Product product = (com.kohler.beans.Product) beanItr.next();
							productList.add(product);
						}	
					}
			} catch (Exception e) {
				System.out.println("Exception Occur in getting ancestors products");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			for(com.kohler.beans.Product product : productList)
			{
				ancestorProductIds.add(product.getId().toString());	
			}
			System.out.println("Category Key products-->" +ancestorProductIds);
			return ancestorProductIds;
		}
		
}
