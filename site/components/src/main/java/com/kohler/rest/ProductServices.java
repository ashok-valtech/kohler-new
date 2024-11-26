/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.rest;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.annotations.Persistable;
import org.hippoecm.hst.content.beans.ObjectBeanPersistenceException;
import org.hippoecm.hst.content.beans.manager.workflow.BaseWorkflowCallbackHandler;
import org.hippoecm.hst.content.beans.manager.workflow.WorkflowPersistenceManager;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.site.HstServices;
import org.onehippo.repository.documentworkflow.DocumentWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.jcraft.jsch.SftpATTRS;
import com.kohler.beans.Filter;
import com.kohler.beans.FilterCompound;
import com.kohler.beans.SeoSupport;
import com.kohler.commons.components.ProductListComponent;
import com.kohler.commons.components.ProductServiceComponent;
import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.components.SEOUpdateComponent;
import com.kohler.commons.components.search.SuggestionService;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.json.beans.Product;
import com.kohler.commons.service.APACCPDBProductAPIImpl;
import com.kohler.commons.service.AlternateFilterNameService;
import com.kohler.commons.service.AlternateFilterNameServiceImpl;
import com.kohler.commons.service.SwatchColorNameFinderService;
import com.kohler.commons.servicebase.CPDBProduct;
import com.kohler.commons.taxonomies.TaxonomyQueryService;
import com.kohler.commons.util.CommonUtil;
import com.kohler.utils.SFTPService;
import com.kohler.utils.ValidateHeaders;

@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
@Path("/productservice/")
public class ProductServices extends KohlerRestServices {

	private static final Logger LOGGER = LoggerFactory.getLogger (ProductServices.class);
	
	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	
	ValidateHeaders validateHeaders = new ValidateHeaders();

