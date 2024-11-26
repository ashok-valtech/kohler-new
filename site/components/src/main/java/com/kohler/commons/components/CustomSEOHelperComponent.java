package com.kohler.commons.components;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFolder;
import org.hippoecm.hst.content.beans.standard.facetnavigation.HippoFacetNavigation;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.forge.seo.support.SEOHelperComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;
import com.kohler.beans.ArticleBannerDocument;
import com.kohler.beans.DocLinks;
import com.kohler.beans.Product;
import com.kohler.commons.beans.AdCopy_Detail;
import com.kohler.commons.components.componentsInfo.CustomSEOHelperComponentParamsInfo;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.ProductDetailService;
import com.kohler.commons.util.CommonUtil;

/**
 * Custom SEO Helper class
 * 
 * @author dhanwan.r Date: 10/07/2019
 * @version 1.0
 */
@ParametersInfo(type = CustomSEOHelperComponentParamsInfo.class)
public class CustomSEOHelperComponent extends SEOHelperComponent {

	private static final String NO_INDEX = "noIndex";

	private static final String NO_FOLLOW = "noFollow";

	private static final String CANONICAL_URL = "canonicalUrl";
	private static final String PIE = " | ";

	private static final Logger LOGGER = LoggerFactory.getLogger(DocLinks.class);

	static final String SEO_COMPOUND_NODETYPE = "seosupport:seo";

	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();

	ProductDetailService productDetailService = new ProductDetailService();

	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
		super.doBeforeRender(request, response);

		CustomSEOHelperComponentParamsInfo params = getComponentParametersInfo(request);

