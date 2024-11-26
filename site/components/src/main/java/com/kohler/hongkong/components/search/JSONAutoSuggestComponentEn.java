/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.hongkong.components.search;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.commons.components.search.SuggestionService;
import com.kohler.commons.constants.Constants;

import net.sf.json.JSONObject;

/**
 * 
 * @author dhanwan.r
 *
 */
public class JSONAutoSuggestComponentEn extends BaseHstComponent {
	private static final Logger LOGGER = LoggerFactory.getLogger(JSONAutoSuggestComponentEn.class);

	private SuggestionService suggestionService = null;

	public JSONAutoSuggestComponentEn(){
		this.suggestionService = HstServices.getComponentManager().getComponent(Constants.SUGGESTION_SERVICE_EN_HK);
	}

	public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
		JSONObject result = new JSONObject();

		try {
			super.doBeforeRender(request, response);
			String term = this.getPublicRequestParameter(request, "q");
			if(!StringUtils.isEmpty(term)){
				List<String> suggestions = this.suggestionService.getSuggestions(term);
				if(suggestions.isEmpty())
				{
					suggestions = this.suggestionService.getSpellCheck(term);	
				}
				result.put("suggestions",suggestions);
			}
		} catch (Exception e) {
			result.put("error","error executing suggest");
			LOGGER.error("error executing login from login component", e);
		}finally{
			try {
				response.getWriter().write(result.toString());
				response.setContentType("application/json");
			} catch (Exception e) {
				LOGGER.error("catestrophic error", e);
				throw new RuntimeException("catestrophic error",e);
			}
		}
	}
}
