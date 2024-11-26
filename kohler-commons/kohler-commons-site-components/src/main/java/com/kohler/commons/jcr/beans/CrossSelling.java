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
@JcrNode(nodeType = Constants.NS_C_CROSS_SELLING)
public class CrossSelling {

	public CrossSelling(String key, Long[] value) {
		this.key = key;
		this.values = value;
	}

	@JcrPath
	private String path;

	@JcrName
	private String nodeName;

	@JcrProperty(name = Constants.NS_KEY)
	private String key;

	@JcrProperty(name = Constants.NS_VALUES)
	private Long[] values;



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
	public Long[] getValues() {
		return values;
	}



	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(Long[] values) {
		this.values = values;
	}

}
