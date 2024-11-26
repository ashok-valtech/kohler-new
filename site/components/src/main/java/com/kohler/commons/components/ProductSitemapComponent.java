/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.taxonomy.api.Category;
import org.onehippo.taxonomy.api.TaxonomyNodeTypes;
import com.google.common.base.Strings;
import com.kohler.beans.CAttributes;
import com.kohler.beans.Product;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.SitemapService;
import com.kohler.commons.taxonomies.TaxonomyService;

/**ta
 * @author Sumit.Pallakhe
 * 
 * class provides implementation for product sitemap xml
 *
 */
public class ProductSitemapComponent extends BaseHstComponent {

	private static final String FORWARD_SLASH = "/";
	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
		String url=request.getRequestURL().toString();
		String categoryString = url.substring(url.lastIndexOf(FORWARD_SLASH) + 1);
		SitemapService sitemapService = new SitemapService();
		List<String> allProductItemNoSKU=null;
		if (!StringUtils.isEmpty(url) && (url.endsWith(Constants.PRODUCT_SITEMAP_PATH) || url.endsWith(Constants.CATEGORY_SITEMAP_ROOTPATH))) {
			allProductItemNoSKU = getCategoriesXML();
		} else {
			allProductItemNoSKU = getProductURLs(request, categoryString);
		}
		request.setAttribute("sitemap", sitemapService.productsXML(allProductItemNoSKU, request));
	}
	private List<String> getProductURLs(HstRequest request,String categoryKey) {
		HippoBean rootBean = request.getRequestContext().getSiteContentBaseBean();
		List<String> urlList = new ArrayList<String>();
		try {
			@SuppressWarnings("unchecked")
			HstQuery query = request.getRequestContext().getQueryManager().createQuery(rootBean, Product.class);
			query.setLimit(Integer.MAX_VALUE);
			if (!StringUtils.isEmpty(categoryKey)) {
				Filter filter = query.createFilter();
				filter.addEqualTo(TaxonomyNodeTypes.HIPPOTAXONOMY_KEYS, categoryKey.split(Pattern.quote("."))[0]);
				query.setFilter(filter);
			}
			HstQueryResult queryResult = query.execute();
			HippoBeanIterator hippoBeansItr = queryResult.getHippoBeans();
			if (hippoBeansItr != null) {
				while (hippoBeansItr.hasNext()) {
					Product productBean = (Product) hippoBeansItr.next();
					String itemNumber = productBean.getItemNo();
					String defaultSku = "";
					for (CAttributes attribute : productBean.getAttributes()) {
						if (attribute.getKey().equalsIgnoreCase(Constants.DEFAULT_SKU)) {
							if (attribute.getValue().length >= 1) {
								defaultSku = attribute.getValue()[0];
							}
							break;
						}
					}
					if (!Strings.isNullOrEmpty(itemNumber) && !Strings.isNullOrEmpty(defaultSku) ) {
						String url = Constants.PRODUCT_DETAILS_PATH + itemNumber + Constants.PRODUCT_URL_SKU + defaultSku;
						if (productBean.getKeys().length > 1) {
							for (String key : productBean.getKeys()) {
								url = Constants.PRODUCT_DETAILS_PATH + itemNumber + Constants.PRODUCT_URL_SKU + defaultSku + "&defaultCategory=" + key;
								urlList.add(url);
							}
						} else {
							urlList.add(url);
						}
					}
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (QueryException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return urlList;
	}
	
	private List<String> getCategoriesXML(){
		List<String> urlList = new ArrayList<String>();
		TaxonomyService taxonomyService = new TaxonomyService();
		List<? extends Category> allCategories=taxonomyService.getParentCategories();
		for(Category category: allCategories){
			List<? extends Category> childrens=category.getChildren();
			for(Category childrenCategory: childrens){
			 String categoryKey=childrenCategory.getKey();
			 urlList.add(Constants.CATEGORY_SITEMAP_ROOTPATH+FORWARD_SLASH+categoryKey+".xml");
			}
		}	
		return urlList;
	}

}
