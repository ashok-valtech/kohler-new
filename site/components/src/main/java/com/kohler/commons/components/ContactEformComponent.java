/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.MailService;
import com.kohler.commons.util.CommonUtil;
import com.onehippo.cms7.eforms.hst.beans.FormBean;
import com.onehippo.cms7.eforms.hst.components.FormStoringEformComponent;
import com.onehippo.cms7.eforms.hst.model.Form;

/**
 * APAC Implementation for AboutUS detail page, inherits from
 * <code>BaseHstComponent</code>
 * 
 * @author dhanwan.r Date: 06/13/2017O
 * @version 1.0
 */
public class ContactEformComponent extends FormStoringEformComponent {

	// private static final Logger log = LoggerFactory.getLogger
	// (ContactEformComponent.class);
	public final static String DEFAULT_UUID_NAME = "u_u_i_d";
	public static final String PROPERTY_FORM_DATA_PAYLOAD = "hst:payload";

	@Override
	public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
		// TODO Auto-generated method stub
		super.doAction(request, response);
		System.out.println("In Action Form");
		FormBean bean = this.getFormBean(request);
		Form form = this.parse(bean, request);
		FormMap formMap = new FormMap(request, form.getFormRelativeUniqueFieldNames());
		System.out.println("name--" + formMap.getField("name").getValue());
		for (String value : formMap.getFieldNames()) {
			System.out.println("formField--" + value + "--" + formMap.getField(value).getValue());
		}
		try {
			ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
			HstRequestContext hstRequestContext = RequestContextProvider.get();
			String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
			String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
			Locale locale = new Locale(language, country);
			ResourceBundle bundle = resourceBundle.getBundle(Constants.CONTACT_FORM_LANDING_PAGE, locale);
			String userTemplateSenderEmailId = resourceBundle.getPropertyValue(bundle, "userTemplateSenderEmailId");
			String emailAddress = formMap.getField("email").getValue();
			String userTemplateSubject = resourceBundle.getPropertyValue(bundle, "userTemplateSubject");
			String userEmailTemplate = resourceBundle.getPropertyValue(bundle, "promotion-user-email-template");
			MailService.sendEmail(userTemplateSenderEmailId, emailAddress, userTemplateSubject, userEmailTemplate);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				response.sendRedirect("/en/thankyou");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
