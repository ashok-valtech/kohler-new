/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/

package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.util.ContentBeanUtils;
import org.hippoecm.hst.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.beans.Product;
import com.kohler.commons.constants.Constants;


/**
 * Product Services component
 * @author dhanwan.r
 * Date:22/01/2017
 * @version 1.0
 */
 
public class ProductServiceComponent {

	
	/**
	 * Gets List of all productTypes  
	 * @param requestContext
	 * @return Map 
	 */
	
	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	
	private static Logger LOG = LoggerFactory.getLogger(ProductServiceComponent.class);
	
	/**
	 * 
	 * @param requestContext
	 * @param locale
	 * @return Returns all the products with its similar product from Category and product type
	 */
	public Map<String, Map <Long, String>> getCategoryProductTypeBasedSimilarProducts (HstRequestContext requestContext, Locale locale) {
		Map<String, Map <Long, String>> relatedProductMap = new HashMap<String, Map <Long, String>>();
		HippoBean rootBean = requestContext.getSiteContentBaseBean();
		HippoBean productFolderBean = null;
		if (rootBean != null) {
			final String myPath = PathUtils.normalizePath(Constants.PRODUCTS_PATH);
			productFolderBean = rootBean.getBean(myPath);
			if (productFolderBean == null) {
				productFolderBean = rootBean;
			}
		}		
		try{
			HstQuery productHstQuery = requestContext.getQueryManager().createQuery(productFolderBean);
			Filter discontinuedFilter = productHstQuery.createFilter();
	    	String fieldRestriction = Constants.NS_DISCONTINUED;
	    	discontinuedFilter.addNotEqualTo(fieldRestriction, true);
	    	productHstQuery.setFilter(discontinuedFilter);
	    	String relPath = PathUtils.normalizePath(Constants.PRODUCTS_FACETS_PATH);
	    	String fieldNameAttribute = Constants.NS_LIST_PRICE;
	    	productHstQuery.addOrderByAscending(fieldNameAttribute);
	    	String idFieldNameAttribute = Constants.NS_ITEM_NO;
	    	productHstQuery.addOrderByAscending(idFieldNameAttribute);
	    	String queryAsString = "xpath("+productHstQuery.getQueryAsString(true)+")";
			HippoFacetNavigationBean hippoFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(relPath, queryAsString);
	    	ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
	    	String categoriesFilterLabel = resourceBundle.getPropertyValue(bundle, Constants.CATEGORIES);
	    	if(hippoFacetNavigationBean != null){
				Iterator<HippoFolderBean> itr = hippoFacetNavigationBean.getFolders().iterator();
		        while(itr.hasNext()) {
		        	HippoFolderBean facet= itr.next();
		        	if(facet.getName().equalsIgnoreCase(categoriesFilterLabel)){
		        		for(HippoFolderBean categoryFolderBean: facet.getFolders()){
							String categoryRelPath = categoryFolderBean.getPath();
							categoryRelPath = categoryRelPath.substring(categoryRelPath.indexOf(relPath));
		        			HippoFacetNavigationBean categoryFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(categoryRelPath, queryAsString);
							if(categoryFacetNavigationBean != null){
		    					HippoResultSetBean resultSet = categoryFacetNavigationBean.getResultSet();
		        				if (resultSet.getCount() > 1) {
		        					HippoDocumentIterator<HippoBean> iterator = resultSet.getDocumentIterator(HippoBean.class);
		        					List<Product> categoryProductList = new LinkedList<Product>();
		        					while (iterator.hasNext()) {
		        						Product product = (Product) iterator.next();
		        						categoryProductList.add(product);
		        					}
		        					if (!categoryProductList.isEmpty()) {
			        					relatedProductMap = getSimilarProductByPrice (categoryProductList , relatedProductMap);
		        					}
		        				}
							}
		        		}
		        	}
		        }
	    	}     
	    	
		}catch(HstComponentException e)
		{
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
			e.printStackTrace();
		}	
		return relatedProductMap;
	}
	
