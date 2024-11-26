/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components.search;

import java.io.IOException;
import java.util.List;

/**
 * 
 * @author dhanwan.r
 *
 */
public interface SuggestionService {

	List<String> getSpellCheck(String term) throws IOException;

	List<String> getSuggestions(String term);

	void createInMemoryIndexInit ();
	
	void createInMemoryIndex();
	
	void createInMemoryIndexByCall ();
	
	void getSynonymTerms();
}