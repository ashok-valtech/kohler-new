/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.kohler.commons.json.beans.Product;

/**
 * 
 * @description the validation of the supplied JSON from CPDB 
 *
 */
public class JSONFileValidator {

	private static final Logger LOG = LoggerFactory.getLogger (JSONFileValidator.class);
	private static JSONFileValidator jsonFileValidator = null;

	public JSONFileValidator() {
		super();
	}

	public static JSONFileValidator getInstance() {
		if (null == jsonFileValidator) {
			jsonFileValidator = new JSONFileValidator();
		}
		return jsonFileValidator;
	}

	/**
	 * @purpose Validate the json document by comparing the date in the header
	 *          with the first product's created date or last modified date
	 * @param requestedDate
	 * @param productLastUpdateDate
	 * @return boolean
	 */
	public boolean isJsonAllowed(Date requestedDate, Date productLastUpdateDate) {
		boolean isAllowed = false;
		if(null == requestedDate){
			isAllowed = false;
		} else if(null ==  productLastUpdateDate){
			isAllowed = true;
		} else if(requestedDate.after(productLastUpdateDate)){
			isAllowed = true;
		}
		return isAllowed;
	}
	
	/**
	 * @purpose Validate the format supplied to upload data to CMS.
	 * @param jsonData
	 * @return boolean
	 */
	public Map<String, Product> isValidJSON(String jsonData) {
		Map<String, Product> products =  null;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			products = mapper.readValue(jsonData,  new TypeReference<Map<String, Product>>(){});
		} catch (JsonMappingException jme) {
			LOG.error(jme.toString());
		} catch(IOException ie ) {
			LOG.error(ie.toString());
		}
		return products;
	}
}
