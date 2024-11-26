/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.CAttributes;
import com.kohler.beans.CCrossSelling;
import com.kohler.beans.CSKU;
import com.kohler.beans.Product;
import com.kohler.commons.beans.AdCopy_Detail;
import com.kohler.commons.beans.Cad_Detail;
import com.kohler.commons.beans.CrossSelling_Detail;
import com.kohler.commons.beans.SKU_Detail;
import com.kohler.commons.beans.Technical_Detail;
import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.util.CommonUtil;

/**
 * @author Sumit.Pallakhe
 *
 */
public class ProductDetailService {
	private static final Logger LOG = LoggerFactory.getLogger(ProductDetailService.class);
	public static final String SITEMAP = "/productDetails/";
	
	/**
	 * @param request
	 * @return
	 * 
	 * get url from request and skuid
	 */
	public String scanUrl(HstRequest request, Product productBean) {
		String skuid = null;
		if (request.getQueryString() != null) {
			Map<String, String> map = CommonUtil.generateQueryStringMap(request.getQueryString());
			String skuIdFromURL = map.get(Constants.SKU_ID);
	        if(productBean != null){
	        	for(CSKU sku: productBean.getSkus()){
	        		if(!Strings.isNullOrEmpty(skuIdFromURL) && !Strings.isNullOrEmpty(sku.getSku()) && skuIdFromURL.equalsIgnoreCase(sku.getSku())){
	        			skuid = skuIdFromURL;
	        			for(CAttributes attribute :  sku.getAttributes()){
	        				if(attribute.getKey().equalsIgnoreCase(Constants.DISCONTINUED_DATE)){
	        					skuid = null;
	        					return skuid;
	        				}
	        			}
	        		}
	        	}
	        }
		}
		return skuid;
	}

	public String getProductName(final String url) {
		String itemNo = null;
		try{
			String urlArray [] = url.split("/");
			if(urlArray.length >= 3){
				itemNo =  urlArray[2];
			}
		}catch(Exception ex){
			LOG.error(ex.getMessage() +" : " + ex);
		}
		return itemNo;
	}

	/**
	 * @param request
	 * @param productId
	 * @return
	 * 
	 * get product by passing id
	 */
	@SuppressWarnings("unchecked")
	public Product getProductById(HstRequest request, Long productId) {
		HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();
		Product productBean = null;
		if (rootBean != null) {
			final String myPath = PathUtils.normalizePath(Constants.PRODUCTS_PATH);
			HippoBean productFolderBean = rootBean.getBean(myPath);
			if (productFolderBean != null) {
				HstQueryResult queryResult = null;
				try {
					HstQuery query = request.getRequestContext().getQueryManager().createQuery(productFolderBean, Product.class);
					Filter filter = query.createFilter();
					filter.addEqualTo(Constants.NS_ID, productId);
					query.setFilter(filter);
					queryResult = query.execute();
					if (queryResult.getHippoBeans().hasNext()) {
						productBean = (Product) queryResult.getHippoBeans().nextHippoBean();
					}
				} catch (IllegalStateException | QueryException ex) {
					LOG.error(ex.getMessage() +" : " + ex);
				}
			}	
		}
		return productBean;
	}

	/**
	 * Get Product by ItemNo
	 */
	/**
	 * @param request
	 * @param productItemNo
	 * @return
	 * 
	 * get product by passing item number
	 */
	public Product getProductByItemNo(HstRequest request, String productItemNo) {
		HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();
		HstQueryResult queryResult = null;
		Product productBean = null;
		if (rootBean != null) {
			final String myPath = PathUtils.normalizePath(Constants.PRODUCTS_PATH);
			HippoBean productFolderBean = rootBean.getBean(myPath);
			if (productFolderBean != null) {
				try {
					@SuppressWarnings("unchecked")
					HstQuery query = request.getRequestContext().getQueryManager().createQuery(productFolderBean, Product.class);
					Filter filter = query.createFilter();
					filter.addEqualToCaseInsensitive(Constants.NS_ITEM_NO, productItemNo);
					query.setFilter(filter);
					queryResult = query.execute();
					if (queryResult.getHippoBeans().hasNext()) {
						productBean = (Product) queryResult.getHippoBeans().nextHippoBean();
					}
				} catch (IllegalStateException | QueryException ex) {
					LOG.error(ex.getMessage() +" : " + ex);
				}
			}
		}
		return productBean;
	}

