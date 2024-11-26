package com.kohler.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.commons.constants.Constants;

/**
 * 
 * Google Captcha verification class
 * @author krushna.mahunta
 * Date:10/08/2018
 * @version 1.0
 * 
 */
public class VerifyRecaptcha {

	private static final Logger LOGGER = LoggerFactory.getLogger(VerifyRecaptcha.class);

	public static boolean verify(String gRecaptchaResponse, String url, String userAgent, String secret)
			throws IOException {
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}
		HttpsURLConnection urlConnection = null;
		try {
			URL obj = new URL(url);
			urlConnection = (HttpsURLConnection) obj.openConnection();

			// add reuqest header
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("User-Agent", userAgent);
			urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String postParams = "secret=" + secret + "&response=" + gRecaptchaResponse;

			// Send post request
			urlConnection.setDoOutput(true);
			
			setParameter(urlConnection, postParams);
			
			String result = readResponse (urlConnection);
			
			// parse JSON response and return 'success' value
			JsonReader jsonReader = Json.createReader(new StringReader(result));
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();

			return jsonObject.getBoolean(Constants.SUCCESS);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return false;
		} finally {
			if (urlConnection != null) {
				try {
					urlConnection.getInputStream().close();
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}

				try {
					urlConnection.disconnect();
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

	private static String readResponse (HttpsURLConnection urlConnection) {
		StringBuilder response = new StringBuilder();
		if (urlConnection != null) {
			String inputLine;
			try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			} catch (Exception e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			} 
		}
		return response.toString();
	}
	
	private static void setParameter (HttpsURLConnection urlConnection, String postParams) {
		try (DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream())) {
			dataOutputStream.writeBytes(postParams);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
