/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
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
public class MuContactEformComponent extends FormStoringEformComponent {

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
		String farwardPage = "/";
		try {
			String name=formMap.getField("name").getValue();
			System.out.println("name--" + name);
			ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
			HstRequestContext hstRequestContext = RequestContextProvider.get();
			String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
			String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
			Locale locale = new Locale(language, country);
			ResourceBundle common_bundle = resourceBundle.getBundle(Constants.CONTACT_FORM_LANDING_PAGE, locale);
			ResourceBundle mu_bundle = resourceBundle.getBundle(Constants.MU_PROMOTION_LANDING_PAGE, locale);
			String userEmailAddress = formMap.getField(Constants.MU_FORM_FIELD_EMAIL).getValue();
			farwardPage = resourceBundle.getPropertyValue(mu_bundle, Constants.MU_FORM_FARWARPAGE);
			farwardPage=farwardPage+name;
			String muSenderEmailId = resourceBundle.getPropertyValue(common_bundle, "contact-form-landing-sender-email-Id");
			String isKohlerMail = resourceBundle.getPropertyValue(mu_bundle, "mu-contact-form-landing-is-kohler-mail");
			int year = LocalDate.now().getYear();
			new Thread() {
				public void run() {
					String userContent = null;
					String muUserEmailTemplate = resourceBundle.getPropertyValue(mu_bundle,"mu-promotion-user-email-template");
					userContent = muUserEmailTemplate;
					String userTemplateBannerImage = resourceBundle.getPropertyValue(mu_bundle, "mu-email-template-banner");
					String userTemplateThankYouMessage = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateThankyou");
					String userTemplateDesc1Message = resourceBundle.getPropertyValue(mu_bundle, "mu-email-template-description1");
					String userTemplateDesc2Message = resourceBundle.getPropertyValue(mu_bundle, "mu-email-template-description2");
					String userTemplateSubscribeMessage = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateSubscribe");	
					String userTemplateUnsubscibeEmail = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateUnsubscribeEmail");
					String regards = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateRegards");
					String regardName = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateRegardsName");
					String userTemplateDearLable = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateDear");
					String userTemplateFacebookURL = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateFacebookURL");
					String userTemplateInstagramURL = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateInstagramURL");
					String userTemplateFacebookImageURL = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateFacebookImageURL");
					String userTemplateInstagramImageURL = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateInstagramImageURL");
					String userTemplateDownloadButtonText = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateDownloadText");
					String userTemplatePdfUrl = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplatePdfUrl");
					String mupromotionlink = resourceBundle.getPropertyValue(mu_bundle, "mu-promotionlink");
					userContent = userContent.replace("${bannerImage}", userTemplateBannerImage);
					userContent = userContent.replace("${thankYou}", userTemplateThankYouMessage);
					userContent = userContent.replace("${Dear}", userTemplateDearLable);
					userContent = userContent.replace("${userName}", name);
					userContent = userContent.replace("${Description1}", userTemplateDesc1Message);
					userContent = userContent.replace("${Description2}", userTemplateDesc2Message);
					userContent = userContent.replace("${mulink}", mupromotionlink);
					userContent = userContent.replace("${unSubscribe}", userTemplateSubscribeMessage);
					userContent = userContent.replace("${UnsubscribeEmail}", userTemplateUnsubscibeEmail);
					userContent = userContent.replace("${regards}", regards);
					userContent = userContent.replace("${teamKohler}", regardName);
					userContent = userContent.replace("${facebookUrl}", userTemplateFacebookURL);
					userContent = userContent.replace("${instagramUrl}", userTemplateInstagramURL);
					userContent = userContent.replace("${facebookImageUrl}", userTemplateFacebookImageURL);
					userContent = userContent.replace("${instagramImageUrl}", userTemplateInstagramImageURL);
					userContent = userContent.replace("${PDFUrl}", userTemplatePdfUrl);
					userContent = userContent.replace("${dowmloadText}", userTemplateDownloadButtonText);
					userContent = userContent.replace("${currentYear}", String.valueOf(year));
					String muUserTemplateSubject = resourceBundle.getPropertyValue(mu_bundle, "mu-userTemplateSubject");
					MailService.sendEmail(muSenderEmailId, userEmailAddress, muUserTemplateSubject,	userContent);
				}
		      }.start();
		     // kohler Template 
		      if(!StringUtils.isEmpty(isKohlerMail))
		      new Thread() {
				public void run() {
					try {
					String mucontactFormTemplateSubject = resourceBundle.getPropertyValue(mu_bundle,"mu-contact-form-email-subject");
					String mukohlerEmailTemplate = resourceBundle.getPropertyValue(mu_bundle,"mu-kohler-email-template");
					String content = mukohlerEmailTemplate;
					String name = formMap.getField(Constants.MU_FORM_FIELD_NAME).getValue();
					String intrestedProducts = formMap.getField(Constants.MU_FORM_FIELD_INTERESTED_PRODUCTS).getValue();
					String score = formMap.getField(Constants.MU_FORM_FIELD_SCORE).getValue();
					String mobileno = formMap.getField(Constants.MU_FORM_FIELD_MOBILENO).getValue();
					String mupdpEmailTemplateLogo = resourceBundle.getPropertyValue(common_bundle,"contact-form-landing-template-logo");
					String muemailTemplatePrivacyPolicy = resourceBundle.getPropertyValue(common_bundle,"contact-form-landing-template-privacy");
					String muemailTemplateFeedbackURL = resourceBundle.getPropertyValue(common_bundle,"contact-form-landing-feedback-URL");
					String mucontactFormKohlerCoLogo = resourceBundle.getPropertyValue(common_bundle,"contact-form-landing-kohler-co-logo");
					String muemailTemplateFeedback = resourceBundle.getPropertyValue(common_bundle,"contact-form-landing-template-feedback");
					String privacyPolicyShareEmailURL = resourceBundle.getPropertyValue(common_bundle,"contact-form-landing-privacy-URL");
					String mucontactFormTempalteTitle = resourceBundle.getPropertyValue(mu_bundle,"mu-contact-form-landing-template-title");
					String mucontactFormTempalteDetailTitle = resourceBundle.getPropertyValue(mu_bundle,"mu-contact-form-landing-details-title");
					String mucontactFormTempalteFirstname = resourceBundle.getPropertyValue(mu_bundle,"mu-contact-form-landing-template-first-name");
					String mucontactFormTempalteEmailTitle = resourceBundle.getPropertyValue(mu_bundle,"mu-contact-form-landing-template-email-address");
					String mucontactFormTempalteScoreTitle = resourceBundle.getPropertyValue(mu_bundle,"mu-contact-form-landing-template-score-title");
					String mucontactFormTempalteMobileTitle = resourceBundle.getPropertyValue(mu_bundle,"mu-contact-form-landing-template-mobile-title");
					String mucontactFormTempalteIntrestedProductsTitle = resourceBundle.getPropertyValue(mu_bundle,"mu-contact-form-landing-template-intrestedproducts-title");
					String mureceiverEmailIds = resourceBundle.getPropertyValue(mu_bundle, "mu-contact-promo-form-receiver-email-Ids");
					content = content.replace("${kohlerLogo}", mupdpEmailTemplateLogo);
					content = content.replace("${contactForm}", mucontactFormTempalteTitle);
					content = content.replace("${contactFormDetails}", mucontactFormTempalteDetailTitle);
					content = content.replace("${firstName}", mucontactFormTempalteFirstname);
					content = content.replace("${firstNameValue}", name);
					content = content.replace("${emailAddress}", mucontactFormTempalteEmailTitle);
					content = content.replace("${emailAddressValue}", userEmailAddress);
					content = content.replace("${score}", mucontactFormTempalteScoreTitle);
					content = content.replace("${scoreValue}", score);
					content = content.replace("${intrestedproducts}", mucontactFormTempalteIntrestedProductsTitle);
					content = content.replace("${intrestedproductsValue}", intrestedProducts);
					content = content.replace("${mobileNo}", mucontactFormTempalteMobileTitle);
					content = content.replace("${mobileNoValue}", mobileno);
					content = content.replace("${feedback}", muemailTemplateFeedback);
					content = content.replace("${feedbackURL}", muemailTemplateFeedbackURL);
					content = content.replace("${privacyPolicy}", muemailTemplatePrivacyPolicy);
					content = content.replace("${privacyPolicyURL}", privacyPolicyShareEmailURL);
					content = content.replace("${currentYear}", String.valueOf(year));
					content = content.replace("${kohlerCo}", mucontactFormKohlerCoLogo);
					MailService.sendEmail(muSenderEmailId, mureceiverEmailIds, mucontactFormTemplateSubject,content);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			      }.start();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				System.out.println("Farward to Thankkyou page");
				response.sendRedirect(farwardPage);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