	/**
	 * @param productAttributes
	 * @return
	 * 
	 * get all technical details from attributes and et the Technical_Detail pojo 
	 */
	public Technical_Detail formatTechnicalDetail(Map<String, String[]> productAttributes) {
		Technical_Detail techDetail = new Technical_Detail();
		try{
			techDetail.setGfLineArt(
					productAttributes.get(Constants.GIF_LINE_ART) != null ? productAttributes.get(Constants.GIF_LINE_ART)[0] : null);
			techDetail.setOverAllHeight(productAttributes.get(Constants.OVERALL_HEIGHT_MM) != null
					? productAttributes.get(Constants.OVERALL_HEIGHT_MM)[0] : null);
			techDetail.setOverAllWidth(productAttributes.get(Constants.OVERALL_WIDTH_MM) != null
					? productAttributes.get(Constants.OVERALL_WIDTH_MM)[0] : null);
			techDetail.setOverAllLength(productAttributes.get(Constants.OVERALL_LENGTH_MM) != null
					? productAttributes.get(Constants.OVERALL_LENGTH_MM)[0] : null);
						
			
			if(productAttributes.get(Constants.SPEC_PDF_FILE_NAME) != null)
			{
				techDetail.setSpecPDF(productAttributes.get(Constants.SPEC_PDF_FILE_NAME));
			}
			if(productAttributes.get(Constants.SPEC_PDF_FILE_NAME2) != null)
			{
				techDetail.getInstallationPdf().put(Constants.SPEC_PDF_FILE_NAME2,productAttributes.get(Constants.SPEC_PDF_FILE_NAME2)[0]);
			}
			if(productAttributes.get(Constants.HOMEGUIDE_WITH_SP_PDF) != null )
			{
				techDetail.getInstallationPdf().put(Constants.HOMEGUIDE_WITH_SP_PDF,productAttributes.get(Constants.HOMEGUIDE_WITH_SP_PDF)[0]);
			}
			if(productAttributes.get(Constants.HOMEGUIDE_WITHOUT_SP_PDF) != null)
			{
				techDetail.getInstallationPdf().put(Constants.HOMEGUIDE_WITHOUT_SP_PDF, productAttributes.get(Constants.HOMEGUIDE_WITHOUT_SP_PDF)[0]);
			}			
			if(productAttributes.get(Constants.INSTALLATION_CARE_WITH_SP_PDF) != null)
			{
				techDetail.getInstallationPdf().put(Constants.INSTALLATION_CARE_WITH_SP_PDF, productAttributes.get(Constants.INSTALLATION_CARE_WITH_SP_PDF)[0]);
			}
			if(productAttributes.get(Constants.INSTALLATION_CARE_WO_SP_PDF) != null)
			{
				techDetail.getInstallationPdf().put(Constants.INSTALLATION_CARE_WO_SP_PDF,productAttributes.get(Constants.INSTALLATION_CARE_WO_SP_PDF)[0]);
			}
			if(productAttributes.get(Constants.INSTALLATION_PDF_FILE_NAME) != null)
			{
				techDetail.getInstallationPdf().put(Constants.INSTALLATION_PDF_FILE_NAME,productAttributes.get(Constants.INSTALLATION_PDF_FILE_NAME)[0]);
			}
			if(productAttributes.get(Constants.INSTALLATION_WITHOUT_SP_PDF) != null)
			{
				// techDetail.getInstallationPdf().put(Constants.INSTALLATION_WITHOUT_SP_PDF,productAttributes.get(Constants.INSTALLATION_WITHOUT_SP_PDF)[0]);
				String[] installationWithoutSpPdf = productAttributes.get(Constants.INSTALLATION_WITHOUT_SP_PDF);
				techDetail.setInstallationWithoutSpPdf(installationWithoutSpPdf );
			}
			if(productAttributes.get(Constants.LID_LABEL_PDF) != null)
			{
				techDetail.getInstallationPdf().put(Constants.LID_LABEL_PDF,productAttributes.get(Constants.LID_LABEL_PDF)[0]);
			}
			if(productAttributes.get(Constants.MAINTENANCE_WITH_SP_PDF) != null)
			{
				techDetail.getInstallationPdf().put(Constants.MAINTENANCE_WITH_SP_PDF,productAttributes.get(Constants.MAINTENANCE_WITH_SP_PDF)[0]);
			}
			if(productAttributes.get(Constants.MAINTENANCE_WITHOUT_SP_PDF) != null)
			{
				techDetail.getInstallationPdf().put(Constants.MAINTENANCE_WITHOUT_SP_PDF,productAttributes.get(Constants.MAINTENANCE_WITHOUT_SP_PDF)[0]);
			}
			if(productAttributes.get(Constants.MSDS_PDF_FILE_NAME) != null)
			{
				techDetail.getInstallationPdf().put(Constants.MSDS_PDF_FILE_NAME,productAttributes.get(Constants.MSDS_PDF_FILE_NAME)[0]);
			}
			if(productAttributes.get(Constants.TOILET_COMPAT_PDS) != null)
			{
				techDetail.getInstallationPdf().put(Constants.TOILET_COMPAT_PDS,productAttributes.get(Constants.TOILET_COMPAT_PDS)[0]);
			}
			if(productAttributes.get(Constants.MATERIAL_PDR_FILE_NAME) != null)
			{
				techDetail.getInstallationPdf().put(Constants.MATERIAL_PDR_FILE_NAME,productAttributes.get(Constants.MATERIAL_PDR_FILE_NAME)[0]);
			}
			if(productAttributes.get(Constants.USER_GUIDE_SPANISH) != null)
			{
				techDetail.getInstallationPdf().put(Constants.USER_GUIDE_SPANISH,productAttributes.get(Constants.USER_GUIDE_SPANISH)[0]);
			}
			if(productAttributes.get(Constants.USER_GUIDE_ENGLISH) != null)
			{
				techDetail.getInstallationPdf().put(Constants.USER_GUIDE_ENGLISH,productAttributes.get(Constants.USER_GUIDE_ENGLISH)[0]);
			}
			if(productAttributes.get(Constants.USER_GUIDE_FRENCH) != null)
			{
				techDetail.getInstallationPdf().put(Constants.USER_GUIDE_FRENCH,productAttributes.get(Constants.USER_GUIDE_FRENCH)[0]);
			}
			if(productAttributes.get(Constants.USER_GUIDE_PDF) != null)
			{
				techDetail.getInstallationPdf().put(Constants.USER_GUIDE_PDF,productAttributes.get(Constants.USER_GUIDE_PDF)[0]);
			}
			if(productAttributes.get(Constants.ENVIRON_PROD_DECL) != null)
			{
				techDetail.getInstallationPdf().put(Constants.ENVIRON_PROD_DECL,productAttributes.get(Constants.ENVIRON_PROD_DECL)[0]);
			}
			if(productAttributes.get(Constants.PARTS_PDF_FILE_NAME) != null)
			{
				techDetail.getInstallationPdf().put(Constants.PARTS_PDF_FILE_NAME,productAttributes.get(Constants.PARTS_PDF_FILE_NAME)[0]);
			}if(productAttributes.get(Constants.MATCHING_LIST_PDF) != null)
			{
				techDetail.getInstallationPdf().put(Constants.MATCHING_LIST_PDF,productAttributes.get(Constants.MATCHING_LIST_PDF)[0]);
			}if(productAttributes.get(Constants.AP_REGIONAL_SPEC_PDF) != null)
			{
				techDetail.getInstallationPdf().put(Constants.AP_REGIONAL_SPEC_PDF,productAttributes.get(Constants.AP_REGIONAL_SPEC_PDF)[0]);
			}
		}catch(Exception ex){
			LOG.error(ex.getMessage() + " : " + ex);
		}
		return techDetail;
	}

