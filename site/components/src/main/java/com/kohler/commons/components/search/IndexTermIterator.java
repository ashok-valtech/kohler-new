/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components.search;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;

/**
 * 
 * @author dhanwan.r
 *
 */
public class IndexTermIterator implements InputIterator{

	private Iterator<String> terms = null;
	private String currentTerm = null;

	public IndexTermIterator(List<String> terms){
		this.terms = terms.iterator();
	}

	@Override
	public BytesRef next() throws IOException {
		if (terms.hasNext()) {
			currentTerm = terms.next();
            try {
                return new BytesRef(currentTerm.getBytes("UTF8"));
            } catch (UnsupportedEncodingException e) {
                throw new Error("Couldn't convert to UTF-8");
            }
        } else {
            return null;
        }
	}

	@Override
	public Set<BytesRef> contexts() {
		return null;
	}

	@Override
	public boolean hasContexts() {
		return false;
	}

	@Override
	public boolean hasPayloads() {
		return false;
	}

	@Override
	public BytesRef payload() {
		return null;
	}

	@Override
	public long weight() {
		return 0;
	}



}
