/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.annotations.Persistable;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.rest.BaseRestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.json.beans.Dealer;
import com.kohler.commons.service.APACDealerAPIImpl;
import com.kohler.commons.servicebase.DealerBase;
import com.kohler.commons.util.CommonUtil;
import com.kohler.utils.DealerConversionUtil;
import com.kohler.utils.ValidateHeaders;

import net.sf.json.JSONObject;

@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
@Path("/storeservice/")
public class StoreLocatorServices extends BaseRestResource {

	private static final Logger LOG = LoggerFactory.getLogger(StoreLocatorServices.class);

	@DELETE
	@Path("/")
	@Persistable
	public  Map<String, Object> flushDealers(@Context HttpHeaders headers) {
		Map<String, Object> returnMap = new HashMap<String, Object> ();
		LOG.info("Deleting Dealers");
		ValidateHeaders ValidateHeaders = new ValidateHeaders();
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			if(!ValidateHeaders.ValidateApiKey(headers, locale)){
				  returnMap.put (Constants.MESSAGE, "Invalid Api Key");
				  returnMap.put (Constants.STATUS, Constants.ERROR_STATUS_VALUE);
				  return returnMap;
				}
			LOG.info("Session created with hippo @Persistable");
			Session session=requestContext.getSession();
			DealerBase dealerBase = new DealerBase(new APACDealerAPIImpl(requestContext.getSiteContentBasePath(), Constants.DEALERS_PATH, language, country));
			dealerBase.dropAllDealers(session);
			LOG.info("All dealer documents deleted successfully!!!");
		} catch (RepositoryException e) {
			e.printStackTrace();
		} 
		  returnMap.put (Constants.MESSAGE, "SUCCESS");
		  return returnMap;
		
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Persistable
	public Map<String, String> createDealers(@Multipart("api-key") String apiKey,
			@Multipart("file") Attachment attachment) throws IOException {
                LOG.info("Creating Dealers");
		HstRequestContext requestContext = RequestContextProvider.get();
		Mount mount = requestContext.getResolvedMount().getMount();
		String language = CommonUtil.getLanguage(mount);
		String country = CommonUtil.getCountry(mount);
		Locale locale = new Locale(language, country);
		ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
		ResourceBundle bundleApacLabel = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
		String invalidApiKeyMsg = resourceBundle.getPropertyValue(bundleApacLabel,"invalidAPIKeyMessage");
		String fileFailureMsg = resourceBundle.getPropertyValue(bundleApacLabel,"fileFailureMessage");
		String fileMissingMsg = resourceBundle.getPropertyValue(bundleApacLabel,"fileMissingMessage");
		String fileSuccessMsg = resourceBundle.getPropertyValue(bundleApacLabel,"fileSuccessMessage");
		String dealerSuccessMsg = resourceBundle.getPropertyValue(bundleApacLabel,"dealerSuccessMessage");
		
		Map<String, String> returnMap = new HashMap<String, String>();
		if (apiKey.isEmpty()) {
			LOG.info("apikey should not be empty ");
			returnMap.put(Constants.FLAG, fileFailureMsg);
			returnMap.put(Constants.MESSAGE, invalidApiKeyMsg);
			return returnMap;
		} else if (attachment == null) {
			LOG.info("Missing file part");
			returnMap.put(Constants.FLAG, fileFailureMsg);
			returnMap.put(Constants.MESSAGE, fileMissingMsg);
			return returnMap;
		} else {
			Map<String, Integer> columnMapping = null;
			LOG.info("Creating Dealers");
			List<Dealer> dealers = new ArrayList<Dealer>();
			DealerConversionUtil dealerConversionUtil = new DealerConversionUtil();
			List<String> finalFieldsList = dealerConversionUtil.decideFields();
			ValidateHeaders ValidateHeaders = new ValidateHeaders();
			try {
				if (!ValidateHeaders.ValidateApiKey(apiKey, locale)) {
					returnMap.put(Constants.FLAG, fileFailureMsg);
					returnMap.put(Constants.MESSAGE, invalidApiKeyMsg);
					return returnMap;
				}
				String fileName = attachment.getContentDisposition().getParameter("filename");
				InputStream inputStream = attachment.getObject(InputStream.class);

				XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> iterator = sheet.iterator();
				while (iterator.hasNext()) {
					Row row = iterator.next();
					if (row.getRowNum() != 0) {
						Dealer dealer = dealerConversionUtil.prepareObjectFromRow(row, columnMapping);
						dealers.add(dealer);
					} else {
						columnMapping = dealerConversionUtil.checkColumnHeader(row, finalFieldsList);
					}
				}
				workbook.close();
				inputStream.close();
				Session session = requestContext.getSession();
				LOG.info("Session created with hippo @Persistable");
				DealerBase dealerBase = new DealerBase(new APACDealerAPIImpl(requestContext.getSiteContentBasePath(), Constants.DEALERS_PATH, language, country));
				List<String> successfulDealers = dealerBase.createDelearFolder(session, dealers);
				returnMap.put(Constants.FLAG, fileSuccessMsg);
				returnMap.put(Constants.MESSAGE, dealerSuccessMsg);
				returnMap.put("DealerList", successfulDealers.toString());
				LOG.info("All dealer documents added successfully!!!");
			} catch (RepositoryException e1) {
				e1.printStackTrace();
			}
		}
		return returnMap;
	}
	
