package com.kohler.commons.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.repository.api.NodeNameCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.commons.constants.Constants;

/**
 * Class for common Utility functions. 
 * @author dhanwan.r
 * Date: 08/25/2017
 * @version 1.0
 */
public class CommonUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommonUtil.class);
	
	public static List <String> FILTER_NAMES = new ArrayList<>();
	
	static {
		FILTER_NAMES.add(Constants.INSTALLATION_TYPE);
		FILTER_NAMES.add(Constants.FTR_NUMBER_OF_HANDLES);
		FILTER_NAMES.add(Constants.FTR_HANDLE_STYLE);
		FILTER_NAMES.add(Constants.FTR_WATER_CONSERVATION);
		FILTER_NAMES.add(Constants.COLLECTION);
		FILTER_NAMES.add(Constants.FTR_CONFIGURATION);
		FILTER_NAMES.add(Constants.FTR_LITERS_PER_FLUSH);
		FILTER_NAMES.add(Constants.FTR_BOWL_SHAPE);
		FILTER_NAMES.add(Constants.FTR_TRAPWAY_TYPE);
		FILTER_NAMES.add(Constants.FTR_MINIMUM_ROUGH_IN);
		FILTER_NAMES.add(Constants.FTR_PRODUCT_TYPE);
		FILTER_NAMES.add(Constants.MATERIAL);
		FILTER_NAMES.add(Constants.OVERALL_LENGTH_MM);
		FILTER_NAMES.add(Constants.OVERALL_HEIGHT_MM);
		FILTER_NAMES.add(Constants.OVERALL_WIDTH_MM);
		FILTER_NAMES.add(Constants.FTR_LIGHTING_TYPE);
		FILTER_NAMES.add(Constants.FTR_MIRROR_DEFOGGER);
		FILTER_NAMES.add(Constants.FTR_FLUSHING_SYSTEM);
		FILTER_NAMES.add(Constants.FTR_BOWL_STYLE);
		FILTER_NAMES.add(Constants.FTR_STYLE);
		FILTER_NAMES.add(Constants.FTR_LENGTH_RANGE_MM);
		FILTER_NAMES.add(Constants.FTR_GLASS_THICKNESS_MM);
		FILTER_NAMES.add(Constants.FTR_FEATURE);
		FILTER_NAMES.add(Constants.FTR_SHAPE);
		FILTER_NAMES.add(Constants.FTR_FEATURE_1);
		FILTER_NAMES.add(Constants.FTR_FEATURE_2);
		FILTER_NAMES.add(Constants.FTR_STYLE_1);
		FILTER_NAMES.add(Constants.FTR_STYLE_2);
	}

	/**
	 * Used to Forward on page not found function
	 * @param response
	 */
	public static void pageNotFound(final HstResponse response,String pageNotFound) {
		try {
			response.forward(pageNotFound);
			response.setStatus(404);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage() + e);
		}
	}
	
	/**
	 * Used to Forward on Homepage function
	 * @param response
	 */
	public static void redirectHomePage(final HstResponse response) {
		try {
			response.sendRedirect("/");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage() + e);
		}
	}
	
	
	/**
	 * Used for (R) and (TM) replacing with html code
	 * @param content
	 * @return content
	 */
	public static String replaceSymbols(String content) {
		content = content.replaceAll("(R)","<sup>&reg;</sup>");
		content = content.replaceAll("(TM)","<sup>&trade;</sup>");
		return content;
	}
	
	/**
	 * Gets list of spec attributes
	 * @return attrs
	 */
	public static List<String> getSpecAttributeList()
    {
        List<String> attrs = Arrays.asList(Constants.ENVIRON_PROD_DECL,Constants.HOMEGUIDE_WITH_SP_PDF, Constants.HOMEGUIDE_WITHOUT_SP_PDF, Constants.INSTALLATION_CARE_WITH_SP_PDF,
        		Constants.INSTALLATION_CARE_WO_SP_PDF, Constants.INSTALLATION_WITHOUT_SP_PDF, Constants.LID_LABEL_PDF, Constants.MAINTENANCE_WITH_SP_PDF,
                Constants.MAINTENANCE_WITHOUT_SP_PDF, Constants.MSDS_PDF_FILE_NAME, Constants.SPEC_PDF_FILE_NAME, Constants.MATERIAL_PDR_FILE_NAME, Constants.USER_GUIDE_SPANISH,
                Constants.USER_GUIDE_ENGLISH, Constants.USER_GUIDE_FRENCH, Constants.USER_GUIDE_PDF, Constants.DWG_PLAN_VIEW, Constants.DXF_PLAN_VIEW, Constants.DWG_FRONT_VIEW,
                Constants.DXF_FRONT_VIEW, Constants.DXF_ALL_VIEWS,Constants.DWG_ALL_VIEWS,Constants.DWG_SIDE_VIEW, Constants.DXF_SIDE_VIEW, Constants.TD_SYMBOL, Constants.TD_DXF_SYMBOL, Constants.TD_OBJ, 
                Constants.TD_3DS,Constants.TD_SKETCHUP, Constants.TD_SKETCHUP, Constants.CUT_OUT_DXF,Constants.PARTS_PDF_FILE_NAME,Constants.MATCHING_LIST_PDF,Constants.AP_REGIONAL_SPEC_PDF);

        return  attrs;
    }
	
	public static String getLanguage(Mount mount){
		String locale = mount.getLocale();
		if(Strings.isNullOrEmpty(locale)){
			return Constants.LOCALE_EN;
		}else{
			String localeArray [] = locale.split("_");
			if(localeArray.length > 0){
				return localeArray[0];
			}else{
				return Constants.LOCALE_EN;
			}
		}
	}
	
	public static String getCountry(Mount mount){
		String locale = mount.getLocale();
		if(Strings.isNullOrEmpty(locale)){
			return Constants.COUNTRY_CODE_APAC;
		}else{
			String localeArray [] = locale.split("_");
			if(localeArray.length > 1){
				return localeArray[1];
			}else{
				return Constants.COUNTRY_CODE_APAC;
			}
		}
	}
	
	/**
	 * Gets map of products using query string
	 * @param queryString
	 * @return queryStringKeyValueMap 
	 */
	public static Map<String, String> generateQueryStringMap(String queryString) {
		Map<String, String> queryStringKeyValueMap = new HashMap<String, String>();
		if (!Strings.isNullOrEmpty(queryString)) {
			String[] queryParams = queryString.split("&");
			for (String param : queryParams) {
				String[] paramKeyVal = param.split("=");
				String key = "";
				String value = "";
				if (paramKeyVal.length >= 1 ) {
					key = paramKeyVal[0];
					if (paramKeyVal.length >= 2) {
						value = paramKeyVal[1];
					}
				}
				queryStringKeyValueMap.put(key, value);
			}
		}
		return queryStringKeyValueMap;
	}
	
	/**
	 * @param hstRequest
	 * @return
	 * 
	 * method provides implementation to get site specific hostname on the basis of mounts. 
	 */
	public static String getHostName(HstRequest hstRequest){
		HstRequestContext requestContext = hstRequest.getRequestContext();
		Mount siteMount = hstRequest.getRequestContext().getResolvedMount().getMount();
		String protocol = siteMount.getScheme();
		if (Strings.isNullOrEmpty(protocol)) {
			protocol = hstRequest.getHeader("x-forwarded-proto") ;
			if (Strings.isNullOrEmpty(protocol)) {
				protocol = hstRequest.getScheme();
			}
		}
		if (!Strings.isNullOrEmpty(protocol)) {
			String protocolArray [] = protocol.split("/");
			if (protocolArray.length >= 1) {
				protocol = protocolArray [0].toLowerCase() + "://";
			}
		}
		String hostName = requestContext.getBaseURL().getHostName();
		String currentSiteName = siteMount.getName();
		Mount parentSite = null;
		if (siteMount.getParent() != null) {
			parentSite = siteMount.getParent();
		}else {
			parentSite = siteMount;
		}	
		Boolean multiSiteFlag= false;	
		for (Mount mount : parentSite.getChildMounts()){	
		 	if (mount.isMapped()){
		 		multiSiteFlag = true;	
		 	}
		}
		
		if(multiSiteFlag && (currentSiteName != parentSite.getName())){
			for (Mount mount : parentSite.getChildMounts()) {
				if (currentSiteName == mount.getName()) {
					hostName = hostName + "/" + mount.getName();
					break;
				}
			}
		}
		System.out.println("Protocol : " + protocol);
		return protocol + hostName;
	}
	
	/**
	 * Email validation
	 */
	public static Boolean isValidEmail(String emailId) {
		String regex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(emailId);
		return matcher.matches();
	}
	
	
	/**
	 * Gets Encoded filters name
	 * @param part
	 * @return  
	 * @throws UnsupportedEncodingException
	 */
	public static String encodePart(String part) {
		String name = NodeNameCodec.decode(part);
		try {
			return name.indexOf(47) > 0 ? URLEncoder.encode(name.replaceAll("\\/", "_x002f_"), Constants.UTF_8)
					: URLEncoder.encode(name, Constants.UTF_8);
		} catch (UnsupportedEncodingException var3) {
			var3.printStackTrace();
			return "";
		}
	}
}
