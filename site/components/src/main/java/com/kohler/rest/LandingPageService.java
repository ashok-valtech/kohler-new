package com.kohler.rest;

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
@Path("/landingpageservice/")
public class LandingPageService extends BaseRestResource {

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	private static final Logger LOGGER = LoggerFactory.getLogger(LandingPageService.class);

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Persistable
	public Map<String, String> createContactForm(@Multipart("koh-contact-request-type") String packageType,
			@Multipart("koh-contact-first-name") String firstName,
			@Multipart("koh-contact-email-address") String emailAddress,
			@Multipart("koh-contact-mobile-number") String mobileNumber,
			@Multipart("koh-districts-request-type") String districtType,
			@Multipart("koh-contact-google-captcha") String gRecaptchaResponse, 
			@Multipart("koh-home-adress-type") String homeAddress) {
		
		int year = LocalDate.now().getYear();
		HstRequestContext hstRequestContext = RequestContextProvider.get();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country);
		ResourceBundle bundle = resourceBundle.getBundle(Constants.CONTACT_FORM_LANDING_PAGE, locale);
		String contactFormErrorMessage = resourceBundle.getPropertyValue(bundle, "contact-form-landing-error-message-key");
		String contactFormEmailErrorMessage = resourceBundle.getPropertyValue(bundle, "contact-form-landing-email-error-key");
		String pdpEmailTemplateLogo = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-logo");
		String emailTemplatePrivacyPolicy = resourceBundle.getPropertyValue(bundle,	"contact-form-landing-template-privacy");
		String emailTemplateFeedbackURL = resourceBundle.getPropertyValue(bundle, "contact-form-landing-feedback-URL");
		String contactFormKohlerCoLogo = resourceBundle.getPropertyValue(bundle, "contact-form-landing-kohler-co-logo");
		String contactFormTempalteTitle = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-title");
		String contactFormTempalteDetailTitle = resourceBundle.getPropertyValue(bundle, "contact-form-landing-details-title");
		String contactFormTempalteDistrictsType = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-district-type");
		String contactFormTempaltePackageType = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-request-type");
		String contactFormTempalteFirstname = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-first-name");
		String contactFormTempalteEmailTitle = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-email-address");
		String contactFormTempalteMobileTitle = resourceBundle.getPropertyValue(bundle,	"contact-form-landing-template-mobile");
		String emailTemplateFeedback = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-feedback");
		String privacyPolicyShareEmailURL = resourceBundle.getPropertyValue(bundle, "contact-form-landing-privacy-URL");
		String contactFormTemplateSubject = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-subject");
		String contactFormCaptchaErrorMessage = resourceBundle.getPropertyValue(bundle, "contact-form-landing-captcha-error-message");
		String contactFormSenderEmailId = resourceBundle.getPropertyValue(bundle, "contact-form-landing-sender-email-Id");
		//String requestTypeLabel = resourceBundle.getPropertyValue(bundle, packageType);
		//String requestTypeEmailIds = resourceBundle.getPropertyValue(bundle, packageType + "-email-Id");
		//String districtTypeLabel = resourceBundle.getPropertyValue(bundle, districtType);
		String userTemplateBannerImage = resourceBundle.getPropertyValue(bundle, "userTemplateBanner");
		String userTemplateThankYouMessage = resourceBundle.getPropertyValue(bundle, "userTemplateThankyou");
		String userTemplateDesc1Message = resourceBundle.getPropertyValue(bundle, "userTemplateDesc1");
		String userTemplateDesc2Message = resourceBundle.getPropertyValue(bundle, "userTemplateDesc2");
		String userTemplateSubscribeMessage = resourceBundle.getPropertyValue(bundle, "userTemplateSubscribe");	
		String userTemplateUnsubscibeEmail = resourceBundle.getPropertyValue(bundle, "userTemplateUnsubscribeEmail");
		String regards = resourceBundle.getPropertyValue(bundle, "userTemplateRegards");
		String regardName = resourceBundle.getPropertyValue(bundle, "userTemplateRegardsName");
		String receiverEmailIds = resourceBundle.getPropertyValue(bundle, "contact-promo-form-receiver-email-Ids");
		String userTemplateDearLable = resourceBundle.getPropertyValue(bundle, "userTemplateDear");
		String userTemplateFacebookURL = resourceBundle.getPropertyValue(bundle, "userTemplateFacebookURL");
		String userTemplateInstagramURL = resourceBundle.getPropertyValue(bundle, "userTemplateInstagramURL");
		String userTemplateFacebookImageURL = resourceBundle.getPropertyValue(bundle, "userTemplateFacebookImageURL");
		String userTemplateInstagramImageURL = resourceBundle.getPropertyValue(bundle, "userTemplateInstagramImageURL");
		String userTemplateDownloadButtonText = resourceBundle.getPropertyValue(bundle, "userTemplateDownloadText");
		String userTemplatePdfUrl = resourceBundle.getPropertyValue(bundle, "userTemplatePdfUrl");
		String userTemplateSenderEmailId = resourceBundle.getPropertyValue(bundle, "userTemplateSenderEmailId");
		String userTemplateSubject = resourceBundle.getPropertyValue(bundle, "userTemplateSubject");
		String userEmailTemplate = resourceBundle.getPropertyValue(bundle, "promotion-user-email-template");
		String kohlerEmailTemplate = resourceBundle.getPropertyValue(bundle, "promotion-kohler-email-template");
		String homeAddressTitle = resourceBundle.getPropertyValue(bundle, "promo-home-address-tiltle");

