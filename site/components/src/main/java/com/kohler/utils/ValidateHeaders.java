/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;

/**
 * Class for Validating Rest API Headers. 
 * @author ashok.kumar
 * Date: 09/15/2017
 * @version 1.0
 */

public class ValidateHeaders {
	private static final Logger LOG = LoggerFactory.getLogger(ValidateHeaders.class);

	
	public Boolean ValidateApiKey(@Context HttpHeaders headers,Locale locale)
	{
		try {
			ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
			ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_PRODUCT_URLS, locale);
			String resourceapi_key = resourceBundle.getPropertyValue(bundle, Constants.API_KEY);
			LOG.info("resourceapi_key-->" +resourceapi_key);
			String apiKey = headers.getRequestHeader("api-key").get(0);
			if(apiKey.trim().equals(resourceapi_key)){
		      return true;  		
			}
	    }	 
		catch (Exception ex) {
			return false;
		}	
		return false;
	}
	
	public Boolean ValidateApiKey(String apiKey,Locale locale)
	{
		try {
			ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
			ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_PRODUCT_URLS, locale);
			String resourceapi_key = resourceBundle.getPropertyValue(bundle, Constants.API_KEY);
			if(apiKey.trim().equals(resourceapi_key)){
		      return true;  		
			}
	    }	 
		catch (Exception ex) {
			return false;
		}	
		return false;
	}

}
