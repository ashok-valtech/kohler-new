/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.service;

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstRequest;

import com.kohler.beans.MediaContact;

public class PressReleaseServiceImpl {
	
	public List<MediaContact> getMediaContacts(HstRequest request){
		HippoBean rootBean = request.getRequestContext ().getSiteContentBaseBean ();
		HstQueryResult queryResult = null;
		List<MediaContact> mediaContacts = new ArrayList<MediaContact>();
		try {
			@SuppressWarnings("unchecked")
			HstQuery query = request.getRequestContext().getQueryManager().createQuery(rootBean, MediaContact.class);
			queryResult = query.execute();
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			while(iterator.hasNext() && mediaContacts.size() < 3){
				mediaContacts.add((MediaContact) iterator.next());
			}
		} catch (IllegalStateException | QueryException e) {
			e.printStackTrace ();
		}
		return mediaContacts;
	}	
}
