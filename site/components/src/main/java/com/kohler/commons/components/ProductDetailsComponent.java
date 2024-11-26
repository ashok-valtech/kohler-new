/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.taxonomy.api.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.Product;
import com.kohler.commons.beans.Product_Detail;
import com.kohler.commons.beans.SKU_Detail;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.ProductDetailService;
import com.kohler.commons.taxonomies.TaxonomyService;
import com.kohler.commons.util.CommonUtil;

/**
 * APAC Implementation for Product details page, inherits from <code>BaseHstComponent</code>
 * @author Sumit.p
 * Date:10/05/2017
 * @version 1.0
 */
public class ProductDetailsComponent extends BaseHstComponent {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductDetailsComponent.class);
	
	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	ProductDetailService productDetailService = new ProductDetailService();

	@SuppressWarnings("static-access")
	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
		HstRequestContext hstRequestContext = request.getRequestContext();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country); 
		ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_PRODUCT_URLS, locale);
		String url = request.getRequestContext().getServletRequest().getPathInfo();
		String productItemNo = productDetailService.getProductName(url);
		Map<String, String> queryStringKeyValueMap = CommonUtil.generateQueryStringMap(request.getQueryString());
		Product product = null;
		if(!Strings.isNullOrEmpty(productItemNo)){
			product = productDetailService.getProductByItemNo(request, productItemNo);
		}
		if (product != null) {
			Product_Detail productDetail = new Product_Detail();
			Map<String, String[]> productAttributes = product.getAttributes().stream()
					.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
			productDetail.setItemNo(product.getItemNo());
			productDetail.setDescription(product.getDescriptionProduct());
			List<SKU_Detail> skuDetails = productDetailService.formatSkuDetail(product, bundle);
			String defaultSKU = productAttributes.get(Constants.DEFAULT_SKU) != null ? productAttributes.get(Constants.DEFAULT_SKU)[0] : null;
			String skusku = productDetailService.scanUrl(request, product);
			if (Strings.isNullOrEmpty(skusku)) {
				productDetail.setDefaultSKU(defaultSKU);
			} else {
				productDetail.setDefaultSKU(skusku);
			}
			skuDetails.forEach(sku->{
				if(sku.getSkuId().equals(productDetail.getDefaultSKU())){
					productDetail.setImgIso(sku.getImgItemIso());
				}
				if(!Strings.isNullOrEmpty(defaultSKU)){
					if(sku.getSkuId().equals(defaultSKU)){
						productDetail.setDiscontinued(sku.getDiscontinued());
					}
				}
			});
			productDetail.setPdpArticleURL(productAttributes.get(Constants.PDP_ARTICLE_URL) != null
			? productAttributes.get(Constants.PDP_ARTICLE_URL)[0] : null);
			productDetail.setShortDescription(productAttributes.get(Constants.DESCRIPTION_PRODUCT_SHORT) != null
					? productAttributes.get(Constants.DESCRIPTION_PRODUCT_SHORT)[0] : null);
			productDetail.setGlamImg(
					productAttributes.get(Constants.IMG_GLAM) != null ? productAttributes.get(Constants.IMG_GLAM)[0] : null);
			productDetail.setYoutubeId(
					productAttributes.get(Constants.YOUTUBE_ID) != null ? productAttributes.get(Constants.YOUTUBE_ID)[0] : null);
			productDetail.setLogosToDisplay((productAttributes.get(Constants.LOGOS_TO_DISPLAY) != null
					? productAttributes.get(Constants.LOGOS_TO_DISPLAY)[0] : null));
			productDetail.setBrandName(productAttributes.get(Constants.BRAND_NAME) != null
					? Arrays.asList(productAttributes.get(Constants.BRAND_NAME)) : null);
			productDetail.setCollection(productAttributes.get(Constants.COLLECTION) != null
					? Arrays.asList(productAttributes.get(Constants.COLLECTION)) : null);
			productDetail.setInstallationType(productAttributes.get(Constants.INSTALLATION_TYPE) != null
					? Arrays.asList(productAttributes.get(Constants.INSTALLATION_TYPE)) : null);
			productDetail.setMaterial(productAttributes.get(Constants.MATERIAL) != null? productAttributes.get(Constants.MATERIAL)[0] : null);
			Map<String, String> imagesMap = new LinkedHashMap<String, String>();
			if(productAttributes.get(Constants.IMG_TOP) != null){
				imagesMap.put("imgTop", productAttributes.get(Constants.IMG_TOP)[0]);
			}
			if(productAttributes.get(Constants.IMG_FRONT) != null){
				imagesMap.put("imgFront", productAttributes.get(Constants.IMG_FRONT)[0]);
			}
			if(productAttributes.get(Constants.IMG_SIDE) != null){
				imagesMap.put("imgSide", productAttributes.get(Constants.IMG_SIDE)[0]);
			}
			if(productAttributes.get(Constants.IMG_DETAIL1) != null){
				imagesMap.put("imgDetail1", productAttributes.get(Constants.IMG_DETAIL1)[0]);
			}
			if(productAttributes.get(Constants.IMG_DETAIL2) != null){
				imagesMap.put("imgDetail2", productAttributes.get(Constants.IMG_DETAIL2)[0]);
			}
			if(productAttributes.get(Constants.IMG_DETAIL3) != null){
				imagesMap.put("imgDetail3", productAttributes.get(Constants.IMG_DETAIL3)[0]);
			}
			if(productAttributes.get(Constants.IMG_DETAIL4) != null){
				imagesMap.put("imgDetail4", productAttributes.get(Constants.IMG_DETAIL4)[0]);
			}
			if(productAttributes.get(Constants.IMG_DETAIL5) != null){
				imagesMap.put("imgDetail5", productAttributes.get(Constants.IMG_DETAIL5)[0]);
			}
			if(productAttributes.get(Constants.IMG_DETAIL6) != null){
				imagesMap.put("imgDetail6", productAttributes.get(Constants.IMG_DETAIL6)[0]);
			}
			productDetail.setImgMap(imagesMap);
			productDetail.setSkus(skuDetails);
			productDetail.setTechDetail(productDetailService.formatTechnicalDetail(productAttributes));
			productDetail.setCadDetail(productDetailService.formatCadDetail(productAttributes));
			productDetail.setAdCopy(productDetailService.formatAdCopyDetail(product));
			productDetail.setCrossSellingDetail(productDetailService.formatCrossSellingDetail(request, product));
			productDetail.setKeys(product.getKeys());
			productDetail.setIsNew(product.getIsNew());
			productDetail.setSeo(product.getSeo());
			String productCategory = "";
			request.setAttribute("product", productDetail);
			if (queryStringKeyValueMap.containsKey("defaultCategory")) {
				String defaultCategoryKey = queryStringKeyValueMap.get("defaultCategory");
				for (String key : product.getKeys()) {
					if (key.equalsIgnoreCase(defaultCategoryKey)) {
						productCategory = key;
					}
				}
			}else{
				if (product.getKeys().length > 0) {
					productCategory = product.getKeys()[0] ;
				}
			}
			if (Strings.isNullOrEmpty(productCategory)) {
				if (product.getKeys().length > 0) {
					productCategory = product.getKeys()[0] ;
				}
			}
			if (!Strings.isNullOrEmpty(productCategory)) {
				TaxonomyService taxonomyService = new TaxonomyService();
				Category category = taxonomyService.getCategoryFromKey(Constants.TAXONOMY_NODE_NAME, productCategory);
				if (category != null) {
					Category parentCat = category.getParent();
					if (parentCat != null) {	
						request.setAttribute("breadcrumbCat", parentCat.getKey());
						request.setAttribute("breadcrumbSubCat", category.getKey());
						request.setAttribute("breadcrumbCatEnName", taxonomyService.getCategorytranslatedName(parentCat, "en_" + country));
						request.setAttribute("breadcrumbCatName", taxonomyService.getCategorytranslatedName(parentCat, language + "_" + country));
						request.setAttribute("breadcrumbSubCatName", taxonomyService.getCategorytranslatedName(category, language + "_" + country));
					}
				}
			}
		} else {
			try {
				bundle = resourceBundle.getBundle(Constants.ESSENTIAL_PAGE_NOT_FOUND, null);
				String pageNotFound = resourceBundle.getPropertyValue(bundle, Constants.PAGE_NOT_FOUND_TITLE);
				response.forward(pageNotFound);
				// response.forward(PlumbingConstants.PAGE_NOT_FOUND_URL);
				return;
			} catch (Exception e) {
				if (LOG.isErrorEnabled()) {
					LOG.error("HST Error:  " + e);
				}
			}
		}
		bundle.clearCache(this.getClass().getClassLoader());
	}
}
