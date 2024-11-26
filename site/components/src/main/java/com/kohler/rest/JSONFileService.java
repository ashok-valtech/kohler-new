/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.rest;

import java.util.Date;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kohler.commons.json.beans.Product;
import com.kohler.commons.service.JSONFileValidator;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author abhash.budayak
 *
 */
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Path("/validation")
public class JSONFileService{
	
	/**
	 * @param requestedDate, last updated date
	 * @param productId
	 * @return boolean
	 */
	@POST
	@Path("/isfreshjson")
	public boolean isJsonAllowed(Map<String, String> dates) {
		boolean isAllowed = false;
		JSONFileValidator validator = JSONFileValidator.getInstance();
		isAllowed = validator.isJsonAllowed(new Date(dates.get("inputDate")), new Date(dates.get("modifiedDate")));
		return isAllowed;
	}
	/**
	 * @param jsonData
	 * @throws JSONException
	 * @return boolean
	 */
	@POST
	@Path("/isvalid")
	@ResponseBody
	public boolean isValidJSON(String jsonData) {
		boolean isValid = false;
		JSONFileValidator validator = JSONFileValidator.getInstance();
		Map<String, Product> products = validator.isValidJSON(jsonData);
		if(null != products){
			 isValid = true;
		}
		System.out.println("JSONFileService : isValidJSONFormat : " + isValid);
		return isValid;
	}
}