	/**
	 * @param productAttributes
	 * @return
	 * 
	 * get all cad details from attributes and et the Cad_Detail pojo 
	 */
	public Cad_Detail formatCadDetail(Map<String, String[]> productAttributes) {
		Cad_Detail cadDetail = new Cad_Detail();
		try{
			cadDetail.setDwgFrontView(
					productAttributes.get(Constants.DWG_FRONT_VIEW) != null ? productAttributes.get(Constants.DWG_FRONT_VIEW)[0] : null);
			cadDetail.setDwgPlanView(
					productAttributes.get(Constants.DWG_PLAN_VIEW) != null ? productAttributes.get(Constants.DWG_PLAN_VIEW)[0] : null);
			cadDetail.setDwgSideView(
					productAttributes.get(Constants.DWG_SIDE_VIEW) != null ? productAttributes.get(Constants.DWG_SIDE_VIEW)[0] : null);
			cadDetail.setDxfFrontView(
					productAttributes.get(Constants.DXF_FRONT_VIEW) != null ? productAttributes.get(Constants.DXF_FRONT_VIEW)[0] : null);
			cadDetail.setDxfPlanView(
					productAttributes.get(Constants.DXF_PLAN_VIEW) != null ? productAttributes.get(Constants.DXF_PLAN_VIEW)[0] : null);
			cadDetail.setDxfSideView(
					productAttributes.get(Constants.DXF_SIDE_VIEW) != null ? productAttributes.get(Constants.DXF_SIDE_VIEW)[0] : null);
			cadDetail.setDwgAllViews(
					productAttributes.get(Constants.DWG_ALL_VIEWS) != null ? productAttributes.get(Constants.DWG_ALL_VIEWS)[0] : null);
			cadDetail.setDxfAllViews(
					productAttributes.get(Constants.DXF_ALL_VIEWS) != null ? productAttributes.get(Constants.DXF_ALL_VIEWS)[0] : null);
			
			cadDetail.setTd_SYMBOL(productAttributes.get(Constants.TD_SYMBOL) != null
					? productAttributes.get(Constants.TD_SYMBOL)[0] : null);
			cadDetail.setTd_3DS(productAttributes.get(Constants.TD_3DS) != null
					? productAttributes.get(Constants.TD_3DS)[0] : null);
			cadDetail.setTd_DXF_SYMBOL(productAttributes.get(Constants.TD_DXF_SYMBOL) != null
					? productAttributes.get(Constants.TD_DXF_SYMBOL)[0] : null);
			cadDetail.setTd_OBJ(productAttributes.get(Constants.TD_OBJ) != null
					? productAttributes.get(Constants.TD_OBJ)[0] : null);
			cadDetail.setTd_SKETCHUP(productAttributes.get(Constants.TD_SKETCHUP) != null
					? productAttributes.get(Constants.TD_SKETCHUP)[0] : null);
			cadDetail.setTd_REVIT(productAttributes.get(Constants.TD_REVIT) != null
					? productAttributes.get(Constants.TD_REVIT)[0] : null);
			
			cadDetail.setCut_OUT_DXF(productAttributes.get(Constants.CUT_OUT_DXF) != null
					? productAttributes.get(Constants.CUT_OUT_DXF)[0] : null);
		}catch(Exception ex){
			LOG.error(ex.getMessage() + " : " + ex);
		}
		return cadDetail;
	}

