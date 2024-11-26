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

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.annotations.Persistable;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.onehippo.cms7.essentials.components.rest.BaseRestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.exceptions.SFMCDataUploadException;
import com.kohler.commons.exceptions.SFMCStatusCodeException;
import com.kohler.commons.exceptions.SFMCTokenGenerateException;
import com.kohler.commons.service.MailService;
import com.kohler.commons.service.SFMCCampaignServiceImpl;
import com.kohler.commons.service.SFMCService;
import com.kohler.commons.util.CommonUtil;

@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
@Path("/hkdigitalshowroom/")
public class HKDigitalShowroomCampaignForm extends BaseRestResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HKDigitalShowroomCampaignForm.class);

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Persistable
	public Map<String, String> createDigitalCampaignForm(@Multipart("koh-bathroom-fixtures") String bathroomFixtures,
			@Multipart("koh-interest-products") String kohlerProducts, @Multipart("hk-digital-form-name") String name,
			@Multipart("hk-digital-form-mobile-number") String mobile,
			@Multipart("hk-digital-form-email-address") String email,
			@Multipart("hk-digital-form-submission-date") String formSubmissionTime) {
		
		HstRequestContext hstRequestContext = RequestContextProvider.get();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country);
		ResourceBundle sfmc_campaign_bundle = resourceBundle.getBundle("sfmc-campaign-bundle", locale);
		SFMCService service = new SFMCCampaignServiceImpl();
		StringBuilder emailBody = new StringBuilder();
		Map<String, String> returnMap = new HashMap<>();
		
		String templateSenderEmailId = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-sender-email-id");
		String sfmcApiErrorMessageToUser = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-api-user-error-message");
		String sfmcApiErrorEmailSubject = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-api-email-error-subject");
		String kohlerReceiverEmailIds = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-error-receiver-email-id");
		String kohlerEmailBodyPart1 = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-error-email-body-part1");
		String kohlerEmailBodyPart2 = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-error-email-body-part2");
		String kohlerEmailBodyCustomerName = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-error-email-name-label");
		String kohlerEmailBodyCustomerEmail = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-error-email-label");
		String kohlerEmailBodyCustomerPhone = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-error-email-phone-label");
		String sfmcTokenApiErrorMessage = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-token-api-error-message");
		String sfmcDataUploadApiErrorMessage = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-form-data-api-error-message");
		String sfmcResultApiErrorMessage = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
				"sfmc-campaign-result-api-error-message");
		try {
			String tokenObject = service.getSFMCToken();
			if(StringUtils.isEmpty(tokenObject)){
				throw new SFMCTokenGenerateException(sfmcTokenApiErrorMessage);
			}
			JSONObject jsonData = createDigitalCampaignFormJson(name, email, mobile, formSubmissionTime, bathroomFixtures, kohlerProducts);
			String requestId = service.uploadSFMCData(tokenObject, jsonData);
			if(StringUtils.isEmpty(requestId)){
				throw new SFMCDataUploadException(sfmcDataUploadApiErrorMessage);
			}
			int requestIdStatus = service.getSFMCRequestIdStatus(requestId, tokenObject);
			if (requestIdStatus == 200) {
				int year = LocalDate.now().getYear();
				String digitalCampaignEmailTemplate = resourceBundle.getPropertyValue(sfmc_campaign_bundle,
					"sfmc-campaign-kohler-template");
				String kohlerTemplate = digitalCampaignEmailTemplate;
				
				String pdpEmailTemplateLogo = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-email-template-logo");
				String emailTemplatePrivacyPolicy = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-privacy-policy");
				String emailTemplateFeedbackURL = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-feedback-url");
				String contactFormKohlerCoLogo = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-kohler-co-label");
				String contactFormTempalteTitle = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-email-template-title");
				String bathroomFixturesLabel = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaing-bathroom-fixture-label");
				String kohlerProductsLabel = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaing-interested-product-label");
				String contactFormTempalteDetailTitle = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-email-template-form-detail-label");
				String contactFormTempalteFirstname = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-email-template-name-label");
				String contactFormTempalteEmailTitle = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-email-label");
				String contactFormTempalteMobileTitle = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-email-template-mobile-label");
				String emailTemplateFeedback = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-feedback-label");
				String privacyPolicyShareEmailURL = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-privacy-url");
				String emailSubject = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-email-template-subject");
				String templateReceiverEmailId = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-template-receiver-email-id");

				kohlerTemplate = kohlerTemplate.replace("${kohlerLogo}", pdpEmailTemplateLogo);
				kohlerTemplate = kohlerTemplate.replace("${contactForm}", contactFormTempalteTitle);
				kohlerTemplate = kohlerTemplate.replace("${contactFormDetails}", contactFormTempalteDetailTitle);
				kohlerTemplate = kohlerTemplate.replace("${bathroomFixturesLabel}", bathroomFixturesLabel);
				kohlerTemplate = kohlerTemplate.replace("${bathroomFixtures}", bathroomFixtures);
				kohlerTemplate = kohlerTemplate.replace("${kohlerProductsLabel}", kohlerProductsLabel);
				kohlerTemplate = kohlerTemplate.replace("${kohlerProducts}", kohlerProducts);
				kohlerTemplate = kohlerTemplate.replace("${firstName}", contactFormTempalteFirstname);
				kohlerTemplate = kohlerTemplate.replace("${firstNameValue}", name);
				kohlerTemplate = kohlerTemplate.replace("${emailAddress}", contactFormTempalteEmailTitle);
				kohlerTemplate = kohlerTemplate.replace("${emailAddressValue}", email);
				kohlerTemplate = kohlerTemplate.replace("${mobileNumber}", contactFormTempalteMobileTitle);
				kohlerTemplate = kohlerTemplate.replace("${mobileNumberValue}", mobile);
				kohlerTemplate = kohlerTemplate.replace("${feedback}", emailTemplateFeedback);
				kohlerTemplate = kohlerTemplate.replace("${feedbackURL}", emailTemplateFeedbackURL);
				kohlerTemplate = kohlerTemplate.replace("${privacyPolicy}", emailTemplatePrivacyPolicy);
				kohlerTemplate = kohlerTemplate.replace("${privacyPolicyURL}", privacyPolicyShareEmailURL);
				kohlerTemplate = kohlerTemplate.replace("${currentYear}", String.valueOf(year));
				kohlerTemplate = kohlerTemplate.replace("${kohlerCo}", contactFormKohlerCoLogo);
				MailService.sendEmail(templateSenderEmailId, templateReceiverEmailId, emailSubject, kohlerTemplate);
				returnMap.put(Constants.SUCCESS, String.valueOf(true));
			} else {
				returnMap.put(Constants.SFMC_API_ERROR_KEY, sfmcApiErrorMessageToUser);
				LOGGER.error(sfmcResultApiErrorMessage);
				throw new SFMCStatusCodeException(sfmcResultApiErrorMessage);
			}

		} catch (SFMCTokenGenerateException tokenException) {
			emailBody.append(tokenException.getMessage());
			LOGGER.error(tokenException.getMessage());
			returnMap.put(Constants.SFMC_API_ERROR_KEY, sfmcApiErrorMessageToUser);
		} catch (SFMCDataUploadException dataUploadException) {
			emailBody.append(dataUploadException.getMessage());
			LOGGER.error(dataUploadException.getMessage());
			returnMap.put(Constants.SFMC_API_ERROR_KEY, sfmcApiErrorMessageToUser);
		} catch (SFMCStatusCodeException statusCodeException) {
			emailBody.append(statusCodeException.getMessage());
			LOGGER.error(statusCodeException.getMessage());
			returnMap.put(Constants.SFMC_API_ERROR_KEY, sfmcApiErrorMessageToUser);
		} finally {
			if (!emailBody.toString().isEmpty()) {
				MailService.sendEmail(templateSenderEmailId, kohlerReceiverEmailIds, sfmcApiErrorEmailSubject,
						kohlerEmailBodyPart1 + emailBody.toString() + kohlerEmailBodyPart2 + kohlerEmailBodyCustomerName
								+ name + kohlerEmailBodyCustomerEmail + email + kohlerEmailBodyCustomerPhone + mobile);
			}
		}
		return returnMap;

	}

	public JSONObject createDigitalCampaignFormJson(String name, String email, String mobile, String formSubmissionTime, String bathroomFixtures, String kohlerProducts) {

		JSONObject jsonData = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject formData = new JSONObject();
		formData.put("Name", name);
		formData.put("Email", email);
		formData.put("Contact Number", mobile);
		formData.put("Submission DateTime-HK", formSubmissionTime);
		formData.put("Plan for bathroom fixtures", bathroomFixtures);
		formData.put("Why interested in Kohler products", kohlerProducts);
		
		array.put(formData);
		jsonData.put("items", array);
		return jsonData;
	}
}
