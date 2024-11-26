/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/

package com.kohler.commons.servicebase.dealer;

/**
 * @author dhanwan.r
 * Date: 08/30/2017
 * @version 1.0 
 */
public class AbstractDealerAPI {
	
	protected String contentBase;
	
	protected String dealerFolderPath;
	
	protected String locale;
	
	protected String country;
	
	public AbstractDealerAPI(){
		
	}
	
	public AbstractDealerAPI(String contentBase, String dealerFolderPath, String locale, String country){
		this.contentBase = contentBase;
		this.dealerFolderPath = dealerFolderPath;
		this.locale = locale;
		this.country = country;
	}
}
