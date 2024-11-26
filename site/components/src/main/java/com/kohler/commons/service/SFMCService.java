package com.kohler.commons.service;

import org.json.JSONObject;

import com.kohler.commons.exceptions.SFMCDataUploadException;
import com.kohler.commons.exceptions.SFMCFileUploadException;
import com.kohler.commons.exceptions.SFMCStatusCodeException;
import com.kohler.commons.exceptions.SFMCTokenGenerateException;

public interface SFMCService {

	public String getSFMCToken() throws SFMCTokenGenerateException;

	public String fileUpload(String fileName, String fileExtensionName, Integer id, String base64Image,
			String tokenObject) throws SFMCFileUploadException;

	public String uploadSFMCData(String token, JSONObject jsonData) throws SFMCDataUploadException;
	
	public Integer getSFMCRequestIdStatus(String requestId, String tokenObject) throws SFMCStatusCodeException;

}
