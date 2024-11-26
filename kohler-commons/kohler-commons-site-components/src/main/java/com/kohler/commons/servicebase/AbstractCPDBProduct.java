/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/

package com.kohler.commons.servicebase;

import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import com.kohler.commons.json.beans.Product;
import com.kohler.commons.servicebase.product.CPDBProductAPI;

/**
 * Abstract class for CPDB product service
 * @author dhanwan.r
 * Date: 08/30/2017
 * @version 1.0 
 */
public abstract class AbstractCPDBProduct {
	
	public CPDBProductAPI cpdbProductAPI;

	public abstract Map<String, Object> createProducts(Session session, Map<String, Product> products);
	
	public abstract Map<String,String> dropProductsfromJcr(Session session, List<String> productIds);
	
	public abstract boolean deleteOtherProducts(Session session, List<String> validproductIds);
	
	public abstract List<String> getAllProductIds(Session session);
	
	public abstract Map <String, Boolean> updatedRelatedProducts(Session session, Map<String, Map <Long, String>> relatedProductMap, long start, long end, Boolean logenable);
	
	public abstract Map <String, Boolean> updateCrossPromotionsProducts(Session session, Map<String, Map<Long, List<Long>>> relatedProductMap);
	
	public AbstractCPDBProduct(){
	}
	
	public AbstractCPDBProduct(CPDBProductAPI cpdbProductAPI){
		this.cpdbProductAPI = cpdbProductAPI;
	}
}
