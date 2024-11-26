package com.kohler.rest;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
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
import com.kohler.commons.exceptions.SFMCFileUploadException;
import com.kohler.commons.exceptions.SFMCStatusCodeException;
import com.kohler.commons.exceptions.SFMCTokenGenerateException;
import com.kohler.commons.service.MailService;
import com.kohler.commons.service.SFMCService;
import com.kohler.commons.service.SFMCServiceImpl;
import com.kohler.commons.util.CommonUtil;

@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
@Path("/hkwarrantyform/")
public class HKWarrantyFormService extends BaseRestResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HKWarrantyFormService.class);

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Persistable
	public Map<String, String> createWarrantyForm(@Multipart("koh-warranty-salutation") String salutation,
			@Multipart("koh-warranty-district") String district,
			@Multipart("koh-warranty-product-purchase") String purchaseDate,
			@Multipart("koh-warranty-product-type") String productType,
			@Multipart("koh-warranty-outlet") String whereToBuy, @Multipart("koh-warranty-email") String email,
			@Multipart("koh-warranty-full-name") String name, @Multipart("koh-warranty-mobile") String mobile,
			@Multipart("koh-warranty-address") String address,
			@Multipart("koh-warranty-product-delivery") String deliveryDate,
			@Multipart("kohler-warranty-privacy-policy") String privacyPolicy,
			@Multipart("kohler-contact-info") String contactInfo, @Multipart("file") Attachment file) {
		
		HstRequestContext hstRequestContext = RequestContextProvider.get();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country);
		ResourceBundle warranty_bundle = resourceBundle.getBundle(Constants.WARRANTY_BUNDLE_NAME, locale);
		SFMCService service = new SFMCServiceImpl();
		StringBuilder emailBody = new StringBuilder();
		Map<String, String> returnMap = new HashMap<>();
		String fileUploadUrl = "";
		String productPurchaseDate = getConvertedDate(purchaseDate);
		String productDeliveryDate = getConvertedDate(deliveryDate);
		boolean kohlerPrivacyPolicy = Boolean.parseBoolean(privacyPolicy);
		boolean kohlercontactInfo = Boolean.parseBoolean(contactInfo);
		String fileName = file.getContentDisposition().getFilename();
		InputStream inputStream = file.getObject(InputStream.class);
		byte[] fileContent = null;
		try {
			fileContent = IOUtils.toByteArray(inputStream);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		String base64enc = Base64.getEncoder().encodeToString(fileContent);
		String filename = fileName.split("[.]")[0];
		String extension = fileName.split("[.]")[1];
		Integer assetId = 0;
		if (extension.equals("pdf")) {
			assetId = 127;
		} else if (extension.equals("jpg")) {
			assetId = 23;
		} else {
			String fileUploadFormatMessage = resourceBundle.getPropertyValue(warranty_bundle,
					Constants.WARRANTY_FILE_UPLOAD_ERROR_MESSAGE_KEY);
			returnMap.put(Constants.FILE_EXT_ERROR_KEY, fileUploadFormatMessage);
			return returnMap;
		}
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
		String sfmcFileUploadApiErrorMessage = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_SFMC_FILE_UPLOAD_ERROR_MESSAGE);
		String sfmcDataUploadApiErrorMessage = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_SFMC_DATA_UPLOAD_ERROR_MESSAGE);
		String sfmcResultApiErrorMessage = resourceBundle.getPropertyValue(warranty_bundle,
				Constants.WARRANTY_SFMC_RESULT_API_ERROR_MESSAGE);
		try {
			String tokenObject = service.getSFMCToken();
			if(StringUtils.isEmpty(tokenObject)){
				throw new SFMCTokenGenerateException(sfmcTokenApiErrorMessage);
			}
			fileUploadUrl = service.fileUpload(filename, extension, assetId, base64enc, tokenObject);
			if(StringUtils.isEmpty(fileUploadUrl)){
				throw new SFMCFileUploadException(sfmcFileUploadApiErrorMessage);
			}
			JSONObject jsonData = createWarrantyFormJson(salutation, email, name, mobile, address, district,
					productPurchaseDate, productDeliveryDate, whereToBuy, productType, kohlerPrivacyPolicy,
					kohlercontactInfo, fileUploadUrl);
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
				String userTemplateDownloadButtonText = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_DOWNLOAD_BUTTON);
				String userTemplateDownloadLink = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_DOWNLOAD_LINK);
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
				String userTemplateTwitterImageURL = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_TWITTER_LOGO_PATH);
				String userTemplateTwitterLink = resourceBundle.getPropertyValue(warranty_bundle,
						Constants.WARRANTY_USER_TEMPLATE_TWITTER_LINK);
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
				userTemplate = userTemplate.replace("${dowmloadLink}", userTemplateDownloadLink);
				userTemplate = userTemplate.replace("${dowmloadText}", userTemplateDownloadButtonText);
				userTemplate = userTemplate.replace("${facebookUrl}", userTemplateFacebookLink);
				userTemplate = userTemplate.replace("${facebookImageUrl}", userTemplateFacebookImageURL);
				userTemplate = userTemplate.replace("${youTubeUrl}", userTemplateYoutubeLink);
				userTemplate = userTemplate.replace("${youTubeImageUrl}", userTemplateYoutubeImageURL);
				userTemplate = userTemplate.replace("${instagramUrl}", userTemplateInstagramLink);
				userTemplate = userTemplate.replace("${instagramImageUrl}", userTemplateInstagramImageURL);
				userTemplate = userTemplate.replace("${twitterUrl}", userTemplateTwitterLink);
				userTemplate = userTemplate.replace("${twitterImageUrl}", userTemplateTwitterImageURL);
				userTemplate = userTemplate.replace("${unSubscribe}", userTemplateSubscribeMessage);
				userTemplate = userTemplate.replace("${UnsubscribeEmail}", userTemplateUnsubscibeEmail);
				userTemplate = userTemplate.replace("${currentYear}", String.valueOf(year));
				MailService.sendEmail(templateSenderEmailId, email, templateSubject, userTemplate);
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
		} catch (SFMCFileUploadException fileUploadException) {
			emailBody.append(fileUploadException.getMessage());
			LOGGER.error(fileUploadException.getMessage());
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

	public JSONObject createWarrantyFormJson(String salutation, String email, String name, String mobile,
			String address, String distrct, String purchasedDate, String productDeliveryDate, String whereToBuy,
			String productType, boolean privacyPolicy, boolean specialOfferOptn, String filePath) {

		JSONObject jsonData = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject formData = new JSONObject();
		formData.put("Salutation", salutation);
		formData.put("Name", name);
		formData.put("Email", email);
		formData.put("Contact Number", mobile);
		formData.put("Address", address);
		formData.put("District", distrct);
		formData.put("Date of Purchase", purchasedDate);
		formData.put("Date of Delivery", productDeliveryDate);
		formData.put("Where to Buy", whereToBuy);
		formData.put("Type of product purchased", productType);
		formData.put("News and Email OptIn", specialOfferOptn);
		formData.put("Kohler Privacy", privacyPolicy);
		formData.put("FileURL", filePath);

		array.put(formData);
		jsonData.put("items", array);
		return jsonData;
	}

	public String getConvertedDate(String inputDate) {
		String finalString = "";
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date) formatter.parse(inputDate);
			SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd/yyyy");
			finalString = newFormat.format(date);
		} catch (Exception e) {
			return "01/27/2021";
		}
		return finalString;
	}
}
