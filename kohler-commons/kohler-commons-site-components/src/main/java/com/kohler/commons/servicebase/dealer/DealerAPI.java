/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/

package com.kohler.commons.servicebase.dealer;

import java.util.List;

import javax.jcr.Session;

import com.kohler.commons.json.beans.Dealer;

/**
 * @author dhanwan.r
 * Date: 08/30/2017
 * @version 1.0 
 */
public interface DealerAPI {
	
	public void dropAllDealers(Session session);
	
	public List<String> createDelearFolder(Session session,List<Dealer> dealers);
	
}
