/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author dhanwan.r
 *
 */
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Path("/compare")
public class ProductCompareService {

	@GET
	@ResponseBody
	public String getProducts(@Context Request request){
		System.out.println("I am calling");
		return "Getting";
	}
}
