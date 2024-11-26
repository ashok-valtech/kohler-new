package com.kohler.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.jcr.RepositoryException;

import org.onehippo.repository.scheduling.RepositoryJobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.commons.constants.Constants;

/**
 * 
 * @author dhanwan.r
 *
 */
public class SuggestionSwatchScheduler extends KohlerScheduler{

	private static final Logger LOGGER = LoggerFactory.getLogger (CMSScheduler.class);
	
	@Override
	public void execute(RepositoryJobExecutionContext context) throws RepositoryException {
		String apiUrl = context.getAttribute(Constants.SUGGESTION_SWATCH_API_URL);
		String apiKey = context.getAttribute(Constants.PIM_API_KEY);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Executing Suggestion Swatch for ApiUrl : '%s'", apiUrl));
		}
		this.executeSuggestionSwatch(apiUrl, apiKey);
	}
	
	// HTTP GET request	
	public void executeSuggestionSwatch (String apiUrl, String apiKey){
		HttpURLConnection urlConnection = null;
		BufferedReader rd = null;
		System.setProperty(Constants.HTTP_KEEP_ALIVE, Constants.FALSE);
		try {
			URL url = new URL(apiUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");	   
			urlConnection.setRequestProperty (Constants.PIM_API_KEY, apiKey);
			rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF8"));
			String line;
			StringBuilder response = new StringBuilder(); 
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("Response String: '%s'", line));
			}
			
		} catch(Exception e){
			if (rd != null ) {
				try {
					rd.close();
				} catch (IOException ex) {
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error(e.getMessage(), ex);
					}
				}
			}
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Exception while checking for new PIM file ", e);
			}
		}
		finally {
			if (urlConnection != null) {
				this.closeURLConnection(urlConnection);
				this.disconnectURLConnection(urlConnection);
			}
		}
	}
}
