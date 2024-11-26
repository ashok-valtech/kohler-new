/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/

package com.kohler.commons.servicebase;

import java.util.List;

import javax.jcr.Session;

import com.kohler.commons.json.beans.Dealer;
import com.kohler.commons.servicebase.dealer.DealerAPI;

/**
 * Abstract class for Dealer Update
 * @author dhanwan.r
 * Date: 08/30/2017
 * @version 1.0 
 */
public abstract class AbstractDealerBase {

	public DealerAPI dealerAPI;
	
	public AbstractDealerBase(){
		
	}
	
	public AbstractDealerBase(DealerAPI dealerAPI){
		
		this.dealerAPI = dealerAPI;
	}

	public abstract void dropAllDealers(Session session);
	
	public abstract List<String> createDelearFolder(Session session,List<Dealer> dealers);
	
}
