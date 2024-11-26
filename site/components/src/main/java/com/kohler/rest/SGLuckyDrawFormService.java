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

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.annotations.Persistable;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.onehippo.cms7.essentials.components.rest.BaseRestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.MailService;
import com.kohler.commons.util.CommonUtil;

@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
@Path("/sgluckydraw/")
public class SGLuckyDrawFormService extends BaseRestResource {

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	private static final Logger LOGGER = LoggerFactory.getLogger(SGLuckyDrawFormService.class);

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Persistable
	public Map<String, String> createWarrantyForm(@Multipart("koh-warranty-salutation") String salutation,
			@Multipart("koh-warranty-product-purchase") String purchaseDate,
			@Multipart("koh-warranty-outlet") String whereToBuy, @Multipart("koh-warranty-email") String email,
			@Multipart("koh-warranty-full-name") String name, @Multipart("koh-warranty-mobile") String mobile,
			@Multipart("koh-warranty-address") String address,
			@Multipart("koh-warranty-product-delivery") String deliveryDate,
			@Multipart("koh-warranty-choose") String purchased,
			@Multipart("koh-warranty-about-kohler") String aboutKohler,
			@Multipart("koh-warranty-suggestions") String suggestions,
			@Multipart("kohler-warranty-privacy-policy") String privacyPolicy,
			@Multipart("kohler-contact-info") String contactInfo, @Multipart("file") Attachment file) {
		
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
		} else {
			assetId = 23;
		}
		
		try {
			String tokenObject = createToken();
			fileUploadUrl = createFileUploadJson(filename, extension, assetId, base64enc, tokenObject);
			if(!fileUploadUrl.isEmpty()){
			createWarrantyFormJson(salutation, email, name, mobile, address, productPurchaseDate,
					productDeliveryDate, whereToBuy, purchased, aboutKohler, suggestions,
					kohlerPrivacyPolicy, kohlercontactInfo, fileUploadUrl, tokenObject);
			}

			HstRequestContext hstRequestContext = RequestContextProvider.get();
			String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
			String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
			Locale locale = new Locale(language, country);
			ResourceBundle warranty_bundle = resourceBundle.getBundle("warranty-registration", locale);

			int year = LocalDate.now().getYear();
			String userTemplate = null;
			String warrantyUserEmailTemplate = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-user-email-template");
			userTemplate = warrantyUserEmailTemplate;
			String userTemplateBannerImage = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-user-template-banner");
			String userTemplateThankYouMessage = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-user-template-thankyou");
			String userTemplateDearLable = resourceBundle.getPropertyValue(warranty_bundle, "mu-template-dear-label");
			String userTemplateDesc1Message = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-user-template-description1");
			String userTemplateDesc2Message = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-user-template-description2");
			String regards = resourceBundle.getPropertyValue(warranty_bundle, "warranty-template-regard-label");
			String regardName = resourceBundle.getPropertyValue(warranty_bundle, "warranty-template-regard-name");
			String userTemplateDownloadButtonText = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-download-button");
			String userTemplateDownloadLink = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-download-link");
			String userTemplateFacebookImageURL = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-facebook-image-url");
			String userTemplateFacebookLink = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-facebook-link");
			String userTemplateYoutubeImageURL = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-youtube-image-url");
			String userTemplateYoutubeLink = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-youtube-link");
			String userTemplateInstagramImageURL = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-instagram-image-url");
			String userTemplateInstagramLink = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-instagram-link");
			String userTemplateTwitterImageURL = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-twitter-image-url");
			String userTemplateTwitterLink = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-twitter-link");
			String userTemplateSubscribeMessage = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-Unsubscribe-label");
			String userTemplateUnsubscibeEmail = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-template-Unsubscribe-email");
			String templateSenderEmailId = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-user-template-sender-email");
			String templateSubject = resourceBundle.getPropertyValue(warranty_bundle, "warranty-template-subject");

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

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Map<String, String> returnMap = new HashMap<>();
		if (fileUploadUrl.isEmpty()) {
			returnMap.put(Constants.MESSAGE, "error");
		} else {
			returnMap.put(Constants.SUCCESS, String.valueOf(true));
		}
		return returnMap;

	}

	/* HTTP Post Request */

	public void createWarrantyFormJson(String salutation, String email, String name, String mobile, String address,
			String purchasedDate, String productDeliveryDate, String whereToBuy, String purchaseBefore,
			String knowAboutKohler, String suggestions, boolean privacyPolicy, boolean specialOfferOptn,
			String hkWarrantyFileUploadUrl, String tokenObject) {

		String warrantyFormJson = "";
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject formData = new JSONObject();
		formData.put("Salutation", salutation);
		formData.put("Name", name);
		formData.put("Email", email);
		formData.put("Contact Number", mobile);
		formData.put("Address", address);
		formData.put("Date of Purchase", purchasedDate);
		formData.put("Date of Delivery", productDeliveryDate);
		formData.put("Where to Buy", whereToBuy);
		formData.put("Have you ever bought KOHLER product", purchaseBefore);
		formData.put("How do you know about KOHLER", knowAboutKohler);
		formData.put("Suggestions", suggestions);
		formData.put("News and Email OptIn", specialOfferOptn);
		formData.put("Kohler Privacy", privacyPolicy);
		formData.put("FileURL", hkWarrantyFileUploadUrl);

		array.put(formData);
		json.put("items", array);
		warrantyFormJson = json.toString();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HstRequestContext hstRequestContext = RequestContextProvider.get();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country);
		ResourceBundle warranty_bundle = resourceBundle.getBundle("warranty-registration", locale);