		final HippoBean document = request.getRequestContext().getContentBean();
		if (document == null || document.getClass().equals(HippoFolder.class)
				|| document.getClass().equals(HippoFacetNavigation.class)) {
			request.setAttribute(CANONICAL_URL, params.getCanonicalUrl());
			request.setAttribute(NO_FOLLOW, params.getNoFollow());
			request.setAttribute(NO_INDEX, params.getNoIndex());
			if (document == null) {
				try {
				String url = request.getRequestContext().getServletRequest().getPathInfo();
				HstRequestContext hstRequestContext = request.getRequestContext();
				String language = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
				String country = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
				Locale locale = new Locale(language, country);
				ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
				String productDetailPageURL = bundle.getString(Constants.PRODUCTDETAILURL);
				// productDetails
				if (url!=null && url.contains(productDetailPageURL)) {
					String productItemNo = productDetailService.getProductName(url);
					if (!Strings.isNullOrEmpty(productItemNo)) {
						String title="";
						String metaDesc="";
						Product product = productDetailService.getProductByItemNo(request, productItemNo);
						String companyName = bundle.getString(Constants.COMPANY_NAME);
						if (product.getSeo()!= null ) {
							if (!Strings.isNullOrEmpty(product.getSeo().getSeoTitle())) {
								title=product.getSeo().getSeoTitle();
							} else
							{
								title=getDefaultTitle(product,companyName);
							}
							if (!Strings.isNullOrEmpty(product.getSeo().getSeoDescription())) {
								metaDesc=product.getSeo().getSeoDescription();
							} else
							{
								metaDesc=getDefaultDescription(product);
							}
							if (!Strings.isNullOrEmpty(product.getSeo().getCanonicalUrl())) {
								request.setAttribute(CANONICAL_URL, product.getSeo().getCanonicalUrl());
							}
							if (product.getSeo().getNoFollow()) {
								request.setAttribute(NO_FOLLOW, product.getSeo().getNoFollow());
							}
							if (product.getSeo().getNoIndex()) {
								request.setAttribute(NO_INDEX, product.getSeo().getNoIndex());
							}
						} 
						if(Strings.isNullOrEmpty(title)) {
							title=getDefaultTitle(product, companyName);
							metaDesc=getDefaultDescription(product);
							if (Strings.isNullOrEmpty(metaDesc)) {
								ProductDetailService productDetailService = new ProductDetailService();
								AdCopy_Detail adcopyDetail = productDetailService.formatAdCopyDetail(product);
								metaDesc = adcopyDetail.getMetaDescription();
							} 
						}
						if(Strings.isNullOrEmpty(metaDesc)) {
							metaDesc=getDefaultDescription(product);
							if (Strings.isNullOrEmpty(metaDesc)) {
								ProductDetailService productDetailService = new ProductDetailService();
								AdCopy_Detail adcopyDetail = productDetailService.formatAdCopyDetail(product);
								metaDesc = adcopyDetail.getMetaDescription();
							} 
						}
						request.setAttribute(Constants.TITLE, title);
						request.setAttribute(Constants.META_DESC, metaDesc);
					}
				}
			 }catch (Exception e) {
					LOGGER.error("Exception occurs in CustomSEOHelperComponent ".concat(e.getMessage()));
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error(e.getMessage());
					}
				}
			}
		} else {
			String canonicalUrl = "";
			String noFollow = "";
			String noIndex = "";
			if (document.getClass().equals(ArticleBannerDocument.class)) {
				canonicalUrl = getPropertyFromArticleBannerDocument(document, "kohler:canonicalUrl");
				noFollow = getPropertyFromArticleBannerDocument(document, "kohler:noFollow");
				noIndex = getPropertyFromArticleBannerDocument(document, "kohler:noIndex");
			} else {
				canonicalUrl = getPropertyFromSeoCompoundDocument(document, "seosupport:canonicalUrl");
				noFollow = getPropertyFromSeoCompoundDocument(document, "seosupport:noFollow");
				noIndex = getPropertyFromSeoCompoundDocument(document, "seosupport:noIndex");
			}
			request.setAttribute(CANONICAL_URL, canonicalUrl);
			request.setAttribute(NO_FOLLOW, Boolean.parseBoolean(noFollow));
			request.setAttribute(NO_INDEX, Boolean.parseBoolean(noIndex));
		}
	}

	/**
	 * 
	 * @param document
	 * @param property
	 * @return property value from compound document
	 */
	private String getPropertyFromSeoCompoundDocument(HippoBean document, String property) {
		try {
			NodeIterator nodes = document.getNode().getNodes();
			while (nodes.hasNext()) {
				Node node = nodes.nextNode();
				if (node.getPrimaryNodeType().getName().equals(SEO_COMPOUND_NODETYPE) && node.hasProperty(property)) {
					return node.getProperty(property).getString();
				}
			}
		} catch (RepositoryException e) {
			LOGGER.warn("Failure retrieving property {} from SEO compound of document {}", property,
					document.getName());
		}
		return null;
	}

	/**
	 * 
	 * @param document
	 * @param property
	 * @return property value from article document node
	 */
	private String getPropertyFromArticleBannerDocument(HippoBean document, String property) {
		try {
			Node node = document.getNode();
			return node.getProperty(property).getString();
		} catch (RepositoryException e) {
			LOGGER.warn("Failure retrieving property {} from SEO compound of document {}", property,
					document.getName());
		}
		return null;
	}

	private String getDefaultTitle(Product product,String companyName) {
		Map<String, String[]> productAttributes = product.getAttributes().stream()
				.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
		String shortDescription = getDefaultDescription(product);
		List<String> brandNames = productAttributes.get(Constants.BRAND_NAME) != null
				? Arrays.asList(productAttributes.get(Constants.BRAND_NAME))
				: null;
		String brandName = null;
		if (brandNames != null && brandNames.size() > 0) {
			brandName = replaceTradeMark(brandNames.get(0));
		}
		String itemNumber = product.getItemNo();
		StringBuilder defaultTitle=new StringBuilder();
		if(!Strings.isNullOrEmpty(brandName)) {
		 defaultTitle.append(brandName);
		 defaultTitle.append(" ");
	    }
		if(!Strings.isNullOrEmpty(shortDescription)) 
	    {
	      defaultTitle.append(shortDescription);
		  defaultTitle.append(PIE);
	    }
		defaultTitle.append(itemNumber);
		defaultTitle.append(PIE);
		defaultTitle.append(companyName);
		return defaultTitle.toString();
	}
	
	private String getDefaultDescription(Product product) {
		Map<String, String[]> productAttributes = product.getAttributes().stream()
				.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
		String shortDescription = productAttributes.get(Constants.DESCRIPTION_PRODUCT_SHORT) != null
				? productAttributes.get(Constants.DESCRIPTION_PRODUCT_SHORT)[0]
				: null;
		if (!Strings.isNullOrEmpty(shortDescription)) {
			shortDescription = replaceTradeMark(shortDescription);
		}
		return shortDescription;
	}
	
	private String replaceTradeMark(String replaceString) {

		if (!Strings.isNullOrEmpty(replaceString)) {
			replaceString = replaceString.replace("(R)", "");
			replaceString = replaceString.replace("(TM)", "");
			replaceString = replaceString.replace("(R)", "<sup>&reg;</sup>");
			replaceString = replaceString.replace("(TM)", "<sup>&trade;</sup>");
		}
		return replaceString;
	}
}
