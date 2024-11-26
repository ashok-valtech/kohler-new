package com.kohler.commons.service;

import java.util.Locale;
import java.util.Map;

import com.kohler.commons.json.beans.Product;

/**
 *  Interface provides template for getting filter alternate Names service
 *  @author dhanwan.r
 * 	Date: 05/24/2019
 * 	@version 1.0
 */
public interface AlternateFilterNameService {

	public Map<String, String> getAlternateFilterNames (Map <String, Product> products, Locale locale);
	
	public Map<String, String> getAlternative (Product product, String langugeJsonFieldSuffix, String langugeAltJsonFieldSuffix); 
	
	public Map<String, String> getAlternativeAttributes (Map<String, Object> attributesOld, String langugeJsonFieldSuffix, Boolean flag);
	
	
}