	/**
	 * Gets List of Similar productIds 
	 * @param session
	 * @param priceProducts
	 * @param index
	 * @return List 
	 */
	public Map<String, Map <Long, String>> getSimilarProductByPrice (List<Product> categoryProductList , Map<String, Map <Long, String>> relatedProductMap)
	{
		Integer totalProduct = categoryProductList.size();
		for (Integer i = 0 ; i < totalProduct; i++) {
			Product product = categoryProductList.get(i);
			String productId = "";
			String productItemNumber = "";
			String productFamily = "";
			if (product.getItemNo() != null){
				productItemNumber = product.getItemNo();
			}
			if (product.getProductFamily () != null) {
				productFamily = product.getProductFamily ();
			}
			if (product.getId() != null) {
				productId = product.getId().toString();
				if (!relatedProductMap.containsKey(productItemNumber)) {
					Set <String> productFamilySet = new HashSet <String> ();
					productFamilySet.add (productFamily);
					List <Product> relatedProducts = new ArrayList <Product> ();
					Integer counter = 0;
					
					Integer reverceCounter = 0;
					Integer forwardCounter = 0;
					//For two lower
					for (Integer j = i - 1; ( (j >= 0) && (counter < 2) ); j-- ) {
						Product relatedProduct = categoryProductList.get(j);
						String relatedProductFamily = "";
						if (relatedProduct.getProductFamily() != null) {
							relatedProductFamily = relatedProduct.getProductFamily();
							if ((!relatedProductFamily.equalsIgnoreCase("")) && (productFamilySet.add (relatedProductFamily))) {
								counter++;
								relatedProducts.add(relatedProduct);
							}
						}
						reverceCounter = j;
					}
					
					//For two higher
					for (Integer j = i + 1; ( (j < totalProduct) && (counter < 4) ); j++ ) {
						Product relatedProduct = categoryProductList.get(j);
						String relatedProductFamily = "";
						if (relatedProduct.getProductFamily() != null) {
							relatedProductFamily = relatedProduct.getProductFamily();
							if ((!relatedProductFamily.equalsIgnoreCase("")) && (productFamilySet.add (relatedProductFamily))) {
								counter++;
								relatedProducts.add(relatedProduct);
							}
						}
						forwardCounter = j;
					}
					
					//Still less related products check for more lower
					if (counter != 4) {
						//For remaining lesser product
						for (Integer j = --reverceCounter; ( (j >= 0) && (counter < 4) ); j-- ) {
							Product relatedProduct = categoryProductList.get(j);
							String relatedProductFamily = "";
							if (relatedProduct.getProductFamily() != null) {
								relatedProductFamily = relatedProduct.getProductFamily();
								if ((!relatedProductFamily.equalsIgnoreCase("")) && (productFamilySet.add (relatedProductFamily))) {
									counter++;
									relatedProducts.add(relatedProduct);
								}
							}
						}
						
						//Still less related product check for more higher
						if (counter != 4) {
							//For remaining lesser product
							for (Integer j = ++forwardCounter; ( (j < totalProduct) && (counter < 4) ); j++ ) {
								Product relatedProduct = categoryProductList.get(j);
								String relatedProductFamily = "";
								if (relatedProduct.getProductFamily() != null) {
									relatedProductFamily = relatedProduct.getProductFamily();
									if ((!relatedProductFamily.equalsIgnoreCase("")) && (productFamilySet.add (relatedProductFamily))) {
										counter++;
										relatedProducts.add(relatedProduct);
									}
								}
							}
							
							//Still less related product now will add lower more with irrespective of product family 
							if (counter != 4) {
								for (Integer j = i - 1; ( (j >= 0) && (counter < 4) ); j-- ) {
									Boolean addedFalg = false;
									Product relatedProduct = categoryProductList.get(j);
									String relatedProductItemNumber = "";
									if (relatedProduct.getItemNo() != null){
										relatedProductItemNumber = relatedProduct.getItemNo();
										for (Product addeRelatedProduct : relatedProducts) {
											String addeRelatedProductItemNumber = "";
											if (addeRelatedProduct.getItemNo() != null){
												addeRelatedProductItemNumber = addeRelatedProduct.getItemNo();
											}
											if (addeRelatedProductItemNumber.equalsIgnoreCase(relatedProductItemNumber)) {
												addedFalg = true;
											}
										}
										if (!addedFalg) {
											counter++;
											relatedProducts.add(relatedProduct);
										}
									}
								}
								
								//Still less related product now will add higher more with irrespective of product family 
								if (counter != 4) {
									for (Integer j = i + 1; ( (j < totalProduct) && (counter < 4) ); j++ ) {
										Boolean addedFalg = false;
										Product relatedProduct = categoryProductList.get(j);
										String relatedProductItemNumber = "";
										if (relatedProduct.getItemNo() != null){
											relatedProductItemNumber = relatedProduct.getItemNo();
											for (Product addeRelatedProduct : relatedProducts) {
												String addeRelatedProductItemNumber = "";
												if (addeRelatedProduct.getItemNo() != null){
													addeRelatedProductItemNumber = addeRelatedProduct.getItemNo();
													if (addeRelatedProductItemNumber.equalsIgnoreCase(relatedProductItemNumber)) {
														addedFalg = true;
													}
												}
											}
											if (!addedFalg) {
												counter++;
												relatedProducts.add(relatedProduct);
											}
										}
									}
								}
							}
						}
					}
					
					Map <Long, String> productIds = new LinkedHashMap<Long, String>();
					for (Product relatedProduct : relatedProducts) {
						productIds.put(relatedProduct.getId(), productId);
					}
					
					//Checking if same related products are exist into the product
					List<com.kohler.beans.CCrossSelling> crossSellingList = product.getCrossselling();
					Boolean relatedChangedFlag = false;
					Long [] relatedProductIds = null;
					if ((crossSellingList == null) || (crossSellingList.isEmpty())) {
						relatedChangedFlag = true;
					} else {	
						for (com.kohler.beans.CCrossSelling crossSelling : crossSellingList) {
							if (crossSelling.getKey().equalsIgnoreCase(Constants.RELATED_PRODUCTS)) {
								relatedProductIds = crossSelling.getValues();
							}
						}
						if (relatedProductIds != null) {
							List <Long> relatedProductList = Arrays.asList(relatedProductIds);
							if (productIds.keySet().size() == relatedProductList.size()) {
								for (Long relatedProductId : relatedProductList) {
									if (!productIds.containsKey(relatedProductId)) {
										relatedChangedFlag = true;
									}
								}
							} else {
								relatedChangedFlag = true;
							}
						}
					}
					
					//Adding null if no need to update the product else adding related products
					if (relatedChangedFlag) {
						relatedProductMap.put(productItemNumber, productIds);
					} else {
						for (Map.Entry<Long, String> entry : productIds.entrySet()) {
							productIds.put(entry.getKey(), null);
						}
						relatedProductMap.put(productItemNumber, productIds);
					}
				}
			}	
		}
		return relatedProductMap;
	}
	
