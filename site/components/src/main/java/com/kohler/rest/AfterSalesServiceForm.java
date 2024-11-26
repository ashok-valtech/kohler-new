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

import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.MailService;
import com.kohler.commons.util.CommonUtil;

@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
@Path("/afterSalesService/")
public class AfterSalesServiceForm extends BaseRestResource {

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	private static final Logger LOGGER = LoggerFactory.getLogger(AfterSalesServiceForm.class);

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Persistable
	public Map<String, String> createContactForm(@Multipart("koh-contact-first-name") String firstName,
			@Multipart("koh-contact-last-name") String lastName,
			@Multipart("koh-contact-email-address") String emailAddress,
			@Multipart("koh-contact-mobile-number") String mobileNumber,
			@Multipart("koh-contact-address") String address,
			@Multipart("koh-source-of-purchasing") String sourceOfPurchasing, @Multipart("koh-contact-sku") String sku,
			@Multipart("koh-contact-request") String request) {

		int year = LocalDate.now().getYear();
		HstRequestContext hstRequestContext = RequestContextProvider.get();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country);
		ResourceBundle bundle = resourceBundle.getBundle(Constants.CONTACT_FORM, locale);

		String contactFormErrorMessage = resourceBundle.getPropertyValue(bundle, "contact-form-error-message-key");
		String pdpEmailTemplateLogo = resourceBundle.getPropertyValue(bundle, "contact-form-template-logo");
		String emailTemplatePrivacyPolicy = resourceBundle.getPropertyValue(bundle, "contact-form-template-privacy");
		String emailTemplateFeedbackURL = resourceBundle.getPropertyValue(bundle, "contact-form-feedback-URL");
		String contactFormKohlerCoLogo = resourceBundle.getPropertyValue(bundle, "contact-form-kohler-co-logo");
		String contactFormTempalteTitle = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-template-title");
		String contactFormTempalteDetailTitle = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-details-title");
		String contactFormTempalteFirstname = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-name");
		String contactFormTempalteLastName = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-last-name");
		String contactFormTempalteEmailTitle = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-email");
		String contactFormTempalteMobileTitle = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-mobile");
		String templateAddressLabel = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-address");
		String templateSourceOfPurchaseLabel = resourceBundle.getPropertyValue(bundle, "after-sales-service-source-template-label");
		String templateSkuLabel = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-sku");
		String templateRequestLabel = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-request");
		String emailTemplateFeedback = resourceBundle.getPropertyValue(bundle, "contact-form-template-feedback");
		String privacyPolicyShareEmailURL = resourceBundle.getPropertyValue(bundle, "contact-form-privacy-URL");
		String emailSubject = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-template-subject");
		String afterSalesServiceSenderEmailId = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-sender-email-Id");
		String receiverEmailId = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-receiver-email-Id");
		String afterSalesServiceTemplate = resourceBundle.getPropertyValue(bundle, "after-sales-service-form-temp");

		String content = afterSalesServiceTemplate;
		content = content.replace("${kohlerLogo}", pdpEmailTemplateLogo);
		content = content.replace("${contactForm}", contactFormTempalteTitle);
		content = content.replace("${contactFormDetails}", contactFormTempalteDetailTitle);
		content = content.replace("${firstName}", contactFormTempalteFirstname);
		content = content.replace("${firstNameValue}", firstName);
		content = content.replace("${lastName}", contactFormTempalteLastName);
		content = content.replace("${lastNameValue}", lastName);
		content = content.replace("${emailAddress}", contactFormTempalteEmailTitle);
		content = content.replace("${emailAddressValue}", emailAddress);
		content = content.replace("${mobileNumber}", contactFormTempalteMobileTitle);
		content = content.replace("${mobileNumberValue}", mobileNumber);
		content = content.replace("${address}", templateAddressLabel);
		content = content.replace("${addressValue}", address);
		content = content.replace("${sourcePurchase}", templateSourceOfPurchaseLabel);
		content = content.replace("${sourcePurchaseValue}", sourceOfPurchasing);
		content = content.replace("${sku}", templateSkuLabel);
		content = content.replace("${skuValue}", sku);
		content = content.replace("${requestDescription}", templateRequestLabel);
		content = content.replace("${requestDescriptionValue}", request);
		content = content.replace("${feedback}", emailTemplateFeedback);
		content = content.replace("${feedbackURL}", emailTemplateFeedbackURL);
		content = content.replace("${privacyPolicy}", emailTemplatePrivacyPolicy);
		content = content.replace("${privacyPolicyURL}", privacyPolicyShareEmailURL);
		content = content.replace("${currentYear}", String.valueOf(year));
		content = content.replace("${kohlerCo}", contactFormKohlerCoLogo);

		Map<String, String> returnMap = new HashMap<>();
		if (!firstName.isEmpty() && !lastName.isEmpty() && !emailAddress.isEmpty() && !mobileNumber.isEmpty()
				&& !address.isEmpty()) {
			try {
				MailService.sendEmail(afterSalesServiceSenderEmailId, receiverEmailId, emailSubject, content);
				returnMap.put(Constants.SUCCESS, String.valueOf(true));
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				returnMap.put(Constants.FLAG, String.valueOf(false));
				returnMap.put(Constants.MESSAGE, contactFormErrorMessage);
				return returnMap;
			}
		} else {
			returnMap.put(Constants.FLAG, String.valueOf(false));
			returnMap.put(Constants.MESSAGE, contactFormErrorMessage);
		}
		return returnMap;
	}

}
