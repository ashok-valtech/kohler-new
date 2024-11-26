/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.util.ContentBeanUtils;
import org.hippoecm.hst.util.PathUtils;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.paging.DefaultPagination;
import org.onehippo.cms7.essentials.components.paging.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.CAttributes;
import com.kohler.beans.CSKU;
import com.kohler.beans.Product;
import com.kohler.commons.components.componentsInfo.EssentialsProductListComponentInfo;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.util.CommonUtil;

import net.sf.json.JSONObject;

/**
 * APAC Implementation for Product category listing page, inherits from <code>EssentialsListComponent</code>
 * @author dhanwan.r
 * Date: 05/05/2017
 * @version 1.0
 */
@ParametersInfo(type = EssentialsProductListComponentInfo.class)
public class EssentialsProductListAjaxComponent extends EssentialsListComponent {

	private static Logger LOG = LoggerFactory.getLogger(EssentialsListComponent.class);
	
	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();

	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		String relativeContentPath = this.getPublicRequestParameter(request, Constants.REQUEST_ATTR_RELATIVE_CONTENT_PATH);
		String pageSizeString = this.getPublicRequestParameter(request, Constants.REQUEST_ATTR_PAGESIZE);
		String currentPageString = this.getPublicRequestParameter(request, Constants.REQUEST_ATTR_CURRENT_PAGE);
		String lastVisitedPageString  = this.getPublicRequestParameter(request, "lastVisitedPage");
		String orderBy = this.getPublicRequestParameter(request, Constants.SORT_FIELD);
		String sort = this.getPublicRequestParameter(request, Constants.SORT_DIRECTION);
		final HstRequestContext context = request.getRequestContext();
		String language = CommonUtil.getLanguage(context.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(context.getResolvedMount().getMount());
		Locale locale = new Locale(language, country); 
		ResourceBundle bundleApacLabel = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
		String OVERALL_LENGTH = resourceBundle.getPropertyValue(bundleApacLabel, Constants.OVERALL_LENGTH_SUFFIX);
		String OVERALL_LENGTH_MIN = OVERALL_LENGTH + Constants.RANGE_FILTER_MIN_SUFFIX;
		String OVERALL_LENGTH_MAX = OVERALL_LENGTH + Constants.RANGE_FILTER_MAX_SUFFIX;
		String OVERALL_HEIGHT = resourceBundle.getPropertyValue(bundleApacLabel, Constants.OVERALL_HEIGHT_SUFFIX);
		String OVERALL_HEIGHT_MIN = OVERALL_HEIGHT + Constants.RANGE_FILTER_MIN_SUFFIX;
		String OVERALL_HEIGHT_MAX = OVERALL_HEIGHT + Constants.RANGE_FILTER_MAX_SUFFIX;
		String OVERALL_WIDTH = resourceBundle.getPropertyValue(bundleApacLabel, Constants.OVERALL_WIDTH_SUFFIX);
		String OVERALL_WIDTH_MIN = OVERALL_WIDTH + Constants.RANGE_FILTER_MIN_SUFFIX;
		String OVERALL_WIDTH_MAX = OVERALL_WIDTH + Constants.RANGE_FILTER_MAX_SUFFIX;
		String url = this.getPublicRequestParameter(request, "url");
		Map <String, String> queryStringKeyValueMap = new HashMap<String, String>();
		if(!Strings.isNullOrEmpty(url)){
			for(String entry : url.split(",")){
				String [] entries = entry.split(":");
				if(entries.length == 2 && !Strings.isNullOrEmpty(entries[0]) && !Strings.isNullOrEmpty(entries[1])){
					queryStringKeyValueMap.put(entries[0], entries[1]);
				}	
			}
		}
		Integer pageSize = 0;
		Integer currentPage = 0;
		Integer lastVisitedPage = 0;
		if(!Strings.isNullOrEmpty(pageSizeString)){
			try {
				pageSize = Integer.parseInt(pageSizeString);
			} catch (NumberFormatException ex) {
				LOG.error("Parsing pageSizeString: " + ex.getMessage());
			}
		}
		if(!Strings.isNullOrEmpty(currentPageString)){
			try {
				currentPage = Integer.parseInt(currentPageString);
			} catch (NumberFormatException ex) {
				LOG.error("Parsing currentPageString: " + ex.getMessage());
			}
		}
		if(!Strings.isNullOrEmpty(lastVisitedPageString)){
			try {
				lastVisitedPage = Integer.parseInt(lastVisitedPageString);
			} catch (NumberFormatException ex) {
				LOG.error("Parsing lastVisitedPageString: " + ex.getMessage());
			}
		}
		
		JSONObject result = new JSONObject();
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
			HstQuery hstQuery = context.getQueryManager().createQuery(productFolderBean);
			
			Filter subFilter = hstQuery.createFilter();
			if (!Strings.isNullOrEmpty(orderBy)
					&& !Strings.isNullOrEmpty(sort)) {
								if (orderBy.equals(Constants.NAME)) {
									String fieldNameAttribute = Constants.NS_DESCRIPTION_PRODUCT;
									if (sort.equals(Constants.SORT_DIRECTION_ASC)) {
										hstQuery.addOrderByAscending(fieldNameAttribute);
									} else if (sort.equals(Constants.SORT_DIRECTION_DESC)) {
										hstQuery.addOrderByDescending(fieldNameAttribute);
									}
								} else {
									hstQuery.addOrderByDescending(Constants.NS_IS_NEW);
									hstQuery.addOrderByDescending(Constants.NS_STARPRODUCT);
									String fieldNameAttribute = Constants.NS_LIST_PRICE;
									hstQuery.addOrderByDescending(fieldNameAttribute);
								}
			}else
			{
				hstQuery.addOrderByDescending(Constants.NS_IS_NEW);
				hstQuery.addOrderByDescending(Constants.NS_STARPRODUCT);
				String fieldNameAttribute = Constants.NS_LIST_PRICE;
				hstQuery.addOrderByDescending(fieldNameAttribute);
				
			}
			
			if (queryStringKeyValueMap.containsKey(OVERALL_LENGTH_MIN)
					&& queryStringKeyValueMap.containsKey(OVERALL_LENGTH_MAX)) {
				try {
					subFilter.addBetween(Constants.NS_OVERALL_LENGTH,
							Long.parseLong(queryStringKeyValueMap.get(OVERALL_LENGTH_MIN)),
							Long.parseLong(queryStringKeyValueMap.get(OVERALL_LENGTH_MAX)));
				} catch (NumberFormatException ex) {
					LOG.error(Constants.NS_OVERALL_LENGTH + " Parsing: " + ex.getMessage());
				}
				
			}

			if (queryStringKeyValueMap.containsKey(OVERALL_HEIGHT_MIN)
					&& queryStringKeyValueMap.containsKey(OVERALL_HEIGHT_MAX)) {
				try {
					subFilter.addBetween(Constants.NS_OVERALL_HEIGHT,
							Long.parseLong(queryStringKeyValueMap.get(OVERALL_HEIGHT_MIN)),
							Long.parseLong(queryStringKeyValueMap.get(OVERALL_HEIGHT_MAX)));
				} catch (NumberFormatException ex) {
					LOG.error(Constants.NS_OVERALL_HEIGHT + " Parsing: " + ex.getMessage());
				}
			}

			if (queryStringKeyValueMap.containsKey(OVERALL_WIDTH_MIN)
					&& queryStringKeyValueMap.containsKey(OVERALL_WIDTH_MAX)) {
				try {
					subFilter.addBetween(Constants.NS_OVERALL_WIDTH,
							Long.parseLong(queryStringKeyValueMap.get(OVERALL_WIDTH_MIN)),
							Long.parseLong(queryStringKeyValueMap.get(OVERALL_WIDTH_MAX)));
				} catch (NumberFormatException ex) {
					LOG.error(Constants.NS_OVERALL_WIDTH + " Parsing: " + ex.getMessage());
				}
			}
			String fieldRestriction = Constants.NS_DISCONTINUED;
        	subFilter.addNotEqualTo(fieldRestriction, true);
        	hstQuery.setFilter(subFilter);
			final HippoFacetNavigationBean hippoFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(hstQuery, relativeContentPath);
			List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
			if (hippoFacetNavigationBean != null) {
				final HippoResultSetBean resultSet = hippoFacetNavigationBean.getResultSet();
				if (resultSet != null) {
					do
					{
						final HippoDocumentIterator<HippoBean> iterator = resultSet.getDocumentIterator(HippoBean.class);
						currentPage++;
						Pageable<HippoBean> pageable = DefaultPagination.emptyCollection();
						pageable = getPageableFactory().createPageable(iterator, resultSet.getCount().intValue(),
								pageSize, currentPage);
						this.constructJson (jsonObjectList, pageable);
					}while (currentPage < lastVisitedPage);
				}
			}
			result.put(Constants.ITEMS, jsonObjectList);
			result.put(Constants.SUCCESS, true);
			result.put(Constants.REQUEST_ATTR_CURRENT_PAGE, currentPage);
		} catch (IllegalStateException e) {
			result.put(Constants.SUCCESS, false);
			LOG.error("Illegal State " + e.getMessage());
			e.printStackTrace();
		} catch (QueryException e) {
			result.put(Constants.SUCCESS, false);
			LOG.error("QueryException " + e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				response.getWriter().write(result.toString());
				response.setContentType(Constants.CONTENT_TYPE_APPLICATION_JSON);
			} catch (Exception e) {
				LOG.error(e.getMessage());
				throw new RuntimeException("catestrophic error", e);
			}
		}
	}
	
	public void constructJson (List<JSONObject> jsonObjectList, Pageable<HippoBean> pageable) {
		for(HippoBean bean: pageable.getItems()){
			Product product = (Product) bean;
			JSONObject productJsonObject = new JSONObject();
			productJsonObject.put("itemNo", product.getItemNo());
			productJsonObject.put("isNew", product.getIsNew());
			if(product.getKeys() != null && product.getKeys().length > 0){
				productJsonObject.put("category", product.getKeys()[0]);
				if (product.getKeys().length > 1) {
					productJsonObject.put("isMultipleAncestor", true);
				} else {
					productJsonObject.put("isMultipleAncestor", false);
				}
			} else {
				productJsonObject.put("isMultipleAncestor", false);
			}
			for(CAttributes attribute: product.getAttributes()){
				if(attribute.getKey().equalsIgnoreCase("BRAND_NAME")){
					String brandName = attribute.getValue()[0];
					brandName = brandName.replace("(R)","<sup>&reg;</sup>");
					brandName = brandName.replace("(TM)","<sup>&trade;</sup>");
					productJsonObject.put("BRAND_NAME", brandName);
				}
				else if(attribute.getKey().equalsIgnoreCase("DESCRIPTION_PRODUCT_SHORT")){
					String description = attribute.getValue()[0];
					description = description.replace("(R)","<sup>&reg;</sup>");
					description = description.replace("(TM)","<sup>&trade;</sup>");
					productJsonObject.put("DESCRIPTION_PRODUCT_SHORT", description);
				}
				else if(attribute.getKey().equalsIgnoreCase("DEFAULT_SKU")){
					String defaultSkuId = attribute.getValue()[0];
					productJsonObject.put("DEFAULT_SKU", defaultSkuId);
				}
				else if(attribute.getKey().equalsIgnoreCase("IMG_ISO")){
					String imgIso = attribute.getValue()[0];
					productJsonObject.put("IMG_ISO", imgIso);
				}
			}
			List<JSONObject> skuJsonObjectList = new ArrayList<JSONObject>();
			for(CSKU csku: product.getSkus()){
				JSONObject skuJsonObject = new JSONObject();
				skuJsonObject.put("sku", csku.getSku());
				Boolean isDefault = false;
				if(productJsonObject.containsKey("DEFAULT_SKU")){
					if(productJsonObject.getString("DEFAULT_SKU").equalsIgnoreCase(csku.getSku())){
						isDefault = true;
					}
				}
				for(CAttributes attribute : csku.getAttributes()){
					if(attribute.getKey().equalsIgnoreCase("IMG_ITEM_ISO")){
						String itemIso = attribute.getValue()[0];
						skuJsonObject.put("IMG_ITEM_ISO", itemIso);
					}else if(attribute.getKey().equalsIgnoreCase("COLOR_FINISH_NAME")){
						String colorFinishName = attribute.getValue()[0];
						skuJsonObject.put("COLOR_FINISH_NAME", colorFinishName);
						if(isDefault){
							productJsonObject.put("COLOR_FINISH_NAME", colorFinishName);
						}
					}else if(attribute.getKey().equalsIgnoreCase("IMG_SWATCH")){
						String imgSwatch = attribute.getValue()[0];
						skuJsonObject.put("IMG_SWATCH", imgSwatch);
						if(isDefault){
							productJsonObject.put("IMG_SWATCH", imgSwatch);
						}
					}else if(attribute.getKey().equalsIgnoreCase("DISCONTINUED_DATE")){
						String discontinuedDate = attribute.getValue()[0];
						skuJsonObject.put("DISCONTINUED_DATE", discontinuedDate);
					}
					if(attribute.getKey().equalsIgnoreCase(Constants.PRODUCT_PRICE)){
						if(attribute.getValue().length > 0){
							String productPrice = attribute.getValue()[0];
							skuJsonObject.put("PRODUCT_PRICE", productPrice);
						}
					}
				}
				skuJsonObjectList.add(skuJsonObject);
				skuJsonObject = null;
			}
			productJsonObject.put("SKUS", skuJsonObjectList);
			jsonObjectList.add(productJsonObject);
			skuJsonObjectList = null;
			productJsonObject = null;
		}
	}
}