	/**
	 * 
	 * @param requestContext
	 * @param locale
	 * @return Returns all the products with its similar product from Different Category and Same product Family
	 */
	public Map<String, Map<Long, List<Long>>> getProductTypeBasedSimilarProducts (HstRequestContext requestContext, Locale locale) {
		Map<String, Map<Long, List<Long>>> crossPromotionsProductMap = new HashMap<String, Map<Long, List<Long>>>();
		Map<String,Map<String,List<Long>>> productFamilyCategoryList=new HashMap<String,Map<String,List<Long>>>();
		HippoBean rootBean = requestContext.getSiteContentBaseBean();
		HippoBean productFolderBean = null;
		if (rootBean != null) {
			final String myPath = PathUtils.normalizePath(Constants.PRODUCTS_PATH);
			productFolderBean = rootBean.getBean(myPath);
			if (productFolderBean == null) {
				productFolderBean = rootBean;
			}
		}		
		try{
			HstQuery productHstQuery = requestContext.getQueryManager().createQuery(productFolderBean);
			Filter discontinuedFilter = productHstQuery.createFilter();
	    	String fieldRestriction = Constants.NS_DISCONTINUED;
	    	discontinuedFilter.addNotEqualTo(fieldRestriction, true);
	    	productHstQuery.setFilter(discontinuedFilter);
	    	String relPath = PathUtils.normalizePath(Constants.PRODUCTS_FACETS_PATH);
	    	String fieldNameAttribute = Constants.NS_LIST_PRICE;
	    	productHstQuery.addOrderByAscending(fieldNameAttribute);
	    	String idFieldNameAttribute = Constants.NS_ITEM_NO;
	    	productHstQuery.addOrderByAscending(idFieldNameAttribute);
	    	String queryAsString = "xpath("+productHstQuery.getQueryAsString(true)+")";
			HippoFacetNavigationBean hippoFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(relPath, queryAsString);
			Set<Product> allProductList = new HashSet<Product>();
	    	if(hippoFacetNavigationBean != null){
				Iterator<HippoFolderBean> itr = hippoFacetNavigationBean.getFolders().iterator();
		        while(itr.hasNext()) {
		        	HippoFolderBean facet= itr.next();
		        	if(facet.getName().equalsIgnoreCase(Constants.PRODUCT_FAMILY)){
		        		for(HippoFolderBean productTypeFolderBean: facet.getFolders()){
							String productTypePath = productTypeFolderBean.getPath();
							productTypePath = productTypePath.substring(productTypePath.indexOf(relPath));
		        			HippoFacetNavigationBean productTypeFacetNavigationBean = ContentBeanUtils.getFacetNavigationBean(productTypePath, queryAsString);
							if(productTypeFacetNavigationBean != null){
		    					HippoResultSetBean resultSet = productTypeFacetNavigationBean.getResultSet();
		        				if (resultSet.getCount() > 0) {
		        					HippoDocumentIterator<HippoBean> iterator = resultSet.getDocumentIterator(HippoBean.class);
		        					Map<String,List<Long>> categoryProducts=new HashMap<String,List<Long>>();
		        					while (iterator.hasNext()) {
		        						Product product = (Product) iterator.next();
		        						allProductList.add(product);
		        						if(product.getKeys()!=null && product.getKeys().length!=0) {
		        						for(String categoryId : product.getKeys()) {
		        						List<Long> PrFamilyCategoryProductIds=categoryProducts.get(categoryId);
		        						if(PrFamilyCategoryProductIds==null){
		        							PrFamilyCategoryProductIds=new LinkedList<Long>();
		        							PrFamilyCategoryProductIds.add(product.getId());
		        							categoryProducts.put(categoryId, PrFamilyCategoryProductIds);
		        						}else if(PrFamilyCategoryProductIds.isEmpty())
		        						{
		        							PrFamilyCategoryProductIds=new LinkedList<Long>();
		        							PrFamilyCategoryProductIds.add(product.getId());
		        							categoryProducts.put(categoryId, PrFamilyCategoryProductIds);
		        						}else
		        						{
		        							PrFamilyCategoryProductIds.add(product.getId()); 
		        							categoryProducts.put(categoryId, PrFamilyCategoryProductIds);
		        						}
		        					  }	
		        					 } else{
		        						 continue;
		        					 }
		        					}
		        					if (!allProductList.isEmpty()) {
		        						productFamilyCategoryList.put(productTypeFolderBean.getName(), categoryProducts);
		        						//crossPromotionsProductMap = getProductTypeProductByPrice (productTypeProductList , crossPromotionsProductMap);
		        					}
		        				}
							}
		        		}
		        	}
		        }
	    	} 
	    	System.out.println("productFamilyCategoryList _PSC-->" +productFamilyCategoryList);
	    	crossPromotionsProductMap=getCrossPrmotoionsProduct(allProductList, productFamilyCategoryList);
	    	System.out.println("crossPromotionsProductMap _PSC-->" +crossPromotionsProductMap);
	    	
		}catch(HstComponentException e)
		{
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
			e.printStackTrace();
		}	
		return crossPromotionsProductMap;
	}
	
	
	
	
	