		/*String fileLocation = Constants.LANDING_PAGE_EMAIL_TEMPLATE_FILE_LOCATION;
		ClassLoader classLoader = EmailServiceComponent.class.getClassLoader();
		File readfile = new File(classLoader.getResource(fileLocation).getFile());*/
		String content = null;
		try {
			content = kohlerEmailTemplate;
			content = content.replace("${kohlerLogo}", pdpEmailTemplateLogo);
			content = content.replace("${contactForm}", contactFormTempalteTitle);
			content = content.replace("${contactFormDetails}", contactFormTempalteDetailTitle);
			content = content.replace("${packageType}", contactFormTempaltePackageType);
			content = content.replace("${packageTypeValue}", packageType);
			content = content.replace("${districtType}", contactFormTempalteDistrictsType);
			content = content.replace("${districtTypeValue}", districtType);
			content = content.replace("${firstName}", contactFormTempalteFirstname);
			content = content.replace("${firstNameValue}", firstName);
			content = content.replace("${homeAddressTitle}", homeAddressTitle);
			content = content.replace("${homeAddressValue}", homeAddress);
			content = content.replace("${emailAddress}", contactFormTempalteEmailTitle);
			content = content.replace("${emailAddressValue}", emailAddress);
			content = content.replace("${mobileNumber}", contactFormTempalteMobileTitle);
			content = content.replace("${mobileNumberValue}", mobileNumber);
			content = content.replace("${feedback}", emailTemplateFeedback);
			content = content.replace("${feedbackURL}", emailTemplateFeedbackURL);
			content = content.replace("${privacyPolicy}", emailTemplatePrivacyPolicy);
			content = content.replace("${privacyPolicyURL}", privacyPolicyShareEmailURL);
			content = content.replace("${currentYear}", String.valueOf(year));
			content = content.replace("${kohlerCo}", contactFormKohlerCoLogo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		/*String userTemplate = Constants.USER_TEMPLATE_FILE_LOCATION;
		File readUserTempfile = new File(classLoader.getResource(userTemplate).getFile());*/
		String userContent = null;
		try {
			userContent = userEmailTemplate;
			userContent = userContent.replace("${bannerImage}", userTemplateBannerImage);
			userContent = userContent.replace("${thankYou}", userTemplateThankYouMessage);
			userContent = userContent.replace("${Dear}", userTemplateDearLable);
			userContent = userContent.replace("${userName}", firstName);
			userContent = userContent.replace("${Description1}", userTemplateDesc1Message);
			userContent = userContent.replace("${Description2}", userTemplateDesc2Message);
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
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		Map<String, String> returnMap = new HashMap<>();
		if (firstName.isEmpty() && contactFormSenderEmailId.isEmpty()
				&& mobileNumber.isEmpty() && receiverEmailIds.isEmpty() && userTemplateSenderEmailId.isEmpty()) {
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
		if (!this.validateEmail(userTemplateSenderEmailId)) {
			LOGGER.error("userTemplateSenderEmailId missing in resource bundle");
			returnMap.put(Constants.FLAG, String.valueOf(false));
			returnMap.put(Constants.MESSAGE, contactFormErrorMessage);
			return returnMap;
		}
		boolean verify = this.verifyCaptcha(bundle, gRecaptchaResponse);
		if (verify) {
			try {
				MailService.sendEmail(contactFormSenderEmailId, receiverEmailIds, contactFormTemplateSubject, content);
				MailService.sendEmail(userTemplateSenderEmailId, emailAddress, userTemplateSubject, userContent);
				returnMap.put(Constants.SUCCESS, String.valueOf(true));
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				returnMap.put(Constants.FLAG, String.valueOf(false));
				returnMap.put(Constants.MESSAGE, contactFormEmailErrorMessage);
				return returnMap;
			}
		} else {
			returnMap.put(Constants.FLAG, contactFormErrorMessage);
			returnMap.put(Constants.MESSAGE, contactFormCaptchaErrorMessage);
		}
		return returnMap;
	}
	
/* Bathtub Contact Form*/
	
	@POST
	@Path("/bathtubpromotion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Persistable
	public Map<String, String> createBathtubContactForm(@Multipart("koh-contact-first-name") String firstName,
			@Multipart("koh-contact-email-address") String emailAddress,
			@Multipart("koh-contact-mobile-number") String mobileNumber,
			@Multipart("koh-districts-request-type") String districtType,
			@Multipart("koh-contact-google-captcha") String gRecaptchaResponse) {
		
		int year = LocalDate.now().getYear();
		HstRequestContext hstRequestContext = RequestContextProvider.get();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country);
		ResourceBundle bundle = resourceBundle.getBundle(Constants.CONTACT_FORM_LANDING_PAGE, locale);
		String contactFormErrorMessage = resourceBundle.getPropertyValue(bundle, "contact-form-landing-error-message-key");
		String contactFormEmailErrorMessage = resourceBundle.getPropertyValue(bundle, "contact-form-landing-email-error-key");
		String pdpEmailTemplateLogo = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-logo");
		String emailTemplatePrivacyPolicy = resourceBundle.getPropertyValue(bundle,	"contact-form-landing-template-privacy");
		String emailTemplateFeedbackURL = resourceBundle.getPropertyValue(bundle, "contact-form-landing-feedback-URL");
		String contactFormKohlerCoLogo = resourceBundle.getPropertyValue(bundle, "contact-form-landing-kohler-co-logo");
		String contactFormTempalteTitle = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-title");
		String contactFormTempalteDetailTitle = resourceBundle.getPropertyValue(bundle, "contact-form-landing-details-title");
		String contactFormTempalteDistrictsType = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-district-type");
		String contactFormTempalteFirstname = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-first-name");
		String contactFormTempalteEmailTitle = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-email-address");
		String contactFormTempalteMobileTitle = resourceBundle.getPropertyValue(bundle,	"contact-form-landing-template-mobile");
		String emailTemplateFeedback = resourceBundle.getPropertyValue(bundle, "contact-form-landing-template-feedback");
		String privacyPolicyShareEmailURL = resourceBundle.getPropertyValue(bundle, "contact-form-landing-privacy-URL");
		String contactFormTemplateSubject = resourceBundle.getPropertyValue(bundle, "contact-form-bathtub_landing-template-subject");
		String contactFormCaptchaErrorMessage = resourceBundle.getPropertyValue(bundle, "contact-form-landing-captcha-error-message");
		String contactFormSenderEmailId = resourceBundle.getPropertyValue(bundle, "contact-form-landing-sender-email-Id");
		//String requestTypeLabel = resourceBundle.getPropertyValue(bundle, packageType);
		//String requestTypeEmailIds = resourceBundle.getPropertyValue(bundle, packageType + "-email-Id");
		//String districtTypeLabel = resourceBundle.getPropertyValue(bundle, districtType);
		String userTemplateBannerImage = resourceBundle.getPropertyValue(bundle, "bathtub-email-template-banner");
		String userTemplateThankYouMessage = resourceBundle.getPropertyValue(bundle, "userTemplateThankyou");
		String userTemplateDesc1Message = resourceBundle.getPropertyValue(bundle, "bathtub-email-template-description1");
		String userTemplateDesc2Message = resourceBundle.getPropertyValue(bundle, "bathtub-email-template-description2");
		String userTemplateSubscribeMessage = resourceBundle.getPropertyValue(bundle, "userTemplateSubscribe");	
		String userTemplateUnsubscibeEmail = resourceBundle.getPropertyValue(bundle, "userTemplateUnsubscribeEmail");
		String regards = resourceBundle.getPropertyValue(bundle, "userTemplateRegards");
		String regardName = resourceBundle.getPropertyValue(bundle, "userTemplateRegardsName");
		String receiverEmailIds = resourceBundle.getPropertyValue(bundle, "contact-promo-form-receiver-email-Ids");
		String userTemplateDearLable = resourceBundle.getPropertyValue(bundle, "userTemplateDear");
		String userTemplateFacebookURL = resourceBundle.getPropertyValue(bundle, "userTemplateFacebookURL");
		String userTemplateInstagramURL = resourceBundle.getPropertyValue(bundle, "userTemplateInstagramURL");
		String userTemplateFacebookImageURL = resourceBundle.getPropertyValue(bundle, "userTemplateFacebookImageURL");
		String userTemplateInstagramImageURL = resourceBundle.getPropertyValue(bundle, "userTemplateInstagramImageURL");
		String userTemplateDownloadButtonText = resourceBundle.getPropertyValue(bundle, "userTemplateDownloadText");
		String userTemplatePdfUrl = resourceBundle.getPropertyValue(bundle, "userTemplatePdfUrl");
		String userTemplateSenderEmailId = resourceBundle.getPropertyValue(bundle, "userTemplateSenderEmailId");
		String userTemplateSubject = resourceBundle.getPropertyValue(bundle, "userTemplateSubject");
		String userEmailTemplate = resourceBundle.getPropertyValue(bundle, "promotion-user-email-template");
		String kohlerEmailTemplate = resourceBundle.getPropertyValue(bundle, "bathtub-kohler-email-template");
		
		/*String fileLocation = Constants.LANDING_PAGE_EMAIL_TEMPLATE_FILE_LOCATION;
		ClassLoader classLoader = EmailServiceComponent.class.getClassLoader();
		File readfile = new File(classLoader.getResource(fileLocation).getFile());*/
		String content = null;
		try {
			content = kohlerEmailTemplate;
			content = content.replace("${kohlerLogo}", pdpEmailTemplateLogo);
			content = content.replace("${contactForm}", contactFormTempalteTitle);
			content = content.replace("${contactFormDetails}", contactFormTempalteDetailTitle);
			content = content.replace("${districtType}", contactFormTempalteDistrictsType);
			content = content.replace("${districtTypeValue}", districtType);
			content = content.replace("${firstName}", contactFormTempalteFirstname);
			content = content.replace("${firstNameValue}", firstName);
			content = content.replace("${emailAddress}", contactFormTempalteEmailTitle);
			content = content.replace("${emailAddressValue}", emailAddress);
			content = content.replace("${mobileNumber}", contactFormTempalteMobileTitle);
			content = content.replace("${mobileNumberValue}", mobileNumber);
			content = content.replace("${feedback}", emailTemplateFeedback);
			content = content.replace("${feedbackURL}", emailTemplateFeedbackURL);
			content = content.replace("${privacyPolicy}", emailTemplatePrivacyPolicy);
			content = content.replace("${privacyPolicyURL}", privacyPolicyShareEmailURL);
			content = content.replace("${currentYear}", String.valueOf(year));
			content = content.replace("${kohlerCo}", contactFormKohlerCoLogo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		/*String userTemplate = Constants.USER_TEMPLATE_FILE_LOCATION;
		File readUserTempfile = new File(classLoader.getResource(userTemplate).getFile());*/
		String userContent = null;
		try {
			userContent = userEmailTemplate;
			userContent = userContent.replace("${bannerImage}", userTemplateBannerImage);
			userContent = userContent.replace("${thankYou}", userTemplateThankYouMessage);
			userContent = userContent.replace("${Dear}", userTemplateDearLable);
			userContent = userContent.replace("${userName}", firstName);
			userContent = userContent.replace("${Description1}", userTemplateDesc1Message);
			userContent = userContent.replace("${Description2}", userTemplateDesc2Message);
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
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		Map<String, String> returnMap = new HashMap<>();
		if (firstName.isEmpty() && contactFormSenderEmailId.isEmpty()
				&& mobileNumber.isEmpty() && receiverEmailIds.isEmpty() && userTemplateSenderEmailId.isEmpty()) {
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
		if (!this.validateEmail(userTemplateSenderEmailId)) {
			LOGGER.error("userTemplateSenderEmailId missing in resource bundle");
			returnMap.put(Constants.FLAG, String.valueOf(false));
			returnMap.put(Constants.MESSAGE, contactFormErrorMessage);
			return returnMap;
		}
		boolean verify = this.verifyCaptcha(bundle, gRecaptchaResponse);
		if (verify) {
			try {
				MailService.sendEmail(contactFormSenderEmailId, receiverEmailIds, contactFormTemplateSubject, content);
				MailService.sendEmail(userTemplateSenderEmailId, emailAddress, userTemplateSubject, userContent);
				returnMap.put(Constants.SUCCESS, String.valueOf(true));
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				returnMap.put(Constants.FLAG, String.valueOf(false));
				returnMap.put(Constants.MESSAGE, contactFormEmailErrorMessage);
				return returnMap;
			}
		} else {
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
