/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.beans;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kohler.beans.Product;

public class CrossSelling_Detail {

	private  Map<String, List<Product>> crossSellingAccessories;
	

	public CrossSelling_Detail() {
		super();
		crossSellingAccessories = new LinkedHashMap<String, List<Product>>();
	}

	public Map<String, List<Product>> getCrossSellingAccessories() {
		return crossSellingAccessories;
	}

	public void setCrossSellingAccessories(Map<String, List<Product>> crossSellingAccessories) {
		this.crossSellingAccessories = crossSellingAccessories;
	}
	
}
