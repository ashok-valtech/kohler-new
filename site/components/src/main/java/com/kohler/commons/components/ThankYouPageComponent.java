/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.Locale;
import java.util.ResourceBundle;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.util.CommonUtil;

/**
 * @author Sumit.Pallakhe
 *
 *         class provides implementation for getting siteindex sitemap xml
 */
public class ThankYouPageComponent extends BaseHstComponent {

	public static final Logger LOGGER = LoggerFactory.getLogger(ThankYouPageComponent.class);

	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
		LOGGER.info("Inside ThankYouPageComponent");
		Mount mount = request.getRequestContext().getResolvedMount().getMount();
		String language = CommonUtil.getLanguage(mount);
		String country = CommonUtil.getCountry(mount);
		Locale bundleLocale = new Locale(language, country);
		String requestType = request.getRequestContext().getServletRequest().getParameter(Constants.THANKYOU_REQUEST_TYPE_PARAMETER);
		ResourceBundle bundle = ResourceBundleUtils.getBundle(Constants.CONTACT_FORM, bundleLocale);
		String requestTypeValue = bundle.getString(requestType);
		if (!Strings.isNullOrEmpty(requestType)) {
			request.setAttribute("requestType", requestTypeValue);
		}

	}

}
