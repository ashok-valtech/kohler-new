/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components.search;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jcr.Credentials;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kohler.commons.constants.Constants;

/**
 * 
 * @author dhanwan.r
 *
 */

public class SuggestionServiceImpl implements SuggestionService {

	private List<SearchQuery> fullIndexQueries = null;
	private int maxSuggestionResults = 10;
	private int maxSpellCheckResults = 1;
	private int refreshIntervalShort;
	private int refreshIntervalLong = 360;
	private StandardAnalyzer analyzer = new StandardAnalyzer();
    private AnalyzingInfixSuggester suggester = null;
    private SpellChecker spellChecker = null;
	private RAMDirectory termsIndex = null;
	private Repository repository;
	private Credentials credentials;
	private String name;
	private String synonymPath;
	

	public String getSynonymPath() {
		return synonymPath;
	}
	public void setSynonymPath(String synonymPath) {
		this.synonymPath = synonymPath;
	}

	public Map<String, List<String>> results = null;

	private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionServiceImpl.class);

	public SuggestionServiceImpl(){
		//Default constructor
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
	
	public List<SearchQuery> getFullIndexQueries() {
		return fullIndexQueries;
	}

	public void setFullIndexQueries(List<SearchQuery> fullIndexQueries) {
		this.fullIndexQueries = fullIndexQueries;
	}
	
	public Map<String, List<String>> getResults() {
		return results;
	}
	public void setResults(Map<String, List<String>> results) {
		this.results = results;
	}
	
	@Override
	public List<String> getSpellCheck(String term) throws IOException {
		List<String> suggestionList = null;
		if (this.spellChecker != null) {
			String[] suggestions = this.spellChecker.suggestSimilar(term, this.maxSpellCheckResults);
			if (suggestions.length > 0) {
				suggestionList = Arrays.asList(suggestions);
			}
		}
		return suggestionList;
	}

	@Override
	public List<String> getSuggestions(String term){
		List<String> suggestions = new ArrayList<>();
		try{
			if (this.suggester != null) {
				List<Lookup.LookupResult> results = this.suggester.lookup(term, false, this.maxSuggestionResults);
				Collections.sort(results, new StartsWithComparator(term));
				for (Lookup.LookupResult result : results) {
					suggestions.add(result.key.toString());
				}
				int count=suggestions.size();
				Map<String, List<String>> synonmynMap = this.results;
				if (suggestions.isEmpty()) {
					if (synonmynMap!=null && synonmynMap.containsKey(term.toLowerCase())) {
						List <String> synonymValues = synonmynMap.get(term.toLowerCase());
						for (String synonymValue : synonymValues) {
							if (count > this.maxSuggestionResults) {
								return suggestions;
							} else {
								suggestions.add(synonymValue);
							}
						}
					}
				} else if (suggestions.size() < this.maxSuggestionResults) {
					List<String> suggestionsSynonmyn = new ArrayList<>();
					suggestionLoop:
					for (String suggestion: suggestions) {
						if (synonmynMap!=null && synonmynMap.containsKey(suggestion.toLowerCase())) {
							List <String> synonymValues = synonmynMap.get(suggestion.toLowerCase());
							for (String synonymValue : synonymValues) {
								if (count >= this.maxSuggestionResults) {
									break suggestionLoop;
								} else {
									suggestionsSynonmyn.add(synonymValue);
									count++;
								}
							}
						}
					}
					if(!suggestionsSynonmyn.isEmpty()){
						suggestions.addAll(suggestionsSynonmyn);
					}
				}
			}
		}catch(Exception e){
			LOGGER.error("error getting auto suggestions",e);
		} 
		return suggestions;
	}
	
	@Override
	public void createInMemoryIndexInit () {
		this.refreshIntervalShort ();
	}
	
	@Override
	public void createInMemoryIndex() {
		LOGGER.info(" InMemoryIndex Method Executed: " + this.getName());
		Session session = null;
		try {
			session = getSession();
			if (session == null){
				this.refreshIntervalShort ();
			}
			else {
				synchronized (this) {
					this.termsIndex = new RAMDirectory();
					List<String> terms=null;
					terms = getTerms(session, this.fullIndexQueries);
					if (terms != null && !terms.isEmpty()) {
						LOGGER.info("createInMemoryIndex " + this.getName());
						createSpellCheckIndex(this.termsIndex,terms);
						createTermsIndex(this.termsIndex,terms);
						getSynonymTerms();
						LOGGER.info("Done createInMemoryIndex " + this.getName());
					}
					this.refreshIntervalLong();
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(ex.getMessage(), ex);
			}
		} finally {
			if (session != null && session.isLive()) {
				session.logout();
			}
		}
	}
	
	@Override
	public void createInMemoryIndexByCall () {
		LOGGER.info(" InMemoryIndexByCall Method Executed: ");
		Session session = null;
		try{
			session = getSession();
			if(session != null){
				synchronized (this) {
					this.termsIndex = new RAMDirectory();
					List<String> terms=null;
					terms = getTerms(session, this.fullIndexQueries);
					if (terms != null && !terms.isEmpty()) {
						LOGGER.info (" InMemoryIndexByCall: "+ this.getName());
						createSpellCheckIndex(this.termsIndex,terms);
						createTermsIndex(this.termsIndex,terms);
						getSynonymTerms();
						LOGGER.info ("Done InMemoryIndexByCall: "+ this.getName());
					}
				}	
			}
		} catch (Exception ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(ex.getMessage(), ex);
			}
		} finally {
			if (session != null && session.isLive()) {
				session.logout();
			}
		}
	}
	
	protected void createTermsIndex(RAMDirectory directory, List<String> terms) throws IOException{
		suggester = new AnalyzingInfixSuggester(directory,this.analyzer);
		suggester.build(new IndexTermIterator(terms));
	}

	protected void createSpellCheckIndex(RAMDirectory directory,List<String> terms) throws IOException{
		try (IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(this.analyzer));) {
			for(String term:terms){
				Document document = new Document();
				document.add(new TextField("suggestion",term,Store.YES));
				writer.addDocument(document);
			}
		}catch(Exception e){
			LOGGER.error("error creating spellcheck index",e);
		}
		this.spellChecker = new SpellChecker(this.termsIndex);
		this.spellChecker.indexDictionary(new PlainTextDictionary(createDictionaryStream(terms)),new IndexWriterConfig(this.analyzer),true);
	}

	protected InputStream createDictionaryStream(List<String> terms){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try (PrintStream printStream = new PrintStream(outputStream)) {
			for(String term:terms){
				printStream.println(term);
			}
			return new ByteArrayInputStream(outputStream.toByteArray());
		}catch(Exception e){
			LOGGER.error("error creating dictionary stream",e);
		}
		return null;
	}

	protected List<String> getTerms(Session session, List<SearchQuery> indexQueries) throws RepositoryException {
		List<String> results = new ArrayList<>();
		if(indexQueries != null && !indexQueries.isEmpty()){
			QueryManager queryManager = session.getWorkspace().getQueryManager();
			for(SearchQuery indexQuery:indexQueries){
				this.getResult(queryManager, indexQuery, results);
			}
		}
		return results;
	}
	
	protected void getResult (QueryManager queryManager, SearchQuery indexQuery, List<String> results) throws RepositoryException {
		Query query = queryManager.createQuery(indexQuery.getQuery(), Query.JCR_SQL2);
		QueryResult result = query.execute();
		RowIterator rows = result.getRows();
		while(rows.hasNext()){
			Row row = rows.nextRow();
			for(String column:result.getColumnNames()){
				String value = row.getValue(column).getString();
				value = value.replace("(R)","");
				value = value.replace("(TM)","");
				if(!results.contains(value.trim())){
					results.add(value.trim());
				}
			}
		}
	}
	private class StartsWithComparator implements Comparator<Lookup.LookupResult>{

		private String term = null;

		public StartsWithComparator(String term){
			this.term = term;
		}

		@Override
		public int compare(LookupResult result1, LookupResult result2) {
			int indexOfA = result1.key.toString().toLowerCase().indexOf(term);
			int indexOfB = result2.key.toString().toLowerCase().indexOf(term);
			if (indexOfA < indexOfB) {
				return -1;
			} else if (indexOfA==indexOfB) {
				return 0;
			} else {
				return 1;
			}
		}
	}

	private class AsyncLoader implements Runnable{

		private SuggestionServiceImpl service = null;

		private Integer threadRefreshInterval = null;
		
		public AsyncLoader(SuggestionServiceImpl service, int threadRefreshInterval){
			this.service = service;
			this.threadRefreshInterval = threadRefreshInterval * 60000;
		}
		public void run() {
			try {
				LOGGER.info(Thread.currentThread().getName());
				Thread.sleep(this.threadRefreshInterval);
				service.createInMemoryIndex ();
				getSynonymTerms();
			} catch (Exception e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			}	
		}

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
	
	private void refreshIntervalShort () {
		try {
			Thread thread = new Thread(new AsyncLoader(this, this.getRefreshIntervalShort ()), this.getName()+ " Suggestion Thread refreshing short interval of " + this.getRefreshIntervalShort () + " Minuts");
			thread.start();
		}catch (Exception ex) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRefreshIntervalShort(int refreshIntervalShort) {
		this.refreshIntervalShort = refreshIntervalShort;
	}
	
	public void  getSynonymTerms(){
		LOGGER.info ("getSynonymTerms Method Called {}", this.getName());
		Session session = null;
		Map<String,List<String>> results = new HashMap<String, List<String>>();
		try {
			String synonmyQuery="select * from [kohler:Synonym] where ISDESCENDANTNODE('"+this.getSynonymPath()+"')";
			session = getSession();
			QueryManager queryManager = session.getWorkspace().getQueryManager();
			Query query = queryManager.createQuery(synonmyQuery, Query.JCR_SQL2);
			QueryResult r = query.execute();
			for (NodeIterator i = r.getNodes(); i.hasNext();) {
				Node node = i.nextNode();
				if (!node.getName().equals("synonym")) {
					continue;
				}
				PropertyIterator pr = node.getProperties();
				while (pr.hasNext()) {
					Property property = pr.nextProperty();
					if (property.getName().equals("hippostd:state")
							&& property.getValue().getString().equals(Constants.PUBLISHED)) {
						if (node.hasNodes()) {
							NodeIterator nodeIterator = node.getNodes();
							while (nodeIterator.hasNext()) {
								Node nodeAttribute = nodeIterator.nextNode();
								String key = nodeAttribute.getProperty("kohler:key").getValue().getString().toLowerCase();
								Value [] values = nodeAttribute.getProperty("kohler:value").getValues();
								ArrayList<String> list=new ArrayList<String>();
								for(Value value : values){
									list.add(value.getString().toString());
								}
								results.put(key.toLowerCase(), list);
							}
						}
					}
				}
			}
			setResults(results);
		} catch (Exception e) {
			LOGGER.error("error initializing While Getting synonmys " + e);
			e.printStackTrace();
		} finally {
			if (session != null && session.isLive()) {
				session.logout();
			}
			LOGGER.info ("done getSynonymTerms Method: {} ", this.getName());
		}
	}
	
}
