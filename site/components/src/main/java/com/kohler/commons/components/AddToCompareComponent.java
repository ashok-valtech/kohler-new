/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.CAttributes;
import com.kohler.beans.CSKU;
import com.kohler.beans.Product;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.ProductDetailService;

import net.sf.json.JSONObject;

/**
 * This Class called on ajax call only and return document object value as json
 * to page.
 * 
 * @author ranjit.sahoo
 * Date: 05/25/2017
 * @version 1.0
 */
public class AddToCompareComponent extends BaseHstComponent {

	private static final Logger LOG = LoggerFactory.getLogger(AddToCompareComponent.class);
	
	public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
		JSONObject result = new JSONObject();
		ProductDetailService productDetailService=null;
		
		try {
			String itemNumber = this.getPublicRequestParameter(request, Constants.ITEM_NUMBER);
			if (!StringUtils.isEmpty(itemNumber)) {
				if (itemNumber != null) {
					String[] itemNumbers = null;
					if (itemNumber.indexOf("|") < 0) {
						itemNumbers = new String[] { itemNumber };
					} else {
						itemNumbers = itemNumber.split("\\|");
					}
					List<JSONObject> itemList = new ArrayList<JSONObject>();
					for (String item : itemNumbers) {
						String itemClone = item;
						String [] items = item.split("!_");
						if (items.length > 1) {
							item = items[0];
						}
						JSONObject subResult = new JSONObject();
						productDetailService=new ProductDetailService();
						HippoBean productBeans = productDetailService.getProductByItemNo(request, item);
						Product product = (Product) productBeans;
						List<CAttributes> listAttributes = product.getAttributes();
						@SuppressWarnings("unchecked")
						Map<String, String> attribueMap = new HashedMap();
						for (CAttributes cAttributes : listAttributes) {
							attribueMap.put(cAttributes.getKey(), cAttributes.getValue()[0]);
						}
						String defaultSku = "";
						String productDescription="";
						String productFamily="";
						String productPrice = "";
						
						defaultSku = attribueMap.get("DEFAULT_SKU");
						CSKU defaultCSKU = null;
						if(!product.getSkus().isEmpty()){
							for(CSKU cSKU : product.getSkus()){
								String skuId = cSKU.getSku();
								if(skuId.equals(defaultSku)){
									defaultCSKU = cSKU;
									break;
								}
							}
							for(CAttributes attribute: defaultCSKU.getAttributes()){
								if(attribute.getKey().equalsIgnoreCase(Constants.PRODUCT_PRICE)){
									if(attribute.getValue().length > 0){
										productPrice = attribute.getValue()[0];
										break;
									}
								}
							}
						}
						if (attribueMap.containsKey(Constants.IMG_ISO) && !attribueMap.get(Constants.IMG_ISO).trim().equals("")) {
								subResult.put("src", attribueMap.get(Constants.IMG_ISO));
						} else {
							String defaultImage = "";
							for(CAttributes attribute: defaultCSKU.getAttributes()){
								if(attribute.getKey().equalsIgnoreCase("IMG_ITEM_ISO")){
									if(attribute.getValue().length > 0){
										defaultImage = attribute.getValue()[0];
										break;
									}
								}
							}
							if(Strings.isNullOrEmpty(defaultImage)){
								subResult.put("src", "blank");
							}else{
								subResult.put("src", defaultImage);
							}
						}
						subResult.put("productPrice", productPrice);
						if(attribueMap.containsKey(Constants.BRAND_NAME)){
							productFamily = attribueMap.get(Constants.BRAND_NAME);
							productFamily = productFamily.replace(Constants.ATTR_R, Constants.ATTR_R_VALUE);
							productFamily = productFamily.replace(Constants.ATTR_TM, Constants.ATTR_TM_VALUE);
							subResult.put(Constants.FAMILY, productFamily);
						} else {
							subResult.put(Constants.FAMILY, productFamily);
						}
						if(attribueMap.containsKey(Constants.DESCRIPTION_PRODUCT_SHORT)){
							productDescription = attribueMap.get(Constants.DESCRIPTION_PRODUCT_SHORT);
							productDescription = productDescription.replace(Constants.ATTR_R, Constants.ATTR_R_VALUE);
							productDescription = productDescription.replace(Constants.ATTR_TM, Constants.ATTR_TM_VALUE);
							subResult.put(Constants.DESCRIPTION, productDescription);
						} else {
							subResult.put(Constants.DESCRIPTION, productDescription);
						}
						subResult.put(Constants.DEFAULTSKU, defaultSku);
						if(product.getIsNew()){
							subResult.put(Constants.IS_NEW, true);
						}else{
							subResult.put(Constants.IS_NEW, false);
						}
						subResult.put(Constants.ITEM_NUMBER, itemClone);
						itemList.add(subResult);
					}
					result.put(Constants.ITEMS, itemList);
					result.put(Constants.SUCCESS, true);
				}
			}

		} catch (Exception e) {
			result.put(Constants.SUCCESS, false);
			LOG.error("error executing login from login component", e);
		} finally {
			try {
				response.getWriter().write(result.toString());
				response.setContentType(Constants.CONTENT_TYPE_APPLICATION_JSON);
			} catch (Exception e) {
				LOG.error("catestrophic error", e);
				throw new RuntimeException("catestrophic error", e);
			}
		}
	}

}
