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
 * @author dhanwan.r
 * Date: 08/30/2017
 * @version 1.0 
 */
public class CPDBProduct extends AbstractCPDBProduct{
	
	public CPDBProduct(){
		
	}
	
	public CPDBProduct(CPDBProductAPI cpdbProductAPI){
		super(cpdbProductAPI);
	}

	@Override
	public Map<String, Object> createProducts(Session session, Map<String, Product> products) {
		// TODO Auto-generated method stub
		return cpdbProductAPI.createProducts(session, products);
	}

	@Override
	public Map<String, String> dropProductsfromJcr(Session session, List<String> productIds) {
		// TODO Auto-generated method stub
		return cpdbProductAPI.dropProductsfromJcr(session, productIds);
	}

	@Override
	public boolean deleteOtherProducts(Session session, List<String> validproductIds) {
		// TODO Auto-generated method stub
		return cpdbProductAPI.deleteOtherProducts(session, validproductIds);
	}

	@Override
	public List<String> getAllProductIds(Session session) {
		// TODO Auto-generated method stub
		return cpdbProductAPI.getAllProductIds(session);
	}
	
	@Override
	public Map <String, Boolean> updatedRelatedProducts(Session session, Map<String, Map <Long, String>> relatedProductMap, long start,
			long end, Boolean logenable) {
		// TODO Auto-generated method stub
		return cpdbProductAPI.updatedRelatedProducts(session, relatedProductMap, start, end, logenable);
	}
	
	@Override
	public Map <String, Boolean> updateCrossPromotionsProducts(Session session, Map<String, Map<Long, List<Long>>> relatedProductMap) {
		// TODO Auto-generated method stub
		return cpdbProductAPI.updateCrossPromotionsProducts(session, relatedProductMap);
	}
}
