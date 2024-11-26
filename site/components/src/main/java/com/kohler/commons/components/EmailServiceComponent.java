package com.kohler.commons.components;

import java.util.Locale;
import java.util.ResourceBundle;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.MailService;
import com.kohler.commons.util.CommonUtil;
import com.kohler.utils.VerifyRecaptcha;

import net.sf.json.JSONObject;

/**
 * Component to share PDP by email
 * @author krushna.mahunta Date:10/08/2018
 * @version 1.0
 */
public class EmailServiceComponent extends EssentialsListComponent {

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceComponent.class);

	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
		HstRequestContext hstRequestContext = request.getRequestContext();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country);
		ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
		String senderMessage = resourceBundle.getPropertyValue(bundle, Constants.SENDER_VALIDATION_MESSAGE);
		String receiverMessage = resourceBundle.getPropertyValue(bundle, Constants.RECEIVER_VALIDATION_MESSAGE);
		String pdpEmailTemplateLogo = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_KOHLER_LOGO);
		String emailTemplateHeaderTitle = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_HEADER_TITLE);
		String emailTemplateBodyMessage = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_BODY_MESSAGE);
		String emailTemplateBodyWrote = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_BODY_WROTE);
		String emailTemplateBodyViewMoreTitle = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_BODY_VIEW_MORE_TITLE);
		String emailTemplateThankYou = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_THANK_YOU);
		String emailTemplateThankYouMessage = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_THANK_YOU_MESSAGE);
		String emailTemplateFeedback = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_FEEDBACK);
		String emailTemplatePrivacyPolicy = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_PRIVACY_POLICY);
		String emailTemplateFeedbackURL = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_FEEDBACK_URL);
		String privacyPolicyShareEmailURL = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_PRIVACY_POLICY_URL);
		String emailAfriendtemplate = resourceBundle.getPropertyValue(bundle, "pdp-email-a-friend-template");
		String emailAFriendSenderEmailId = resourceBundle.getPropertyValue(bundle, "pdp-email-a-friend-sender-email-id");
		String friendEmailIdTitle = resourceBundle.getPropertyValue(bundle, "pdp-friend-email-title");
		
		
		
		String userName = this.getPublicRequestParameter(request, "name");
		String senderEmail = this.getPublicRequestParameter(request, "email");
		String recipientsEmail = this.getPublicRequestParameter(request, "recipientsemail");
		String gRecaptchaResponse = this.getPublicRequestParameter(request, "g-recaptcha-response");
		String subject = resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_SUBJECT_PART1) +" "+ userName +" " + resourceBundle.getPropertyValue(bundle, Constants.EMAIL_TEMPLATE_SUBJECT_PART2) ;
		String emailMessage = this.getPublicRequestParameter(request, "message");
		String productDetailURL = this.getPublicRequestParameter(request, "currentURL");
		String copyRight = this.getPublicRequestParameter(request, "copyRight");
		String brandNameandShortDesc = this.getPublicRequestParameter(request, "brandName");
		
        String content = emailAfriendtemplate;
            content = content.replace("${kohlerLogo}", pdpEmailTemplateLogo);
            content = content.replace("${Productrecommendation}", emailTemplateHeaderTitle);
            content = content.replace("${sendersName}", userName);
            content = content.replace("${bodyMessage}", emailTemplateBodyMessage);
            content = content.replace("${wrote}", emailTemplateBodyWrote);
            content = content.replace("${message}", emailMessage);
            content = content.replace("${friendEmailIdTitle}", friendEmailIdTitle);
            content = content.replace("${friendEmailId}", senderEmail);
            content = content.replace("${vieMmore}", emailTemplateBodyViewMoreTitle);
            content = content.replace("${productDeatilURL}", productDetailURL);
            content = content.replace("${thankYou}", emailTemplateThankYou);
            content = content.replace("${regards}", emailTemplateThankYouMessage);
            content = content.replace("${feedback}", emailTemplateFeedback);
            content = content.replace("${feedbackURL}", emailTemplateFeedbackURL);
            content = content.replace("${privacyPolicy}", emailTemplatePrivacyPolicy);
            content = content.replace("${privacyPolicyURL}", privacyPolicyShareEmailURL);
            content = content.replace("${copyRight}", copyRight);
            content = content.replace("${brandNameandShortDesc}", brandNameandShortDesc);

		Boolean receippientFlag = true;
		if (recipientsEmail.contains(",")) {
			for (String recipient : recipientsEmail.split(",")) {
				boolean valid = this.validateEmail(response, receiverMessage, recipient);
				if (!valid) {
					receippientFlag = false;
					break;
				}
			}
		}
		if (!Strings.isNullOrEmpty(userName) && this.validateEmail(response, senderMessage, emailAFriendSenderEmailId)
				&& receippientFlag) {
			JSONObject emailServiceJsonObject = new JSONObject();
			boolean verify = this.verifyCaptcha(bundle, gRecaptchaResponse);
			if (verify) {
				recipientsEmail = recipientsEmail + "," + senderEmail;
				MailService.sendEmail(emailAFriendSenderEmailId, recipientsEmail, subject, content);
				emailServiceJsonObject.put(Constants.SUCCESS, true);
				this.sendJson(response, emailServiceJsonObject);
			} else {
				emailServiceJsonObject.put(Constants.SUCCESS, false);
				this.sendJson(response, emailServiceJsonObject);
			}
		}
	}

	protected void sendJson(HstResponse response, JSONObject emailServiceJsonObject) {
		try {
			response.getWriter().write(emailServiceJsonObject.toString());
			response.setContentType(Constants.CONTENT_TYPE_APPLICATION_JSON);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	protected Boolean verifyCaptcha(ResourceBundle bundle, String gRecaptchaResponse) {
		boolean verify = false;
		try {
			String captchaURL = resourceBundle.getPropertyValue(bundle, Constants.CAPTCHA_URL);
			String captchaAgent = resourceBundle.getPropertyValue(bundle, Constants.CAPTCHA_AGENT);
			String captchaSecerteKey = resourceBundle.getPropertyValue(bundle, Constants.CAPTCHA_SECRETE_KEY);

			verify = VerifyRecaptcha.verify(gRecaptchaResponse, captchaURL, captchaAgent, captchaSecerteKey);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return verify;
	}

	protected Boolean validateEmail(HstResponse response, String message, String emailId) {
		if (Strings.isNullOrEmpty(emailId) || !CommonUtil.isValidEmail(emailId)) {
			JSONObject emailServiceJsonObject = new JSONObject();
			emailServiceJsonObject.put(Constants.SUCCESS, false);
			emailServiceJsonObject.put(Constants.MESSAGE, message);
			this.sendJson(response, emailServiceJsonObject);
			return false;
		}
		return true;
	}

}
