/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.beans.Banner;
import com.kohler.beans.GlobalProject;
import com.kohler.beans.Product;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.taxonomies.TaxonomyService;
import com.kohler.commons.util.CommonUtil;

/**
 * APAC Implementation for Global projects details, inherits from <code>EssentialsContentComponent</code>
 * @author Girijanadan.p
 * Date: 06/05/2017
 * @version 1.0
 */
public class GlobalProjectsDetailCompnent extends EssentialsContentComponent {
	
	private static final Logger LOG = LoggerFactory.getLogger(GlobalProjectsDetailCompnent.class);
	TaxonomyService taxonomyService = new TaxonomyService();
	
	@Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
		super.doBeforeRender(request, response);
		HstRequestContext hstRequestContext = request.getRequestContext();
		String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Map<String, String> countryMap = taxonomyService.getGlobalProjectFirstLevelCategory(language  + "_" + country);
		request.setAttribute(Constants.REQUEST_ATTR_COUNTRIES,countryMap);
		final HstRequestContext context = request.getRequestContext();
        final GlobalProject bean = (GlobalProject) context.getContentBean();
        String[] relatedproduct=bean.getRelatedProduct();
        
        List<HippoBean> globalprojectBannerList =bean.getBannerLink();
        List<Banner> allBanners = new ArrayList<Banner>();
        for(HippoBean globalProjectBannerDoc : globalprojectBannerList){
        	allBanners.add((Banner)globalProjectBannerDoc);
        }
		if (!ArrayUtils.isEmpty(relatedproduct)) {
			List<Product> relatedProductList = getRelatedProducts(relatedproduct, request);
			request.setAttribute(Constants.REQUEST_ATTR_RELATEDPRODUCTLIST, relatedProductList);
		}
		request.setAttribute(Constants.REQUEST_ATTR_GLOBALPROJECTBANNERS, allBanners);
	}
	
	/**
	 * Gets list of products
	 * @param request
	 * @param prodIds
	 * @return relatedProducts
	 */
	private List<Product> getRelatedProducts(String[] prodIds,HstRequest request){
		List<Product> relatedProducts = new ArrayList<Product>();
		for (String prod : prodIds) {
			Product productById = getProductByID(prod,request);
			if(null != productById){
			    relatedProducts.add(productById);
			}
		}
		return relatedProducts;
	}
	
	/**
	 * Gets products based on itemNo
	 * @param prodId
	 * @param request
	 * @return product
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Product getProductByID(String prodId,HstRequest request){
		Product product = null;
		HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();
		HstQuery query;
		try {
			query = request.getRequestContext().getQueryManager().createQuery(rootBean,Product.class);
			Filter filter = query.createFilter();
			filter.addEqualTo(Constants.NS_ITEM_NO, prodId.trim());
			query.setFilter(filter);
			HstQueryResult queryResult = query.execute();
			HippoBeanIterator hippoBeanItr = queryResult.getHippoBeans();
			product = (Product) hippoBeanItr.next();
			
		} catch (Exception e) {
			LOG.error("No such products found", e.getMessage());
			return null;
		}
		return product;
	}
}
