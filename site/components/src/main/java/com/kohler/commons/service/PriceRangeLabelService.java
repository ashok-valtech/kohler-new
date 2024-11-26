package com.kohler.commons.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.hippoecm.hst.core.component.HstRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;

/**
 * Class will define the price range label based on selected category and selecte filter
 * @author dhanwan.r
 * Date:05/05/2017
 * @version 1.0
 */
public class PriceRangeLabelService {
	
	private static Logger log = LoggerFactory.getLogger(PriceRangeLabelService.class);

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();

	public void definePriceRangeLabel (HstRequest request, Locale locale, String categoryName, String filterName) {
		try {
			ResourceBundle bundlePriceRangeLabel = resourceBundle.getBundle(Constants.PRICE_RANGE_LABELS, locale);
			String country = resourceBundle.getPropertyValue(bundlePriceRangeLabel, Constants.PRICE_RANGE_COUNTRIES);
			if (!Strings.isNullOrEmpty(country)) {
				if (this.isPriceRangeLabelValidForCategory(bundlePriceRangeLabel, locale, categoryName)) {
					if (this.isPriceRangeLabelValidForFilter(bundlePriceRangeLabel, locale, categoryName, filterName)) {
						String luxury = resourceBundle.getPropertyValue(bundlePriceRangeLabel, Constants.PRICE_RANGE_LUXURY);
						String premium = resourceBundle.getPropertyValue(bundlePriceRangeLabel, Constants.PRICE_RANGE_PREMIUM);
						String basic = resourceBundle.getPropertyValue(bundlePriceRangeLabel, Constants.PRICE_RANGE_BASIC);
						Map<String, String> priceRangeMap = new HashMap<>();
						priceRangeMap.put(luxury, this.setPriceRangeLabelForFilter(request, bundlePriceRangeLabel, categoryName + "-" + filterName+ "-" + luxury));
						priceRangeMap.put(premium, this.setPriceRangeLabelForFilter(request, bundlePriceRangeLabel, categoryName + "-" + filterName+ "-" + premium));
						priceRangeMap.put(basic, this.setPriceRangeLabelForFilter(request, bundlePriceRangeLabel, categoryName + "-" + filterName + "-" + basic));
						request.setAttribute(Constants.REQUEST_PRICE_RANGE_MAP, priceRangeMap);
					}
				}
			}
		} catch (Exception ex) {
			if (log.isErrorEnabled()) {
				log.error(ex.getMessage(), ex);
			}
		}
	}
	
	private Boolean isPriceRangeLabelValidForCategory (ResourceBundle bundlePriceRangeLabel, Locale locale, String categoryName) {
		String categories = resourceBundle.getPropertyValue(bundlePriceRangeLabel, Constants.PRICE_RANGE_CATEGORIES);
		if (!Strings.isNullOrEmpty(categories)) {
			List<String> categoryList = Arrays.asList(categories.split(","));
			if (categoryList.contains(categoryName)) {
				return true;
			}
		}
		return false;
	}
	
	private Boolean isPriceRangeLabelValidForFilter (ResourceBundle bundlePriceRangeLabel, Locale locale, String categoryName, String filterName) {
		String filters = resourceBundle.getPropertyValue(bundlePriceRangeLabel, categoryName);
		if (!Strings.isNullOrEmpty(filters)) {
			List<String> filterList = Arrays.asList(filters.split(","));
			if (filterList.contains(filterName)) {
				return true;
			}
		}
		return false;
	}
	
	private String setPriceRangeLabelForFilter (HstRequest request, ResourceBundle bundlePriceRangeLabel, String key) {
		String priceRangeLabel = resourceBundle.getPropertyValue(bundlePriceRangeLabel, key);
		if (!Strings.isNullOrEmpty(priceRangeLabel)) {
			return priceRangeLabel;
		}
		return "";
	}
}
