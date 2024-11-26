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
import com.kohler.commons.service.SFMCIBTServiceImpl;
import com.kohler.commons.service.SFMCService;
import com.kohler.commons.util.CommonUtil;

@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
@Path("/IBTform/")
public class IDBrandRegistrationForm extends BaseRestResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IDBrandRegistrationForm.class);

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Persistable
	public Map<String, String> createIBTForm(@Multipart("koh-brand-customer-email") String email,
			@Multipart("koh-brand-customer-name") String name, @Multipart("koh-brand-phone-number") String mobile,
			@Multipart("koh-brand-project-location") String projectLocation, @Multipart("koh-brand-budget-range") String budgetRange,
			@Multipart("koh-brand-timeline-project") String projectTimeline,
			@Multipart("koh-brand-project-as") String projectAs,
			@Multipart("koh-brand-project-type") String projectType,
			@Multipart("kohler-warranty-privacy-policy") String privacyPolicy,
			@Multipart("kohler-contact-info") String contactInfo) {
		
		HstRequestContext hstRequestContext = RequestContextProvider.get();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country);
		ResourceBundle warranty_bundle = resourceBundle.getBundle(Constants.WARRANTY_BUNDLE_NAME, locale);
		SFMCService service = new SFMCIBTServiceImpl();
		StringBuilder emailBody = new StringBuilder();
		Map<String, String> returnMap = new HashMap<>();
		boolean kohlerPrivacyPolicy = Boolean.parseBoolean(privacyPolicy);
		boolean kohlercontactInfo = Boolean.parseBoolean(contactInfo);
		
		String templateSenderEmailId = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_SENDER_EMAIL_ID_KEY);
		String sfmcApiErrorMessageToUser = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_SFMC_API_ERROR_MESSAGE_KEY);
		String sfmcApiErrorEmailSubject = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_SFMC_API_EMAIL_SUBJECT_KEY);
		String kohlerReceiverEmailIds = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_KOHLER_RECEIVER_EMAILS_KEY);
		String kohlerEmailBodyPart1 = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_KOHLER_EMAIL_BODY_PART1);
		String kohlerEmailBodyPart2 = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_KOHLER_EMAIL_BODY_PART2);
		String kohlerEmailBodyCustomerName = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_KOHLER_EMAIL_NAME_TITLE);
		String kohlerEmailBodyCustomerEmail = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_KOHLER_EMAIL_TITLE);
		String kohlerEmailBodyCustomerPhone = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_KOHLER_PHONE_TITLE);
		String sfmcTokenApiErrorMessage = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_SFMC_TOKEN_ERROR_MESSAGE);
		String sfmcDataUploadApiErrorMessage = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_SFMC_DATA_UPLOAD_ERROR_MESSAGE);
		String sfmcResultApiErrorMessage = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_SFMC_RESULT_API_ERROR_MESSAGE);
		try {
			String tokenObject = service.getSFMCToken();
			if(StringUtils.isEmpty(tokenObject)){
				throw new SFMCTokenGenerateException(sfmcTokenApiErrorMessage);
			}
			JSONObject jsonData = createFormJson(email, name, mobile, projectType, projectAs,
					budgetRange, projectTimeline, projectLocation, kohlerPrivacyPolicy, kohlercontactInfo);
			String requestId = service.uploadSFMCData(tokenObject, jsonData);
			if(StringUtils.isEmpty(requestId)){
				throw new SFMCDataUploadException(sfmcDataUploadApiErrorMessage);
			}
			int requestIdStatus = service.getSFMCRequestIdStatus(requestId, tokenObject);
			if (requestIdStatus == 200) {
				int year = LocalDate.now().getYear();
				String userTemplate = null;
				String warrantyUserEmailTemplate = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_EMAIL_TEMPLATE);
				userTemplate = warrantyUserEmailTemplate;
				String userTemplateBannerImage = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_BANNER);
				String userTemplateThankYouMessage = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_THNAKYOU);
				String userTemplateDearLable = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_DEAR);
				String userTemplateDesc1Message = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_DESC1);
				String userTemplateDesc2Message = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_DESC2);
				String regards = resourceBundle.getPropertyValue(warranty_bundle, Constants.WARRANTY_USER_TEMPLATE_REGARD_LABEL);
				String regardName = resourceBundle.getPropertyValue(warranty_bundle, Constants.WARRANTY_USER_TEMPLATE_REGARD_NAME);
				String userTemplateFacebookImageURL = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_FACEBOOK_LOGO_PATH);
				String userTemplateFacebookLink = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_FACEBOOK_LINK);
				String userTemplateYoutubeImageURL = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_YOUTUBE_LOGO_PATH);
				String userTemplateYoutubeLink = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_YOUTUBE_LINK);
				String userTemplateInstagramImageURL = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_INSTA_LOGO_PATH);
				String userTemplateInstagramLink = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_INSTA_LINK);
				String userTemplateSubscribeMessage = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_UNSUBSCRIBE_LABEL);
				String userTemplateUnsubscibeEmail = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_UNSUBSCRIBE_EMAIL);
				String templateSubject = resourceBundle.getPropertyValue(warranty_bundle, Constants.WARRANTY_USER_TEMPLATE_SUBJECT);
				
				userTemplate = userTemplate.replace("${bannerImage}", userTemplateBannerImage);
				userTemplate = userTemplate.replace("${thankYou}", userTemplateThankYouMessage);
				userTemplate = userTemplate.replace("${Dear}", userTemplateDearLable);
				userTemplate = userTemplate.replace("${userName}", name);
				userTemplate = userTemplate.replace("${Description1}", userTemplateDesc1Message);
				userTemplate = userTemplate.replace("${Description2}", userTemplateDesc2Message);
				userTemplate = userTemplate.replace("${regards}", regards);
				userTemplate = userTemplate.replace("${teamKohler}", regardName);
				userTemplate = userTemplate.replace("${facebookUrl}", userTemplateFacebookLink);
				userTemplate = userTemplate.replace("${facebookImageUrl}", userTemplateFacebookImageURL);
				userTemplate = userTemplate.replace("${youTubeUrl}", userTemplateYoutubeLink);
				userTemplate = userTemplate.replace("${youTubeImageUrl}", userTemplateYoutubeImageURL);
				userTemplate = userTemplate.replace("${instagramUrl}", userTemplateInstagramLink);
				userTemplate = userTemplate.replace("${instagramImageUrl}", userTemplateInstagramImageURL);
				userTemplate = userTemplate.replace("${unSubscribe}", userTemplateSubscribeMessage);
				userTemplate = userTemplate.replace("${UnsubscribeEmail}", userTemplateUnsubscibeEmail);
				userTemplate = userTemplate.replace("${currentYear}", String.valueOf(year));
				MailService.sendEmail(templateSenderEmailId, email, templateSubject, userTemplate);
				returnMap.put(Constants.SUCCESS, String.valueOf(true));
			} else {
				returnMap.put(Constants.SFMC_API_ERROR_KEY, sfmcApiErrorMessageToUser);
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

	public JSONObject createFormJson(String email, String fullName, String mobile, String projectType, String projectAs,
			String budgetRange, String projectTimeline, String projectLocation, boolean privacyPolicy,
			boolean contactInfo) {

		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject formData = new JSONObject();

		formData.put("Customer Email", email);
		formData.put("Customer Name", fullName);
		formData.put("Customer Phone Number", mobile);
		formData.put("Project Type", projectType);
		formData.put("Project As", projectAs);
		formData.put("Range Budget", budgetRange);
		formData.put("Timeline Project", projectTimeline);
		formData.put("Project Location", projectLocation);
		formData.put("Kohler Privacy", privacyPolicy);
		formData.put("News and Email OptIn", contactInfo);

		array.put(formData);
		json.put("items", array);
		return json;
	}

}
