package com.kohler.commons.components;

import java.util.Locale;
import java.util.ResourceBundle;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;
import org.onehippo.cms7.essentials.components.CommonComponent;

import com.google.common.base.Strings;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.util.CommonUtil;

public class PromotionRibbonComponent extends CommonComponent {

	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		super.doBeforeRender(request, response);
		final HstRequestContext requestContext = request.getRequestContext();
		Mount currentMount = requestContext.getResolvedMount().getMount();
		String language = CommonUtil.getLanguage(currentMount);
		String country = CommonUtil.getCountry(currentMount);
		Locale locale = new Locale(language, country);
		ResourceBundle bundle = ResourceBundleUtils.getBundle(Constants.APAC_LABELS, locale);
		String promotionRibbonPath = bundle.getString(Constants.PROMOTION_RIBBON_PATH);
		if (!Strings.isNullOrEmpty(promotionRibbonPath)) {
			HippoBean promotionRibbonDoc = getScopeBean(promotionRibbonPath);
			request.setAttribute("promotionRibbonContent", promotionRibbonDoc);
		}
	}
}