	@GET
	@Path("/dealer")
	@Produces({ MediaType.TEXT_PLAIN })
	public String getCountries() {

		Set<String> listOfStates = new TreeSet<String> ();
		HstRequestContext requestContext = RequestContextProvider.get ();
		HippoBean rootBean = requestContext.getSiteContentBaseBean ();
		HstQueryResult queryResult = null;

		try {
			@SuppressWarnings("unchecked")
			HstQuery query = requestContext.getQueryManager ().createQuery (rootBean, com.kohler.beans.Dealer.class);
			// Filter filter = query.createFilter ();
			// filter.addEqualTo ("kohler:country", country);
			// query.setFilter (filter);
			queryResult = query.execute ();

			if (queryResult.getTotalSize () >= 1) {
				HippoBeanIterator iterator = queryResult.getHippoBeans ();
				while (iterator.hasNext ()) {
					HippoBean bean = iterator.nextHippoBean ();
					if (bean != null && bean instanceof com.kohler.beans.Dealer) {
						com.kohler.beans.Dealer currentDealer = (com.kohler.beans.Dealer) bean;
						listOfStates.add (currentDealer.getCountry ());
					}
				}
			}
		} catch (IllegalStateException | QueryException e) {
			e.printStackTrace ();
		}
		return optionsTagCreator (listOfStates.toString (), "Country");

	}



	@GET
	@Path("/dealer/{country}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String getStates(@PathParam("country") String country) {

		Set<String> listOfStates = new TreeSet<String> ();
		HstRequestContext requestContext = RequestContextProvider.get ();
		HippoBean rootBean = requestContext.getSiteContentBaseBean ();
		HstQueryResult queryResult = null;

		try {
			@SuppressWarnings("unchecked")
			HstQuery query = requestContext.getQueryManager ().createQuery (rootBean, com.kohler.beans.Dealer.class);
			Filter filter = query.createFilter ();
			filter.addEqualTo ("kohler:country", country);
			query.setFilter (filter);
			queryResult = query.execute ();

			if (queryResult.getTotalSize () >= 1) {
				HippoBeanIterator iterator = queryResult.getHippoBeans ();
				while (iterator.hasNext ()) {
					HippoBean bean = iterator.nextHippoBean ();
					if (bean != null && bean instanceof com.kohler.beans.Dealer) {
						com.kohler.beans.Dealer currentDealer = (com.kohler.beans.Dealer) bean;
						listOfStates.add (currentDealer.getState ());
					}
				}
			}
		} catch (IllegalStateException | QueryException e) {
			e.printStackTrace ();
		}
		return optionsTagCreator (listOfStates.toString (), "State");

	}



