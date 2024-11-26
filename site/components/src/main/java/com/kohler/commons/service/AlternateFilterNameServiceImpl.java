package com.kohler.commons.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.json.beans.Product;
import com.kohler.commons.json.beans.Skus;
import com.kohler.commons.util.CommonUtil;

/**
 *	Class provides implementation for getting filter alternate Names
 *	@author dhanwan.r
 * 	Date: 05/24/2019
 * 	@version 1.0
 */
public class AlternateFilterNameServiceImpl implements AlternateFilterNameService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger (AlternateFilterNameServiceImpl.class);
	
	@Override
	public Map<String, String> getAlternateFilterNames (Map <String, Product> products, Locale locale) {
		Map<String, String> alternateMap = new HashMap<String, String> ();
		try {
			ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
			ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
			String langugeJsonFieldSuffix = "";
			String langugeAltJsonFieldSuffix = "";
			if(locale.getLanguage().equalsIgnoreCase(Constants.LOCALE_EN)){
				try {
					langugeAltJsonFieldSuffix = bundle.getString(Constants.COUNTRY_JSON_FIELD_SUFFIX);
				} catch (Exception ex) { 
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
			}else {	
				try {
					langugeJsonFieldSuffix = bundle.getString(Constants.COUNTRY_JSON_FIELD_SUFFIX);
				} catch (Exception ex) { 
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}	
			}
			for (Entry<String, Product> productEntry : products.entrySet()) {
				Product product = productEntry.getValue();
				alternateMap.putAll(getAlternative(product, langugeJsonFieldSuffix, langugeAltJsonFieldSuffix));
			}
		}catch (Exception ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
		return alternateMap;
	}

	@Override
	public Map<String, String> getAlternative (Product product, String langugeJsonFieldSuffix, String langugeAltJsonFieldSuffix) {
		Map<String, String> alternateMap = new HashMap<String, String> ();
		
		if (Strings.isNullOrEmpty(langugeAltJsonFieldSuffix)) {
			if (!Strings.isNullOrEmpty(langugeJsonFieldSuffix)) {
				//English ==> Local
				if(product.getAttributes() != null){
					alternateMap.putAll(getAlternativeAttributes(product.getAttributes(), langugeJsonFieldSuffix, true));
				}
				if(product.getSkus() != null){
					for (Skus skus : product.getSkus()) {
						alternateMap.putAll(getAlternativeAttributes(skus.getSkuAttributes(), langugeJsonFieldSuffix, true));
					}
				}
			}
		} else {
			//Local ==> English
			if(product.getAttributes() != null){
				alternateMap.putAll(getAlternativeAttributes(product.getAttributes(), langugeAltJsonFieldSuffix, false));
			}
			if(product.getSkus() != null){
				for (Skus skus : product.getSkus()) {
					alternateMap.putAll(getAlternativeAttributes(skus.getSkuAttributes(), langugeAltJsonFieldSuffix, false));
				}
			}
		}
		return alternateMap;
	}
	
	@Override
	public Map<String, String> getAlternativeAttributes(Map<String, Object> attributesOld,
			String langugeJsonFieldSuffix, Boolean flag) {
		Map<String, String> alternateMap = new HashMap<String, String> ();
		if ((attributesOld != null) && (!attributesOld.isEmpty())) {
			for (String entry : CommonUtil.FILTER_NAMES) {
				Object defaultValue = null;
				Object localValue = null;
				if (attributesOld.containsKey(entry)) {
					defaultValue = attributesOld.get(entry);
				}
				if (attributesOld.containsKey(entry + langugeJsonFieldSuffix)) {
					localValue = attributesOld.get(entry + langugeJsonFieldSuffix);
				}
				if ((defaultValue != null) && (localValue != null)) {
					if ((defaultValue instanceof String) && (localValue instanceof String)) {
						if (flag) {
							alternateMap.put(CommonUtil.encodePart(String.valueOf(defaultValue)), CommonUtil.encodePart(String.valueOf(localValue)));
						} else {
							alternateMap.put(CommonUtil.encodePart(String.valueOf(localValue)), CommonUtil.encodePart(String.valueOf(defaultValue)));
						}
					}else if ((defaultValue instanceof Map) && (localValue instanceof Map)) {
						@SuppressWarnings("unchecked")
						Map<String, Object> defaultValues = (Map<String, Object>) defaultValue;
						@SuppressWarnings("unchecked")
						Map<String, Object> localValues = (Map<String, Object>) localValue;
						for (Map.Entry<String, Object> defaultValuesSet : defaultValues.entrySet()) {
							if (flag) {
								alternateMap.put(CommonUtil.encodePart(String.valueOf(defaultValues.get(defaultValuesSet.getKey()))), CommonUtil.encodePart(String.valueOf(localValues.get(defaultValuesSet.getKey()))));
							} else {
								alternateMap.put(CommonUtil.encodePart(String.valueOf(localValues.get(defaultValuesSet.getKey()))), CommonUtil.encodePart(String.valueOf(defaultValues.get(defaultValuesSet.getKey()))));
							}	
						}
						
					}
				} 
			}
		}
		return alternateMap;		
	}

	
}