	/**
	 * @param request
	 * @param product
	 * @return
	 * 
	 * get all cross selling details from product and et the CrossSelling_Detail pojo 
	 */
	public CrossSelling_Detail formatCrossSellingDetail(HstRequest request, Product product) {
		CrossSelling_Detail crossSellingDetail = new CrossSelling_Detail();
		try{
			List<CCrossSelling> crossSellingBeans = product.getCrossselling();
			List<Product> tempRecommendedList = new ArrayList<Product>();
			List<Product> tempRlatedList = new ArrayList<Product>();
			List<Product> crossPromoList = new ArrayList<Product>();
			for (CCrossSelling cCrossSelling : crossSellingBeans) {
				if (cCrossSelling.getKey().equals(Constants.RECOMMENDED_ACCESSORIES)) {
					for (Long itemId : cCrossSelling.getValues()) {
						if (itemId != null && itemId > 0) {
							try {
								Product recommendedProduct = getProductById(request, itemId);
								if (recommendedProduct != null) {
									tempRecommendedList.add(recommendedProduct);
								}
							} catch (Exception ex) {
								continue;
							}
						}
					}
				}
				if (cCrossSelling.getKey().equals(Constants.RELATED_PRODUCTS)) {
					for (Long itemId : cCrossSelling.getValues()) {
						if (itemId != null && itemId > 0) {
							try {
								Product relatedProduct = getProductById(request, itemId);
								if (relatedProduct != null) {
									tempRlatedList.add(relatedProduct);
								}
							} catch (Exception ex) {
								continue;
							}
						}
					}
				}
				
				if (cCrossSelling.getKey().equals(Constants.CROSS_PROMOTIONS)) {
					for (Long itemId : cCrossSelling.getValues()) {
						if (itemId != null && itemId > 0) {
							try {
								Product relatedProduct = getProductById(request, itemId);
								if (relatedProduct != null) {
									crossPromoList.add(relatedProduct);
								}
							} catch (Exception ex) {
								continue;
							}
						}
					}
				}
			}
			crossSellingDetail.getCrossSellingAccessories().put("recommendedAccessories", tempRecommendedList);
			crossSellingDetail.getCrossSellingAccessories().put("relatedAccessories", tempRlatedList);
			crossSellingDetail.getCrossSellingAccessories().put("upSellProducts", crossPromoList);
		}catch(Exception ex){
			LOG.error(ex.getMessage() + " : " + ex);
		}
		return crossSellingDetail;
	}
	
