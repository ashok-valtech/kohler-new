package com.kohler.commons.components;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;
import org.onehippo.cms7.essentials.components.CommonComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.Filter;
import com.kohler.beans.FilterCompound;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.util.CommonUtil;

/**
 * BaseComponent to execute once per page
 * @author dhanwan.r
 * Date: 07/08/2018
 * @version 1.0
 */
public class BaseComponent extends CommonComponent {
	
	private static final Logger LOG = LoggerFactory.getLogger (BaseComponent.class);
	
	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		super.doBeforeRender(request, response);
		final HstRequestContext requestContext = request.getRequestContext();
		Mount currentMount = requestContext.getResolvedMount().getMount();
		Mount parentMount = currentMount;
		String language = CommonUtil.getLanguage(currentMount);
		String country = CommonUtil.getCountry(currentMount);
		Locale locale = new Locale(language, country); 
		Boolean isParent = currentMount.getParent() == null;
		if (!isParent) {
			parentMount = currentMount.getParent();
		}
		ResourceBundle bundle = ResourceBundleUtils.getBundle(Constants.APAC_LABELS, locale);
		if (this.isMultisite(parentMount)) {
			try {
				String currentLocale = language + "_" + country;
				String savedLocale = "";
				Cookie[] cookies = request.getCookies();
				String cookieName = bundle.getString(Constants.COOKIE_NAME_REME);
				String cookieDuration = bundle.getString(Constants.COOKIE_DURATION_REME);
				if (cookies != null && cookies.length > 0) {
					savedLocale = this.getCookieByName(cookieName, cookies);
				}
				if (!Strings.isNullOrEmpty(savedLocale) && !savedLocale.equalsIgnoreCase(currentLocale)) {
					addCookie(response, currentLocale, cookieName, cookieDuration);
					this.redirectToUrl(bundle, locale, requestContext, response, isParent);
				} else {
					String alternateFilterCookieName = bundle.getString(Constants.ALTERNATE_FILTER_COOKIE_NAME);
					String alternateFilterCookie = this.getCookieByName(alternateFilterCookieName, cookies);
					if (!Strings.isNullOrEmpty(alternateFilterCookie)
							&& alternateFilterCookie.equalsIgnoreCase(Constants.ALTERNATE_FILTER_COOKIE_VALUE)) {
						removeCookie(alternateFilterCookieName, response);
						String queryString = requestContext.getServletRequest().getQueryString();
						String url = requestContext.getServletRequest().getPathInfo();
						if (!isParent) {
							url = "/en" + url;
						}
						url = checkForListingPage(bundle, locale, requestContext, queryString, url);
						if (!Strings.isNullOrEmpty(url)) {
							this.redirectToUrl(url, response);
						}
					}
				}
			} catch (Exception e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		String bizSchema = bundle.getString(Constants.KOHLER_BIZ_SCHEMA);
		if (bizSchema != null && !bizSchema.isEmpty()) {
			String bizSchemaScriptPath = bundle.getString(Constants.BIZ_SCHEMA_SCRIPT);
			if (!Strings.isNullOrEmpty(bizSchemaScriptPath)) {
				HippoBean bizSchemaContent = getScopeBean(bizSchemaScriptPath);
				request.setAttribute(Constants.REQUEST_KOHLER_BIZ_SCHEMA_DOC, bizSchemaContent);
			}
		}
	}
	
	/**
	 * Method to set cookie duration
	 * @param cookie
	 * @param cookieDuration
	 */
	protected void setCookieDuration (Cookie cookie, String cookieDuration) {
		try {
    		cookie.setMaxAge(60 * 60 * Integer.valueOf(cookieDuration));
    	} catch (Exception ex) {
    		if (LOG.isErrorEnabled()) {
				LOG.error(ex.getMessage(), ex);
			}
    	}
	}
	
	/**
	 * 
	 * @param cookieName
	 * @param cookies
	 * @return cookie values based on name
	 */
	protected String getCookieByName (String cookieName, Cookie[] cookies) {
		String savedLocale = "";
		if ((cookies != null) && (cookies.length >= 0) ) {
			for (int index = 0; index < cookies.length; index++) {
				if (cookieName.trim().equalsIgnoreCase(cookies[index].getName().trim())) {
					savedLocale = cookies[index].getValue();
					break;
				}
			}
		}
		return savedLocale;
	}
	
	/**
	 * Method to check if site is multisite
	 * @param parentMount
	 * @return
	 */
	protected Boolean isMultisite (Mount parentMount) {
		for (Mount childMount : parentMount.getChildMounts()) {	
		 	if (childMount.isMapped()) {
		 		return true;	
		 	}
		}
		return false;
	}
	
	/**
	 * Method to construct redirect URL and redirect. 
	 * @param bundle
	 * @param locale
	 * @param requestContext
	 * @param response
	 * @param isParent
	 */
	protected void redirectToUrl (ResourceBundle bundle, Locale locale, HstRequestContext requestContext, HstResponse response, Boolean isParent) {
		try {
			String url = requestContext.getServletRequest().getPathInfo();
			if (isParent) {
				url = "/en" + url;
			} else {
				if (Strings.isNullOrEmpty(url)) {
					url = "/";
				} else {	
					if (url.startsWith("/en/")) {
						url = url.replace("/en/", "/");
					} else if (url.startsWith("/en")){
						url = url.replace("/en", "/");
					}
				}
			}
			String queryString = requestContext.getServletRequest().getQueryString();
			if (!Strings.isNullOrEmpty(queryString)) {
				url = url + "?" + queryString;
			}
			String alternateFilterCookieName = bundle.getString(Constants.ALTERNATE_FILTER_COOKIE_NAME);
			addCookie(response, Constants.ALTERNATE_FILTER_COOKIE_VALUE, alternateFilterCookieName, "2");
			response.sendRedirect(url);
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * Redirect to URL method
	 * @param url
	 * @param response
	 */
	protected void redirectToUrl (String url, HstResponse response) {
		try {
			response.sendRedirect(url);
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * Add Cookie method
	 * @param response
	 * @param currentLocale
	 * @param cookieName
	 * @param cookieDuration
	 */
	protected void addCookie (HstResponse response, String currentLocale, String cookieName, String cookieDuration) {
		Cookie cookie = new Cookie(cookieName, currentLocale);
		cookie.setSecure(true);
	    cookie.setPath("/");
		if (Strings.isNullOrEmpty(cookieDuration)) {
	    	cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
	    } else {
	    	this.setCookieDuration(cookie, cookieDuration);
	    }
		response.addCookie(cookie);
	}
	
	/**
	 * Remove cookie method
	 * @param cookieName
	 * @param response
	 */
	protected void removeCookie (String cookieName, final HstResponse response) {
		Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
	}
	
	/**
	 * 
	 * @param bundle
	 * @param locale
	 * @param requestContext
	 * @return site url with translated facet value
	 */
	protected String checkForListingPage (ResourceBundle bundle, Locale locale, HstRequestContext requestContext,String queryString, String url) {
		//String queryString = requestContext.getServletRequest().getQueryString();
		//String url = requestContext.getServletRequest().getPathInfo();
		 if (!Strings.isNullOrEmpty(url)) { 
			ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
			String categories = resourceBundle.getPropertyValue(bundle,Constants.CATEGORIES);
			String searchPageURL = resourceBundle.getPropertyValue(bundle,Constants.SEARCH_PAGE_URL);
			Map<String, String> filters = null;
			if ((url.contains("/" + categories + "/")) || (url.contains("/" + searchPageURL))) {
				String filterDocumentPath  = resourceBundle.getPropertyValue(bundle, Constants.ALTERNATE_FILTER_DOCUMENT_PATH);
				if (Strings.isNullOrEmpty(filterDocumentPath)) {
					filterDocumentPath = Constants.ALTERNATE_FILTER_DOCUMENT_PATH_DEFAULT;
				}
				HippoBean contentBaseFolder = requestContext.getSiteContentBaseBean();
				HippoBean filterDocumentBean = contentBaseFolder.getBean(filterDocumentPath);
				if (filterDocumentBean instanceof Filter)
				{
					Filter filterDocument = (Filter) filterDocumentBean;
					if (!filterDocument.getKohler_filtercompound().isEmpty()) {
						filters = filterDocument.getKohler_filtercompound().stream().collect(Collectors.toMap(FilterCompound::getDefaultVal, FilterCompound::getAlternate));
					}
				}
			}
			String subUrl = "";
			StringBuffer parentUrl = null;
			if (url.contains("/" + categories + "/")) {
				Integer catIndex = url.indexOf("/" + categories + "/");
				parentUrl = new StringBuffer(url.substring(0, catIndex));
				subUrl = url.substring(catIndex + 1);
				String []subUrlSplits = subUrl.split("/");
				parentUrl.append("/");
				parentUrl.append(subUrlSplits[0]);
				parentUrl.append("/");
				parentUrl.append(subUrlSplits[1]);
				Integer catValIndex = subUrl.indexOf(subUrlSplits[1]);
				Integer catValIndexNext = subUrl.indexOf("/", catValIndex);
				if (catValIndexNext > -1) {
					subUrl = subUrl.substring(catValIndexNext+1);
				} else {
					subUrl = "";
				}
			} else if (url.contains("/" + searchPageURL)) {
				Integer resultIndex = url.indexOf("/" + searchPageURL);
				parentUrl = new StringBuffer(url.substring(0, resultIndex));
				parentUrl.append("/");
				parentUrl.append(searchPageURL);
				subUrl = url.substring(resultIndex + 1);
				resultIndex = subUrl.indexOf(searchPageURL);
				Integer catValIndexNext = subUrl.indexOf("/", resultIndex);
				if (catValIndexNext > -1) {
					subUrl = subUrl.substring(catValIndexNext+1);
				} else {
					subUrl = "";
				}
			}
			StringBuffer subUrlFinal = new StringBuffer();
			if (!Strings.isNullOrEmpty(subUrl)) {
				String []subUrlSplits = subUrl.split("/");
				for (Integer i = 0; i < subUrlSplits.length; i++) {
					subUrlFinal.append("/");
					subUrlFinal.append(subUrlSplits[i]);
					subUrlFinal.append("/");
					i++;
					String valueUrl = CommonUtil.encodePart(subUrlSplits[i]);
					if (filters.containsKey(valueUrl)) {
						subUrlFinal.append(filters.get(valueUrl));
					} else {
						subUrlFinal.append(subUrlSplits[i]);
					}
				}
			}
			if ((subUrlFinal != null) && !Strings.isNullOrEmpty(subUrlFinal.toString())) {
				parentUrl.append(subUrlFinal.toString());
			}
			if (!Strings.isNullOrEmpty(queryString)) {
				parentUrl.append("?");
				parentUrl.append(queryString);
			}
			if (parentUrl != null) {
				url = parentUrl.toString();
			}
		}
		return url;
	}
}
