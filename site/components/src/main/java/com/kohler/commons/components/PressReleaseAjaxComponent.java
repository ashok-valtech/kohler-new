/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.linking.HstLinkCreator;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kohler.beans.Pressreleases;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.util.CommonUtil;

import net.sf.json.JSONObject;

/**
 * APAC Implementation for load more functionality By Ajax Call From UI Side inherits from <code>EssentialsListComponent</code>
 * @author dhanwan.r 
 * Date: 09/06/2017
 * @version 1.0
 */
@ParametersInfo(type = EssentialsListComponentInfo.class)
public class PressReleaseAjaxComponent extends EssentialsListComponent {

	private static final Logger LOG = LoggerFactory.getLogger(PressReleaseAjaxComponent.class);
	
	public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
		
		HstRequestContext requestContext = RequestContextProvider.get();
		Mount mount = requestContext.getResolvedMount().getMount();
		String language = CommonUtil.getLanguage(mount);
		String country = CommonUtil.getCountry(mount);
		Locale locale = new Locale(language, country);
		ResourceBundle resourceBundle = ResourceBundleUtils.getBundle(Constants.APAC_LABELS, locale);
		String offset = this.getPublicRequestParameter(request, Constants.OFFSET);
		final EssentialsListComponentInfo paramInfo = getComponentParametersInfo(request);
		String sortByorder = paramInfo.getSortOrder();
		String sortByfield = paramInfo.getSortField();
		Integer limit = paramInfo.getPageSize();
		JSONObject result = new JSONObject();
		try {
			List<Pressreleases> pressReleaseBeanList = getPressReleaseBeanByFilter(limit, offset, sortByorder,
					sortByfield, request);
			List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
			if (pressReleaseBeanList.size() == 0) {
				result.put(Constants.ITEMS, "");
				result.put(Constants.SUCCESS, true);
				result.put(Constants.OFFSET, offset);
			} else {
				HstLinkCreator linkCreator = request.getRequestContext().getHstLinkCreator();
				for (Pressreleases pressrelease : pressReleaseBeanList) {
					JSONObject pressReleaseJsonObject = new JSONObject();
					SimpleDateFormat format = new SimpleDateFormat(resourceBundle.getString(Constants.RESOURCE_PRESS_RELEASE_DATE_FORMAT),locale);
					String date = format.format(pressrelease.getDate().getTime());
					pressReleaseJsonObject.put(Constants.DATE, date);
					pressReleaseJsonObject.put(Constants.DESCRIPTION, pressrelease.getDescription());
					HstLink link = linkCreator.create(pressrelease, request.getRequestContext());
					String beanHstURL = link.toUrlForm(request.getRequestContext(), false);
					pressReleaseJsonObject.put(Constants.REQUEST_ATTR_HSTLINK, beanHstURL);
					jsonObjectList.add(pressReleaseJsonObject);
				}
				result.put(Constants.ITEMS, jsonObjectList);
				result.put(Constants.SUCCESS, true);
				result.put(Constants.OFFSET, Integer.parseInt(offset) + limit);
			}
		} catch (Exception e) {
			result.put(Constants.SUCCESS, false);
			LOG.error(e.getMessage());
		} finally {
			try {
				response.getWriter().write(result.toString());
				response.setContentType(Constants.CONTENT_TYPE_APPLICATION_JSON);
			} catch (Exception e) {
				LOG.error(e.getMessage());
				throw new RuntimeException("catestrophic error", e);
			}
		}

	}

	/**
	 * This method uses for pagination
	 * @param limit
	 * @param offset
	 * @param sortByorder
	 * @param sortByfield
	 * @param request
	 * @return pressReleaseBeanList
	 * @throws IllegalStateException
	 * @throws QueryException
	 */
	public List<Pressreleases> getPressReleaseBeanByFilter(Integer limit, String offset, String sortByorder,
			String sortByfield, HstRequest request) {
		final EssentialsListComponentInfo paramInfo = getComponentParametersInfo(request);
		HstRequestContext hstRequestContext = request.getRequestContext();
		HippoBean rootBean = hstRequestContext.getContentBean();
		if(rootBean == null)
		{
			if(paramInfo != null)
			{
				final String path = getScopePath(paramInfo);
				if(path != null)
				{
					rootBean = getSearchScope(request, path);
				}
				
			}
		}	
		List<Pressreleases> pressReleaseBeanList = new ArrayList<Pressreleases>();
		try {
			if (rootBean != null) {
				@SuppressWarnings("unchecked")
				HstQuery query = hstRequestContext.getQueryManager().createQuery(rootBean,
						Pressreleases.class);
				query.setLimit(limit);
				query.setOffset(Integer.parseInt(offset));
				if (sortByorder.equals(Constants.SORT_DIRECTION_ASC)) {
					query.addOrderByAscending(sortByfield);
				} else if (sortByorder.equals(Constants.SORT_DIRECTION_DESC)) {
					query.addOrderByDescending(sortByfield);
				}
				HstQueryResult queryResult = query.execute();
				HippoBeanIterator resultDocumentBeans = queryResult.getHippoBeans();
				while (resultDocumentBeans.hasNext()) {
					HippoBean documentBean = resultDocumentBeans.nextHippoBean();
					if (documentBean != null) {
						Pressreleases pressrelease = (Pressreleases) documentBean;
						pressReleaseBeanList.add(pressrelease);
					}
				}
			}
		} catch (IllegalStateException | QueryException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return pressReleaseBeanList;
	}
}
