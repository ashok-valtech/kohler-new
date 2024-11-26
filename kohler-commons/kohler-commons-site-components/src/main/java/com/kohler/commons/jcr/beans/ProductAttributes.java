/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.jcr.beans;

import org.jcrom.annotations.JcrName;
import org.jcrom.annotations.JcrNode;
import org.jcrom.annotations.JcrPath;
import org.jcrom.annotations.JcrProperty;

import com.kohler.commons.constants.Constants;

/**
 * 
 * @author harish.p
 *
 */
@JcrNode(nodeType = Constants.NS_CATTRIBUTES)
public class ProductAttributes {

	@JcrPath
	private String path;

	@JcrName
	private String nodeName;

	@JcrProperty(name = Constants.NS_KEY)
	private String key;

	@JcrProperty(name = Constants.NS_VALUE)
	private String[] values;



	public ProductAttributes(String key, String[] values) {
		this.key = key;
		this.values = values;
	}



	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}



	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}



	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}



	/**
	 * @param nodeName
	 *            the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}



	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}



	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}



	/**
	 * @return the values
	 */
	public String[] getValues() {
		return values;
	}



	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(String[] values) {
		this.values = values;
	}

}
