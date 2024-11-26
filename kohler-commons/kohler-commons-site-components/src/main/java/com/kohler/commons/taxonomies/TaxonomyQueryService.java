/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.taxonomies;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.jackrabbit.commons.JcrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class TaxonomyQueryService {

	private static final Logger LOG = LoggerFactory.getLogger (TaxonomyQueryService.class);

	private Session session;
	private String langauge;
	private String country;


	public TaxonomyQueryService(Session session, String language, String country) {
		this.session = session;
		this.langauge = language;
		this.country = country;
	}



	public Map<String, String> getTaxonomyCategories(String nodeName) {
		String path = "/content/taxonomies";
		Node rootTaxonomy = null;
		Map<String, String> allCategoryMap = new HashMap<String, String> ();
		try {
			rootTaxonomy = JcrUtils.getNodeIfExists (path, session);
			if (rootTaxonomy != null) {
				NodeIterator nodeIterator = rootTaxonomy.getNodes ();
				while (nodeIterator.hasNext ()) {
					Node node = nodeIterator.nextNode ();
					if (node.getName ().equalsIgnoreCase (nodeName)) {
						NodeIterator childNodeIterator = node.getNodes ();
						while (childNodeIterator.hasNext ()) {
							Node versionNode = childNodeIterator.nextNode ();
							getCategories (versionNode, allCategoryMap);
						}
					}
				}
			}

		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.info (e.getMessage ());

		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e.getMessage () + e);
			}
		}
		return allCategoryMap;
	}



	private void getCategories(Node node, Map<String, String> allCategories) {
		try {
			if (node.getProperty ("hippostd:state").getValue ().getString ().equalsIgnoreCase ("published")) {
				NodeIterator publishedChildNodeIterator = node.getNodes ();
				while (publishedChildNodeIterator.hasNext ()) {
					Node parentCategoryNode = publishedChildNodeIterator.nextNode ();
					String childValue = parentCategoryNode.getProperty ("hippotaxonomy:key").getValue ().getString ();
					String childKey = getSynonymForCategory (parentCategoryNode);
					if (childKey != null) {
						allCategories.put (childKey, childValue);
					}
					getChildNodeValues (parentCategoryNode, allCategories);
				}

			}
		} catch (Exception e) {
			LOG.info (e.getMessage ());
		}

	}



	private void getChildNodeValues(Node node, Map<String, String> allCategory) {
		try {
			Iterator<Node> childNodes = JcrUtils.getChildNodes (node).iterator ();
			while (childNodes.hasNext ()) {
				Node childNode = childNodes.next ();
				if (!childNode.getName ().equalsIgnoreCase ("hippotaxonomy:categoryinfos")) {
					String value = childNode.getProperty ("hippotaxonomy:key").getValue ().getString ();
					String key = getSynonymForCategory (childNode);
					if (!Strings.isNullOrEmpty(key)) {
						allCategory.put (key, value);
					}
				}
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			LOG.info (e.getMessage ());
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e.getMessage () + e);
			}
		}

	}



	private String getSynonymForCategory(Node catNode) {
		String ancestors = null;
		try {
			NodeIterator childNodeIterator = catNode.getNodes ();
			while (childNodeIterator.hasNext ()) {
				Node childNode = childNodeIterator.nextNode ();
				if (childNode.getName ().equalsIgnoreCase ("hippotaxonomy:categoryinfos")) {
					NodeIterator itr = childNode.getNodes ();
					while (itr.hasNext ()) {
						Node node = itr.nextNode ();
						if (node.getName ().equalsIgnoreCase (langauge + "_" + country)) {
							if (node.hasProperty("hippotaxonomy:synonyms")) {
								Value[] values = node.getProperty ("hippotaxonomy:synonyms").getValues ();
								if (values != null && values.length > 0) {
									ancestors = values[0].getString ();
								}
							}
						}
					}
				}
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			if (LOG.isErrorEnabled()) {
				LOG.error(e.getMessage () + e);
			}
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e.getMessage () + e);
			}
		}
		return ancestors;
	}
}