	@POST
	@Path("/products")
	@Persistable
	public Map<String, Object> createProducts(@Context HttpHeaders headers, Map<String, Product> products) {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			System.out.println("CreateProducts service initiated");
			long lStartTime = System.currentTimeMillis();
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			if (!validateHeaders.ValidateApiKey(headers, locale)) {
				returnMap.put(Constants.MESSAGE, Constants.INVALID_HEADERS);
				returnMap.put(Constants.STATUS, Constants.ERROR_STATUS_VALUE);
				return returnMap;
			}
			AlternateFilterNameService alternateFilterNameService = new AlternateFilterNameServiceImpl();
			Map<String, String> alternateMap = alternateFilterNameService.getAlternateFilterNames(products, locale);
			Session session = requestContext.getSession();
			CPDBProduct cpdbProduct = new CPDBProduct(new APACCPDBProductAPIImpl(requestContext.getSiteContentBasePath(), Constants.PRODUCTS_PATH, language, country));
			returnMap = cpdbProduct.createProducts(session, products);
			long lEndTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			System.out.println("Execution time for creating products size "+products.size()+" is " + formatter.format((lEndTime - lStartTime) / 1000d) + " seconds");
			lStartTime = System.currentTimeMillis();
			updateAlternateFilterNames(alternateMap, requestContext, locale);
			lEndTime = System.currentTimeMillis();
			System.out.println("Execution time for update alternate filtername "+products.size()+" is " + formatter.format((lEndTime - lStartTime) / 1000d) + " seconds");
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		} 
		return returnMap;
	}

	@POST
	@Path("/addupdateAndKeepProducts")
	@Persistable
	public Map<String, Object> addOrUpdateProducts(@Context HttpHeaders headers, Map<String, Product> products) {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			if (!validateHeaders.ValidateApiKey(headers, locale)) {
				returnMap.put(Constants.MESSAGE, Constants.INVALID_HEADERS);
				returnMap.put(Constants.STATUS, Constants.ERROR_STATUS_VALUE);
				return returnMap;
			}
			Session session = requestContext.getSession();
			CPDBProduct cpdbProduct = new CPDBProduct(
					new APACCPDBProductAPIImpl(requestContext.getSiteContentBasePath(), Constants.PRODUCTS_PATH, language, country));
			returnMap = cpdbProduct.createProducts(session, products);
			if (returnMap.get(Constants.PAYLOAD) != null) {
				this.deleteOtherProducts(session, cpdbProduct, returnMap);
			}
			this.updateSwatch(returnMap, language, country);
			this.updateSuggestion(returnMap, locale);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		} 
		return returnMap;
	}

	private void deleteOtherProducts (Session session, CPDBProduct cpdbProduct, Map<String, Object> returnMap) {
		try {
			Object payloadObj = returnMap.get(Constants.PAYLOAD);
			@SuppressWarnings("unchecked")
			List<String> productIds = (List<String>) payloadObj;
			cpdbProduct.deleteOtherProducts(session, productIds);
		} catch (Exception ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
	}
	
	@DELETE
	@Path("/products")
	@Persistable
	public Map<String, String> dropProducts(@Context HttpHeaders headers, List<String> productids) {
		Map<String, String> deleteProductIds = new HashMap<>();
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			if (!validateHeaders.ValidateApiKey(headers, locale)) {
				deleteProductIds.put(Constants.MESSAGE, Constants.INVALID_HEADERS);
				deleteProductIds.put(Constants.STATUS, Constants.ERROR_STATUS_VALUE);
				return deleteProductIds;
			}
			Session session = requestContext.getSession();
			CPDBProduct cpdbProduct = new CPDBProduct(new APACCPDBProductAPIImpl(requestContext.getSiteContentBasePath(), Constants.PRODUCTS_PATH, language, country));
			deleteProductIds = cpdbProduct.dropProductsfromJcr(session, productids);
			Map<String, Object> returnMap = new HashMap<>();
			this.updateSwatch(returnMap, language, country);
			this.updateSuggestion(returnMap, locale);
			return deleteProductIds;

		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return deleteProductIds;
		} 
	}
	
	@DELETE
	@Path("/deleteancestorproducts/{childCategoryKey}")
	@Persistable
	public Map<String, String> deleteProductsByAncestors(@Context HttpHeaders headers, @PathParam("childCategoryKey") String childCategoryKey) {
		Map<String, String> deleteProductIds = new HashMap<>();
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			if (!validateHeaders.ValidateApiKey(headers, locale)) {
				deleteProductIds.put(Constants.MESSAGE, Constants.INVALID_HEADERS);
				deleteProductIds.put(Constants.STATUS, Constants.ERROR_STATUS_VALUE);
				return deleteProductIds;
			}
			ProductListComponent plc= new ProductListComponent();
			List<String> productids=plc.getAncestorProducts(childCategoryKey, requestContext);
			Session session = requestContext.getSession();
			CPDBProduct cpdbProduct = new CPDBProduct(new APACCPDBProductAPIImpl(requestContext.getSiteContentBasePath(), Constants.PRODUCTS_PATH, language, country));
			deleteProductIds = cpdbProduct.dropProductsfromJcr(session, productids);
			Map<String, Object> returnMap = new HashMap<>();
			this.updateSwatch(returnMap, language, country);
			this.updateSuggestion(returnMap, locale);
			return deleteProductIds;
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return deleteProductIds;
		} 
	}

	@DELETE
	@Path("/keepproducts")
	@Persistable
	public Boolean deleteProducts(@Context HttpHeaders headers, List<String> productids) {
		LOGGER.info("Calling remove invalide product from system "); 
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Session session = requestContext.getSession();
			CPDBProduct cpdbProduct = new CPDBProduct(new APACCPDBProductAPIImpl(requestContext.getSiteContentBasePath(), Constants.PRODUCTS_PATH, language, country));
			Boolean keepProducts = cpdbProduct.deleteOtherProducts(session, productids);
			Locale locale = new Locale(language, country); 
			Map<String, Object> returnMap = new HashMap<>();
			this.updateSwatch(returnMap, language, country);
			this.updateSuggestion(returnMap, locale);
			/*this.updateRelatedProducts(requestContext, locale, -1, -1, true);
			this.updateCrossPromotionsProducts(requestContext, locale);*/
			return keepProducts;
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return false;
		} 
	}
	
	@GET
	@Path("/taxonomy")
	public Map<String, String> getTaxonomy(@Context HttpHeaders headers) throws RepositoryException {
		Map<String, String> returnMap = new HashMap<>();
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Session session = requestContext.getSession();
			TaxonomyQueryService taxonomyQueryService = new TaxonomyQueryService (session, language, country);
			returnMap = taxonomyQueryService.getTaxonomyCategories (Constants.TAXONOMY_NODE_NAME);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return returnMap;
	}

	@GET
	@Path("/products")
	@Persistable
	public List<String> getProductIds(@Context HttpHeaders headers) throws Exception {
		System.out.println("In getProductIds service---> com.kohler.rest.ProductServices ");
		List<String> productIds = new ArrayList<>();
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			if (!validateHeaders.ValidateApiKey(headers, locale)) {
				productIds.add("Invalid Api_key");
				return productIds;
			}
			Session session = requestContext.getSession();
			CPDBProduct cpdbProduct = new CPDBProduct(new APACCPDBProductAPIImpl(requestContext.getSiteContentBasePath(), Constants.PRODUCTS_PATH, language, country));
			productIds=cpdbProduct.getAllProductIds(session);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			productIds.add("Error in fetching product Ids");

		} 
		return productIds;
	}
	
	@POST
	@Path("/swatch")
	@Persistable
	public Map<String, Object> updateSwatch(@Context HttpHeaders headers) {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			if (!validateHeaders.ValidateApiKey(headers, locale)) {
				returnMap.put(Constants.MESSAGE, Constants.INVALID_HEADERS);
				returnMap.put(Constants.STATUS, Constants.ERROR_STATUS_VALUE);
				return returnMap;
			}
			this.updateSwatch(returnMap, language, country);
		} catch (Exception e) {
			returnMap.put(Constants.STATUS, Constants.FAILURE);	
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return returnMap;
	}
	
	@POST
	@Path("/suggestion")
	@Persistable
	public Map<String, Object> updateSuggestion(@Context HttpHeaders headers) {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			if (!validateHeaders.ValidateApiKey(headers, locale)) {
				returnMap.put(Constants.MESSAGE, Constants.INVALID_HEADERS);
				returnMap.put(Constants.STATUS, Constants.ERROR_STATUS_VALUE);
				return returnMap;
			}
			this.updateSuggestion(returnMap, locale);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			returnMap.put(Constants.STATUS, Constants.FAILURE);	
		} 
		return returnMap;
	}
	
	
	@POST
	@Path("/updatedrelatedproducts/{start}/{end}/{logenable}")
	@Persistable
	public Map<String, Boolean> updatedRelatedProducts(@Context HttpHeaders headers,@PathParam("start") long start,@PathParam("end") long end,@PathParam("logenable") Boolean logenable) {
		Map <String, Boolean> listUpdatedproducts = null;
		try {
			long lStartTime = System.currentTimeMillis();
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country); 
			listUpdatedproducts = updateRelatedProducts(requestContext, locale, lStartTime, end, logenable);
			long lEndTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			System.out.println("Execution time is " + formatter.format((lEndTime - lStartTime) / 1000d) + " seconds");			
			return listUpdatedproducts;
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return listUpdatedproducts;
		} 
	}
	
	
	@POST
	@Path("/suggestionswatchsimilar")
	@Persistable
	public Map<String, Object> suggestionSwatchSimilar(@Context HttpHeaders headers) {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			if (!validateHeaders.ValidateApiKey(headers, locale)) {
				returnMap.put(Constants.MESSAGE, Constants.INVALID_HEADERS);
				returnMap.put(Constants.STATUS, Constants.ERROR_STATUS_VALUE);
				return returnMap;
			}
			this.updateSwatch(returnMap, language, country);
			this.updateSuggestion(returnMap, locale);
			this.updateRelatedProducts(requestContext, locale, -1, -1, true);
			this.updateCrossPromotionsProducts(requestContext, locale);
			returnMap.put(Constants.STATUS, Constants.SUCCESS);	
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			returnMap.put(Constants.STATUS, Constants.FAILURE);	
		} 
		return returnMap;
	}
	
	@HEAD
	@Path("/jsonFileData/{jsonFileName}")
	@Persistable
	public Response getJsonFileMetadata(@Context HttpHeaders headers, @PathParam("jsonFileName") String jsonFileName) {
		Response response = null;
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			SFTPService sftpService = new SFTPService(locale);
			SftpATTRS SftpATTRS = sftpService.getFileMetadata(jsonFileName);
			String SFTPFileModifiedDateTime = null;
			if (SftpATTRS != null) {
				SFTPFileModifiedDateTime = SftpATTRS.getMtimeString();
			}
			response = Response.ok("this body will be ignored")
                    .header("Last-Modified", SFTPFileModifiedDateTime)
                    .build();
		} catch (Exception e) {
			response = Response.status(500).build();
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		} 
		return response;
	}
	
	@GET
	@Path("/jsonFileData/{jsonFileName}")
	@Persistable
	public String getJsonFileData(@Context HttpHeaders headers, @PathParam("jsonFileName") String jsonFileName) {
		String response = "";
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			SFTPService sftpService = new SFTPService(locale);
			response = sftpService.getFileData(jsonFileName);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}	
		} 
		return response;
	}
	
	@GET
	@Path("/suggestionswatch")
	@Persistable
	public Map<String, Object> suggestionSwatch (@Context HttpHeaders headers) {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			if (!validateHeaders.ValidateApiKey(headers, locale)) {
				returnMap.put(Constants.MESSAGE, Constants.INVALID_HEADERS);
				returnMap.put(Constants.STATUS, Constants.ERROR_STATUS_VALUE);
				return returnMap;
			}
			this.updateSwatch (returnMap, language, country);
			this.updateSuggestion(returnMap, locale);
			returnMap.put(Constants.STATUS, Constants.SUCCESS);	
		} catch (Exception e) {
			returnMap.put(Constants.STATUS, Constants.FAILURE);	
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		} 
		return returnMap;
	}
	
	private void updateSwatch (Map<String, Object> returnMap, String language, String country) {
		try{
			SwatchColorNameFinderService swatchColorNameFinderService = HstServices.getComponentManager().getComponent(Constants.SWATCH_COLOR_NAME_FINDER_SERVICE + language + country);
			if(swatchColorNameFinderService!= null) {
				swatchColorNameFinderService.getSwatchColorNameByCall();
				returnMap.put(Constants.STATUS, Constants.SUCCESS);	
			}
			System.out.println("IN updateSwatch  Service and calling swatchColorNameFinderService getSwatchColorNameByCall-->"+ !(swatchColorNameFinderService == null));
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("IN updateSwatch  Service and calling swatchColorNameFinderService getSwatchColorNameByCall-->"+ !(swatchColorNameFinderService == null));
			}
		}catch(Exception e)
		{
			System.out.println("Error in getting swatchcolorName");
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}	
		    returnMap.put(Constants.STATUS, Constants.FAILURE);	
		}
	}
	
	private void updateSuggestion (Map<String, Object> returnMap, Locale locale)  {
		try{
			ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
			String suggestionServiceName = resourceBundle.getPropertyValue(bundle, Constants.SUGGESTION_SERVICE);
			SuggestionService suggestionService = HstServices.getComponentManager().getComponent(suggestionServiceName);
			if (suggestionService != null) {
				suggestionService.createInMemoryIndexByCall();
			}
			System.out.println("IN updateSuggestion  Service and calling suggestionService createInMemoryIndexByCall-->"+ !(suggestionService == null));
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("IN updateSuggestion  Service and calling suggestionService createInMemoryIndexByCall-->"+ !(suggestionService == null));
			}
			returnMap.put(Constants.STATUS, Constants.SUCCESS);	
		}catch(Exception e)
		{
			System.out.println("Error in updateSuggestion");
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}	
		    returnMap.put(Constants.STATUS, Constants.FAILURE);	
		}
	}
	
	private Map <String, Boolean> updateRelatedProducts (HstRequestContext requestContext, Locale locale, long start, long end, Boolean logenable) throws RepositoryException {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("In updateRelatedProducts");
		}
		Session session = requestContext.getSession();
		ProductServiceComponent productServiceComponent= new ProductServiceComponent();
		Map<String, Map <Long, String>> relatedProductMap = productServiceComponent.getCategoryProductTypeBasedSimilarProducts (requestContext, locale);
		CPDBProduct cpdbProduct = new CPDBProduct(new APACCPDBProductAPIImpl(requestContext.getSiteContentBasePath(), Constants.PRODUCTS_PATH, locale.getLanguage (), locale.getCountry ()));
		return cpdbProduct.updatedRelatedProducts(session, relatedProductMap, start, end, logenable);
	}
	
	@POST
	@Path("/updatedcrosspromotions")
	@Persistable
	public Map<String, Boolean> updatedCrossPromotionsProducts(@Context HttpHeaders headers) {
		Map <String, Boolean> listUpdatedproducts = null;
		try {
			long lStartTime = System.currentTimeMillis();
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country); 
			listUpdatedproducts = updateCrossPromotionsProducts(requestContext, locale);
			long lEndTime = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			System.out.println("Execution time updatedCrossSellingProducts is " + formatter.format((lEndTime - lStartTime) / 1000d) + " seconds");			
			return listUpdatedproducts;
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return listUpdatedproducts;
		} 
	}
	
	private Map <String, Boolean> updateCrossPromotionsProducts (HstRequestContext requestContext, Locale locale) throws RepositoryException {
		long lStartTime = System.currentTimeMillis();
		System.out.println("In updateCrossPromotionsProducts");
		if (LOGGER.isInfoEnabled()) {
			System.out.println("In updateCrossPromotionsProducts");
		}
		Session session = requestContext.getSession();
		ProductServiceComponent productServiceComponent= new ProductServiceComponent();
		Map<String, Map<Long, List<Long>>> relatedProductMap = productServiceComponent.getProductTypeBasedSimilarProducts (requestContext, locale);
		CPDBProduct cpdbProduct = new CPDBProduct(new APACCPDBProductAPIImpl(requestContext.getSiteContentBasePath(), Constants.PRODUCTS_PATH, locale.getLanguage (), locale.getCountry ()));
		long lEndTime = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		Map <String, Boolean> resposne=cpdbProduct.updateCrossPromotionsProducts(session, relatedProductMap);
		System.out.println("Execution time updateCrossPromotionsProducts is " + formatter.format((lEndTime - lStartTime) / 1000d) + " seconds");			
		return resposne;
	}
	
	@POST
	@Path("/updatedSynonmys")
	@Persistable
	public Map<String, String> updatedSynonmys(@Context HttpHeaders headers) {
		Map<String, String> returnMap = new HashMap<>();
		try {
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale locale = new Locale(language, country);
			ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
			String suggestionServiceName = resourceBundle.getPropertyValue(bundle, Constants.SUGGESTION_SERVICE);
			SuggestionService suggestionService = HstServices.getComponentManager().getComponent(suggestionServiceName);
			if (suggestionService != null) {
				suggestionService.getSynonymTerms();
			}
			System.out.println("IN updateSuggestion  Service and calling suggestionService updatedSynonmys-->"
					+ !(suggestionService == null));
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("IN updateSuggestion  Service and calling suggestionService updatedSynonmys-->"
						+ !(suggestionService == null));
			}
			returnMap.put(Constants.STATUS, Constants.SUCCESS);
		} catch (Exception e) {
			System.out.println("Error in updateSuggestion");
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			returnMap.put(Constants.STATUS, Constants.FAILURE);
		}
		return returnMap;
	}
	
	
	/**
	 * 
	 * @param alternateMap
	 * @param requestContext
	 * @param locale
	 */
	private void updateAlternateFilterNames (Map<String, String> alternateMap, HstRequestContext requestContext, Locale locale) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("*****************************Alternate Value  MAP ****************");
		}
		if (!alternateMap.isEmpty()) {
			WorkflowPersistenceManager wpm = null;
			try {
				ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
				ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
				String filterDocumentPath  = resourceBundle.getPropertyValue(bundle, Constants.ALTERNATE_FILTER_DOCUMENT_PATH);
				if (Strings.isNullOrEmpty(filterDocumentPath)) {
					filterDocumentPath = Constants.ALTERNATE_FILTER_DOCUMENT_PATH_DEFAULT;
				}
				wpm = (WorkflowPersistenceManager) getPersistenceManager(requestContext);
				wpm.setWorkflowCallbackHandler(
						new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
							public void processWorkflow(
									DocumentWorkflow wf) throws Exception {
								wf.publish();
							}
						});
				HippoFolderBean contentBaseFolder =
                        getMountContentBaseBean(requestContext);
				String siteCanonicalBasePath = contentBaseFolder.getPath();
				Filter filter= (Filter) wpm.getObject(siteCanonicalBasePath + "/"+filterDocumentPath);
				List<FilterCompound> existingDocuments = filter.getKohler_filtercompound();
				Map<String, String> alternateExistingMap = new HashMap<>();
				for (FilterCompound existing : existingDocuments) {
					alternateExistingMap.put(existing.getDefaultVal(), existing.getAlternate());
				}
				List<FilterCompound> filterCompoundList = new ArrayList<>();
				Integer alternateExistingMapOldSize = alternateExistingMap.size();
				alternateExistingMap.putAll(alternateMap);
				Integer alternateExistingMapSize = alternateExistingMap.size();
				if (alternateExistingMapSize > alternateExistingMapOldSize ) {
					for (Map.Entry<String, String> valuesSet : alternateExistingMap.entrySet()) {
						FilterCompound filteCompound = new FilterCompound();
						filteCompound.setDefaultVal(valuesSet.getKey());
						filteCompound.setAlternate(valuesSet.getValue());
						filterCompoundList.add(filteCompound);
					}
					filter.setKohler_filtercompound(filterCompoundList);
		            wpm.update(filter);
				}
			} catch (Exception e) {
	            if (wpm != null) {
	                try {
	                    wpm.refresh();
	                } catch (ObjectBeanPersistenceException e1) {
	                	if (LOGGER.isErrorEnabled()) {
	        				LOGGER.error(e1.getMessage(), e1);
	        			}
	                }
	            }
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			} finally {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("END *****************************Alternate Value  MAP ****************");
				}
			}
		}
	}
	
	/**
     * 
      * @param resourceBundleName
     */
     @GET
     @Path("/getKeys/{resourceBundleName}")
     @Persistable
     public Map<String, String> getResourceBundleValues(@Context HttpHeaders headers,@PathParam("resourceBundleName") String resourceBundleName) {
           Map<String, String> returnMap = new TreeMap<>();
           try {
                HstRequestContext requestContext = RequestContextProvider.get();
                Mount mount = requestContext.getResolvedMount().getMount();
                String language = CommonUtil.getLanguage(mount);
                String country = CommonUtil.getCountry(mount);
                Locale locale = new Locale(language, country);
                ResourceBundle bundle = resourceBundle.getBundle(resourceBundleName, locale);
                Set<String> keys = bundle.keySet();
                for(String key : keys)
                {
                  String values  = resourceBundle.getPropertyValue(bundle, key);
                  returnMap.put(key, values);
                }
                
           } catch (Exception e) {
                System.out.println("Error in Getting Resource Bundle Keys-" +e.getMessage());
                if (LOGGER.isErrorEnabled()) {
                     LOGGER.error(e.getMessage(), e);
                }
                returnMap.put(Constants.STATUS, e.getMessage());
           }
           return returnMap;
     }
     
    
     
    @POST
  	@Path("/updateFilterNames")
  	@Persistable
  	public Map<String, Object> updateFilterNames(@Context HttpHeaders headers, Map<String, Product> products) {
  		Map<String, Object> returnMap = new HashMap<>();
  		try {
  			System.out.println("updateFilterNames service initiated");
  			long lStartTime = System.currentTimeMillis();
  			HstRequestContext requestContext = RequestContextProvider.get();
  			Mount mount = requestContext.getResolvedMount().getMount();
  			String language = CommonUtil.getLanguage(mount);
  			String country = CommonUtil.getCountry(mount);
  			Locale locale = new Locale(language, country);
  			if (!validateHeaders.ValidateApiKey(headers, locale)) {
  				returnMap.put(Constants.MESSAGE, Constants.INVALID_HEADERS);
  				returnMap.put(Constants.STATUS, Constants.ERROR_STATUS_VALUE);
  				return returnMap;
  			}
  			AlternateFilterNameService alternateFilterNameService = new AlternateFilterNameServiceImpl();
  			Map<String, String> alternateMap = alternateFilterNameService.getAlternateFilterNames(products, locale);
  			updateAlternateFilterNames(alternateMap, requestContext, locale);
  			NumberFormat formatter = new DecimalFormat("#0.00000");
  			long lEndTime = System.currentTimeMillis();
  			System.out.println("Execution time for update alternate filtername "+products.size()+" is " + formatter.format((lEndTime - lStartTime) / 1000d) + " seconds");
  		} catch (Exception e) {
  			if (LOGGER.isErrorEnabled()) {
  				LOGGER.error(e.getMessage(), e);
  			}
  		} 
  		return returnMap;
  	}
    
    @POST
  	@Path("/updateSearchFilterNames")
  	@Persistable
  	public Map<String, Object> updateSearchFilterNames(@Context HttpHeaders headers, Map<String, String> filters) {
  		Map<String, Object> returnMap = new HashMap<>();
  		try {
  			System.out.println("updateSearchFilterNames service initiated");
  			long lStartTime = System.currentTimeMillis();
  			HstRequestContext requestContext = RequestContextProvider.get();
  			Mount mount = requestContext.getResolvedMount().getMount();
  			String language = CommonUtil.getLanguage(mount);
  			String country = CommonUtil.getCountry(mount);
  			Locale locale = new Locale(language, country);
  			if (!validateHeaders.ValidateApiKey(headers, locale)) {
  				returnMap.put(Constants.MESSAGE, Constants.INVALID_HEADERS);
  				returnMap.put(Constants.STATUS, Constants.ERROR_STATUS_VALUE);
  				return returnMap;
  			}
  			updateAlternateSearchFilterNames(filters, requestContext, locale);;
  			NumberFormat formatter = new DecimalFormat("#0.00000");
  			long lEndTime = System.currentTimeMillis();
  			System.out.println("Execution time for update Search alternate filtername "+filters.size()+" is " + formatter.format((lEndTime - lStartTime) / 1000d) + " seconds");
  		} catch (Exception e) {
  			if (LOGGER.isErrorEnabled()) {
  				LOGGER.error(e.getMessage(), e);
  			}
  		} 
  		return returnMap;
  	}
    
    /**
	 * 
	 * @param alternateMap
	 * @param requestContext
	 * @param locale
	 */
	private void updateAlternateSearchFilterNames (Map<String, String> alternateMap, HstRequestContext requestContext, Locale locale) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("*****************************Alternate SEARCH Value  MAP ****************");
		}
		if (!alternateMap.isEmpty()) {
			WorkflowPersistenceManager wpm = null;
			try {
				ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
				ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
				String filterDocumentPath  = resourceBundle.getPropertyValue(bundle, Constants.ALTERNATE_SEARCH_FILTER_DOCUMENT_PATH);
				if (Strings.isNullOrEmpty(filterDocumentPath)) {
					filterDocumentPath = Constants.ALTERNATE_SEARCH_FILTER_DOCUMENT_PATH_DEFAULT;
				}
				wpm = (WorkflowPersistenceManager) getPersistenceManager(requestContext);
				wpm.setWorkflowCallbackHandler(
						new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
							public void processWorkflow(
									DocumentWorkflow wf) throws Exception {
								wf.publish();
							}
						});
				HippoFolderBean contentBaseFolder =
                        getMountContentBaseBean(requestContext);
				String siteCanonicalBasePath = contentBaseFolder.getPath();
				System.out.println("--" +(siteCanonicalBasePath + "/"+filterDocumentPath));
				Filter filter= (Filter) wpm.getObject(siteCanonicalBasePath + "/"+filterDocumentPath);
				System.out.println("is Filter null--"+(filter==null));
				List<FilterCompound> existingDocuments = filter.getKohler_filtercompound();
				Map<String, String> alternateExistingMap = new HashMap<>();
				for (FilterCompound existing : existingDocuments) {
					alternateExistingMap.put(existing.getDefaultVal(), existing.getAlternate());
				}
				List<FilterCompound> filterCompoundList = new ArrayList<>();
				Integer alternateExistingMapOldSize = alternateExistingMap.size();
				alternateExistingMap.putAll(alternateMap);
				Integer alternateExistingMapSize = alternateExistingMap.size();
				if (alternateExistingMapSize > alternateExistingMapOldSize ) {
					for (Map.Entry<String, String> valuesSet : alternateExistingMap.entrySet()) {
						FilterCompound filteCompound = new FilterCompound();
						filteCompound.setDefaultVal(valuesSet.getKey());
						filteCompound.setAlternate(valuesSet.getValue());
						filterCompoundList.add(filteCompound);
					}
					filter.setKohler_filtercompound(filterCompoundList);
		            wpm.update(filter);
				}
			} catch (Exception e) {
	            if (wpm != null) {
	                try {
	                    wpm.refresh();
	                } catch (ObjectBeanPersistenceException e1) {
	                	if (LOGGER.isErrorEnabled()) {
	        				LOGGER.error(e1.getMessage(), e1);
	        			}
	                }
	            }
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			} finally {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("END *****************************Alternate Value  MAP ****************");
				}
			}
		}
	}
	
	  @POST
	  	@Path("/updateProducSeo")
	  	@Persistable
	  	public Map<String, Object> updateProductSeo(@Context HttpHeaders headers, LinkedHashMap<String,Map<String,String>> productSeo) {
	  		Map<String, Object> returnMap = new HashMap<>();
	  		try {
	  			System.out.println("updateProductSeo service initiated");
	  			long lStartTime = System.currentTimeMillis();
	  			HstRequestContext requestContext = RequestContextProvider.get();
	  			Mount mount = requestContext.getResolvedMount().getMount();
	  			String language = CommonUtil.getLanguage(mount);
	  			String country = CommonUtil.getCountry(mount);
	  			Locale locale = new Locale(language, country);
	  			if (!validateHeaders.ValidateApiKey(headers, locale)) {
	  				returnMap.put(Constants.MESSAGE, Constants.INVALID_HEADERS);
	  				returnMap.put(Constants.STATUS, Constants.ERROR_STATUS_VALUE);
	  				return returnMap;
	  			}
	  		  for (Map.Entry<String,Map<String,String>> entry : productSeo.entrySet())  
	  		  {
	  			Boolean update=updateEachProductSeo(entry.getKey(),entry.getValue(), requestContext, locale);
	  			returnMap.put(entry.getKey(), update);
	  		  }
	  			NumberFormat formatter = new DecimalFormat("#0.00000");
	  			long lEndTime = System.currentTimeMillis();
	  			System.out.println("Execution time for update updateProductSeo alternate Seo Products "+productSeo.size()+" is " + formatter.format((lEndTime - lStartTime) / 1000d) + " seconds");
	  		} catch (Exception e) {
	  			if (LOGGER.isErrorEnabled()) {
	  				LOGGER.error(e.getMessage(), e);
	  			}
	  		} 
	  		return returnMap;
	  	}
	  
	   /**
		 * 
		 * @param updateEachProductSeo
		 * @param requestContext
		 * @param locale
		 */
	private Boolean updateEachProductSeo(String itemNo, Map<String, String> productSeo,
			HstRequestContext requestContext, Locale locale) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("*****************************ProductSeo  Value  MAP ****************");
		}
		if (!productSeo.isEmpty()) {
			WorkflowPersistenceManager wpm = null;
			try {
				String ProductDocument = "products/" + itemNo.charAt(0) + "/" + itemNo.charAt(1) + "/"+ productSeo.get(Constants.ID);
				HippoFolderBean contentBaseFolder = getMountContentBaseBean(requestContext);
				String siteCanonicalBasePath = contentBaseFolder.getPath();
				wpm = (WorkflowPersistenceManager) getPersistenceManager(requestContext);
				wpm.setWorkflowCallbackHandler(new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
					public void processWorkflow(DocumentWorkflow wf) throws Exception {
						wf.publish();
					}
				});
				com.kohler.beans.Product product = (com.kohler.beans.Product) wpm.getObject(siteCanonicalBasePath + "/" + ProductDocument);
				if(product==null)
					return false;
				String title = productSeo.get(Constants.REQUEST_SEO_TITLE) == null ? "" : productSeo.get(Constants.REQUEST_SEO_TITLE);
				String desc = productSeo.get(Constants.REQUEST_SEO_DESC) == null ? "" : productSeo.get(Constants.REQUEST_SEO_DESC);
				String cannrl = productSeo.get(Constants.REQUEST_CANONICAL_URL) == null ? "" : productSeo.get(Constants.REQUEST_CANONICAL_URL);
				Boolean noIndex = false;
				Boolean noFollow = false;
				if (productSeo.get(Constants.REQUEST_NO_INDEX) != null && productSeo.get(Constants.REQUEST_NO_INDEX).equalsIgnoreCase(Constants.REQUEST_YES))
					noIndex = true;
				if (productSeo.get(Constants.REQUEST_NO_FOLLOW) != null && productSeo.get(Constants.REQUEST_NO_FOLLOW).equalsIgnoreCase(Constants.REQUEST_YES))
					noFollow = true;
				SeoSupport seoSupportProduct = product.getSeo();
				if (seoSupportProduct != null) {
					String title1 = seoSupportProduct.getSeoTitle() == null ? "" : seoSupportProduct.getSeoTitle();
					String desc1 = seoSupportProduct.getSeoDescription() == null ? ""
							: seoSupportProduct.getSeoDescription();
					String cannrl1 = seoSupportProduct.getCanonicalUrl() == null ? ""
							: seoSupportProduct.getCanonicalUrl();
					Boolean noIndex1 = seoSupportProduct.getNoIndex();
					Boolean noFollow1 = seoSupportProduct.getNoFollow();
					if (title.equals(title1) && desc.equals(desc1) && cannrl.equals(cannrl1) && (noIndex == noIndex1)
							&& (noFollow == noFollow1))
						return false;
				}
				SeoSupport seoSupport = new SeoSupport(title, desc, cannrl, noIndex, noFollow);
				product.setSeo(seoSupport);
				wpm.update(product);
				return true;
			} catch (Exception e) {
				if (wpm != null) {
					try {
						wpm.refresh();
					} catch (ObjectBeanPersistenceException e1) {
						if (LOGGER.isErrorEnabled()) {
							LOGGER.error(e1.getMessage(), e1);
						}
					}
				}
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
				return false;
			} finally {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("END *****************************ProductSeo Value  MAP ****************");
				}
			}
		} else {
			return false;
		}
	}

}
