package com.kohler.commons.components;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.CommonComponent;

import com.kohler.beans.BizSchema;

/**
 * APAC Implementation for Biz Schema script component
 * @author dhanwan.r
 * Date: 10/09/2019
 * @version 1.0
 */
public class BizSchemaComponent extends CommonComponent {
	
	@Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
		final HstRequestContext context = request.getRequestContext();
        final BizSchema bean = (BizSchema) context.getContentBean();
		if(bean != null){
        	request.setAttribute(REQUEST_ATTR_DOCUMENT, bean);
        }
    }
}
