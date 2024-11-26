/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/

package com.kohler.commons.servicebase;

import java.util.List;

import javax.jcr.Session;

import com.kohler.commons.json.beans.Dealer;
import com.kohler.commons.servicebase.dealer.DealerAPI;

/**
 * @author dhanwan.r
 * Date: 08/30/2017
 * @version 1.0 
 */
public class DealerBase extends AbstractDealerBase{
	
	public DealerBase(){
		
	}

	public DealerBase(DealerAPI dealerAPI){
		super(dealerAPI);
	}

	@Override
	public void dropAllDealers(Session session) {
		// TODO Auto-generated method stub
		dealerAPI.dropAllDealers(session);
	}

	@Override
	public List<String> createDelearFolder(Session session, List<Dealer> dealers) {
		// TODO Auto-generated method stub
		return dealerAPI.createDelearFolder(session, dealers); 
	}

}
