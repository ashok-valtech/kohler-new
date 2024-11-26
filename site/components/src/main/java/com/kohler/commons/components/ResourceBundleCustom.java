/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.hippoecm.hst.resourcebundle.ResourceBundleRegistry;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation is provided to access resource bundle and its properties.
 * 
 * @author dhanwan.r Date: 05/23/2017
 * @version 1.0
 */
public class ResourceBundleCustom {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceBundleCustom.class);

	/**
	 * Get ResourceBundle by passing BundleName and Locale
	 * @param bundleName
	 * @param locale
	 * @return
	 */
	public ResourceBundle getBundle(String bundleName, Locale locale) {
		ResourceBundle bundle = null;
		bundle = ResourceBundleUtils.getBundle(bundleName, locale);
		return bundle;
	}

	/**
	 * method provides implementation to format property value of resource
	 * only replacing first placeholder in the property is possible through this method.
	 * @param bundle
	 * @param propertyName
	 * @param arguments
	 * @return
	 */
	public String formatResource(ResourceBundle bundle, String propertyName, Object[] arguments) {
		String message = null;
		String resource = bundle.getString(propertyName);
		if (arguments.length > 0) {
			String[] str = (String[]) arguments[0];
			if (str[0] == null || str[0].isEmpty()) {
				message = null;
			} else {
				MessageFormat messageFormat = new MessageFormat(resource);
				message = messageFormat.format(arguments[0]);
			}
		}
		return message;
	}

	/**
	 * get property values by passing bundle name and property name.
	 * @param bundle
	 * @param propertyName
	 * @return
	 */
	public String getPropertyValue(ResourceBundle bundle, String propertyName) {
		String propertyValue = null;
		try {
			propertyValue = bundle.getString(propertyName);
		} catch (Exception ex) {
			if (LOG.isErrorEnabled()) {
				LOG.error(String.format("no property [%s] available in request bundle [%s]", propertyName, bundle.getBaseBundleName()));
			}
		}
		return propertyValue;
	}
	
	/**
	 * Get ResourceBundle from HttpServletRequest
	 * @return ResourceBundle
	 */
	public ResourceBundle getResourceBundle (HttpServletRequest request, String bundleName) {
		ResourceBundle bundle = null;
		try {
			Locale locale = new Locale(request.getLocale().getLanguage(), request.getLocale().getCountry());
			ResourceBundleRegistry resourceBundleRegistry = HstServices.getComponentManager().getComponent(ResourceBundleRegistry.class.getName());
			bundle = resourceBundleRegistry.getBundle(bundleName, locale);
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
		}
		return bundle;
	}
}
