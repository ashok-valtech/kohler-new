/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;
import org.onehippo.cms7.essentials.components.paging.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.beans.MaterialColorPalette;
import com.kohler.commons.constants.Constants;

/**
 * APAC Implementation for ColorPalette, inherits from <code>EssentialsListComponent</code>
 * @author Girijanandan.p
 * Date: 05/23/2017
 * @version 1.0
 */
@ParametersInfo(type = EssentialsListComponentInfo.class)
public class ColorPaletteComponent extends EssentialsListComponent{
	
	private static Logger LOGGER = LoggerFactory.getLogger(ColorPaletteComponent.class);
	@Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        super.doBeforeRender(request, response);
        final EssentialsListComponentInfo paramInfo = getComponentParametersInfo(request);
        final String path = getScopePath(paramInfo);
        LOGGER.debug("Calling EssentialsListComponent for documents path:  [{}]", path);
        final HippoBean scope = getSearchScope(request, path);
        if (scope == null) {
            LOGGER.warn("Search scope was null");
            handleInvalidScope(request, response);
            return;
        }
        final Pageable<? extends HippoBean> pageable;
        if (scope instanceof HippoFacetNavigationBean) {
            pageable = doFacetedSearch(request, paramInfo, scope);
        } else {
            pageable = doSearch(request, paramInfo, scope);
        }
		Map<String, List<MaterialColorPalette>> beanMap = new TreeMap<String, List<MaterialColorPalette>>();
        for(HippoBean hippoBean : pageable.getItems()){
        	MaterialColorPalette bean = (MaterialColorPalette) hippoBean;
        	if (beanMap.get(bean.getType()) == null) {
        		List<MaterialColorPalette> beans = new ArrayList<MaterialColorPalette>();
        		beans.add(bean);
        		beanMap.put(bean.getType(), beans);
        	} else {
        		List<MaterialColorPalette> beans = beanMap.get (bean.getType());
        		beans.add(bean);
        		beanMap.put(bean.getType(), beans);
        	}
        }
		request.setAttribute(Constants.REQUEST_ATTR_BEANMAP, beanMap);
    }
}