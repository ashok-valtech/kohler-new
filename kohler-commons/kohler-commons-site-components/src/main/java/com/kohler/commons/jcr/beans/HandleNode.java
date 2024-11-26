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
@JcrNode(nodeType = Constants.HIPPO_HANDLE)
public class HandleNode {

	@JcrPath
	private String path;

	@JcrName
	private String nodeName;

	@JcrProperty(name = Constants.HIPPO_NAME)
	private String name;



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
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
