package com.kohler.rest;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.annotations.Persistable;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.rest.BaseRestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.MailService;
import com.kohler.commons.util.CommonUtil;
import com.kohler.utils.VerifyRecaptcha;

@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
@Path("/contactform/")
public class ContactFormService extends BaseRestResource {

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactFormService.class);

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Persistable
	public Map<String, String> createContactForm(@Multipart("koh-contact-request-type") String requestType,
			@Multipart("koh-contact-first-name") String firstName, @Multipart("koh-contact-last-name") String lastName,
			@Multipart("koh-contact-email-address") String emailAddress,
			@Multipart("koh-contact-mobile-number") String mobileNumber,
			@Multipart("koh-contact-description") String description, @Multipart("file") Attachment file,
			@Multipart("koh-contact-google-captcha") String gRecaptchaResponse) {
		int year = LocalDate.now().getYear();
		String fileName = file.getContentDisposition().getParameter("filename");
		HstRequestContext hstRequestContext = RequestContextProvider.get();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country);
		ResourceBundle bundle = resourceBundle.getBundle(Constants.CONTACT_FORM, locale);
		String contactFormErrorMessage = resourceBundle.getPropertyValue(bundle, "contact-form-error-message-key");
		String contactFormEmailErrorMessage = resourceBundle.getPropertyValue(bundle, "contact-form-email-error-key");
		String pdpEmailTemplateLogo = resourceBundle.getPropertyValue(bundle, "contact-form-template-logo");
		String emailTemplatePrivacyPolicy = resourceBundle.getPropertyValue(bundle,	"contact-form-template-privacy");
		String emailTemplateFeedbackURL = resourceBundle.getPropertyValue(bundle, "contact-form-feedback-URL");
		String contactFormKohlerCoLogo = resourceBundle.getPropertyValue(bundle, "contact-form-kohler-co-logo");
		String contactFormTempalteTitle = resourceBundle.getPropertyValue(bundle, "contact-form-template-title");
		String contactFormTempalteDetailTitle = resourceBundle.getPropertyValue(bundle, "contact-form-details-title");
		String contactFormTempalteRequestType = resourceBundle.getPropertyValue(bundle,	"contact-form-template-request-type");
		String contactFormTempalteFirstname = resourceBundle.getPropertyValue(bundle, "contact-form-template-first-name");
		String contactFormTempalteLastName = resourceBundle.getPropertyValue(bundle, "contact-form-template-last-name");
		String contactFormTempalteEmailTitle = resourceBundle.getPropertyValue(bundle, "contact-form-template-email-address");
		String contactFormTempalteMobileTitle = resourceBundle.getPropertyValue(bundle, "contact-form-template-mobile");
		String contactFormTempalteDescription = resourceBundle.getPropertyValue(bundle,	"contact-form-template-description");
		String contactFormTempalteAttachTitle = resourceBundle.getPropertyValue(bundle,	"contact-form-template-attached-file-name");
		String emailTemplateFeedback = resourceBundle.getPropertyValue(bundle, "contact-form-template-feedback");
		String privacyPolicyShareEmailURL = resourceBundle.getPropertyValue(bundle,	"contact-form-privacy-URL");
		String contactFormTempalteAttachName = resourceBundle.getPropertyValue(bundle,	"contact-form-template-attach-file-name");
		String contactFormTemplateSubject = resourceBundle.getPropertyValue(bundle,	"contact-form-template-subject");
		String contactFormCaptchaErrorMessage = resourceBundle.getPropertyValue(bundle,	"contact-form-captcha-error-message");
		String contactFormSenderEmailId = resourceBundle.getPropertyValue(bundle, "contact-form-sender-email-Id");
		String requestTypeLabel = resourceBundle.getPropertyValue(bundle, requestType);
		String requestTypeEmailIds = resourceBundle.getPropertyValue(bundle, requestType + "-email-Id");
		String contactFormTemplate  = resourceBundle.getPropertyValue(bundle, "contact-form-template");
		
		String content = contactFormTemplate;
			content = content.replace("${kohlerLogo}", pdpEmailTemplateLogo);
			content = content.replace("${contactForm}", contactFormTempalteTitle);
			content = content.replace("${contactFormDetails}", contactFormTempalteDetailTitle);
			content = content.replace("${requestType}", contactFormTempalteRequestType);
			content = content.replace("${requestTypeValue}", requestTypeLabel);
			content = content.replace("${firstName}", contactFormTempalteFirstname);
			content = content.replace("${firstNameValue}", firstName);
			content = content.replace("${lastName}", contactFormTempalteLastName);
			content = content.replace("${lastNameValue}", lastName);
			content = content.replace("${emailAddress}", contactFormTempalteEmailTitle);
			content = content.replace("${emailAddressValue}", emailAddress);
			content = content.replace("${mobileNumber}", contactFormTempalteMobileTitle);
			content = content.replace("${mobileNumberValue}", mobileNumber);
			content = content.replace("${requestDescription}", contactFormTempalteDescription);
			content = content.replace("${requestDescriptionValue}", description);
			content = content.replace("${attachedFile}", contactFormTempalteAttachTitle);
			if (fileName != null && !fileName.isEmpty()) {
				content = content.replace("${attachedFileValue}", fileName);
			}else{
				content = content.replace("${attachedFileValue}", contactFormTempalteAttachName);
			}
			content = content.replace("${feedback}", emailTemplateFeedback);
			content = content.replace("${feedbackURL}", emailTemplateFeedbackURL);
			content = content.replace("${privacyPolicy}", emailTemplatePrivacyPolicy);
			content = content.replace("${privacyPolicyURL}", privacyPolicyShareEmailURL);
			content = content.replace("${currentYear}", String.valueOf(year));
			content = content.replace("${kohlerCo}", contactFormKohlerCoLogo);
			
		Map<String, String> returnMap = new HashMap<>();
		if (requestTypeLabel.isEmpty() && firstName.isEmpty() && lastName.isEmpty() && contactFormSenderEmailId.isEmpty()
				&& mobileNumber.isEmpty() && description.isEmpty() && requestTypeEmailIds.isEmpty()) {
			LOGGER.info(contactFormErrorMessage);
			returnMap.put(Constants.FLAG, contactFormErrorMessage);
			returnMap.put(Constants.MESSAGE, contactFormErrorMessage);
		}
		if (!this.validateEmail(contactFormSenderEmailId)) {
			LOGGER.error("contact-form-sender-email-Id missing in resource bundle");
			returnMap.put(Constants.FLAG, String.valueOf(false));
			returnMap.put(Constants.MESSAGE, contactFormErrorMessage);
			return returnMap;
		}
		boolean verify = this.verifyCaptcha(bundle, gRecaptchaResponse);
		if (verify) {
			if (Strings.isNullOrEmpty(fileName)) {
				try {
					MailService.sendEmail(contactFormSenderEmailId, requestTypeEmailIds, contactFormTemplateSubject, content);
					returnMap.put(Constants.SUCCESS, String.valueOf(true));
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					returnMap.put(Constants.FLAG, String.valueOf(false));
					returnMap.put(Constants.MESSAGE, contactFormEmailErrorMessage);
					return returnMap;
				}
			} else {
				try {
					InputStream inputStream = file.getObject(InputStream.class);
					MailService.sendEmailWithAttachment(contactFormSenderEmailId, requestTypeEmailIds, contactFormTemplateSubject, content, inputStream, fileName);
					returnMap.put(Constants.SUCCESS, String.valueOf(true));
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					returnMap.put(Constants.FLAG, String.valueOf(false));
					returnMap.put(Constants.MESSAGE, contactFormEmailErrorMessage);
					return returnMap;
				}
			}
		}else{
			returnMap.put(Constants.FLAG, contactFormErrorMessage);
			returnMap.put(Constants.MESSAGE, contactFormCaptchaErrorMessage);
		}
		return returnMap;
	}

	protected Boolean validateEmail(String emailId) {
		Boolean flag = true;
		if (Strings.isNullOrEmpty(emailId) || !CommonUtil.isValidEmail(emailId)) {
			flag = false;
		}
		return flag;
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
}
