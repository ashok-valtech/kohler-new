package com.kohler.commons.exception;

/**
 * Exception class for no taxonomy found.
 * @author dhanwan.r
 *
 */
public class NoTaxonomyFoundExeption extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoTaxonomyFoundExeption(String message) {
		super(message);
	}
	
}