	/**
	 * Gets List of Similar productIds 
	 * @param session
	 * @param priceProducts
	 * @param index
	 * @return List 
	 */
	public Map <String, Map<Long, List<Long>>> getCrossPrmotoionsProduct (Set<Product> allProductList , Map<String,Map<String,List<Long>>> productFamilyCategoryList)
	{
		Map<Long, List<Long>> crossproductIdRelatedId=new HashMap<Long,List<Long>>();
		Map<String, Map<Long, List<Long>>> crossPromotionsProductItemNo=new HashMap<String, Map<Long, List<Long>>>();
		try{
		for(Product product: allProductList)
		{
			String productfamily= product.getProductFamily();
			List<String> categoryKeys=Arrays.asList(product.getKeys());
			List<Long> autoCalCrossProductIds=new LinkedList<Long>();
			int crossCount=0;
            Map<String,List<Long>> categoryProducts=productFamilyCategoryList.get(productfamily);
			for (Map.Entry<String, List<Long>> entry : categoryProducts.entrySet()){
				if(crossCount>=4)
				{
					crossproductIdRelatedId.put(product.getId(), autoCalCrossProductIds);
					break;
				}
				List<Long> ids=entry.getValue();	
				if(ids!=null&&ids.size()>0) {
					if(!categoryKeys.contains(entry.getKey()) && product.getId()!=ids.get(0) && !autoCalCrossProductIds.contains(ids.get(0))) {
						autoCalCrossProductIds.add(ids.get(0));
						crossCount++;
					} 
				}	 
			}
			if(crossCount<2)
			{
				for (Map.Entry<String, List<Long>> entry : categoryProducts.entrySet()){
					if(crossCount>=4)
					{
						crossproductIdRelatedId.put(product.getId(), autoCalCrossProductIds);
						break;
					}
					List<Long> ids=entry.getValue();	
					if(ids!=null&&ids.size()>1) {
						if(!categoryKeys.contains(entry.getKey()) && product.getId()!=ids.get(1) && !autoCalCrossProductIds.contains(ids.get(1))) {	 
							autoCalCrossProductIds.add(ids.get(1));
							crossCount++;
						}
					}			
				}	
			}

			if(crossCount<3)
			{
				for (Map.Entry<String, List<Long>> entry : categoryProducts.entrySet()){
					if(crossCount>=4)
					{
						crossproductIdRelatedId.put(product.getId(), autoCalCrossProductIds);
						break;
					}
					List<Long> ids=entry.getValue();	
					if(ids!=null&&ids.size()>2) {
						if(!categoryKeys.contains(entry.getKey()) && product.getId()!=ids.get(2) && !autoCalCrossProductIds.contains(ids.get(2))) {
							autoCalCrossProductIds.add(ids.get(2));
							crossCount++;
						} 
					}		
				}	
			}
			if(crossCount<4)
			{
				for (Map.Entry<String, List<Long>> entry : categoryProducts.entrySet()){
					if(crossCount>=4)
					{
						crossproductIdRelatedId.put(product.getId(), autoCalCrossProductIds);
						break;
					}
					List<Long> ids=entry.getValue();	
					if(ids!=null&&ids.size()>3) {
						if(!categoryKeys.contains(entry.getKey()) && product.getId()!=ids.get(3) && !autoCalCrossProductIds.contains(ids.get(3))) { 
							autoCalCrossProductIds.add(ids.get(3));
							crossCount++;
						} 
					}   
				}	
			}
			/*if(crossCount<5)
			{
				for (Map.Entry<String, List<Long>> entry : categoryProducts.entrySet()){
					if(crossCount>=4)
					{
						crossproductIdRelatedId.put(product.getId(), crossProductIds);
						break;
					}
					List<Long> ids=entry.getValue();	
					if(ids!=null && ids.size()>4) {
						if(!categoryKeys.contains(entry.getKey()) && product.getId()!=ids.get(4) && !crossProductIds.contains(ids.get(4)) ) {		 
							crossProductIds.add(ids.get(4));
							crossCount++;
						} 
					}			
				}	

			}*/
			List<com.kohler.beans.CCrossSelling> crossSellingList = product.getCrossselling();
			Long [] crossPromotionsProductIds = null;
			List<Long> crossProductIdsList=new LinkedList<Long>();
			for (com.kohler.beans.CCrossSelling crossSelling : crossSellingList) {
				if (crossSelling.getKey().equalsIgnoreCase(Constants.CROSS_PROMOTIONS)) {
					crossPromotionsProductIds = crossSelling.getValues();
					crossProductIdsList=Arrays.asList(crossPromotionsProductIds);
					break;
				}
			}
			List <Long> crossPromotionDefaultList = new LinkedList<Long>(crossProductIdsList);
			if(crossPromotionDefaultList!=null && crossPromotionDefaultList.contains(0l))
			 crossPromotionDefaultList.remove(0l);
			Collections.sort(autoCalCrossProductIds);
			Collections.sort(crossPromotionDefaultList);
			if(crossPromotionDefaultList!=null && !crossPromotionDefaultList.isEmpty()) {
				if(autoCalCrossProductIds!=null && !autoCalCrossProductIds.isEmpty()) {
					if(crossPromotionDefaultList.equals(autoCalCrossProductIds)) 
						crossproductIdRelatedId.put(product.getId(), null);	
					else {
					for(Long id: autoCalCrossProductIds){
						if(!crossPromotionDefaultList.contains(id))
					      crossPromotionDefaultList.add(id);
					}
					 if(crossPromotionDefaultList.size()<4)
					    crossproductIdRelatedId.put(product.getId(), crossPromotionDefaultList);
					 else
						 crossproductIdRelatedId.put(product.getId(), crossPromotionDefaultList.subList(0, 3));
					} 
				}else
				{
				  crossproductIdRelatedId.put(product.getId(), null);
				}
			} else
			{
				if(autoCalCrossProductIds!=null && !autoCalCrossProductIds.isEmpty())
				  crossproductIdRelatedId.put(product.getId(), autoCalCrossProductIds);
				else
					crossproductIdRelatedId.put(product.getId(), null);	
			}
		}
		for(Product product: allProductList)
		{
			 Map<Long,List<Long>> updatedList=new HashMap<Long,List<Long>>();
			 updatedList.put(product.getId(), crossproductIdRelatedId.get(product.getId()));
			 crossPromotionsProductItemNo.put(product.getItemNo(), updatedList);
		}
		
		}catch(Exception e){
			System.err.println("Exception in calculating crossPromotionsIds-->" +e);
			e.printStackTrace();
		}
		return crossPromotionsProductItemNo;
	}

}
