/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.io.IOException;
import java.time.LocalDate;
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
 * E-form implementation
 * <code>FormStoringEformComponent</code>
 * @author krushna.mahunta Date: 04/08/2021
 * @version 1.0
 */
public class HygieneEformPromotionComponent extends FormStoringEformComponent {

	public final static String DEFAULT_UUID_NAME = "u_u_i_d";
	public static final String PROPERTY_FORM_DATA_PAYLOAD = "hst:payload";

	@Override
	public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
		super.doAction(request, response);
		System.out.println("In Action Form");
		FormBean bean = this.getFormBean(request);
		Form form = this.parse(bean, request);
		FormMap formMap = new FormMap(request, form.getFormRelativeUniqueFieldNames());
		String farwardPage = "/";
		try {
			String name=formMap.getField("name").getValue();
			String mobileNumber=formMap.getField("mobileno").getValue();
			String homeAddress=formMap.getField("address").getValue();
			String userEmailAddress = formMap.getField(Constants.MU_FORM_FIELD_EMAIL).getValue();
			String store=formMap.getField("store").getValue();
			String collections=formMap.getField("collections").getValue();
						
			ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
			HstRequestContext hstRequestContext = RequestContextProvider.get();
			String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
			String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
			Locale locale = new Locale(language, country);
			ResourceBundle hygiene_bundle = resourceBundle.getBundle(Constants.HYGIENE_PROMOTION_PAGE, locale);
			farwardPage = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-farwardPage");
			farwardPage=farwardPage.concat(name);
			String hygieneSenderEmailId = resourceBundle.getPropertyValue(hygiene_bundle, "hygiene-form-sender-emaild");
			int year = LocalDate.now().getYear();
			new Thread() {
				public void run() {
					String userContent = null;
					String muUserEmailTemplate = resourceBundle.getPropertyValue(hygiene_bundle,"hygiene-promotion-user-email-template");
					userContent = muUserEmailTemplate;
					String userTemplateBannerImage = resourceBundle.getPropertyValue(hygiene_bundle, "hygiene-user-email-template-banner");
					String userTemplateThankYouMessage = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-userTemplateThankyou");
					String userTemplateDesc1Message = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-user-email-template-description1");
					String userTemplateDesc2Message = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-user-email-template-description2");
					String userTemplateSubscribeMessage = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-userTemplateSubscribe");	
					String userTemplateUnsubscibeEmail = resourceBundle.getPropertyValue(hygiene_bundle, "hygiene-userTemplateUnsubscribeEmail");
					String regards = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-userTemplateRegards");
					String regardName = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-userTemplateRegardsName");
					String userTemplateDearLable = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-userTemplateDear");
					String userTemplateFacebookURL = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-userTemplateFacebookURL");
					String userTemplateFacebookImageURL = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-userTemplateFacebookImageURL");
					String userTemplateDownloadButtonText = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-userTemplateDownloadText");
					String userTemplatePdfUrl = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-userTemplatePdfUrl");
					String muUserTemplateSubject = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-userTemplateSubject");
					
					String kohlerLogo = resourceBundle.getPropertyValue(hygiene_bundle, "kohlertemplatelogo");
					String kohlerTemplateTitle = resourceBundle.getPropertyValue(hygiene_bundle, "kohlerTemplateTitle");
					String kohlerTemplateDetailsTitle = resourceBundle.getPropertyValue(hygiene_bundle, "kohlerTempDetailsTitle");
					String kohlerTempNameTitle = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-contact-form-name");
					String kohlerTempMobileTitle = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-contact-form-mobileno");
					String kohlerHomeAddrTitle = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-promo-home-address-tiltle");
					String kohlerTempEmailTitle = resourceBundle.getPropertyValue(hygiene_bundle, "hygiene-contact-form-email");
					String kohelrTempStoreTitle = resourceBundle.getPropertyValue(hygiene_bundle, "hygiene-form-selectstoreName");
					String kohelrTempCollectionTitle = resourceBundle.getPropertyValue(hygiene_bundle, "hygene-contact-form-collection-title");
					String kohlerPPTitle = resourceBundle.getPropertyValue(hygiene_bundle, "hygiene-form-privacyPolicyTitle");
					String kohlerPPURL = resourceBundle.getPropertyValue(hygiene_bundle, "hygiene-form-privacyPolicyLink");
					String kohlerTempFBTitle = resourceBundle.getPropertyValue(hygiene_bundle, "kohlerTempFeedBackTitle");
					String kohlerTempFBURL = resourceBundle.getPropertyValue(hygiene_bundle, "kohlerTemplateFeedBackURL");
					String kohlerCoTitle = resourceBundle.getPropertyValue(hygiene_bundle, "kohlerTempKohlercoTitle");
					String kohlerTemplateSenderEmail = resourceBundle.getPropertyValue(hygiene_bundle, "kohlerTempalteSenderEmialid");
					String kohlerTemplateReceiverEmail = resourceBundle.getPropertyValue(hygiene_bundle, "kohlerTemplateReceiverEmail");
					String kohlerTemplateSubject = resourceBundle.getPropertyValue(hygiene_bundle, "kohlerTemplateSubject");
					
					userContent = userContent.replace("${bannerImage}", userTemplateBannerImage);
					userContent = userContent.replace("${thankYou}", userTemplateThankYouMessage);
					userContent = userContent.replace("${Dear}", userTemplateDearLable);
					userContent = userContent.replace("${userName}", name);
					userContent = userContent.replace("${Description1}", userTemplateDesc1Message);
					userContent = userContent.replace("${Description2}", userTemplateDesc2Message);
					userContent = userContent.replace("${unSubscribe}", userTemplateSubscribeMessage);
					userContent = userContent.replace("${UnsubscribeEmail}", userTemplateUnsubscibeEmail);
					userContent = userContent.replace("${regards}", regards);
					userContent = userContent.replace("${teamKohler}", regardName);
					userContent = userContent.replace("${facebookUrl}", userTemplateFacebookURL);
					userContent = userContent.replace("${facebookImageUrl}", userTemplateFacebookImageURL);
					userContent = userContent.replace("${PDFUrl}", userTemplatePdfUrl);
					userContent = userContent.replace("${dowmloadText}", userTemplateDownloadButtonText);
					userContent = userContent.replace("${currentYear}", String.valueOf(year));
					
					String kohlerContent = null;
					String kohlerTemplate = resourceBundle.getPropertyValue(hygiene_bundle,"hygiene-promotion-kohler-temp");
					kohlerContent = kohlerTemplate;
					kohlerContent = kohlerContent.replace("${kohlerLogo}", kohlerLogo);
					kohlerContent = kohlerContent.replace("${contactForm}", kohlerTemplateTitle);
					kohlerContent = kohlerContent.replace("${contactFormDetails}", kohlerTemplateDetailsTitle);
					kohlerContent = kohlerContent.replace("${firstName}", kohlerTempNameTitle);
					kohlerContent = kohlerContent.replace("${firstNameValue}", name);
					kohlerContent = kohlerContent.replace("${mobileNumber}", kohlerTempMobileTitle);
					kohlerContent = kohlerContent.replace("${mobileNumberValue}", mobileNumber);
					kohlerContent = kohlerContent.replace("${homeAddressTitle}", kohlerHomeAddrTitle);
					kohlerContent = kohlerContent.replace("${homeAddressValue}", homeAddress);
					kohlerContent = kohlerContent.replace("${emailAddress}", kohlerTempEmailTitle);
					kohlerContent = kohlerContent.replace("${emailAddressValue}", userEmailAddress);
					kohlerContent = kohlerContent.replace("${selectStoreTitle}", kohelrTempStoreTitle);
					kohlerContent = kohlerContent.replace("${selectStoreValue}", store);
					kohlerContent = kohlerContent.replace("${collectionTitle}", kohelrTempCollectionTitle);
					kohlerContent = kohlerContent.replace("${collectionTitleValue}", collections);
					kohlerContent = kohlerContent.replace("${feedback}", kohlerTempFBTitle);
					kohlerContent = kohlerContent.replace("${feedbackURL}", kohlerTempFBURL);
					kohlerContent = kohlerContent.replace("${privacyPolicy}", kohlerPPTitle);
					kohlerContent = kohlerContent.replace("${privacyPolicyURL}", kohlerPPURL);
					kohlerContent = kohlerContent.replace("${currentYear}", String.valueOf(year));
					kohlerContent = kohlerContent.replace("${kohlerCo}", kohlerCoTitle);
					
					MailService.sendEmail(hygieneSenderEmailId, userEmailAddress, muUserTemplateSubject, userContent);
					MailService.sendEmail(kohlerTemplateSenderEmail, kohlerTemplateReceiverEmail, kohlerTemplateSubject, kohlerContent);
				}
		      }.start();
		    		
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				System.out.println("Farward to hygiene Thankyou page");
				response.sendRedirect(farwardPage);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
