package com.kohler.commons.service;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Credentials;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.commons.components.search.SearchQuery;
import com.kohler.commons.constants.Constants;

/**
 * 
 * @author dhanwan.r
 *
 */
public class SwatchColorNameFinderServiceImpl implements SwatchColorNameFinderService{

	private static final Logger LOGGER = LoggerFactory.getLogger(SwatchColorNameFinderServiceImpl.class);
	
    private Repository repository;
	
    private Credentials credentials;
	
	private SearchQuery fullIndexQuery = null;
	
	Map<String, String> swatchColorNameMap = null;
	
	private int refreshIntervalShort;
	
	private int refreshIntervalLong = 360;
	
	public void setRefreshIntervalShort(int refreshIntervalShort) {
		this.refreshIntervalShort = refreshIntervalShort;
	}
	private String name;
	
	public SwatchColorNameFinderServiceImpl(){
		this.swatchColorNameMap = new HashMap<>();
	}
	
	public SearchQuery getFullIndexQuery() {
		return fullIndexQuery;
	}

	public void setFullIndexQuery(SearchQuery fullIndexQuery) {
		this.fullIndexQuery = fullIndexQuery;
	}

	public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
    public int getRefreshIntervalShort () {
		return this.refreshIntervalShort;
	}
    
    public Map<String, String> getSwatchColorNameMap() {
		return this.swatchColorNameMap;
	}

	public void setSwatchColorNameMap(Map<String, String> swatchColorNameMap) {
		this.swatchColorNameMap = swatchColorNameMap;
	}
	
	public void getSwatchColorNameInit(){
		this.refreshIntervalShort ();
	}

	public void getSwatchColorName(){
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(" getSwatchColorName Method Executed:  {}", this.getName());
		}
		Session session = null;
		try{
			session = getSession();
			if (session == null){
				this.refreshIntervalShort ();
			} else {
					synchronized (this) {
						getTearms(session);
						this.refreshIntervalLong();
					}
			}
		} catch(Exception e){
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("error in getSwatchColorName ",e);
			}
		} finally {
			if (session != null && session.isLive()) {
				session.logout();
			}
		}
	}
	
	public void getSwatchColorNameByCall(){
		Session session = null;
		try{
			LOGGER.info(" getSwatchColorNameByCall Method Executed: {}", this.getName());
			session = getSession();
			synchronized (this) {
				getTearms(session);
			}
		}catch(Exception e){
			LOGGER.error("error in getSwatchColorName ",e);
		}finally {
			if (session != null && session.isLive()) {
				session.logout();
			}
		}
	}
    
	private void getTearms(Session session) throws RepositoryException {
		LOGGER.info("Called getTearms {}", this.getName());
		if(this.fullIndexQuery == null){
			LOGGER.info("No SearchQuery object avaiable:  {}", this.getName());
			return;
		}	
		QueryManager queryManager = session.getWorkspace().getQueryManager();
		Query query = queryManager.createQuery(this.fullIndexQuery.getQuery(), Query.JCR_SQL2);
		QueryResult result = query.execute();
		for (NodeIterator i = result.getNodes(); i.hasNext();) {
			Node node = i.nextNode();
			NodeIterator skuAttributes = node.getNodes();
			String swatch = null;
			String colorName = null;
			for (NodeIterator j = skuAttributes; j.hasNext();) {
				Node nodeAttribute = j.nextNode();
				String key = nodeAttribute.getProperty(Constants.NS_KEY).getValue().getString();
				if (key.equalsIgnoreCase(Constants.COLOR_FINISH_NAME)) {
					String value = nodeAttribute.getProperty(Constants.NS_VALUE).getValues()[0].getString();
					colorName = value;
				} else if (key.equalsIgnoreCase(Constants.IMG_SWATCH)) {
					String value = nodeAttribute.getProperty(Constants.NS_VALUE).getValues()[0].getString();
					swatch = value;
				}
			}
			if (swatch != null && colorName != null) {
				swatchColorNameMap.put(swatch, colorName);
			}
		}
		LOGGER.info("End getTearms {}", this.getName());
	}
	
	public Session getSession() {
		Session session = null;
		if (this.repository != null) {
			try {
				session = this.repository.login(credentials);
			} catch (RepositoryException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
         return session;
	}
	
	private class AsyncLoader implements Runnable{
		private SwatchColorNameFinderServiceImpl service = null;
		private Integer threadRefreshInterval = null;
		public AsyncLoader(SwatchColorNameFinderServiceImpl service, Integer threadRefreshInterval){
			this.service = service;
			this.threadRefreshInterval = threadRefreshInterval * 60000;
		}
		public void run() {
			try {
				LOGGER.info(Thread.currentThread().getName());
				Thread.sleep(this.threadRefreshInterval);
				service.getSwatchColorName();
			} catch (Exception e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("error setting refresh interval for swatch color mapper: ");
				}
			}
		}
	}
	
	private void refreshIntervalShort () {
		try {
			Thread thread = new Thread(new AsyncLoader(this, this.getRefreshIntervalShort()), this.getName()+ " Swatch Color name Refresh Thread short interval of " + this.getRefreshIntervalShort () + " Minuts");
			thread.start();
		}catch (Exception ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private void refreshIntervalLong () {
		try {
			Thread thread = new Thread(new AsyncLoader(this, this.getRefreshIntervalLong ()), this.getName()+ " Suggestion Thread refreshing long interval of " + this.getRefreshIntervalLong () + " Minuts");
			thread.start();
		}catch (Exception ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
	}

	public int getRefreshIntervalLong() {
		return refreshIntervalLong;
	}
	public void setRefreshIntervalLong(int refreshIntervalLong) {
		this.refreshIntervalLong = refreshIntervalLong;
	}
}
