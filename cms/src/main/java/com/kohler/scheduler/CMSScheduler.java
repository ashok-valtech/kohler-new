package com.kohler.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.onehippo.repository.scheduling.RepositoryJobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.json.beans.Product;
import com.kohler.commons.service.JSONFileValidator;
import com.kohler.commons.service.MailService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 
 * @author dhanwan.r
 *
 */
public class CMSScheduler extends KohlerScheduler {
	
	private static final Logger LOG = LoggerFactory.getLogger (CMSScheduler.class);

	/* (non-Javadoc)
	 * @see com.kohler.scheduler.KohlerScheduler#execute(org.onehippo.repository.scheduling.RepositoryJobExecutionContext)
	 * 
	 * Method executes after scheduled time.
	 * Starting point for scheduler
	 */
	@Override
	public void execute (RepositoryJobExecutionContext context) throws RepositoryException {
		
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("jsonFileUrl : '%s'", context.getAttribute(Constants.PIM_JSON_FILE_URL)));
			LOG.info(String.format("hippoPimApiUrl : '%s'", context.getAttribute(Constants.PIM_HIPPO_API_URL)));
			LOG.info("Checking whether new PIM file is Uploaded or Not...");
		}
		Boolean isNewFileFlag = true;// isNewPimFileAvailable(context);
		if (isNewFileFlag) {
			LOG.info("New PIM file is available, fetching the content of new PIM file");
			String jsonData = getContentOfNewPimFile(context);
			LOG.info("Done with fetching content of new PIM file");
			JSONFileValidator jSONValidator = JSONFileValidator.getInstance();
			Map<String, Product> products = jSONValidator.isValidJSON(jsonData);
			StringBuilder emailBody = null;
			String sender = context.getAttribute(Constants.PIM_MAIL_SENDER);
			String recipients = context.getAttribute(Constants.PIM_MAIL_RECIPIENTS);
			String subject = context.getAttribute(Constants.PIM_MAIL_SUBJECT);
			if (null == products) {
				LOG.error("PIM file is not in correct format.");
				emailBody = new StringBuilder (context.getAttribute(Constants.PIM_MAIL_BODY));
				emailBody = emailBody.append(" \n\n" + "PIM file is not in correct format.");
		    	MailService.sendEmail(sender, recipients, subject, emailBody.toString());
			} else {
				Set<String> productIds = this.initiatePimUpload(context, products);
				if (!products.keySet().isEmpty()) {
					deletePimUnavailable (context, productIds);
				}
			}
		}else{
			LOG.info("New PIM file is not available, No need to run the pim import");
		}
		
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("CMSScheduler Job endeded. end time: '%s'", new Date()));
		}
	}
	private Set<String> initiatePimUpload (RepositoryJobExecutionContext context, Map<String, Product> products) {
		LOG.info("Starting to load PIM file");
		String sender = context.getAttribute(Constants.PIM_MAIL_SENDER);
		String recipients = context.getAttribute(Constants.PIM_MAIL_RECIPIENTS);
		String subject = context.getAttribute(Constants.PIM_MAIL_SUBJECT);
		MailService.sendEmail(sender, recipients, subject, context.getAttribute(Constants.PIM_INIT_MAIL_BODY));
		
		Map<String, Object> responseMap = prepareBatches (context, products);
		StringBuilder responceStringBuffer = new StringBuilder("");
		if (responseMap != null) {
			if (responseMap.containsKey(Constants.STATUS_CODE)&& responseMap.get(Constants.STATUS_CODE) != null) {
				 responseMap.get(Constants.STATUS_CODE);
			}
			if (responseMap.containsKey(Constants.RESPONCE_STRING)&& responseMap.get(Constants.RESPONCE_STRING) != null) {
				responceStringBuffer = new StringBuilder (responseMap.get(Constants.RESPONCE_STRING).toString());
			}
		}
		StringBuilder emailBody = new StringBuilder (context.getAttribute(Constants.PIM_MAIL_BODY));
		emailBody = emailBody.append(responceStringBuffer.toString());
		MailService.sendEmail(sender, recipients, subject, emailBody.toString());
		Set<String> uploadedProductNos = (Set<String>) responseMap.get("payload");
		return uploadedProductNos;
	}
	
	private Map<String, Object> prepareBatches (RepositoryJobExecutionContext context, Map<String, Product> products) {
		LOG.info("!!!!!!!!!  prepareBatches");
		Map<String, Object> responseMap = new HashMap <> ();
		Map<Integer, Object> batchResponseMap = new HashMap <> ();
		Integer batchSize = this.getBatchSize(context);
		Integer count = 0;	
		Integer batchExecuted = 0;
		Integer productsLoadingStatus = 0;
		String responseString = "";
		Set<String> uploadedProductNos = new HashSet<String> ();
		try {
			Integer totoalProducts = products.size();
			Map<String, Product> batch = new  HashMap<>();
			for (Entry<String, Product> entry : products.entrySet()) {
				count = count + 1;
				batch.put(entry.getKey(), entry.getValue());
				if ((count % batchSize == 0) || (totoalProducts <= ((batchExecuted * batchSize) + count))) {
					Map<String, Object> responseBodyMap = this.loadBatch(batchExecuted, context, batch);
					count = 0;
					batchExecuted++;
					if ((responseBodyMap.containsKey(Constants.STATUS_CODE)) && (responseBodyMap.get(Constants.STATUS_CODE) != null)) {
						productsLoadingStatus = (int) responseBodyMap.get(Constants.STATUS_CODE);
					}
					if ((responseBodyMap.containsKey(Constants.RESPONCE_STRING)) && (responseBodyMap.get(Constants.RESPONCE_STRING) != null)) {
						responseString = responseBodyMap.get(Constants.RESPONCE_STRING).toString();
					}
					if (responseBodyMap.containsKey("body") && responseBodyMap.get("body") != null) {
						batchResponseMap.put(batchExecuted, responseBodyMap.get("body"));
					    JSONObject jsonObject = JSONObject.fromObject(responseBodyMap.get("body"));
					    JSONArray uploadedProducts = jsonObject.getJSONArray("payload");
					    for (int i = 0; i < uploadedProducts.size(); i++) {
					        uploadedProductNos.add(uploadedProducts.getString(i));
					    }
					}
				}
			}
		} catch (Exception ex) { 
			if (LOG.isErrorEnabled()) {
				LOG.error(ex.getMessage(), ex);
			}
		}finally {
			responseMap.put(Constants.STATUS_CODE, productsLoadingStatus);
			responseMap.put(Constants.RESPONCE_STRING, responseString);
			responseMap.put("payload", uploadedProductNos);
			for (Entry<Integer, Object> entry : batchResponseMap.entrySet()) {
				LOG.info("$$$$$$$$$$$ Batch Response of : " + entry.getKey());
				LOG.info ("Batch Body : " + entry.getValue());
			}
			LOG.info("================= Total uploaded products : " + uploadedProductNos);
		}
		return responseMap;
	}
	
	private Map<String, Object> loadBatch (Integer batchExecuted, RepositoryJobExecutionContext context, Map<String, Product> batch) {
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("!!!!!!!!!  batchExecuted : '%s'", batchExecuted));
			LOG.info(String.format("!!!!!!!!!  total uploaded products : '%s'", batch.size()));
		}
		Map<String, Object> responseBodyMap = loadPimFileData(context, batch);
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("!!!!!!!!!  cleared batch after execution : '%s'", batchExecuted));
		}
		this.clearBatch(batch);
		Object statusCodeObject = responseBodyMap.get(Constants.STATUS_CODE);
		if ( (statusCodeObject != null) && !(statusCodeObject.equals(Integer.valueOf(200)))) {
			this.pauseExecution(context);
		} else {
			if (LOG.isInfoEnabled()) {
				LOG.info("!!!!!!!!!  status code is {} , hence no need to take a pause for {} ",statusCodeObject ,batchExecuted);
			}
		}
		return responseBodyMap;
	}
	
	private Integer getBatchSize (RepositoryJobExecutionContext context) {
		Integer batchSize = 50;
		if (!Strings.isNullOrEmpty(context.getAttribute(Constants.PIM_BATCH_SIZE))) {
			try {
				batchSize = Integer.parseInt(context.getAttribute(Constants.PIM_BATCH_SIZE));
				if (batchSize == null || batchSize < 0) {
					batchSize = 50;
				}
			}catch (NumberFormatException ex) {
				if (LOG.isErrorEnabled()) {
					LOG.error("Error Batch size parsing ", ex);
				}
				batchSize = 50;
			}
		}
		return batchSize;
	}
	
	private void clearBatch (Map<String, Product> batch) {
		try {
			batch.clear();
		} catch (UnsupportedOperationException ex) {
			if (LOG.isErrorEnabled()) {
				LOG.error(String.format("UnsupportedOperationException while clearing batch '%s'", ex));
			}
		}
	}
	
	private long getPauseTime (RepositoryJobExecutionContext context) {
		long pauseTimeInSeconds = 30;
		try {
			if(context.getAttribute(Constants.PIM_PAUSE_TIME)!=null){
				if (LOG.isInfoEnabled()) {
					LOG.info(String.format("Found pauseTime in context with value: '%s'", context.getAttribute("pauseTime")));
				}
                pauseTimeInSeconds = Long.parseLong(context.getAttribute("pauseTime"));
            }
		} catch (NumberFormatException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e.getMessage(), e);
			}
		}
		return pauseTimeInSeconds;
	}
	
	private void pauseExecution (RepositoryJobExecutionContext context) {
		long pauseTimeInSeconds = this.getPauseTime(context);
		try {
			Thread.sleep(pauseTimeInSeconds * 1000);
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Interrupted Exception while loading pim file data", e);
			}
		}
	}
	// HTTP HEAD request	
	public Boolean isNewPimFileAvailable(RepositoryJobExecutionContext context){
		Boolean flag = false;
		HttpURLConnection urlConnection = null;
		System.setProperty(Constants.HTTP_KEEP_ALIVE, Constants.FALSE);
		try {
			URL url = new URL(context.getAttribute(Constants.PIM_JSON_FILE_URL));
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("HEAD");	   
			String lastModified = urlConnection.getHeaderField("Last-Modified");
			if (LOG.isInfoEnabled()) {
				LOG.info(String.format("File lastModified date : '%s'", lastModified));
			}
			if(lastModified == null){
				if (LOG.isErrorEnabled()) {
					LOG.error("No JSON file at the given location");
				}
			}else{
				flag = this.updateNoteTime(context, lastModified);
			}
		} catch(Exception e){
			if (LOG.isErrorEnabled()) {
				LOG.error("Exception while checking for new PIM file ", e);
			}
		}
		finally {
			if (urlConnection != null) {
				this.closeURLConnection(urlConnection);
				this.disconnectURLConnection(urlConnection);
			}
		}
		return flag;
	}
	
	public Boolean updateNoteTime (RepositoryJobExecutionContext context, String lastModified) {
		Session session=null;
		Boolean flag = false;
		try {
			session = context.createSystemSession();
			Node currentNode = session.getNode(context.getAttribute(Constants.PIM_FILE_LAST_MODIFIED_PATH));
			Property lastModifiedDateTimeProperty = currentNode.getProperty(Constants.PIM_LAST_MODIFIED_DATETIME);
			Value lastModifiedDateTimeValue = lastModifiedDateTimeProperty.getValue();
			String existingLastModifiedDateTime = lastModifiedDateTimeValue.getString();
			if (LOG.isInfoEnabled()) {
				LOG.info(String.format("existingLastModifiedDateTime : '%s'", existingLastModifiedDateTime));
			}
			if(existingLastModifiedDateTime != null && existingLastModifiedDateTime.equals(lastModified)){
				flag = false;
			}
			else{
				flag = true;
				currentNode.setProperty(Constants.PIM_LAST_MODIFIED_DATETIME, lastModified);
			}
		}  catch(Exception e){
			if (LOG.isErrorEnabled()) {
				LOG.error("Exception while checking for new PIM file ", e);
			}
			flag=false;
		}
		finally {
			if (session != null && session.isLive()){
				try {
					session.save();
					session.logout();
				} catch (Exception e) {
					if (LOG.isErrorEnabled()) {
						LOG.error("Exception while saving the session ", e);
					}
				} 
			}
		}
		return flag;
	}

	// HTTP GET request
	public String getContentOfNewPimFile(RepositoryJobExecutionContext context){
		StringBuilder response = new StringBuilder(); 
		HttpURLConnection urlConnection = null;
		BufferedReader rd = null;
		System.setProperty(Constants.HTTP_KEEP_ALIVE, Constants.FALSE);
		try {
			URL url = new URL(context.getAttribute(Constants.PIM_JSON_FILE_URL));
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");
			urlConnection.setRequestProperty("Accept", "*/*");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF8"));
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
		} catch (IOException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("IOException while checking for new PIM file ", e);
			}
		} 
		finally { 
			if (rd != null ) {
				try {
					rd.close();
				} catch (IOException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(e.getMessage(), e);
					}
				}
			}
			if (urlConnection != null) {
				this.closeURLConnection(urlConnection);
				this.disconnectURLConnection(urlConnection);
			}
		}
		return response.toString();
	}

	// HTTP POST request
	private Map<String, Object> loadPimFileData(RepositoryJobExecutionContext context, Map<String, Product> products) {
		System.setProperty(Constants.HTTP_KEEP_ALIVE, Constants.FALSE);
		LOG.info("!!!!!!!!!  loadPimFileData");
		String apiKey = "";
		if (!Strings.isNullOrEmpty (context.getAttribute(Constants.PIM_API_KEY))) {
			apiKey = context.getAttribute(Constants.PIM_API_KEY);
		}
		String pimApiURL = "";
		if (!Strings.isNullOrEmpty (context.getAttribute(Constants.PIM_HIPPO_API_URL))) {
			pimApiURL = context.getAttribute(Constants.PIM_HIPPO_API_URL);
			if (LOG.isInfoEnabled()) {
				LOG.info(String.format("pimApiURL: '%s'", pimApiURL));
			}
		}
		int timeout = 400;
		RequestConfig config = RequestConfig.custom().
		  setConnectTimeout(timeout * 1000).
		  setConnectionRequestTimeout(timeout * 1000).
		  setSocketTimeout(timeout * 1000).build();
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		CloseableHttpResponse response=null;
		int statusCode = 0;
		String json = "";
		Map<String, Object> responseMap = new HashMap<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			String productJson = mapper.writeValueAsString(products);
			HttpPost request = new HttpPost(pimApiURL);
			StringEntity params =new StringEntity(productJson, "UTF-8");
			request.addHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
			request.addHeader(Constants.PIM_API_KEY, apiKey);
			request.addHeader(Constants.ACCEPT, Constants.APPLICATION_JSON);
			request.addHeader(Constants.ACCEPT_LANGUAGE, "en-US,en;q=0.8");
			request.setEntity(params);
			response = httpClient.execute(request);
			if (LOG.isInfoEnabled()) {
				LOG.info(String.format("!!! Response after loading products '%s'", response.toString()));
			}
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Exception loadPimFileData ", e);
			}
		} finally {
			try {
				if (response != null) {
					statusCode = response.getStatusLine().getStatusCode();
					if (statusCode < 300) {
						json = EntityUtils.toString(response.getEntity());
					} else {
						json = "";
					}
					responseMap.put(Constants.STATUS_CODE, statusCode);
					responseMap.put(Constants.RESPONCE_STRING, response.toString());
					responseMap.put("body", json);
				}
				httpClient.close();
			} catch (IOException | NullPointerException e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(Constants.PIM_LOADING_EXCEPTION_MSG, e);
				}
			}
		}
		return responseMap;
	}
	
	// HTTP DELETE request
	private Map<String, Object> deletePimUnavailable(RepositoryJobExecutionContext context, Set<String> productIds) {
		LOG.info("!!!!!!!!!  deletePimUnavailable");
		System.setProperty(Constants.HTTP_KEEP_ALIVE, Constants.FALSE);
		String apiKey = "";
		if (!Strings.isNullOrEmpty (context.getAttribute(Constants.PIM_API_KEY))) {
			apiKey = context.getAttribute(Constants.PIM_API_KEY);
		}
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response=null;
		int statusCode = 0;
		String json = "";
		Map<String, Object> responseMap = new HashMap<>();
		try {
			HttpDeleteWithBody request = new HttpDeleteWithBody(context.getAttribute(Constants.PIM_HIPPO_API_DELETE_URL));
			StringEntity params =new StringEntity(productIds.toString(), "UTF-8");
			request.addHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
			request.addHeader(Constants.PIM_API_KEY, apiKey);
			request.addHeader(Constants.ACCEPT, Constants.APPLICATION_JSON);
			request.addHeader(Constants.ACCEPT_LANGUAGE, "en-US,en;q=0.8");
			request.setEntity(params);
			response = httpClient.execute(request);
			if (LOG.isInfoEnabled()) {
				LOG.info(String.format("Response after loading products '%s'", response.toString()));
			}
		}catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(Constants.PIM_LOADING_EXCEPTION_MSG, e);
			}
		} finally {
			try {
				if (response != null && response.getStatusLine() != null) {
					statusCode = response.getStatusLine().getStatusCode();
					if (statusCode < 300) {
						json = EntityUtils.toString(response.getEntity());
					} else {
						json = "";
					}
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException | NullPointerException e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(Constants.PIM_LOADING_EXCEPTION_MSG, e);
				}
			}
			responseMap.put(Constants.STATUS_CODE, statusCode);
			responseMap.put("body", json);
		}
		return responseMap;
	}
}

class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";

    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public HttpDeleteWithBody(final URI uri) {
        super();
        setURI(uri);
    }

    public HttpDeleteWithBody() {
        super();
    }
}