package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.ThumbnailBanner;
import com.kohler.commons.components.componentsInfo.ThumdnailCarousalComponentInfo;
import com.kohler.commons.constants.Constants;

/**
 * APAC Implementation for Thumbnail Banner in KEC , inherits from <code>BaseComponent</code>
 * @author krushna.mahunta
 * Date: 11/27/2018
 * @version 1.0
 */
@ParametersInfo(type = ThumdnailCarousalComponentInfo.class)
public class ThumdnailCarousalComponent extends BaseComponent {
	
	private static final Logger LOG = LoggerFactory.getLogger(ThumdnailCarousalComponent.class);

	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		super.doBeforeRender(request, response);
		final ThumdnailCarousalComponentInfo paramInfo = getComponentParametersInfo(request);
		String path = paramInfo.getPath();
		final HippoBean scope = getScopeBean(path);
		final String sortField = paramInfo.getSortField();
		final String sortOrder = paramInfo.getSortOrder();
		if (scope == null) {
			return;
		}
		HstQueryResult queryResult = null;
		List<ThumbnailBanner> thumbnailBanners = new ArrayList<>();
		try {
			@SuppressWarnings("unchecked")
			HstQuery query = request.getRequestContext().getQueryManager().createQuery(scope, ThumbnailBanner.class);
			if (!Strings.isNullOrEmpty(sortField) && !Strings.isNullOrEmpty(sortOrder)) {
				if (sortOrder.equalsIgnoreCase(Constants.SORT_DIRECTION_ASC)) {
					query.addOrderByAscending(sortField);
				}else {
					query.addOrderByDescending(sortField);
				}
			}
			queryResult = query.execute();
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			while(iterator.hasNext()){
				thumbnailBanners.add( (ThumbnailBanner) iterator.next());
			}
			request.setAttribute("thumbnailBanners", thumbnailBanners);
		} catch (IllegalStateException | QueryException e) {
			LOG.error(e.getMessage(),e);
		}
	}
}
