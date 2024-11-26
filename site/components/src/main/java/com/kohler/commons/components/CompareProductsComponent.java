/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;

import org.apache.cxf.common.util.StringUtils;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;
import org.onehippo.cms7.essentials.components.CommonComponent;
import com.kohler.beans.Product;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.ProductDetailService;
import com.kohler.commons.util.CommonUtil;

/**
 * APAC Implementation for Compare Products, inherits from <code>CommonComponent</code>
 * @author dhanwan.r
 * Date: 05/10/2017
 * @version 1.0
 */
public class CompareProductsComponent extends CommonComponent {
	
	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
		super.doBeforeRender(request, response);
		HstRequestContext hstRequestContext = request.getRequestContext();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country); 
		String cookiesKey = null;
		String productsString = "";
		String categoryKey = null;
		ProductDetailService productDetailService=null;
		List<Product> products = new ArrayList<>();
		Cookie[] cookies = null;
		cookies = request.getCookies();
		ResourceBundle bundle = ResourceBundleUtils.getBundle(Constants.APAC_LABELS, locale);
		String cookieName = bundle.getString(Constants.COOKIE_NAME);
		if (cookies != null && cookies.length > 0) {
			for (int index = 0; index < cookies.length; index++) {
				cookiesKey = cookies[index].getName();
				if (cookieName.trim().equalsIgnoreCase(cookiesKey.trim())) {
					productsString = cookies[index].getValue();
				}
			}
		}
		List<String> result = null;
		if (!StringUtils.isEmpty(productsString)) {
			List<String> productUUIDList = Arrays.asList(productsString.split("\\."));
			categoryKey = productUUIDList.get(0);
			result = Arrays.asList(productUUIDList.get(1).split("\\|"));
		}
		if (result != null) {
			for (String productUID : result) {
				String [] productUIDArray = productUID.split("!_");
				if (productUIDArray.length > 1) {
					productUID = productUIDArray[0];
				}
				productDetailService=new ProductDetailService();
				HippoBean productBeans = productDetailService.getProductByItemNo(request, productUID);
				if (productBeans != null) {
					Product product = (Product) productBeans;
					products.add(product);
				}
			}
		}
		request.setAttribute("items", products);
		request.setAttribute("categoryKey", categoryKey);
	}
}
