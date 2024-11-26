/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.List;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import com.kohler.beans.MediaContact;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.PressReleaseServiceImpl;

/**
 * APAC Implementation for Press release content, inherits from <code>EssentialsContentComponent</code>
 * @author dhanwan.r
 * Date: 02/06/2017
 * @version 1.0
 */
public class PressReleaseContentComponent extends EssentialsContentComponent{

	PressReleaseServiceImpl pressReleaseService = new PressReleaseServiceImpl();
	
	@Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
		super.doBeforeRender(request, response);
		
		List<MediaContact>  mediaContacts = pressReleaseService.getMediaContacts(request);
		request.setAttribute(Constants.REQUEST_ATTR_MEDIACONTACTS, mediaContacts);
	}
}