	@GET
	@Path("/dealer/{country}/{state}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String getCities(@PathParam("country") String country, @PathParam("state") String state) {

		Set<String> listOfStates = new TreeSet<String> ();
		HstRequestContext requestContext = RequestContextProvider.get ();
		HippoBean rootBean = requestContext.getSiteContentBaseBean ();
		HstQueryResult queryResult = null;

		try {
			@SuppressWarnings("unchecked")
			HstQuery query = requestContext.getQueryManager ().createQuery (rootBean, com.kohler.beans.Dealer.class);
			// Filter filter = query.createFilter ();
			Filter mainFilter = query.createFilter ();
			mainFilter.addEqualTo ("kohler:country", country.trim ());

			Filter filter = query.createFilter ();
			filter.addEqualTo ("kohler:state", state.trim ());
			mainFilter.addAndFilter (filter);
			query.setFilter (mainFilter);
			queryResult = query.execute ();

			if (queryResult.getTotalSize () >= 1) {
				HippoBeanIterator iterator = queryResult.getHippoBeans ();
				while (iterator.hasNext ()) {
					HippoBean bean = iterator.nextHippoBean ();
					if (bean != null && bean instanceof com.kohler.beans.Dealer) {
						com.kohler.beans.Dealer currentDealer = (com.kohler.beans.Dealer) bean;
						listOfStates.add (currentDealer.getCity ());
					}
				}
			}
		} catch (IllegalStateException | QueryException e) {
			e.printStackTrace ();
		}

		return optionsTagCreator (listOfStates.toString (), "City");
	}



	private String optionsTagCreator(String listOfElements, String type) {
		List<String> currentList = Arrays.asList (listOfElements.replace ("[", "").replace ("]", "").split (","));
		String completeTag = "";
		for (String currentElementValue : currentList) {
			String currentElement = "<option value=\""+currentElementValue.trim()+"\">" + currentElementValue.trim() + "</option>";
			completeTag = completeTag + currentElement;
		}
		return completeTag;
	}
	
	@POST
	@Path("/getBingResponse/{ipAddress}")
	@Persistable
	public JSONObject getBingResponse(@Context HttpHeaders headers, @PathParam("ipAddress") String ipAddress) {
		JSONObject jsonObject = null;
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
			ResourceBundle bundleApacLabel = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
			String ipUrl = resourceBundle.getPropertyValue(bundleApacLabel, "bingIpLocation");
			String key = resourceBundle.getPropertyValue(bundleApacLabel, "bingCurrentLocationKey");
			String app = resourceBundle.getPropertyValue(bundleApacLabel, "appType");
			SimpleDateFormat dateFormat = new SimpleDateFormat("mm-dd-yy@hh:mma");
			String formattedDate = dateFormat.format(new Date()).toString();
			String version = resourceBundle.getPropertyValue(bundleApacLabel, "bingVersion");
			MessageFormat messageFormat = new MessageFormat(ipUrl);
			String ipUrl1 = messageFormat.format(new Object[] { ipAddress, key, app, formattedDate, version });
			jsonObject = getResponse(ipUrl1);
			return jsonObject;
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e.getMessage(), e);
			}
			return null;
		}
	}
	
	private JSONObject getResponse(String ipUrl) {
		JSONObject jsonObject = null;
		HttpURLConnection conn =null;
		try {
			URL url = new URL(ipUrl);
			conn= (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			InputStream inputStream = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
			String output;
			String output1 = "";
			while ((output = br.readLine()) != null) {
				output1 = output1 + output;
			}
			jsonObject = new ObjectMapper().readValue(output1, JSONObject.class);
			conn.disconnect();
			return jsonObject;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(conn!=null)
				conn.disconnect();
		}
		return jsonObject;
	}
}
