/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.rest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.onehippo.cms7.essentials.components.rest.BaseRestResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kohler.commons.constants.Constants;

public class KohlerRestServices extends BaseRestResource {

	public HttpResponse httpGETService(Map<String, Object> map) {
		HttpResponse response = null;
		try {
			HttpClient client = HttpClientBuilder.create ().build ();
			HttpGet request = new HttpGet (Constants.IS_FRESH_JSON_URL);
			request.setHeader ("Content-Type", "application/json");
			request.setHeader ("Accept", "application/json");
			response = client.execute (request);
		} catch (ClientProtocolException e) {
			e.printStackTrace ();
		} catch (IOException e) {
			e.printStackTrace ();
		}
		return response;
	}



	public ResponseEntity<String> httpPOSTService(Map<String, Object> map) {
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object> ();
		String jsonData = null;
		try {
			jsonData = readJsonFromFile ();
		} catch (Exception e) {

			e.printStackTrace ();
		}
		parts.add ("jsonData", jsonData);

		HttpHeaders headers = new HttpHeaders ();
		headers.setContentType (MediaType.APPLICATION_JSON);
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>> (parts,
				headers);

		ResponseEntity<String> responseEntity;
		RestTemplate restTemplate = new RestTemplate ();
		responseEntity = restTemplate.exchange (Constants.IS_VALID_JSON_URL, HttpMethod.POST, entity, String.class);
		return responseEntity;
	}



	/**
	 * @description purely temporary, once the service for fetching the json
	 *              comes we shall remove this method
	 * @return
	 * @throws Exception
	 */
	public String readJsonFromFile() throws Exception {
		String result = null;
		String filePath = "D://kohler//sample.json";
		BufferedReader br = new BufferedReader (new FileReader (filePath));
		try {
			StringBuilder sb = new StringBuilder ();
			String line = br.readLine ();

			while (line != null) {
				sb.append (line);
				sb.append (System.lineSeparator ());
				line = br.readLine ();
			}
			result = sb.toString ();
		} finally {
			br.close ();
		}
		return result;
	}

}