		try {
			String dataExtensionKey = resourceBundle.getPropertyValue(warranty_bundle, "warranty-dataExtensionKey");
			String jsonFormUrl = resourceBundle.getPropertyValue(warranty_bundle, "warranty-json-form-URL");
			String actualUrl = jsonFormUrl.replace("{0}", dataExtensionKey);
			HttpPost httpPost = new HttpPost(actualUrl);
			//LOGGER.info("actualUrl=======================================" + actualUrl);
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
			httpPost.setHeader("Accept", "application/json");
			httpPost.addHeader("Authorization", "Bearer " + tokenObject);
			httpPost.setEntity(new StringEntity(warrantyFormJson, Charsets.UTF_8));
			//LOGGER.info("warrantyFormJson=======================================" + warrantyFormJson);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String responseContent = EntityUtils.toString(entity, "UTF-8");
			
			LOGGER.info("=================responsecode==========================="
					+ response.getStatusLine().getStatusCode());
			LOGGER.info("=================responseContent===========================" + responseContent);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public String createFileUploadJson(String fileName, String fileExtensionName, Integer id, String base64Image, String tokenObject) {
		JsonObjectBuilder fileNameBuilder = Json.createObjectBuilder();
		JsonObjectBuilder assetTypeBuilder = Json.createObjectBuilder();
		String fileUploadJson = "";
		String generatedString = RandomStringUtils.randomAlphanumeric(10);
		
		fileNameBuilder.add("name", fileName+generatedString);
		assetTypeBuilder.add("name", fileExtensionName).add("id", id);
		fileNameBuilder.add("assetType", assetTypeBuilder);
		fileNameBuilder.add("file", base64Image);
		JsonObject empJsonObject = fileNameBuilder.build();
		fileUploadJson = empJsonObject.toString();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HstRequestContext hstRequestContext = RequestContextProvider.get();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country);
		ResourceBundle warranty_bundle = resourceBundle.getBundle("warranty-registration", locale);
		String fileURL ="";
		try {

			String fileUploadPostURL = resourceBundle.getPropertyValue(warranty_bundle,
					"warranty-sfmc-file-upload-url");
			HttpPost httpPost = new HttpPost(fileUploadPostURL);
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
			httpPost.setHeader("Accept", "application/json");
			httpPost.addHeader("Authorization", "Bearer " + tokenObject);
			httpPost.setEntity(new StringEntity(fileUploadJson, Charsets.UTF_8));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String responseContent = EntityUtils.toString(entity, "UTF-8");
			JSONObject jsonObject = new JSONObject(responseContent);
			fileURL = jsonObject.getJSONObject("fileProperties").getString("publishedURL");
			//LOGGER.info("fileURL-----------------------------------------------------"+fileURL);
			LOGGER.info("=================responsecode==========================="
					+ response.getStatusLine().getStatusCode());
			LOGGER.info("=================responseContent===========================" + responseContent);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileURL;
	}

	/* Creating Token */

	@SuppressWarnings("unchecked")
	public String createToken() {
		System.setProperty(Constants.HTTP_KEEP_ALIVE, Constants.FALSE);

		int timeout = 400;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		CloseableHttpResponse response = null;
		HstRequestContext hstRequestContext = RequestContextProvider.get();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(language, country);
		ResourceBundle warranty_bundle = resourceBundle.getBundle("warranty-registration", locale);
		String clientId = resourceBundle.getPropertyValue(warranty_bundle, "warranty-token-client-id");
		String clientSecret = resourceBundle.getPropertyValue(warranty_bundle, "warranty-token-client-secret");
		String accountId = resourceBundle.getPropertyValue(warranty_bundle, "warranty-token-account-id");
		String json = "";
		int statusCode = 0;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode user = mapper.createObjectNode();
		user.put("grant_type", "client_credentials");
		user.put("client_id", clientId);
		user.put("client_secret", clientSecret);
		user.put("account_id", accountId);

		String accessToken = "";
		String tokenURL = resourceBundle.getPropertyValue(warranty_bundle, "warranty-token-URL");
		try {
			HttpPost request = new HttpPost(tokenURL);
			request.addHeader("Content-Type", "application/json;charset=UTF-8");
			request.addHeader("Accept", "application/json");
			StringEntity params = new StringEntity(user.toString(), Charsets.UTF_8);
			request.setEntity(params);
			response = httpClient.execute(request);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("!!! Response after loading '%s'", response.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					statusCode = response.getStatusLine().getStatusCode();
					if (statusCode < 500) {
						json = EntityUtils.toString(response.getEntity());
					} else {
						json = "";
					}
				}
				httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Map<String, String> map = mapper.readValue(json.toString(), Map.class);
				accessToken = map.get("access_token");
				//LOGGER.info("=================accessToken===========================" + accessToken);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return accessToken;

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