	/**
	 * @param product
	 * @return
	 * 
	 * list all sku of the product
	 */
	public List<SKU_Detail> formatSkuDetail(Product product, ResourceBundle bundleApacLabel) {
		ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
		int maxBuyNowShow = Constants.DEFAULT_MAX_BUY_NOW;
		if (resourceBundle.getPropertyValue(bundleApacLabel, Constants.MAX_BUY_NOW) != null) {
			maxBuyNowShow = Integer.parseInt(resourceBundle.getPropertyValue(bundleApacLabel, Constants.MAX_BUY_NOW));
		}
		List<SKU_Detail> skuDetail = new ArrayList<SKU_Detail>();
		try{
			List<CSKU> dummyskus = product.getSkus();
			for (CSKU csku : dummyskus) {
				SKU_Detail sku = new SKU_Detail();
				sku.setSkuId(csku.getSku());
				Map<String, String> skuAttributes = csku.getAttributes().stream()
						.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue() != null && x.getValue().length > 0 ? x.getValue()[0] : ""));
				sku.setImgItemIso(skuAttributes.get(Constants.IMG_ITEM_ISO));
				sku.setColorFinishName(skuAttributes.get(Constants.COLOR_FINISH_NAME));
				sku.setImgSwatch(skuAttributes.get(Constants.IMG_SWATCH));
				sku.setDiscontinued(skuAttributes.get(Constants.DISCONTINUED_DATE));
				sku.setProductPrice(skuAttributes.get(Constants.PRODUCT_PRICE));
				
				for(int i = 1; i <= maxBuyNowShow; i++){
					if(skuAttributes.get(Constants.RETAILER + i +"_IMG")!= null && !skuAttributes.get(Constants.RETAILER + i +"_IMG").isEmpty()) {
						sku.getRetailerImageMap().put(Constants.RETAILER + i, skuAttributes.get(Constants.RETAILER + i +"_IMG"));
					}
					if (skuAttributes.get(Constants.RETAILER + i + "_URL")!= null && !skuAttributes.get(Constants.RETAILER + i +"_URL").isEmpty()) {
						sku.getRetailerUrlMap().put(Constants.RETAILER + i, skuAttributes.get(Constants.RETAILER + i + "_URL"));
				    }
				}
				
				/**
				 * For Available Countries
				 */
				Map<String, String[]> availableInCountries = new LinkedHashMap<String, String[]>();
				for(CAttributes attr: csku.getAttributes()){
					if(attr.getKey().indexOf("SKU_") != -1){
						availableInCountries.put(attr.getKey(),attr.getValue());
					}
				}
				sku.setAvailableInCountries(availableInCountries);
				
				//End Of Available Countries
				
				if (skuAttributes.get(Constants.IMG_SWATCH) == null || skuAttributes.get(Constants.IMG_SWATCH).isEmpty()
						|| skuAttributes.get(Constants.IMG_ITEM_ISO) == null || skuAttributes.get(Constants.IMG_ITEM_ISO).isEmpty()) {
				} else {
					skuDetail.add(sku);
				}
			}
		}catch(Exception ex){
			LOG.error(ex.getMessage() + " : " + ex);
		}	
		return skuDetail;
	}

	/**
	 * @param product
	 * @param skusku
	 * @return
	 * 
	 * find imageItemIso from skuid 
	 */
	public String[] findImageItemIsoForSKU(Product product, String skusku) {
		String[] imgItemIso = null;
		try{
			List<CSKU> skuItems = product.getSkus();
			for (CSKU csku : skuItems) {
				String sku = csku.getSku();
				if (sku != null) {
					if (sku.equals(skusku)) {
						List<CAttributes> allAttr = csku.getAttributes();
						for (CAttributes cAttributes : allAttr) {
							if (cAttributes.getKey().equals(Constants.IMG_ITEM_ISO))
								imgItemIso = cAttributes.getValue();
						}
	
						return imgItemIso;
					}
				}
			}
		}catch(Exception ex){
			LOG.error(ex.getMessage() + " : " + ex);
		}	
		return imgItemIso;
	}

	/**
	 * @param product
	 * @return
	 * 
	 * format adCopy details
	 */
	public AdCopy_Detail formatAdCopyDetail(Product product) {
		AdCopy_Detail adCopyDetail = new AdCopy_Detail();
		try{
			List<CAttributes> dummyAdCopy = product.getAdcopy();
			Map<String, String> dummyAdCopyMap = dummyAdCopy.stream()
					.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()[0]));
			Map<String, String> features = new TreeMap<String, String>(dummyAdCopyMap);
			Map<String, String> installation = new TreeMap<String, String>(dummyAdCopyMap);
			Map<String, String> technology = new TreeMap<String, String>(dummyAdCopyMap);
			Map<String, String> rebates = new TreeMap<String, String>(dummyAdCopyMap);
			Map<String, String> hydrotherapy = new TreeMap<String, String>(dummyAdCopyMap);
			Map<String, String> material = new TreeMap<String, String>(dummyAdCopyMap);
	
			List<String> featureKeys = new ArrayList<String>();
			List<String> materialKeys = new ArrayList<String>();
			List<String> technologyKeys = new ArrayList<String>();
			List<String> rebateKeys = new ArrayList<String>();
			List<String> installationKeys = new ArrayList<String>();
			List<String> hydrotheraphyKeys = new ArrayList<String>();
	
			for (int i = 1; i <= 10; i++) {
				if (i <= 5) {
					featureKeys.add(Constants.WEB_FEATURES_+"0" + i);
					installationKeys.add(Constants.WEB_INSTALLATION_+"0" + i);
					technologyKeys.add(Constants.WEB_TECHNOLOGY_+"0" + i);
					rebateKeys.add(Constants.WEB_REBATES_+"0" + i);
					materialKeys.add(Constants.WEB_MATERIAL_+"0" + i);
					hydrotheraphyKeys.add(Constants.WEB_HYDROTHERAPY_+"0" + i);
				} else if (i > 5 && i < 10)
					featureKeys.add(Constants.WEB_FEATURES_+"0" + i);
				else
					featureKeys.add(Constants.WEB_FEATURES_ + i);
			}
	
			features.keySet().retainAll(featureKeys);
			installation.keySet().retainAll(installationKeys);
			technology.keySet().retainAll(technologyKeys);
			rebates.keySet().retainAll(rebateKeys);
			hydrotherapy.keySet().retainAll(hydrotheraphyKeys);
			material.keySet().retainAll(materialKeys);
	
			adCopyDetail.setWebFeatures(features);
			adCopyDetail.setWebHydrotherapy(hydrotherapy);
			adCopyDetail.setWebInstallation(installation);
			adCopyDetail.setWebMaterial(material);
			adCopyDetail.setWebRebates(rebates);
			adCopyDetail.setWebTechnology(technology);
			adCopyDetail.setMetaDescription(dummyAdCopyMap.get(Constants.META_DESCRIPTION));
			adCopyDetail.setMetaTitle(dummyAdCopyMap.get(Constants.META_TITLE));
			adCopyDetail.setNarrativeDescription(dummyAdCopyMap.get(Constants.NARRATIVE_DESCRIPTION));
		}catch(Exception ex){
			LOG.error(ex.getMessage() + " : " + ex);
		}
		return adCopyDetail;
	}

	/**
	 * @param request
	 * @param productItemNo
	 * @return
	 * 
	 * get product by passing item number
	 */
	public Product getProductByItemNoOrSkuId(HstRequest request, String productItemNo) {
		HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();
		HstQueryResult queryResult = null;
		Product productBean = null;
		if (rootBean != null) {
			final String myPath = PathUtils.normalizePath(Constants.PRODUCTS_PATH);
			HippoBean productFolderBean = rootBean.getBean(myPath);
			if (productFolderBean != null) {
				try {
					@SuppressWarnings("unchecked")
					HstQuery query = request.getRequestContext().getQueryManager().createQuery(productFolderBean, Product.class);
					Filter productFilter = query.createFilter();
					Filter filter = query.createFilter();
					filter.addEqualToCaseInsensitive(Constants.NS_ITEM_NO, productItemNo);
					productFilter.addOrFilter(filter);
		        	Filter filterSku = query.createFilter();
		        	String fieldRestriction = "kohler:skus/@kohler:sku";
		        	filterSku.addEqualToCaseInsensitive(fieldRestriction, productItemNo);	
		        	productFilter.addOrFilter(filterSku);
					query.setFilter(productFilter);
					queryResult = query.execute();
					if (queryResult.getHippoBeans().hasNext()) {
						productBean = (Product) queryResult.getHippoBeans().nextHippoBean();
					}
				} catch (IllegalStateException | QueryException ex) {
					LOG.error(ex.getMessage() +" : " + ex);
				}
			}
		}
		return productBean;
	}
}
