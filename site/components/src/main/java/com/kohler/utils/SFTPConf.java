package com.kohler.utils;

/**
 * SFTPConfigutation data model
 * 
 * @author Sumit.Pallakhe
 *
 */
public class SFTPConf {

	private String sftpHost;
	private Integer sftpPort;
	private String sftpUser;
	private String sftpPassword;
	private String workingDirectory;
	private String fileName;
	private Integer batchSize;
	private String fileLastModifiedDateTime;
	private String suggestionSwatchSimilarUrl;
	private String apiKey;
	private String mailSubSchedularStarted;

	/**
	 * @param sftpHost
	 * @param sftpPort
	 * @param sftpUser
	 * @param sftpPassword
	 * @param workingDirectory
	 * @param fileName
	 * @param batchSize
	 * @param fileLastModifiedDateTime
	 */
	public SFTPConf(String sftpHost, Integer sftpPort, String sftpUser, String sftpPassword, String workingDirectory,
			String fileName, Integer batchSize, String fileLastModifiedDateTime,String suggestionSwatchSimilarUrl,String apiKey,String mailSubSchedularStarted) {
		this.sftpHost = sftpHost;
		this.sftpPort = sftpPort;
		this.sftpUser = sftpUser;
		this.sftpPassword = sftpPassword;
		this.workingDirectory = workingDirectory;
		this.fileName = fileName;
		this.batchSize = batchSize;
		this.fileLastModifiedDateTime = fileLastModifiedDateTime;
		this.suggestionSwatchSimilarUrl=suggestionSwatchSimilarUrl;
		this.apiKey=apiKey;
		this.mailSubSchedularStarted=mailSubSchedularStarted;
	}


	public String getSftpHost() {
		return sftpHost;
	}

	public void setSftpHost(String sftpHost) {
		this.sftpHost = sftpHost;
	}

	public Integer getSftpPort() {
		return sftpPort;
	}

	public void setSftpPort(Integer sftpPort) {
		this.sftpPort = sftpPort;
	}

	public String getSftpUser() {
		return sftpUser;
	}

	public void setSftpUser(String sftpUser) {
		this.sftpUser = sftpUser;
	}

	public String getSftpPassword() {
		return sftpPassword;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	public String getFileLastModifiedDateTime() {
		return fileLastModifiedDateTime;
	}

	public void setFileLastModifiedDateTime(String fileLastModifiedDateTime) {
		this.fileLastModifiedDateTime = fileLastModifiedDateTime;
	}

	public String getSuggestionSwatchSimilarUrl() {
		return suggestionSwatchSimilarUrl;
	}

	public void setSuggestionSwatchSimilarUrl(String suggestionSwatchSimilarUrl) {
		this.suggestionSwatchSimilarUrl = suggestionSwatchSimilarUrl;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getMailSubSchedularStarted() {
		return mailSubSchedularStarted;
	}

	public void setMailSubSchedularStarted(String mailSubSchedularStarted) {
		this.mailSubSchedularStarted = mailSubSchedularStarted;
	}
	
}
