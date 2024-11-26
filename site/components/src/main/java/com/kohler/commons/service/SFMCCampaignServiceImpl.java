package com.kohler.commons.service;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.exceptions.SFMCDataUploadException;
import com.kohler.commons.exceptions.SFMCFileUploadException;
import com.kohler.commons.exceptions.SFMCStatusCodeException;
import com.kohler.commons.exceptions.SFMCTokenGenerateException;
import com.kohler.commons.util.CommonUtil;

public class SFMCCampaignServiceImpl implements SFMCService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SFMCCampaignServiceImpl.class);

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	HstRequestContext hstRequestContext = RequestContextProvider.get();
	String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
	String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
	Locale locale = new Locale(language, country);
	ResourceBundle sfmc_campaign_bundle = resourceBundle.getBundle("sfmc-campaign-bundle", locale);

	@SuppressWarnings("unchecked")
	@Override
	public String getSFMCToken() throws SFMCTokenGenerateException {
		System.setProperty(Constants.HTTP_KEEP_ALIVE, Constants.FALSE);
		int timeout = 400;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		CloseableHttpResponse response = null;
		String clientId = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-client-id");
		String clientSecret = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-client-secret-id");
		String tokenURL = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "smfc-campaign-token-end-point");

		String json = "";
		int statusCode = 0;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode user = mapper.createObjectNode();
		user.put("grant_type", "client_credentials");
		user.put("client_id", clientId);
		user.put("client_secret", clientSecret);
		String accessToken = "";
		try {
			HttpPost request = new HttpPost(tokenURL);
			request.addHeader("Content-Type", "application/json;charset=UTF-8");
			request.addHeader("Accept", "application/json");
			StringEntity params = new StringEntity(user.toString(), Charsets.UTF_8);
			request.setEntity(params);
			response = httpClient.execute(request);
			LOGGER.info("=================response===========================" + response);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("!!! Response after loading '%s'", response.toString()));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new SFMCTokenGenerateException(response.toString());
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
				response.close();
				httpClient.close();
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw new SFMCTokenGenerateException(response.toString());
			}
			try {
				Map<String, String> map = mapper.readValue(json.toString(), Map.class);
				accessToken = map.get("access_token");
				//LOGGER.info("=================accessToken===========================" + accessToken);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw new SFMCTokenGenerateException(response.toString());
			}
		}

		return accessToken;
	}

	@Override
	public String uploadSFMCData(String token, JSONObject jsonData) throws SFMCDataUploadException {
		String warrantyFormJson = "";
		warrantyFormJson = jsonData.toString();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String requestId = "";
		String responseContent = "";
		try {
			String dataExtensionKey = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-data-extension-key");
			String jsonFormUrl = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-form-data-end-point");
			String actualUrl = jsonFormUrl.replace("{0}", dataExtensionKey);
			HttpPost httpPost = new HttpPost(actualUrl);
			// LOGGER.info("actualUrl=======================================" + actualUrl);
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
			httpPost.setHeader("Accept", "application/json");
			httpPost.addHeader("Authorization", "Bearer " + token);
			httpPost.setEntity(new StringEntity(warrantyFormJson, Charsets.UTF_8));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
			JSONObject jsonObject = new JSONObject(responseContent);
			requestId = jsonObject.getString("requestId");
			LOGGER.info("=================requestId===========================" + requestId);
			LOGGER.info("=================responsecode===========================" + response.getStatusLine().getStatusCode());
			LOGGER.info("=================responseContent===========================" + responseContent);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new SFMCDataUploadException(responseContent);
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
				throw new SFMCDataUploadException(responseContent);
			}
		}

		return requestId;
	}
	
	@Override
	public Integer getSFMCRequestIdStatus(String requestId, String tokenObject) throws SFMCStatusCodeException {
		String resultApi = resourceBundle.getPropertyValue(sfmc_campaign_bundle, "sfmc-campaign-result-end-point");
		String actualResultApi = resultApi.replace("{0}", requestId);
		HttpGet httpGet = new HttpGet(actualResultApi);
		httpGet.addHeader("Authorization", "Bearer " + tokenObject);
		int statusCode = 0;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			statusCode = response.getStatusLine().getStatusCode();
			LOGGER.info("=================statusCode===========================" + statusCode);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			throw new SFMCStatusCodeException(response.toString());
		}finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
				throw new SFMCStatusCodeException(response.toString());
			}
			
		}
		return statusCode;
	}

	@Override
	public String fileUpload(String fileName, String fileExtensionName, Integer id, String base64Image,
			String tokenObject) throws SFMCFileUploadException {
		return "";
	}


}
