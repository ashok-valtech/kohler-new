package com.kohler.commons.exceptions;

/**
 * Exception class to handle document in use 
 * @author dhanwan.r
 * Date: 06/08/2019
 * @version 1.0
 */
public class DocumentInUseException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DocumentInUseException () {
		super();
	}
	
	public DocumentInUseException (String message) {
        super(message);
    }
}
